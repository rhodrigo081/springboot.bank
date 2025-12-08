package com.example.demo.service;

import com.example.demo.dto.PixKeyResponseDTO;
import com.example.demo.enums.PixKeyType;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.PixKey;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PixKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class PixKeyService {

    @Autowired
    private PixKeyRepository pixKeyRepository;

    @Autowired
    private AccountRepository accountRepository;

    public PixKeyResponseDTO convertToResponse(PixKey pixKey) {
        if (pixKey == null) {
            return null;
        }

        return new PixKeyResponseDTO(pixKey.getKey());
    }

    @Transactional
    public PixKeyResponseDTO createPixKeyPhone(String cpf) {

        Account accountSearchedByCustomerCpf = accountRepository.findByUser_Cpf(cpf).orElseThrow(() -> new NotFoundException("Account not found"));

        PixKey pixKeyPhone = new PixKey();
        String accountPhone = accountSearchedByCustomerCpf.getUser().getPhone();
        List<PixKey> pixKeys = accountSearchedByCustomerCpf.getPixKeys();

        pixKeyPhone.setKey(accountPhone);
        pixKeyPhone.setAccount(accountSearchedByCustomerCpf);
        pixKeyPhone.setType(PixKeyType.PHONE);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyPhone);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyPhone);

        return convertToResponse(pixKeySaved);
    }

    @Transactional
    public PixKeyResponseDTO createPixKeyEmail(String cpf) {

        Account accountSearchedByCustomerCpf = accountRepository.findByUser_Cpf(cpf).orElseThrow(() -> new NotFoundException("Account not found"));

        PixKey pixKeyEmail = new PixKey();
        String accountEmail = accountSearchedByCustomerCpf.getUser().getEmail();
        List<PixKey> pixKeys = accountSearchedByCustomerCpf.getPixKeys();

        pixKeyEmail.setKey(accountEmail);
        pixKeyEmail.setAccount(accountSearchedByCustomerCpf);
        pixKeyEmail.setType(PixKeyType.EMAIL);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyEmail);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyEmail);

        return convertToResponse(pixKeySaved);
    }

    @Transactional
    public PixKeyResponseDTO createPixKeyCpf(String cpf) {

        Account accountSearchedByCustomerCpf = accountRepository.findByUser_Cpf(cpf).orElseThrow(() -> new NotFoundException("Account not found"));

        PixKey pixKeyCpf = new PixKey();
        List<PixKey> pixKeys = accountSearchedByCustomerCpf.getPixKeys();

        pixKeyCpf.setKey(cpf);
        pixKeyCpf.setAccount(accountSearchedByCustomerCpf);
        pixKeyCpf.setType(PixKeyType.CPF);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyCpf);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyCpf);


        return convertToResponse(pixKeySaved);
    }

    @Transactional
    public PixKeyResponseDTO generateRandoKey(String cpf) {

        Account accountSearchedByCustomerCpf = accountRepository.findByUser_Cpf(cpf).orElseThrow(() -> new NotFoundException("Account not found"));

        PixKey randomPixKey = new PixKey();
        String randomKey = UUID.randomUUID().toString();
        List<PixKey> pixKeys = accountSearchedByCustomerCpf.getPixKeys();

        randomPixKey.setAccount(accountSearchedByCustomerCpf);
        randomPixKey.setKey(randomKey);
        randomPixKey.setType(PixKeyType.RANDOM);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }
        pixKeys.add(randomPixKey);

        PixKey randomPixKeySaved = pixKeyRepository.save(randomPixKey);
        accountSearchedByCustomerCpf.setPixKeys(pixKeys);
        accountRepository.save(accountSearchedByCustomerCpf);


        return convertToResponse(randomPixKeySaved);
    }
}
