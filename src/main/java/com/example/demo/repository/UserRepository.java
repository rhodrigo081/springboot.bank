package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @EntityGraph(attributePaths = {"account"})
    Optional<User> findByEmail(String email);

    @EntityGraph(attributePaths = {"account"})
    Optional<User> findByCpf(String cpf);

    Optional<User> findByPhone(String phone);
}
