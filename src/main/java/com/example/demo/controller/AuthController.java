package com.example.demo.controller;

import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.dto.RegisterResponseDTO;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import com.example.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    private record Message(String message) {
    }

    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody RegisterRequestDTO registerRequestDTO) {

        try {
            RegisterResponseDTO registerResponseDTO = authService.register(registerRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(new Message("Registered Successfully!\n" + registerResponseDTO));
        } catch (Exception e) {
            if (e.getMessage().contains("Duplicate entry") || e.getMessage().contains("Unique")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Message("Error: Email or Cpf already registered!"));
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("Error: " + e.getMessage()));
        }

    }

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            var authenticationToken = new UsernamePasswordAuthenticationToken(loginRequestDTO.login(), loginRequestDTO.password());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            User currentUser = (User) authentication.getPrincipal();

            String token = tokenService.getToken(currentUser.getAccount());

            return ResponseEntity.status(HttpStatus.OK).body(new Message("Logged in Successfully!\n" + token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Message("Error: Invalid Credentials." ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Message("Error: " + e.getMessage()));
        }
    }


}
