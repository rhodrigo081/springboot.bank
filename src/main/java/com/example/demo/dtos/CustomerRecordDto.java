package com.example.demo.dtos;

import com.example.demo.validation.CPF;
import jakarta.validation.constraints.NotBlank;

public record CustomerRecordDto(@NotBlank(message = "Full is required") String fullName,
                                @NotBlank(message = "Date of birth is required") String dateBirth,
                                @NotBlank(message = "CPF is required") @CPF(message = "CPF invalid") String cpf,
                                @NotBlank(message = "Email is required") String email,
                                @NotBlank(message = "Phone is required") String phone) {
}
