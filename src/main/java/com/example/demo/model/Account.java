package com.example.demo.model;

import com.example.demo.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TB_Account")
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(name = "login", unique = true, nullable = false, length = 100)
    protected String login;

    @Column(name = "password", nullable = false, length = 255)
    protected String password;

    @Column(name = "type", nullable = false)
    protected AccountType type;

    @Column(name = "balance", precision = 19, scale = 2)
    @DecimalMin(value = "0.0")
    protected BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    protected List<PixKey> pixKeys;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties("account")
    protected List<Transaction> transactions;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_document", referencedColumnName = "document")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("account")
    protected Customer customer;

    @Column(name = "date_creation" ,nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "date_update", nullable = false)
    protected LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    protected Long version;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Account(String login, String password, AccountType type, BigDecimal balance, List<PixKey> pixKeys, List<Transaction> transactions, Customer customer) {
        this.login = login;
        this.password = password;
        this.type = type;
        this.balance = balance;
        this.pixKeys = pixKeys;
        this.transactions = transactions;
        this.customer = customer;
    }
}
