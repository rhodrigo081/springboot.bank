package com.example.demo.dto;

import com.example.demo.model.User;
import com.example.demo.model.PixKey;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AccountRequestDTO(@NotBlank(message = "Password is required") String password, User user,
                                List<PixKey> pixKeys) {
}
