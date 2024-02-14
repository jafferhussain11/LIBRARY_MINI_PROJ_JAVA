package com.example.demo.dto;

import com.example.demo.models.TransactionType;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {

    @NotBlank
    private String StudentRollNumber;
    @NotBlank
    private TransactionType transactionType;
    @NotBlank
    private Integer adminId;
    @NotBlank
    private Integer bookId;

}
