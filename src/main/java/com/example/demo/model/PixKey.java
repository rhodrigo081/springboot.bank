package com.example.demo.model;

import com.example.demo.enums.PixKeyType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "TB_PixKey")
public class PixKey {

    @Id
    private String key;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PixKeyType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Version
    @Column(name = "version")
    private Long version;
}
