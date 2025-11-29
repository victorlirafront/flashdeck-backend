package com.flashcards.infrastructure.controller;

import com.flashcards.application.dto.auth.LoginRequest;
import com.flashcards.application.dto.auth.LoginResponse;
import com.flashcards.application.dto.auth.RegisterRequest;
import com.flashcards.application.usecases.auth.LoginUseCase;
import com.flashcards.application.usecases.auth.RegisterUserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final LoginUseCase loginUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    
    public AuthController(LoginUseCase loginUseCase, RegisterUserUseCase registerUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.registerUserUseCase = registerUserUseCase;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = loginUseCase.execute(request);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest request) {
        LoginResponse response = registerUserUseCase.execute(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

