package com.example.demo.controller;

import com.example.demo.dto.UpdateUserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.model.User;
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

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody  UpdateUserRequestDTO updateUserRequestDTO) {
        User user = (User) authentication.getPrincipal();
        UserResponseDTO updatedUser = userService.updateUser(user.getCpf(), updateUserRequestDTO);

        return ResponseEntity.status(HttpStatus.OK).body("Updated User" + updatedUser);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(Authentication authentication) {
        String currentUserCpf = authentication.getName();
        UserResponseDTO deltedUser = userService.deleteUser(currentUserCpf);

        return ResponseEntity.status(HttpStatus.OK).body("Deleted User" + deltedUser);
    }
}
