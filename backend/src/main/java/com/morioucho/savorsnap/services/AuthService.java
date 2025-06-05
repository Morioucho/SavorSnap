package com.morioucho.savorsnap.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public boolean authenticate(String username, String rawPassword) {
        return userService.findByUsername(username)
                .map(user -> new BCryptPasswordEncoder().matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}
