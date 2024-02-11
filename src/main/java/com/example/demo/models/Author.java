package com.example.demo.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(unique = true,nullable = false)
    private String email;

    @CreationTimestamp
    private Date createdOn;

    @OneToMany(mappedBy = "author") //will fetch all the books written by the author or the foreign key is in the book table
    private List<Book> books;
}
