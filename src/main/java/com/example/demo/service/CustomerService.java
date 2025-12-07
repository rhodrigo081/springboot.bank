package com.example.demo.service;

import com.example.demo.dtos.CustomerRequestDTO;
import com.example.demo.dtos.CustomerResponseDTO;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer convertToModel(CustomerRequestDTO customerRequestDTO) {
        if (customerRequestDTO == null) {
            return null;
        }

        Customer customer = new Customer();
        BeanUtils.copyProperties(customerRequestDTO, customer);
        return customer;
    }

    public CustomerResponseDTO convertToResponse(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new CustomerResponseDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateOfBirth().toLocalDate());
    }



}
