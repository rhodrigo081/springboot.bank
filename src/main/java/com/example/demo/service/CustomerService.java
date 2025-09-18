package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.NoSuchElementException;
import java.util.Optional;


public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveCustomer(Customer customer) {
        if (StringUtils.isEmpty(customer.getDocument()) || StringUtils.isEmpty(customer.getPhone()) || StringUtils.isEmpty(customer.getEmail())
        || StringUtils.isEmpty(customer.getFirstName())  || StringUtils.isEmpty(customer.getLastName())) {
            throw new IllegalArgumentException("Fill all fields");
        }

        return customerRepository.save(customer);
    }

    public void associateAccount(Customer customer,Account account){

        if(customer != null && account != null){
            throw new IllegalArgumentException("");
        }


    }

    public Optional<Customer> findCustomerByDocument(String document){

            Optional<Customer> searchedCustomer = customerRepository.findCustomerByDocument(document);

            if(searchedCustomer == null ||  searchedCustomer.isEmpty()){
                throw new NoSuchElementException("Customer not found");
            }

            return searchedCustomer;
    }

}
