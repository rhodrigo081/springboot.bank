package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record AccountStatementResponseDTO(
        UUID accountId,
        Long accountNumber,
        BigDecimal currentBalance,
        BigDecimal totalDeposits,
        BigDecimal totalWithdrawals,
        BigDecimal totalTransfersIn,
        BigDecimal totalTransfersOut,
        List<TransactionResponseDTO> transactions
) {}