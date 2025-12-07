package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

import java.math.BigDecimal;

@Data
@Entity
@DiscriminatorValue("SAVING")
@EqualsAndHashCode(callSuper = true)
public class SavingAccount extends Account {
    @Column(name = "monthly_yield_rate", precision = 10, scale = 2)
    private BigDecimal monthlyYieldRate = new BigDecimal("0.01");

}
