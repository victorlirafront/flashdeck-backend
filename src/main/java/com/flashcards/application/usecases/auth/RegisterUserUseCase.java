package com.flashcards.application.usecases.auth;

import com.flashcards.application.dto.auth.RegisterRequest;
import com.flashcards.application.dto.auth.LoginResponse;
import com.flashcards.application.mapper.UserMapper;
import com.flashcards.domain.entity.User;
import com.flashcards.domain.exception.UserAlreadyExistsException;
import com.flashcards.domain.repository.UserRepository;
import com.flashcards.domain.valueobject.Email;
import com.flashcards.domain.valueobject.Password;
import com.flashcards.infrastructure.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserUseCase {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    
    public RegisterUserUseCase(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    
    public LoginResponse execute(RegisterRequest request) {
        Email email = Email.of(request.email());
        
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.email());
        }
        
        Password password = Password.fromPlainText(request.password(), passwordEncoder);
        User user = User.create(request.name(), email, password);
        
        User savedUser = userRepository.save(user);
        
        String token = jwtTokenProvider.generateToken(savedUser);
        
        return UserMapper.toLoginResponse(savedUser, token);
    }
}

