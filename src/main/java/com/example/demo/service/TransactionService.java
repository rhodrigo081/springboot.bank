package com.example.demo.service;

import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private AccountService accountService;

}
