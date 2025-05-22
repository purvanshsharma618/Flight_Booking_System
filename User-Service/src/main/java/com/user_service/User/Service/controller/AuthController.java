package com.user_service.User.Service.controller;

import com.user_service.User.Service.dto.AuthRequest;
import com.user_service.User.Service.dto.AuthResponse;
import com.user_service.User.Service.model.User;
import com.user_service.User.Service.security.CustomUserDetails;
import com.user_service.User.Service.jwt.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService service;

    public AuthController(AuthenticationManager authenticationManager, AuthService service) {
        this.authenticationManager = authenticationManager;
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            if (authentication.isAuthenticated()) {
                // ✅ Cast to UserDetails to get role
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                String token = service.generateToken(userDetails);
                return ResponseEntity.ok(new AuthResponse( "Login successful",token));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new AuthResponse( "Invalid credentials",null));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthResponse("Invalid credentials",null));
    }


    @PostMapping("/register")
    public ResponseEntity<?> addNewUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveUser(user));
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
}

