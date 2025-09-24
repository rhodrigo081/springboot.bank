package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
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

    @Column(name = "last_name", nullable = false, length = 60)
    protected String lastName;

    @Column(name = "cpf", nullable = false, length = 11)
    protected String cpf;

    @Column(name = "email", nullable = false, length = 60)
    protected String email;

    @Column(name = "phone", nullable = false, length = 11)
    protected String phone;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnoreProperties("customer")
    protected Account account;

    public Customer(String firstName, String lastName, String cpf, String email, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpf = cpf;
        this.email = email;
        this.phone = phone;
    }
}
