package com.example.demo.controller;

import com.example.demo.dto.AmountRequestDTO;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.dto.TransferRequestDTO;
import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
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

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(Authentication authentication, @RequestBody AmountRequestDTO amountRequestDTO) {

        User user = (User) authentication.getPrincipal();
        Account account = user.getAccount();

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, account.getUser().getCpf(), null, amountRequestDTO.amount());

        TransactionResponseDTO depositResponse = transactionService.toDeposit(transactionRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Deposited Successfully!" + depositResponse);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(Authentication authentication, @RequestBody AmountRequestDTO amountRequestDTO) {

        User user = (User) authentication.getPrincipal();
        Account account = user.getAccount();

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(account.getUser().getCpf(), null, null, amountRequestDTO.amount());

        TransactionResponseDTO withdrawResponse = transactionService.toWithdraw(transactionRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Withdraw Successfully!" + withdrawResponse);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(Authentication authentication, @RequestBody TransferRequestDTO transferRequestDTO) {
        User user = (User) authentication.getPrincipal();
        Account accountOrigin = user.getAccount();
        Account accountReceiver = accountRepository.findByPixKeys_Key(transferRequestDTO.pixKey()).orElseThrow(() -> new RuntimeException("PIX key not found"));

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(accountOrigin.getUser().getCpf(), accountReceiver.getUser().getCpf(), null, transferRequestDTO.amount());

        TransactionResponseDTO transferResponse = transactionService.toTransfer(transactionRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Transfer Successfully!" + transferResponse);
    }

}
