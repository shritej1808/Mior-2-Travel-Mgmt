package com.example.Travel_mgmt_minor.service;

import com.example.Travel_mgmt_minor.Entity.Authority;
import com.example.Travel_mgmt_minor.Entity.User;
import com.example.Travel_mgmt_minor.Repository.AuthorityRepository;
import com.example.Travel_mgmt_minor.Repository.UserRepository;
import com.example.Travel_mgmt_minor.dto.RegisterRequest;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       AuthorityRepository authorityRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());


        String role = request.getRole();  // <--- get role here

        Authority authority = authorityRepository.findByAuthority(role)
                .orElseThrow(() -> new RuntimeException("Role not found: " + role));


        user.addAuthority(authority);
        userRepository.save(user);
    }
}
