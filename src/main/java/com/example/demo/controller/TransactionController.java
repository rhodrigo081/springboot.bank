package com.example.demo.controller;

import com.example.demo.dto.PixKeyRequestDTO;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.model.Account;
import com.example.demo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private record Message(String message) {
    }

    @PostMapping("/deposit")
    public ResponseEntity<Message> deposit(@RequestBody Authentication authentication, BigDecimal amount) {

        Account account = (Account) authentication.getPrincipal();
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, account, null, amount);
        transactionService.toDeposit(transactionRequestDTO);

        TransactionResponseDTO depositResponse = transactionService.toDeposit(transactionRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new Message("Deposited Successfully!" + depositResponse));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Message> withdraw(@RequestBody Authentication authentication, BigDecimal amount) {
        Account account = (Account) authentication.getPrincipal();
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(account, null, null, amount);

        TransactionResponseDTO withdrawResponse = transactionService.toWithdraw(transactionRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new Message("Withdraw Successfully!" + withdrawResponse));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Message> transfer(@RequestBody Authentication authentication, String pixKey, BigDecimal amount) {
        Account accountOrigin = (Account) authentication.getPrincipal();
        Account 
    }

}
