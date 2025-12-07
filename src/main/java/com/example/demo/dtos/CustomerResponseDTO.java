package com.example.demo.dtos;

import java.time.LocalDate;

public record CustomerResponseDTO(String firstName, String lastName, String email, String phone, LocalDate dateOfBirth) {
}
