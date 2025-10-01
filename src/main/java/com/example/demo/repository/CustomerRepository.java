package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findCustomerByCPFContaining(String cpf);
    List<Customer> findCustomerByEmailContaining(String email);
    List<Customer> findCustomerByPhoneContaining(String phone);
    List<Customer> findCustomerByNameContaining(String name);

    Optional<Customer> findCustomerByExactCPF(String exactCPF);
    Optional<Customer> findCustomerByExactEmail(String exactEmail);
    Optional<Customer> findCustomerByExactPhone(String exactPhone);
    Optional<Customer> findCustomerByExactName(String exactName);
}
