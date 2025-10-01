package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Optional<Customer> findCustomerByCPF(String cpf);
    Optional<Customer> findCustomerByEmail(String email);
    Optional<Customer> findCustomerByPhone(String phone);
}
