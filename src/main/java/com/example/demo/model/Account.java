package com.example.demo.model;

import com.example.demo.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TB_Account")
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(name = "login", unique = true, nullable = false, length = 100)
    protected String login;

    @Column(name = "password", nullable = false, length = 50)
    protected String password;

    @Column(name = "type", nullable = false)
    protected AccountType type;

    @Column(name = "balance")
    protected Double balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    protected List<PixKey> pixKeys;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("account")
    protected List<Transaction> transactions;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_document")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("account")
    protected Customer customer;

    @Column(name = "date_creation" ,nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "date_update", nullable = false, updatable = false)
    protected LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
