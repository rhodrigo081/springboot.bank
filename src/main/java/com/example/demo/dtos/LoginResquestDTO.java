package com.example.demo.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginResquestDTO(@NotBlank(message = "Login is required") String login, @NotBlank(message = "Password is required") String password) {
}
