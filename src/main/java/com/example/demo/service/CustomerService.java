package com.example.demo.service;

import com.example.demo.dtos.CustomerRecordDto;
import com.example.demo.model.Account;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer customerRegister(CustomerRecordDto customerDTO) {
        try {
            if (customerDTO == null) {
                throw new IllegalArgumentException("Fill all fields");
            }

            Customer customerByCPF = findCustomerByExactCPF(customerDTO.cpf());
            Customer customerByEmail = findCustomerByExactEmail(customerDTO.email());
            Customer customerByPhone = findCustomerByExactPhone(customerDTO.phone());

            if (customerByCPF != null || customerByEmail != null || customerByPhone != null) {
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
    public List<Customer> findCustomerByCPFContaining(String cpf) {
        try {
            List<Customer> customers = customerRepository.findCustomerByCPFContaining(cpf);

            if (customers.isEmpty()) {
                throw new NoSuchElementException("No users found with: " + cpf);
            }

            return customers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomerByEmailContaining(String email) {
        try {
            List<Customer> customers = customerRepository.findCustomerByEmailContaining(email);

            if (customers.isEmpty()) {
                throw new NoSuchElementException("No users found with: " + email);
            }

            return customers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomerByPhoneContaining(String phone) {
        try {
            List<Customer> customers = customerRepository.findCustomerByPhoneContaining(phone);

            if (customers.isEmpty()) {
                throw new NoSuchElementException("No users found with: " + phone);
            }

            return customers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomerByNameContaining(String name) {
        try {
            List<Customer> customers = customerRepository.findCustomerByNameContaining(name);

            if (customers.isEmpty()) {
                throw new NoSuchElementException("No users found with: " + name);
            }

            return customers;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByExactCPF(String exactCPF) {
        try {
            return customerRepository.findCustomerByExactCPF(exactCPF).orElseThrow(() -> new NoSuchElementException(exactCPF));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByExactEmail(String exactEmail) {
        try {
            return customerRepository.findCustomerByExactEmail(exactEmail).orElseThrow(() -> new NoSuchElementException(exactEmail));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByExactPhone(String exactPhone) {
        try {
            return customerRepository.findCustomerByExactPhone(exactPhone).orElseThrow(() -> new NoSuchElementException(exactPhone));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByExactName(String exactName) {
        try {
            return customerRepository.findCustomerByExactName(exactName).orElseThrow(() -> new NoSuchElementException(exactName));
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
    public Customer updateCustomer(String cpf, CustomerRecordDto customerDTO) {
        try {
            Customer customer = customerRepository.findCustomerByExactCPF(cpf).orElseThrow(() -> new NoSuchElementException(cpf));

            customer.setEmail(customerDTO.email());
            customer.setPhone(customerDTO.phone());

            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
