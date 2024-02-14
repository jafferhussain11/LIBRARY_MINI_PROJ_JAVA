package com.example.demo.repository;

import com.example.demo.models.Book;
import com.example.demo.models.Genre;
import com.example.demo.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface StudentRepository extends JpaRepository<Student,Integer> {

    List<Student> findByName(String name);

    List<Student> findByRollNumber(String rollNumber );

    List<Student> findByEmail(String email);
}
