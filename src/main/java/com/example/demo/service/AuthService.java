package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        Optional<User> userCpf = userRepository.findByCpf(login);
        if (userCpf.isPresent()) {
            return userCpf.get();
        }

        Optional<User> userEmail = userRepository.findByEmail(login);
        if (userEmail.isPresent()) {
            return userEmail.get();
        }

        throw new UsernameNotFoundException("Usuário não encontrado com o login: " + login);
    }

    @Transactional
    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        UserRequestDTO userRequestDTO = new UserRequestDTO(registerRequestDTO.firstName(), registerRequestDTO.lastName(), registerRequestDTO.cpf(), registerRequestDTO.dateOfBirth(), registerRequestDTO.email(), registerRequestDTO.phone());
        UserResponseDTO userSaved = userService.registerUser(userRequestDTO);

        User user = userRepository.findByCpf(registerRequestDTO.cpf()).orElseThrow(() -> new NotFoundException("User not found with cpf: " + registerRequestDTO.cpf()));

        String cryptPassword = passwordEncoder.encode(registerRequestDTO.password());
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO(cryptPassword, user, null);
        AccountResponseDTO accountSaved = accountService.createAccount(accountRequestDTO);

        return new RegisterResponseDTO(accountSaved, userSaved);
    }

}
