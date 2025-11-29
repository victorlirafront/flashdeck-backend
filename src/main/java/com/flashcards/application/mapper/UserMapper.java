package com.flashcards.application.mapper;

import com.flashcards.application.dto.auth.LoginResponse;
import com.flashcards.domain.entity.User;

public class UserMapper {
    
    public static LoginResponse toLoginResponse(User user, String token) {
        return new LoginResponse(user.getName(), token);
    }
}

