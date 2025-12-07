package com.example.demo.repository;

import com.example.demo.model.Account;

import com.example.demo.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByCustomer_Cpf(String cpf);
    Optional<Account> findByCustomer_Email(String email);
    Optional<Account> findByPixKeys_Key(String pixKey);

}
