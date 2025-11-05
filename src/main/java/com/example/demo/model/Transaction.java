package com.example.demo.model;

import com.example.demo.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TB_Transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    @JsonIgnoreProperties("transaction")
    protected Account accountSender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    @JsonIgnoreProperties("transaction")
    protected Account accountReceiver;

    @Column(name = "date", nullable = false, updatable = false)
    protected LocalDateTime date;

    @Column(name = "transaction_type", nullable = false)
    protected TransactionType type;

    @Column(name = "amount", nullable = false)
    protected BigDecimal amount;

    @Version
    @Column(name = "version")
    protected Long version;

    @PrePersist
    public void prePersist() {
        this.date = LocalDateTime.now();
    }
}
