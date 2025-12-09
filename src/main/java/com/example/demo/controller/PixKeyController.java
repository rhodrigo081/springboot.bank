package com.example.demo.controller;

import com.example.demo.dto.PixKeyResponseDTO;
import com.example.demo.model.Account;
import com.example.demo.model.User;
import com.example.demo.service.PixKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pixkey")
public class PixKeyController {

    @Autowired
    private PixKeyService pixKeyService;

    @GetMapping("/list")
    public ResponseEntity<List<PixKeyResponseDTO>> generateAllPixKeys(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Account account = user.getAccount();

        List<PixKeyResponseDTO> pixKeys = pixKeyService.findPixKeysUserId(account);

        return ResponseEntity.status(HttpStatus.OK).body(pixKeys);
    }

    @PostMapping("/phone")
    public ResponseEntity<?> generatePixKeyPhone(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Account account = user.getAccount();

        pixKeyService.generatePixKeyPhone(account);
        List<PixKeyResponseDTO> pixKeys = pixKeyService.findPixKeysUserId(account);

        return ResponseEntity.status(HttpStatus.OK).body("Your Pix Keys: " + pixKeys);
    }

    @PostMapping("/cpf")
    public ResponseEntity<?> generatePixKeyCpf(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Account account = user.getAccount();

        pixKeyService.generatePixKeyCpf(account);
        List<PixKeyResponseDTO> pixKeys = pixKeyService.findPixKeysUserId(account);

        return ResponseEntity.status(HttpStatus.OK).body("Your Pix Keys: " + pixKeys);
    }

    @PostMapping("/email")
    public ResponseEntity<?> generatePixKeyEmail(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Account account = user.getAccount();

        pixKeyService.generatePixKeyEmail(account);
        List<PixKeyResponseDTO> pixKeys = pixKeyService.findPixKeysUserId(account);

        return ResponseEntity.status(HttpStatus.OK).body("Your Pix Keys: " + pixKeys);
    }

    @PostMapping("/random")
    public ResponseEntity<?> generatePixKeyRandom(Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Account account = user.getAccount();

        pixKeyService.generateRandomKey(account);
        List<PixKeyResponseDTO> pixKeys = pixKeyService.findPixKeysUserId(account);

        return ResponseEntity.status(HttpStatus.OK).body("Your Pix Keys: " + pixKeys);
    }

}
