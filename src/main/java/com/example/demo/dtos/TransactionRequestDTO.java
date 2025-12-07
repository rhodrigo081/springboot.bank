package com.example.demo.dtos;

import com.example.demo.enums.TransactionType;
import com.example.demo.model.Account;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TransactionRequestDTO(Account accountOrigin, Account accountReceiver,
                                    @NotBlank(message = "Type of transaction is required") TransactionType type,
                                    BigDecimal amount) {
}
