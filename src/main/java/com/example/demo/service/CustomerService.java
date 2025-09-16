package com.example.demo.service;

import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomerService{

    @Autowired
    private CustomerRepository customerRepository;
}
