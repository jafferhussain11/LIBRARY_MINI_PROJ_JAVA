package com.example.demo.repository;

import com.example.demo.models.Book;
import com.example.demo.models.Student;
import com.example.demo.models.Transaction;
import com.example.demo.models.TransactionType;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    //findTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc LIMIT 1
    public Transaction findFirstTransactionByStudentAndBookAndTransactionTypeOrderByIdDesc(Student student, Book book, TransactionType transactionType);
}
