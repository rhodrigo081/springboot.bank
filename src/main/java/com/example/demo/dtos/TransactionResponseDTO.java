package com.example.demo.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(AccountResponseDTO accountReceiver, BigDecimal amount, LocalDateTime createdAt) {
}
