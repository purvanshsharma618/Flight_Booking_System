package com.user_service.User.Service.repository;

import  com.user_service.User.Service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (used for login & authentication)
    Optional<User> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Check if phone number already exists (optional)
    boolean existsByPhoneNumber(String phoneNumber);
}