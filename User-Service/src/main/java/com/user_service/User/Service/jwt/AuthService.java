package com.user_service.User.Service.jwt;

import com.user_service.User.Service.model.User;
import com.user_service.User.Service.repository.UserRepository;
import com.user_service.User.Service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    public String saveUser(User credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
        repository.save(credential);
        return "user added to the system";
    }

    public String generateToken(CustomUserDetails userDetails) {
        return jwtService.generateToken(userDetails);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }
}
