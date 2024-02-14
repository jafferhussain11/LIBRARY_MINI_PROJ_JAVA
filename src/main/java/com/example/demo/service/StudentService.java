package com.example.demo.service;

import com.example.demo.models.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    StudentRepository studentRepository;

    public void createStudent(Student student){

        studentRepository.save(student);
    }

    public List<Student> findStudent(String searchKey,String searchValue) throws Exception {

        if(searchKey.equals("email")){

            return studentRepository.findByEmail(searchValue);
        }else if (searchKey.equals("name")){

            return studentRepository.findByName(searchValue);
        }else if(searchKey.equals("id")){

            Optional<Student> student = studentRepository.findById(Integer.parseInt(searchValue));
            if(student.isPresent()){
                return Arrays.asList(student.get());
            }else{
                return new ArrayList<>();
            }

        }else if(searchKey.equals("rollNumber")){

            return studentRepository.findByRollNumber(searchValue);
        }else{

            throw new Exception("Invalid search key");
        }
    }
}
