package com.example.demo.service;

import com.example.demo.dtos.TransactionRecordDto;
import com.example.demo.enums.TransactionType;
import com.example.demo.model.Account;
import com.example.demo.model.PixKey;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    private AccountService accountService;

    @Transactional
    private void validTransaction(TransactionRecordDto transactionDTO) {
        try {

            if (transactionDTO == null) {
                throw new IllegalArgumentException("Fill all fields");
            }

            Account receiver = transactionDTO.accountReceiver();
            if (receiver == null) {
                throw new IllegalArgumentException("Account not found");
            }

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Transaction toTransfer(TransactionRecordDto transactionDTO) {
        try {
            validTransaction(transactionDTO);

            Account receiver = transactionDTO.accountReceiver();

            if (transactionDTO.transactionType() != TransactionType.TRANSFER) {
                throw new IllegalArgumentException("Invalid account type");
            }

            accountService.findAccountByPixKey((PixKey) receiver.getPixKeys());

            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionDTO, transaction);

            return transactionRepository.save(transaction);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Transaction toWithdraw(TransactionRecordDto transactionDTO) {
        try {

            validTransaction(transactionDTO);
            Account receiver = transactionDTO.accountReceiver();
            if (transactionDTO.transactionType() != TransactionType.WITHDRAW) {
                throw new IllegalArgumentException("Invalid account type");
            }

            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionDTO, transaction);
            return transactionRepository.save(transaction);

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Transaction toDeposit(TransactionRecordDto transactionDTO) {
        try {
            validTransaction(transactionDTO);

            Account receiver = transactionDTO.accountReceiver();
            if (transactionDTO.transactionType() != TransactionType.DEPOSIT) {
                throw new IllegalArgumentException("Invalid account type");
            }

            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(transactionDTO, transaction);

            return transactionRepository.save(transaction);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private TransactionType transactionByType(TransactionRecordDto transactionDTO) {
        return switch (transactionDTO.transactionType()) {
            case DEPOSIT -> TransactionType.DEPOSIT;
            case WITHDRAW -> TransactionType.WITHDRAW;
            case TRANSFER -> TransactionType.TRANSFER;
            default -> throw new IllegalArgumentException("Invalid transaction type");
        };
    }

    @Transactional(readOnly = true)
    public List<Transaction> findByUserId(Long userId) {
        try {
            return transactionRepository.findByUserId(userId);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
