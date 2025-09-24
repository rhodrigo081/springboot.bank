package com.example.demo.model;


import com.example.demo.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_Saving_Account")
@PrimaryKeyJoinColumn(name = "account_id")
public class SavingAccount extends Account {
    @Column(name = "monthly_yield_rate", precision = 10, scale = 2)
    protected BigDecimal monthlyYieldRate = new BigDecimal("0.01");

    public SavingAccount(String login, String password, AccountType type, BigDecimal balance, List<PixKey> pixKeys, List<Transaction> transactions, Customer customer, BigDecimal monthlyYieldRate) {
        super(login, password, type, balance, pixKeys, transactions, customer);
        this.monthlyYieldRate = monthlyYieldRate;
    }
}
