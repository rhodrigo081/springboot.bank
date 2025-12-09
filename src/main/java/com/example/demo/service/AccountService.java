package com.example.demo.service;

import com.example.demo.dto.AccountRequestDTO;
import com.example.demo.dto.AccountResponseDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.InvalidParameterException;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserService userService;

    public AccountResponseDTO convertToResponse(Account account) {
        if (account == null) {
            return null;
        }

        UserResponseDTO userResponseDTO = userService.convertToResponse(account.getUser());

        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getBalance(), userResponseDTO);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO findAccountByUserCpf(String cpf) {
        Account accountSearchedByCustomerCpf = accountRepository.findByUser_Cpf(cpf).orElseThrow(() -> new NotFoundException("Account not found"));

        return convertToResponse(accountSearchedByCustomerCpf);
    }

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {

        if (accountRequestDTO == null) {
            throw new InvalidArgumentException("Fill all fields");
        }

        User accountOwner = accountRequestDTO.user();

        if (accountRepository.findByUser_Cpf(accountOwner.getCpf()).isPresent()) {
            throw new InvalidParameterException("Account already exists");
        }

        Account account = new Account();

        Long maxNumber = accountRepository.countAccounts();
        account.setPassword(accountRequestDTO.password());
        account.setUser(accountOwner);
        account.setAccountNumber(maxNumber == null ? 1 : maxNumber + 1);
        account.setBalance(BigDecimal.ZERO);
        account.setAgency("777");

        Account savedAccount = accountRepository.save(account);

        return convertToResponse(savedAccount);
    }
}
