package com.example.demo.dtos;

import com.example.demo.model.Account;
import jakarta.validation.constraints.NotBlank;

public record TransactionRecordDto(@NotBlank(message = "Receiver is required") Account accountReceiver, @NotBlank(message = "Amount is required") Double amount) {
}
