package com.example.demo.service;

import com.example.demo.enums.AccountType;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account save(Account account) {
        if(account.getLogin() == null || account.getPassword() == null) {

        }

        return accountRepository.save(account);
    }

}
