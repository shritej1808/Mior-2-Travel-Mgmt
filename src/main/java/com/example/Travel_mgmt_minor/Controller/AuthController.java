package com.example.Travel_mgmt_minor.Controller;


import com.example.Travel_mgmt_minor.dto.RegisterRequest;
import com.example.Travel_mgmt_minor.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @GetMapping("/user/role")
    public ResponseEntity<String> getRole(Authentication auth) {
        return ResponseEntity.ok(auth.getAuthorities().iterator().next().getAuthority());
    }
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("username", authentication.getName());
        userInfo.put("roles", authentication.getAuthorities());
        return ResponseEntity.ok(userInfo);
    }


}
