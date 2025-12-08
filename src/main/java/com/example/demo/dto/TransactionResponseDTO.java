package com.example.demo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(AccountResponseDTO accountOrigin, AccountResponseDTO accountReceiver,
                                     BigDecimal amount, LocalDateTime createdAt) {
}
