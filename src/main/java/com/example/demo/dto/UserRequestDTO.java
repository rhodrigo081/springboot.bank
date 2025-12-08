package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequestDTO(@NotBlank(message = "First name is required") String firstName,
                             @NotBlank(message = "Last name is required") String lastName,
                             @NotBlank(message = "CPF is required") @CPF(message = "CPF invalid") String cpf,
                             @NotBlank(message = "Email is required") String email,
                             @NotBlank(message = "Phone is required") String phone) {
}
