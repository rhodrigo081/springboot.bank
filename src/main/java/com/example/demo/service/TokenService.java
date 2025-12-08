package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.exception.InvalidArgumentException;
import com.example.demo.exception.InvalidCredentialsExceptions;
import com.example.demo.exception.UnauthorizedAccessException;
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
    private String secretKey;

    public String getToken(Account account) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            User user = account.getUser();

            if(user == null){
                throw new InvalidArgumentException("Account not registered.");
            }

            String token = JWT.create().withIssuer("Transactions").withSubject(user.getCpf()).withClaim("email", user.getEmail()).withClaim("role", user.getRole().name()).withExpiresAt(getExpirationDate()).sign(algorithm);

            return token;
        }catch (UnauthorizedAccessException e){
            throw new UnauthorizedAccessException("Failed to load token.");
        } catch (Exception e) {
            throw new RuntimeException("Internal error occurred.");
        }
    }

    public boolean validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWT.require(algorithm).withIssuer("Transactions").build().verify(token);

            return true;
        } catch (InvalidCredentialsExceptions e) {
            throw new InvalidArgumentException("Invalid token.");
        }  catch (Exception e) {
            throw new RuntimeException("Internal error occurred.");
        }
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm).withIssuer("Transactions").build().verify(token).getSubject();
        } catch (InvalidCredentialsExceptions e) {
            throw new InvalidArgumentException("Invalid token.");
        } catch (Exception e) {
            throw new RuntimeException("Internal error occurred.");
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
