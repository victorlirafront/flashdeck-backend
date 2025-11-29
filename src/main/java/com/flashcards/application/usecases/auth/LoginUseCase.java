package com.flashcards.application.usecases.auth;

import com.flashcards.application.dto.auth.LoginRequest;
import com.flashcards.application.dto.auth.LoginResponse;
import com.flashcards.application.mapper.UserMapper;
import com.flashcards.domain.entity.User;
import com.flashcards.domain.exception.InvalidCredentialsException;
import com.flashcards.domain.exception.UserNotFoundException;
import com.flashcards.domain.repository.UserRepository;
import com.flashcards.domain.valueobject.Email;
import com.flashcards.infrastructure.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    
    public LoginUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    public LoginResponse execute(LoginRequest request) {
        Email email = Email.of(request.email());
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.email()));
        
        if (!user.verifyPassword(request.password(), passwordEncoder)) {
            throw new InvalidCredentialsException("Invalid password");
        }
        
        String token = jwtTokenProvider.generateToken(user);
        
        return UserMapper.toLoginResponse(user, token);
    }
}

