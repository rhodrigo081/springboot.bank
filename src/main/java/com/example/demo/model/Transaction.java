package com.example.demo.model;

import com.example.demo.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_origin_id")
    @JsonIgnoreProperties("transaction")
    private Account accountOrigin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_receiver_id")
    @JsonIgnoreProperties("transaction")
    private Account accountReceiver;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "transaction_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Version
    @Column(name = "version")
    private Long version;

    @PrePersist
    private void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
