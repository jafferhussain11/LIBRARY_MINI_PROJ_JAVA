package com.example.demo.dto;

import com.example.demo.models.Admin;
import com.example.demo.models.Student;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CreateStudentRequest {

    @NotBlank
    private String rollNumber;

    @NotBlank
    private String email;
    @NotBlank
    private String name;

    public Student toStudent(){

        return Student.builder()
                .email(email)
                .name(name)
                .rollNumber(rollNumber)
                .build();
    }
}
