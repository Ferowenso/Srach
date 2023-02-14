package com.example.backend.controller;

import com.example.backend.dto.JwtResponse;
import com.example.backend.dto.LoginRequest;
import com.example.backend.model.User;
import com.example.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtResponse> signIn(@RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(authService.signIn(loginRequest), HttpStatus.OK);
    }

}
