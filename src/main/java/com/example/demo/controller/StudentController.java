package com.example.demo.controller;

import com.example.demo.dto.CreateStudentRequest;
import com.example.demo.dto.SearchStudentRequest;
import com.example.demo.models.Student;
import com.example.demo.repository.StudentRepository;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    StudentService studentService;
    @PostMapping("/student")
    public void addStudent(@RequestBody @Valid CreateStudentRequest createStudentRequest){

        studentService.createStudent(createStudentRequest.toStudent());
    }

    @GetMapping("/getStudent")
    public List<Student> getStudent(@RequestBody SearchStudentRequest searchStudentRequest) throws Exception {

        return studentService.findStudent(searchStudentRequest.getSearchKey(),searchStudentRequest.getSearchValue());
    }
}
