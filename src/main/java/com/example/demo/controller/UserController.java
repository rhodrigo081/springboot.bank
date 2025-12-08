package com.example.demo.controller;

import com.example.demo.dto.UpdateUserRequestDTO;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private record Message(String message){}


    @PutMapping("/update")
    public ResponseEntity<Message> updateUser(@RequestBody Authentication authentication, UpdateUserRequestDTO updateUserRequestDTO) {
        String currentUserCpf = authentication.getName();
        UserResponseDTO updatedUser = userService.updateUser(currentUserCpf, updateUserRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body( new Message("Updated User" + updatedUser));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Message> deleteUser(@RequestBody Authentication authentication) {
        String currentUserCpf = authentication.getName();
        UserResponseDTO deltedUser = userService.deleteUser(currentUserCpf);

        return ResponseEntity.status(HttpStatus.OK).body( new Message("Deleted User" + deltedUser));
    }
}
