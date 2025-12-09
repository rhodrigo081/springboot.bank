package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record RegisterRequestDTO(@NotBlank(message = "First name is required") String firstName,
                                 @NotBlank(message = "Last name is required") String lastName,
                                 @NotBlank(message = "CPF is required") @CPF String cpf,
                                 @NotBlank(message = "Phone is required") String phone,
                                 @NotBlank(message = "Email is required") @Email String email,
                                 LocalDate dateOfBirth,
                                 @NotBlank(message = "Password is required") String password) {
}
