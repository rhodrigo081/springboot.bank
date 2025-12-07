package com.example.demo.dtos;

import com.example.demo.model.Customer;
import com.example.demo.model.PixKey;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record AccountRequestDTO(@NotBlank(message = "Password is required") String password, Customer customer,
                                List<PixKey> pixKeys) {
}
