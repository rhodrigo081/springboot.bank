package com.example.demo.model;

import com.example.demo.enums.AccountType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(unique = true, nullable = false)
    protected String login;

    @Column(nullable = false)
    protected String password;

    @Column(nullable = false)
    protected AccountType type;

    protected Double balance;

    @Column(unique = true)
    protected List<Transaction> transactions;
}
