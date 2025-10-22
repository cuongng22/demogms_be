package com.example.demo.controller;

import com.example.demo.request.AuthRequest;
import com.example.demo.response.AuthResponse;
import com.example.demo.service.MyUserDetailsService;
import com.example.demo.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {
    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/authenticate", produces = "application/json")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) throws Exception {

        // 1. Xác thực người dùng (username/password)
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Nếu sai username/password, ném exception
            throw new Exception("Incorrect username or password", e);
        }

        // 2. Nếu xác thực thành công, tải UserDetails
        final UserDetails userDetails = myUserDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        // 3. Tạo JWT token
        final String jwt = jwtUtil.generateToken(userDetails);

        // 4. Trả về token cho client
        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @GetMapping(value = "/hello", produces = "application/json")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World" );
    }
}
