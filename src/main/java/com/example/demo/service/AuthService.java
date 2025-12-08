package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws NotFoundException {
        Optional<User> userByEmail = userRepository.findByEmail(login);
        if (userByEmail.isPresent()) {
            return userByEmail.get();
        }

        Optional<User> userByCpf = userRepository.findByCpf(login);
        if (userByCpf.isPresent()) {
            return userByCpf.get();
        }

        throw new NotFoundException("User not found with login: " + login);
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        UserRequestDTO userRequestDTO = new UserRequestDTO(registerRequestDTO.firstName(), registerRequestDTO.lastName(), registerRequestDTO.cpf(), registerRequestDTO.email(), registerRequestDTO.password());
        UserResponseDTO userSaved = userService.registerUser(userRequestDTO);

        String cryptPassword = passwordEncoder.encode(registerRequestDTO.password());
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(cryptPassword, userRequestDTO, null);
        AccountResponseDTO accountSaved = accountService.createAccount(accountRequestDTO);

        return new RegisterResponseDTO(accountSaved, userSaved);
    }

}
