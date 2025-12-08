package com.example.demo.service;

import com.example.demo.dto.UpdateUserRequestDTO;
import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponseDTO convertToResponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponseDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getDateOfBirth());
    }

    @Transactional
    public UserResponseDTO registerUser(UserRequestDTO userRequestDTO) {

        userRepository.findByCpf(userRequestDTO.cpf()).ifPresent(user -> {
            throw new InvalidArgumentException("Cpf already exists");
        });

        userRepository.findByEmail(userRequestDTO.email()).ifPresent(user -> {
            throw new InvalidArgumentException("Email already exists");
        });

        User userRegistered = new User();
        BeanUtils.copyProperties(userRequestDTO, userRegistered);

        userRepository.save(userRegistered);

        return convertToResponse(userRegistered);
    }

    @Transactional
    public UserResponseDTO updateUser(String cpf, UpdateUserRequestDTO updateUserRequestDTO) {
        User userByCpf = userRepository.findByCpf(cpf).orElseThrow(() -> new InvalidArgumentException("User not found"));

        if (updateUserRequestDTO.email() != null && !updateUserRequestDTO.email().isEmpty()) {

            userRepository.findByEmail(updateUserRequestDTO.email()).ifPresent(foundCustomer -> {
                if (!foundCustomer.getCpf().equals(cpf)) {
                    throw new InvalidArgumentException("User email already exists");
                }
            });
            userByCpf.setEmail(updateUserRequestDTO.email());
        }

        if(updateUserRequestDTO.phone() != null && !updateUserRequestDTO.phone().isEmpty()) {
            userRepository.findByPhone(updateUserRequestDTO.phone()).ifPresent(foundCustomer -> {
                if (!foundCustomer.getCpf().equals(cpf)) {
                    throw new InvalidArgumentException("User phone already exists");
                }
            });
            userByCpf.setPhone(updateUserRequestDTO.phone());
        }
        userRepository.save(userByCpf);


        return convertToResponse(userByCpf);
    }

    @Transactional
    public UserResponseDTO deleteUser(String cpf) {
        User deletedUser = userRepository.findByCpf(cpf).orElseThrow(() -> new InvalidArgumentException("User not found"));

        userRepository.delete(deletedUser);

        return convertToResponse(deletedUser);
    }

}
