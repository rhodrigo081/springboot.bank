package com.example.demo.controller;

import com.example.demo.dto.AccountResponseDTO;
import com.example.demo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<?> accountDetails(Authentication authentication) {
        String currentUserCpf =  authentication.getName();

        AccountResponseDTO currentAccount = accountService.findAccountByUserCpf(currentUserCpf);
        System.out.println("Current Account : " + currentAccount);

        return ResponseEntity.status(HttpStatus.OK).body(currentAccount);
    }

}
