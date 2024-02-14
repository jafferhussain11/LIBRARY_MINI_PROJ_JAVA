package com.example.demo.service;

import com.example.demo.dto.CreateTransactionRequest;
import com.example.demo.models.*;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public String initiateTransaction(CreateTransactionRequest createTransactionRequest) {
        // initiate the transaction

        if(createTransactionRequest.getTransactionType().equals("ISSUE")){
            //issue the book

        }
        else if(createTransactionRequest.getTransactionType().equals("RETURN")){
            //return the book
        }


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
        if(student.getBooks().size()>maxBooksAllowed){
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



    }
}
