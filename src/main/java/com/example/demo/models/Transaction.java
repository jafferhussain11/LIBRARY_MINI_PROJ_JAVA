package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String txId;

    private TransactionType transactionType;

    private TransactionStatus transactionStatus;

    @JoinColumn
    @ManyToOne
    private Book book;  // MAny rows in Transaction table can belong to one book

    @JoinColumn
    @ManyToOne
    private Student student;

}
