package com.example.demo.repository;

import com.example.demo.model.Account;

import com.example.demo.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Account findAccountByLogin(String login);
    Account findAccountByPixKey(PixKey pixKey);

}
