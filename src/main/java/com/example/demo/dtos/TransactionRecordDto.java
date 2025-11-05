package com.example.demo.dtos;

import com.example.demo.enums.TransactionType;
import com.example.demo.model.Account;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record TransactionRecordDto(@NotBlank Account accountSender, @NotBlank TransactionType transactionType,
                                   @NotBlank(message = "Receiver is required") Account accountReceiver,
                                   @NotBlank(message = "Amount is required") BigDecimal amount) {
}
