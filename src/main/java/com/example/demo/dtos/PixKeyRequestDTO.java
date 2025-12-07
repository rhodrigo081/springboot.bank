package com.example.demo.dtos;

import com.example.demo.enums.PixKeyType;
import jakarta.validation.constraints.NotBlank;

public record PixKeyRequestDTO(@NotBlank(message = "Choose pix key type") PixKeyType pixKeyType, String pixKey) {
}
