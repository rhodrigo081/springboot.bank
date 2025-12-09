package com.example.demo.dto;

import java.math.BigDecimal;

public record TransferRequestDTO(String pixKey, BigDecimal amount) {
}
