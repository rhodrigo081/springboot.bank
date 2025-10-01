package com.example.demo.service;

import com.example.demo.dtos.CustomerRecordDto;
import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer customerRegister(CustomerRecordDto customerDTO) {
        try {
            if(customerDTO == null){
                throw new IllegalArgumentException("Fill all fields");
            }

            Customer customerByCPF = findCustomerByCPF(customerDTO.cpf());
            Customer customerByEmail = findCustomerByEmail(customerDTO.email());
            Customer customerByPhone = findCustomerByPhone(customerDTO.phone());

            if(customerByCPF != null ||  customerByEmail != null || customerByPhone != null){
                throw new IllegalArgumentException("User Already Exists");
            }

            Customer customer = new Customer();

            BeanUtils.copyProperties(customerDTO, customer);

            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByCPF(String cpf) {
        try {
            return customerRepository.findCustomerByCPF(cpf).orElseThrow(() -> new NoSuchElementException(cpf));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerById(UUID id) {
        try {
            return customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id.toString()));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByEmail(String email) {
        try{
            return customerRepository.findCustomerByEmail(email).orElseThrow(() -> new NoSuchElementException(email));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByPhone(String phone) {
        try{
            return customerRepository.findCustomerByPhone(phone).orElseThrow(() -> new NoSuchElementException(phone));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Customer accountAssociate(Account account, Customer customer) {
        try {
            customer.setAccount(account);
            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Customer updateCustomer(UUID id, CustomerRecordDto customerDTO) {
        try{
            Customer customer = customerRepository.findById(id).orElseThrow(() -> new NoSuchElementException(id.toString()));

            customer.setEmail(customerDTO.email());
            customer.setPhone(customerDTO.phone());

            return customerRepository.save(customer);
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }
}
