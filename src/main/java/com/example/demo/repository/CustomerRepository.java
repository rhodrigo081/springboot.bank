package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByDocument(String document);
    Customer findCustomerByEmail(String email);
    Customer findCustomerByPhone(String phone);
}
