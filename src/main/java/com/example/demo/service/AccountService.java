package com.example.demo.service;

import com.example.demo.dtos.AccountRequestDTO;
import com.example.demo.dtos.AccountResponseDTO;
import com.example.demo.dtos.CustomerResponseDTO;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.Customer;
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
    private CustomerService customerService;

    public AccountResponseDTO convertToResponse(Account account) {
        if (account == null) {
            return null;
        }

        CustomerResponseDTO customerResponseDTO = customerService.convertToResponse(account.getCustomer());

        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getBalance(), customerResponseDTO);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO findAccountByCustomerCpf(String cpf) {
        Account accountSearchedByCustomerCpf = accountRepository.findByCustomer_Cpf(cpf).orElseThrow(() -> new NotFoundException("Account not found"));

        return convertToResponse(accountSearchedByCustomerCpf);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO findAccountByCustomerEmail(String email) {
        Account accountSearchedByCustomerEmail = accountRepository.findByCustomer_Email(email).orElseThrow(() -> new NotFoundException("Account not found"));

        return convertToResponse(accountSearchedByCustomerEmail);
    }

    @Transactional(readOnly = true)
    public AccountResponseDTO findAccountByPixKey(String pixKey) {
        Account accountSearchedByPixKey = accountRepository.findByPixKeys_Key(pixKey).orElseThrow(() -> new NotFoundException("Account not found"));

        return convertToResponse(accountSearchedByPixKey);
    }

    @Transactional
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO, Customer accountOwner) {

        if (accountRequestDTO == null) {
            throw new InvalidArgumentException("Fill all fields");
        }

        if (accountRepository.findByCustomer_Cpf(accountOwner.getCpf()).isPresent()) {
            throw new InvalidParameterException("Account already exists");
        }

        Account account = new Account();

        account.setPassword(accountRequestDTO.password());
        account.setCustomer(accountOwner);
        account.setBalance(BigDecimal.ZERO);
        account.setAgency("777");

        Account savedAccount = accountRepository.save(account);

        return convertToResponse(savedAccount);
    }
}
