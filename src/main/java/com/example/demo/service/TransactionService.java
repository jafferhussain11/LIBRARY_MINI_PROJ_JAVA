package com.example.demo.service;

import com.example.demo.dto.CreateTransactionRequest;
import com.example.demo.models.*;
import com.example.demo.repository.TransactionRepository;
import org.hibernate.query.spi.Limit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class TransactionService {


    @Autowired
    private StudentService studentService;

    @Autowired
    private BookService bookService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AdminService adminService;

    @Value("${max.books.allowed}")
    private int maxBooksAllowed;

    @Value("${max.days.allowed}")
    private int maxDaysAllowed;

    public String initiateTransaction(CreateTransactionRequest createTransactionRequest) throws Exception {
        // initiate the transaction

//        if(createTransactionRequest.getTransactionType()== TransactionType.ISSUE){
//            //issue the book
//
//            return issueBook(createTransactionRequest);
//
//
//
//        }
//        else if(createTransactionRequest.getTransactionType() == TransactionType.RETURN){
//            //return the book
//            return issueBook(createTransactionRequest);
//        }
//
//        return null;

        return createTransactionRequest.getTransactionType() == TransactionType.RETURN
                ? returnBook(createTransactionRequest)
                : issueBook(createTransactionRequest);
    }

    public String issueBook(CreateTransactionRequest createTransactionRequest) throws Exception {
        // fetch the student and check if student exists

        List<Student> studentList = studentService.findStudent("rollNumber",createTransactionRequest.getStudentRollNumber());
        Student student = !studentList.isEmpty() ? studentList.get(0) : null;

        List<Book> bookList = bookService.findBook("id", String.valueOf(createTransactionRequest.getBookId()));
        Book book = !bookList.isEmpty() ? bookList.get(0) : null;

        Admin admin = adminService.findAdminById(createTransactionRequest.getAdminId());


        if ((student == null) ) {
            throw new Exception("Student  not found");
        }

        if ((book == null) ) {
            throw new Exception("Book not found");
        }

        if ((admin == null) ) {
            throw new Exception("Admin not found");
        }

        //if book exists and is not assigned to another student then assign to current student

        if(book.getStudent()!=null) {

            throw new Exception("Book already assigned to another student" + book.getStudent().getRollNumber());
        }
        if(student.getBooks().size()>=maxBooksAllowed){
            throw new Exception("Student has already been issued 5 books, which is the maximum limit");
        }

        Transaction transaction = null;

        try{
            transaction = Transaction.builder()
                    .txId(UUID.randomUUID().toString())
                    .book(book)
                    .admin(admin)
                    .student(student)
                    .transactionType(TransactionType.ISSUE)
                    .transactionStatus(TransactionStatus.PENDING)
                    .build();

            //save the transaction as pending
            transactionRepository.save(transaction);

            //assign the book to the student

            book.setStudent(student);
            bookService.createOrUpdateBook(book);

            transaction.setTransactionStatus(TransactionStatus.SUCCESS);


        } catch (Exception e) {
            transaction.setTransactionStatus(TransactionStatus.FAIL);
            book.setStudent(null);

        }  finally {
            transactionRepository.save(transaction);
        }

        return transaction.getTxId();

    }

    public String returnBook(CreateTransactionRequest createTransactionRequest) throws Exception {



        List<Student> studentList = studentService.findStudent("rollNumber",createTransactionRequest.getStudentRollNumber());
        Student student = !studentList.isEmpty() ? studentList.get(0) : null;

        List<Book> bookList = bookService.findBook("id", String.valueOf(createTransactionRequest.getBookId()));
        Book book = !bookList.isEmpty() ? bookList.get(0) : null;

        Admin admin = adminService.findAdminById(createTransactionRequest.getAdminId());

        if ((student == null) ) {
            throw new Exception("Student  not found");
        }

        if ((book == null) ) {
            throw new Exception("Book not found");
        }

        if ((admin == null) ) {
            throw new Exception("Admin not found");
        }


        //check if book is assigned to student

        if(book.getStudent()==null || !Objects.equals(book.getStudent().getId(), student.getId()))//if not same student
        {
            throw new Exception("This book is assigned to some other Student OR not assigned");
        }

        //fetch issuance transaction ,we need this for fine calculation,find latest ISSUED transaction of the student
        //as we are returning the book, we need to find the latest issued transaction of the student
        Transaction latestIssuedTransaction = transactionRepository.findFirstTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(student,book,TransactionType.ISSUE);
        if(latestIssuedTransaction == null){

            throw new Exception("This Book has not been issued to Anyone");
        }
        Transaction transaction = null;
        try {
                Integer fine = fineCalculator(latestIssuedTransaction.getCreatedOn());

                transaction = Transaction.builder()
                        .book(book)
                        .student(student)
                        .transactionType(createTransactionRequest.getTransactionType())
                        .fine(fine)
                        .admin(admin)
                        .transactionStatus(TransactionStatus.PENDING)
                        .txId(UUID.randomUUID().toString())
                        .build();

                transactionRepository.save(transaction);

                if(fine==0){

                    book.setStudent(null);
                    bookService.createOrUpdateBook(book);
                    transaction.setTransactionStatus(TransactionStatus.SUCCESS);

                }
        }catch (Exception e){

                transaction.setTransactionStatus(TransactionStatus.FAIL);
        }finally {
            transactionRepository.save(transaction);
        }

        return transaction.getTxId();
    }

    public Integer fineCalculator(Date dateOfIssue) {

        long fine = 0;
        long numberOfDays = TimeUnit.DAYS.convert(System.currentTimeMillis() - dateOfIssue.getTime(), TimeUnit.MILLISECONDS);
        if (numberOfDays > maxDaysAllowed){
            fine = 2 * (numberOfDays - maxDaysAllowed);
        }
        return (int) fine;


    }


}
