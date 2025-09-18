package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "TB_CheckingAccount")
@PrimaryKeyJoinColumn(name = "account_id")
public class CheckingAccount extends Account {
}
