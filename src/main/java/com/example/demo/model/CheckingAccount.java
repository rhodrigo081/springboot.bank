package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@Entity
@DiscriminatorValue("CHECKING")
@EqualsAndHashCode(callSuper = true)
public class CheckingAccount extends Account {

    @Column(name = "daily_limit", precision = 10, scale = 2)
    private BigDecimal dailyLimit = new BigDecimal("5000.00");

}
