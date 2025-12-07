package com.example.demo.service;

import com.example.demo.dtos.AccountRequestDTO;
import com.example.demo.dtos.AccountResponseDTO;
import com.example.demo.dtos.CustomerResponseDTO;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerService customerService;

    public Account convertToModel(AccountRequestDTO accountRequestDTO) {
        if(accountRequestDTO == null) {
            return null;
        }
        Account account = new Account();
        BeanUtils.copyProperties(accountRequestDTO, account);

        return account;
    }

    public AccountResponseDTO convertToResponse(Account account) {
        if(account == null) {
            return null;
        }

        CustomerResponseDTO customerResponseDTO = customerService.convertToResponse(account.getCustomer());

        return new AccountResponseDTO(account.getAccountNumber(), account.getAgency(), account.getBalance(), customerResponseDTO);
    }

}
