package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchStudentRequest {

    @NotBlank
    private String searchKey;
    @NotBlank
    private String searchValue;


}