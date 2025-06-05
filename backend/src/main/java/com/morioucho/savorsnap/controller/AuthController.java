package com.morioucho.savorsnap.controller;

import com.morioucho.savorsnap.dto.RegisterRequest;

import com.morioucho.savorsnap.services.UserService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully.");
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
        }
    }
}
