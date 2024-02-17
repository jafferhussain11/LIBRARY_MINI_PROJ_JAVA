package com.example.demo.dto;

import com.example.demo.models.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionRequest {

    @NotNull
    private String studentRollNumber;
    @NotNull
    private TransactionType transactionType;
    @NotNull
    private Integer adminId;
    @NotNull
    private Integer bookId;

}
