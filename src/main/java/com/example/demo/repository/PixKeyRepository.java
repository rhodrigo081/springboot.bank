package com.example.demo.repository;

import com.example.demo.model.PixKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PixKeyRepository extends JpaRepository<PixKey, String> {

    List<PixKey> findByAccount_Id(UUID accountId);
}
