package com.example.demo.service;

import com.example.demo.dto.AccountResponseDTO;
import com.example.demo.dto.AccountStatementResponseDTO;
import com.example.demo.dto.TransactionRequestDTO;
import com.example.demo.dto.TransactionResponseDTO;
import com.example.demo.enums.TransactionType;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.PixKey;
import com.example.demo.model.Transaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.TransactionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private Transaction saveTransaction(TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO == null) {
            return null;
        }

        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(transactionRequestDTO, transaction);

        transactionRepository.save(transaction);

        return transaction;
    }

    private TransactionResponseDTO convertToResponse(Transaction transaction) {
        if (transaction == null) {
            return null;
        }

        AccountResponseDTO accountSender = accountService.convertToResponse(transaction.getAccountOrigin());
        AccountResponseDTO accountReceiver = accountService.convertToResponse(transaction.getAccountReceiver());

        return new TransactionResponseDTO(accountSender, accountReceiver, transaction.getAmount(), transaction.getCreatedAt());
    }


    @Transactional(readOnly = true)
    public AccountStatementResponseDTO getAccountStatement(UUID accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NotFoundException("Account not found"));

        List<Transaction> sentTransactions = transactionRepository.findAllByAccountOrigin_Id(accountId);

        List<Transaction> receivedTransactions = transactionRepository.findAllByAccountReceiver_Id(accountId);

        List<Transaction> allTransactions = new ArrayList<>();
        allTransactions.addAll(sentTransactions);
        allTransactions.addAll(receivedTransactions);

        allTransactions = allTransactions.stream().distinct().sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt())).collect(Collectors.toList());

        BigDecimal totalDeposits = BigDecimal.ZERO;
        BigDecimal totalWithdrawals = BigDecimal.ZERO;
        BigDecimal totalTransfersIn = BigDecimal.ZERO;
        BigDecimal totalTransfersOut = BigDecimal.ZERO;

        for (Transaction transaction : allTransactions) {
            switch (transaction.getType()) {
                case DEPOSIT:
                    totalDeposits = totalDeposits.add(transaction.getAmount());
                    break;
                case WITHDRAW:
                    totalWithdrawals = totalWithdrawals.add(transaction.getAmount());
                    break;
                case TRANSFER:
                    if (transaction.getAccountOrigin() != null && transaction.getAccountOrigin().getId().equals(accountId)) {
                        totalTransfersOut = totalTransfersOut.add(transaction.getAmount());
                    } else if (transaction.getAccountReceiver() != null && transaction.getAccountReceiver().getId().equals(accountId)) {
                        totalTransfersIn = totalTransfersIn.add(transaction.getAmount());
                    }
                    break;
            }
        }

        List<TransactionResponseDTO> transactionDTOs = allTransactions.stream().map(this::convertToResponse).collect(Collectors.toList());

        return new AccountStatementResponseDTO(account.getId(), account.getAccountNumber(), account.getBalance(), totalDeposits, totalWithdrawals, totalTransfersIn, totalTransfersOut, transactionDTOs);
    }

    @Transactional
    public TransactionResponseDTO toDeposit(TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Amount must be greater than zero");
        }

        Account accountReceiver = accountRepository.findByUser_Cpf(transactionRequestDTO.accountReceiver()).orElseThrow(() -> new NotFoundException("Account not found"));

        BigDecimal newBalance = accountReceiver.getBalance().add(transactionRequestDTO.amount());
        accountReceiver.setBalance(newBalance);
        accountRepository.save(accountReceiver);

        TransactionRequestDTO depositRequest = new TransactionRequestDTO(null, transactionRequestDTO.accountReceiver(), TransactionType.DEPOSIT, transactionRequestDTO.amount());
        Transaction savedTransaction = saveTransaction(depositRequest);

        return convertToResponse(savedTransaction);
    }

    @Transactional
    public TransactionResponseDTO toWithdraw(TransactionRequestDTO transactionRequestDTO) {
        if (transactionRequestDTO.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Amount must be greater than zero");
        }

        Account accountOrigin = accountRepository.findByUser_Cpf(transactionRequestDTO.accountOrigin()).orElseThrow(() -> new NotFoundException("Account not found"));
        BigDecimal newBalance = accountOrigin.getBalance().subtract(transactionRequestDTO.amount());

        accountOrigin.setBalance(newBalance);
        accountRepository.save(accountOrigin);

        TransactionRequestDTO withdrawRequest = new TransactionRequestDTO(transactionRequestDTO.accountOrigin(), null, TransactionType.WITHDRAW, transactionRequestDTO.amount());
        Transaction savedTransaction = saveTransaction(withdrawRequest);

        return convertToResponse(savedTransaction);
    }

    @Transactional
    public TransactionResponseDTO toTransfer(TransactionRequestDTO transactionRequestDTO) {

        if (transactionRequestDTO.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException("Amount must be greater than zero");
        }

        Account accountOrigin = accountRepository.findByUser_Cpf(transactionRequestDTO.accountOrigin()).orElseThrow(() -> new NotFoundException("Account not found"));
        Account accountReceiver = accountRepository.findByPixKeys_Key(transactionRequestDTO.accountReceiver()).orElseThrow(() -> new NotFoundException("Account not found"));

        List<String> pixKeys = accountReceiver.getPixKeys().stream().map(PixKey::getKey).toList();

        if (accountOrigin.getBalance().compareTo(transactionRequestDTO.amount()) < 0) {
            throw new InvalidArgumentException("Insufficient balance");
        }

        if (accountOrigin.getUser().getCpf().equals(accountReceiver.getUser().getCpf())) {
            throw new InvalidArgumentException("Is not possible to transfer to the same account.");
        }

        accountOrigin.setBalance(accountOrigin.getBalance().subtract(transactionRequestDTO.amount()));
        accountRepository.save(accountOrigin);

        accountReceiver.setBalance(accountReceiver.getBalance().add(transactionRequestDTO.amount()));
        accountRepository.save(accountReceiver);

        TransactionRequestDTO transferRequest = new TransactionRequestDTO(transactionRequestDTO.accountOrigin(), transactionRequestDTO.accountReceiver(), TransactionType.TRANSFER, transactionRequestDTO.amount());
        Transaction savedTransaction = saveTransaction(transferRequest);

        return convertToResponse(savedTransaction);
    }
}
