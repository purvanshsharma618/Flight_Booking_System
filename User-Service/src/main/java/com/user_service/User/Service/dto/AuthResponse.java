package com.user_service.User.Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class AuthResponse {
    private String message;
    //    private String role;
    private String token;

    public AuthResponse(String message,String token){
        this.message=message;
//        this.role=role;
        this.token=token;
    }
    public AuthResponse(){}
    public String getMessage() {
        return message;
    }

//    public String getRole() {
//        return role;
//    }

    public String getToken() {
        return token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public void setRole(String role) {
//        this.role = role;
//    }

    public void setToken(String token) {
        this.token = token;
    }
}