package com.example.demo.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthResponse {
    private String token;
    public AuthResponse(String jwt) {
        this.token = jwt;
    }
}
