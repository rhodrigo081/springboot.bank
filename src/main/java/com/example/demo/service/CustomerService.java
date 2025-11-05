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

    private void checkIfCustomerExists(CustomerRecordDto customerDTO) {
        Customer customerByCPF = findCustomerByCPF(customerDTO.cpf());
        Customer customerByEmail = findCustomerByEmail(customerDTO.email());
        Customer customerByPhone = findCustomerByPhone(customerDTO.phone());

        if (customerByCPF != null) {
            throw new IllegalArgumentException("Customer with CPF already exists");
        }
        if (customerByEmail != null) {
            throw new IllegalArgumentException("Customer with Email already exists");
        }
        if (customerByPhone != null) {
            throw new IllegalArgumentException("Customer with Phone already exists");
        }

    }

    @Transactional
    public Customer customerCreate(CustomerRecordDto customerDTO) {
        try {
            if (customerDTO == null) {
                throw new IllegalArgumentException("Fill all fields");
            }

            checkIfCustomerExists(customerDTO);

            Customer customer = new Customer();
            BeanUtils.copyProperties(customerDTO, customer);

            return customerRepository.save(customer);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomerByCPFContaining(String cpf) {
        try {
            List<Customer> customers = customerRepository.findCustomerByCpfContaining(cpf);

            if (customers.isEmpty()) {
                throw new NoSuchElementException("No users found with: " + cpf);
            }

            return customers;
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public List<Customer> findCustomerByNameContaining(String name) {
        try {
            List<Customer> customers = customerRepository.findCustomerByFullNameContaining(name);

            if (customers.isEmpty()) {
                throw new NoSuchElementException("No users found with: " + name);
            }

            return customers;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByCPF(String cpf) {
        try {
            Customer searchedCustomer = customerRepository.findCustomerByCpf(cpf);

            if (searchedCustomer == null) {
                throw new NoSuchElementException("No users found with this cpf");
            }

            return searchedCustomer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByEmail(String email) {
        try {

            Customer searchedCustomer = customerRepository.findCustomerByEmail(email);

            if (searchedCustomer == null) {
                throw new NoSuchElementException("No users found with this email");
            }

            return searchedCustomer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByPhone(String phone) {
        try {
            Customer searchedCustomer = customerRepository.findCustomerByPhone(phone);

            if (searchedCustomer == null) {
                throw new NoSuchElementException("No users found with this phone");
            }

            return searchedCustomer;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(readOnly = true)
    public Customer findCustomerByName(String name) {
        try {

            Customer searchedCustomer = customerRepository.findCustomerByFullName(name);

            if (searchedCustomer == null) {
                throw new NoSuchElementException("No users found with this name");
            }

            return searchedCustomer;

        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Customer accountAssociate(Account account, Customer customer) {
        try {
            customer.setAccount(account);
            return customerRepository.save(customer);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Customer updateCustomer(String cpf, CustomerRecordDto customerDTO) {
        try {
            Customer customer = customerRepository.findCustomerByCpf(cpf);

            customer.setEmail(customerDTO.email());
            customer.setPhone(customerDTO.phone());

            return customerRepository.save(customer);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
