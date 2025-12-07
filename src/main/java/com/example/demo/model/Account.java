package com.example.demo.model;

import com.example.demo.enums.AccountType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Table(name = "TB_Account")
public  class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer accountNumber;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name= "agency", nullable = false)
    private String agency = "123";

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    @Column(name = "balance", precision = 19, scale = 2)
    @DecimalMin(value = "0.0")
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PixKey> pixKeys;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnoreProperties("account")
    private List<Transaction> transactions;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_document", referencedColumnName = "document")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties("account")
    private Customer customer;

    @Column(name = "date_creation" ,nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "date_update", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
