package com.example.demo.controller;

import com.example.demo.dto.CreateStudentRequest;
import com.example.demo.dto.CreateTransactionRequest;
import com.example.demo.service.BookService;
import com.example.demo.service.StudentService;
import com.example.demo.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;



    @PostMapping("/initiateTransaction")
    public void initiateTransaction(@RequestBody @Valid CreateTransactionRequest createTransactionRequest){

        transactionService.initiateTransaction(createTransactionRequest);
    }
}
