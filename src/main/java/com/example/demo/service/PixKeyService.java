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

    @Transactional(readOnly = true)
    public List<PixKeyResponseDTO> findPixKeysUserId(Account account) {

        List<PixKey> pixKeys = pixKeyRepository.findByAccount_Id(account.getId());

        return pixKeys.stream().map(this::convertToResponse).toList();
    }

    @Transactional
    public PixKeyResponseDTO generatePixKeyPhone(Account account) {

        Account accountById = accountRepository.findById(account.getId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        PixKey pixKeyPhone = new PixKey();
        List<PixKey> pixKeys = accountById.getPixKeys();

        pixKeyPhone.setKey(accountById.getUser().getPhone());
        pixKeyPhone.setAccount(accountById);
        pixKeyPhone.setType(PixKeyType.PHONE);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyPhone);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyPhone);

        return convertToResponse(pixKeySaved);
    }

    @Transactional
    public PixKeyResponseDTO generatePixKeyEmail(Account account) {

        Account accountById = accountRepository.findById(account.getId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        PixKey pixKeyEmail = new PixKey();
        List<PixKey> pixKeys = accountById.getPixKeys();

        pixKeyEmail.setKey(accountById.getUser().getEmail());
        pixKeyEmail.setAccount(accountById);
        pixKeyEmail.setType(PixKeyType.EMAIL);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyEmail);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyEmail);

        return convertToResponse(pixKeySaved);
    }

    @Transactional
    public PixKeyResponseDTO generatePixKeyCpf(Account account) {

        Account accountById = accountRepository.findById(account.getId())
                .orElseThrow(() -> new NotFoundException("Account not found"));

        PixKey pixKeyCpf = new PixKey();
        List<PixKey> pixKeys = accountById.getPixKeys();

        pixKeyCpf.setKey(accountById.getUser().getCpf());
        pixKeyCpf.setAccount(accountById);
        pixKeyCpf.setType(PixKeyType.EMAIL);

        if (pixKeys.size() >= 5) {
            throw new InvalidArgumentException("Account has reached the maximum limit of Pix Keys");
        }

        pixKeys.add(pixKeyCpf);
        PixKey pixKeySaved = pixKeyRepository.save(pixKeyCpf);

        return convertToResponse(pixKeySaved);
    }


    @Transactional
    public PixKeyResponseDTO generateRandomKey(Account account) {

        Account accountById = accountRepository.findById(account.getId()).orElseThrow(() -> new NotFoundException("Account not found"));

        PixKey randomPixKey = new PixKey();
        String randomKey = UUID.randomUUID().toString();
        List<PixKey> pixKeys = accountById.getPixKeys();

        randomPixKey.setAccount(accountById);
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
