package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findCustomerByCpfContaining(String cpf);

    List<Customer> findCustomerByEmailContaining(String email);

    List<Customer> findCustomerByPhoneContaining(String phone);

    List<Customer> findCustomerByFullNameContaining(String fullName);

    Customer findCustomerByCpf(String cpf);

    Customer findCustomerByEmail(String email);

    Customer findCustomerByPhone(String phone);

    Customer findCustomerByFullName(String fullName);
}
