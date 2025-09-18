package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TB_Customer")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;

    @Column(name = "first_name", nullable = false, length = 60)
    protected String firstName;

    @Column(name = "last_name",nullable = false, length = 60)
    protected String lastName;

    @Column(name = "document", nullable = false, length = 14)
    protected String document;

    @Column(name = "email", nullable = false, length = 60)
    protected String email;

    @Column(name = "phone", nullable = false, length = 11)
    protected String phone;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("customer")
    protected Account account;
}
