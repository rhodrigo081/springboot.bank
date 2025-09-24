package com.example.demo.dtos;

import com.example.demo.model.PixKey;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AccountRecordDto(@NotBlank(message = "Login is required") String login,
                               @NotBlank(message = "Password is required") String password,
                               List<PixKey> pixKeys) {
}
