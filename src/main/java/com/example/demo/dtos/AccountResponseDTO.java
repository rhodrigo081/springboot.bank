package com.example.demo.dtos;

import java.math.BigDecimal;

public record AccountResponseDTO(Integer accountNumber, String agency, BigDecimal balance, CustomerResponseDTO customerResposeDTO) {
}
