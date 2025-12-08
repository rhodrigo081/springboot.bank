package com.example.demo.repository;

import com.example.demo.model.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findByUser_Cpf(String cpf);
    Optional<Account> findByUser_Email(String email);
    Optional<Account> findByPixKeys_Key(String pixKey);

    @Query("SELECT COUNT(a) FROM Account a")
    Long countAccounts();

}
