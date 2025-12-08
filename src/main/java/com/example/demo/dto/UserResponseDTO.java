package com.example.demo.dto;

import java.time.LocalDate;

public record UserResponseDTO(String firstName, String lastName, String email, String phone, LocalDate dateOfBirth) {
}
