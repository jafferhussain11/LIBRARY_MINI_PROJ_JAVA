package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchBookRequest {

    @NotBlank
    private String searchKey;
    @NotBlank
    private String searchValue;


}
