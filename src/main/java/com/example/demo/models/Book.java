package com.example.demo.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @ManyToOne  // many books can be written by one author, or many rows in the book table can have the same author_id
    @JoinColumn
    private Author author;


    @ManyToOne
    @JoinColumn
    private Student student;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "book")  // mappedBy tells the hibernate that the book field in the Transaction class is the owner of the relationship this is a bidirectional relationship
    private List<Transaction> transactions;


}
