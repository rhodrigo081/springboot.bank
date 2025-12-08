package com.example.demo.dto;

import java.math.BigDecimal;

public record AccountResponseDTO(Long accountNumber, String agency, BigDecimal balance, UserResponseDTO userResponseDTO) {
}
