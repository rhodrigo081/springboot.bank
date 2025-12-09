package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.exception.InvalidCredentialsExceptions;
import com.example.demo.model.Account;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String getToken(Account account) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            User usuario = account.getUser();

            if (usuario == null) {
                throw new InvalidArgumentException("Account user is null");
            }

            return JWT.create().withIssuer("Transactions").withSubject(usuario.getCpf()).withClaim("email", usuario.getEmail()).withClaim("role", usuario.getRole().name()).withExpiresAt(getExpirationDate()).sign(algorithm);
        } catch (InvalidArgumentException e) {
            throw new InvalidArgumentException("Failed on data process");
        } catch (Exception e) {
            throw new RuntimeException("Internal error");
        }
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            JWT.require(algorithm).withIssuer("Transactions").build().verify(token);

            return true;
        } catch (InvalidCredentialsExceptions e) {
            throw new InvalidArgumentException("Validate token failed");
        } catch (Exception e) {
            throw new RuntimeException("Internal error");
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm).withIssuer("Transactions").build().verify(token).getSubject();
        } catch (InvalidCredentialsExceptions e) {
            throw new InvalidArgumentException("Validate token failed");
        } catch (Exception e) {
            throw new RuntimeException("Internal error");
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}