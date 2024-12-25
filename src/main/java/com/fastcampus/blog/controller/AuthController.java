package com.fastcampus.blog.controller;

import com.fastcampus.blog.request.auth.LoginRequest;
import com.fastcampus.blog.response.auth.LoginResponse;
import com.fastcampus.blog.service.JwtService;
import com.fastcampus.blog.service.MyUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @PostMapping("/api/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService
                    .generateToken(myUserDetailsService.loadUserByUsername(loginRequest.getUsername()));

            return LoginResponse.builder().token(token).build();
        }

        throw new UsernameNotFoundException("invalid user");
    }
}
