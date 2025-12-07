package com.example.demo.dtos;

import com.example.demo.enums.PixKeyType;
import com.example.demo.model.Account;

public record PixKeyRequestDTO(PixKeyType type, String key, Account account) {
}
