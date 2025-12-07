package com.example.demo.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record RegisterRequestDTO(@NotBlank(message = "First name is required") String firstName,
                                 @NotBlank(message = "Last name is required") String lastName,
                                 @NotBlank(message = "CPF is required") @CPF String cpf,
                                 @NotBlank(message = "Email is required") @Email String email,
                                 @NotBlank(message = "Date of birth is required")LocalDate dateOfBirth,
                                 @NotBlank(message = "Password is required") String password) {
}
