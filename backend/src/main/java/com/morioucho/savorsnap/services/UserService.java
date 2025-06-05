package com.morioucho.savorsnap.services;

import com.morioucho.savorsnap.dto.RegisterRequest;
import com.morioucho.savorsnap.entity.AppUser;
import com.morioucho.savorsnap.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AppUser> registerUser(RegisterRequest request) throws Exception {
        if(!verifyUser(request)) {
            throw new Exception("Invalid username, password, or email.");
        }

        AppUser user = new AppUser();

        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEmail(request.email());
        user.setRole("USER");

        userRepository.save(user);

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
            log.warn("Username {} is already taken.", username);
            return false;
        }

        // Check for empty fields.
        if(username.isBlank() ||
           password.isBlank() ||
           email.isBlank()) {
            log.warn("Username, password, or email is blank.");
            return false;
        }

        // Improper username length.
        if(username.length() < 5) {
            log.warn("Username ({}) must be at least 5 characters long.", username);
            return false;
        }

        // Improper password length.
        if(password.length() < 8) {
            log.warn("Password must be at least 8 characters long.");
            return false;
        }

        if(!email.contains("@") || !email.contains(".")) {
            log.warn("Email ({}) must contain '@' and '.' characters.", email);
            return false;
        }

        return true;
    }
}
