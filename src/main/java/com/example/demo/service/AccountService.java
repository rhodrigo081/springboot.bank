package com.example.demo.service;

import com.example.demo.dtos.AccountRecordDto;
import com.example.demo.model.Account;
import com.example.demo.model.CheckingAccount;
import com.example.demo.model.SavingAccount;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private void checkIfAccountExists(AccountRecordDto accountDTO) {

        Account accountByLogin = findAccountByLogin(accountDTO.login());

        if (accountByLogin != null) {
            throw new IllegalArgumentException("Account with this login already exists");
        }
    }

    @Transactional
    public Account accountRegister(AccountRecordDto accountDTO) {
        try {

            if (accountDTO == null) {
                throw new IllegalArgumentException("Fill all fields");
            }

            checkIfAccountExists(accountDTO);
            Account account = accountByType(accountDTO);

            BeanUtils.copyProperties(accountDTO, account);
            return accountRepository.save(account);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Account login(AccountRecordDto accountDTO) {
        try {
            if (accountDTO == null) {
                throw new IllegalArgumentException("Fill all fields");
            }

            Account account = findAccountByLogin(accountDTO.login());

            if (!accountDTO.password().equals(account.getPassword())) {
                throw new IllegalArgumentException("Wrong password");
            }

            return account;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Account findAccountByLogin(String login) {
        try {
            return accountRepository.findAccountByLogin(login).orElseThrow(() -> new IllegalArgumentException("Invalid login"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private Account accountByType(AccountRecordDto accountDTO) {
        return switch (accountDTO.accountType()) {
            case CHECKING -> new CheckingAccount();
            case SAVING -> new SavingAccount();
            default -> throw new IllegalArgumentException("Invalid account type");
        };
    }

}
