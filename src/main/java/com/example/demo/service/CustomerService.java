package com.example.demo.service;

import com.example.demo.dtos.CustomerRequestDTO;
import com.example.demo.dtos.CustomerResponseDTO;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponseDTO convertToResponse(Customer customer) {
        if (customer == null) {
            return null;
        }

        return new CustomerResponseDTO(customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone(), customer.getDateOfBirth().toLocalDate());
    }

    @Transactional
    public Customer saveCustomer(CustomerRequestDTO customerRequestDTO) {
        customerRepository.findByCpf(customerRequestDTO.cpf()).ifPresent(customer -> {
            throw new InvalidArgumentException("Customer already exists");
        });

        customerRepository.findByEmail(customerRequestDTO.email()).ifPresent(customer -> {
            throw new InvalidArgumentException("Customer already exists");
        });

        Customer customerRegistered = new Customer();
        BeanUtils.copyProperties(customerRequestDTO, customerRegistered);

        customerRepository.save(customerRegistered);

        return customerRegistered;
    }

    @Transactional
    public CustomerResponseDTO updateCustomer(UUID id, CustomerRequestDTO customerRequestDTO) {
        Customer customerById = customerRepository.findById(id).orElseThrow(() -> new InvalidArgumentException("Customer not found"));

        if (customerRequestDTO.email() != null && !customerRequestDTO.email().isEmpty()) {

            customerRepository.findByEmail(customerRequestDTO.email()).ifPresent(foundCustomer -> {
                if (!foundCustomer.getId().equals(id)) {
                    throw new InvalidArgumentException("Customer email already exists");
                }
            });
            customerById.setEmail(customerRequestDTO.email());
        }

        if(customerRequestDTO.firstName() != null && !customerRequestDTO.firstName().isEmpty()) {
            customerById.setFirstName(customerRequestDTO.firstName());
        }

        if (customerRequestDTO.lastName() != null && !customerRequestDTO.lastName().isEmpty()) {
            customerById.setLastName(customerRequestDTO.lastName());
        }

        if(customerRequestDTO.phone() != null && !customerRequestDTO.phone().isEmpty()) {
            customerRepository.findByPhone(customerRequestDTO.phone()).ifPresent(foundCustomer -> {
                if (!foundCustomer.getId().equals(id)) {
                    throw new InvalidArgumentException("Customer phone already exists");
                }
            });
            customerById.setPhone(customerRequestDTO.phone());
        }

        Customer updatedCustomer = saveCustomer(customerRequestDTO);

        return convertToResponse(updatedCustomer);
    }

    @Transactional
    public CustomerResponseDTO deleteCustomer(UUID id) {
        Customer deletedCustomer = customerRepository.findById(id).orElseThrow(() -> new InvalidArgumentException("Customer not found"));

        customerRepository.delete(deletedCustomer);

        return convertToResponse(deletedCustomer);
    }

}
