package com.example.demo.service;

import com.example.demo.dtos.PixKeyResponseDTO;
import com.example.demo.enums.PixKeyType;
import com.example.demo.exception.InvalidArgumentException;
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
    public PixKeyResponseDTO createPixKeyPhone(Account account) {

        PixKey pixKeyPhone = new PixKey();
        String accountPhone = account.getCustomer().getPhone();
        List<PixKey> pixKeys = account.getPixKeys();

        pixKeyPhone.setKey(accountPhone);
        pixKeyPhone.setAccount(account);
        pixKeyPhone.setType(PixKeyType.PHONE);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyPhone);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyPhone);

        return convertToResponse(pixKeySaved);
    }

    @Transactional
    public PixKeyResponseDTO createPixKeyEmail(Account account) {

        PixKey pixKeyEmail = new PixKey();
        String accountEmail = account.getCustomer().getEmail();
        List<PixKey> pixKeys = account.getPixKeys();

        pixKeyEmail.setKey(accountEmail);
        pixKeyEmail.setAccount(account);
        pixKeyEmail.setType(PixKeyType.EMAIL);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyEmail);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyEmail);

        return convertToResponse(pixKeySaved);
    }

    @Transactional
    public PixKeyResponseDTO createPixKeyCpf(Account account) {

        PixKey pixKeyCpf = new PixKey();
        String accountCpf = account.getCustomer().getCpf();
        List<PixKey> pixKeys = account.getPixKeys();

        pixKeyCpf.setKey(accountCpf);
        pixKeyCpf.setAccount(account);
        pixKeyCpf.setType(PixKeyType.CPF);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyCpf);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyCpf);


        return convertToResponse(pixKeySaved);
    }

    @Transactional
    public PixKeyResponseDTO generateRandoKey(Account account) {

        PixKey randomPixKey = new PixKey();
        String randomKey = UUID.randomUUID().toString();
        List<PixKey> pixKeys = account.getPixKeys();

        randomPixKey.setAccount(account);
        randomPixKey.setKey(randomKey);
        randomPixKey.setType(PixKeyType.RANDOM);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }
        pixKeys.add(randomPixKey);

        PixKey randomPixKeySaved = pixKeyRepository.save(randomPixKey);


        return convertToResponse(randomPixKeySaved);
    }
}
