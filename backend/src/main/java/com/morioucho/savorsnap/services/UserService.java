package com.morioucho.savorsnap.services;

import com.morioucho.savorsnap.dto.RegisterRequest;
import com.morioucho.savorsnap.entity.AppUser;
import com.morioucho.savorsnap.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AppUser> registerUser(RegisterRequest request) throws Exception {
        if(!verifyUser(request)) {
            throw new Exception();
        }

        AppUser user = new AppUser();

        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRole("USER");

        return Optional.of(user);
    }

    public Optional<AppUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean verifyUser(RegisterRequest registerRequest) {
        String username = registerRequest.username();
        String password = registerRequest.password();
        String email = registerRequest.email();

        // Check if username is taken.
        if(findByUsername(username).isPresent()) {
            return false;
        }

        // Check for empty fields.
        if(username.isBlank() ||
           password.isBlank() ||
           email.isBlank()) {
            return false;
        }

        // Improper username length.
        if(username.length() < 5) {
            return false;
        }

        // Improper password length.
        if(password.length() < 8) {
            return false;
        }

        return email.contains("@") && email.contains(".");
    }
}
