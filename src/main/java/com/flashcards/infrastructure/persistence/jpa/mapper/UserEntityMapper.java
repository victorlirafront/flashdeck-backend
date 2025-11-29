package com.flashcards.infrastructure.persistence.jpa.mapper;

import com.flashcards.domain.entity.User;
import com.flashcards.domain.valueobject.Email;
import com.flashcards.domain.valueobject.Password;
import com.flashcards.infrastructure.persistence.jpa.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserEntityMapper {
    
    private final PasswordEncoder passwordEncoder;
    
    public UserEntityMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    
    public UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.getId());
        entity.setName(user.getName());
        entity.setEmail(user.getEmail().getValue());
        entity.setPassword(user.getPassword().getHashedValue());
        return entity;
    }
    
    public User toDomain(UserEntity entity) {
        Email email = Email.of(entity.getEmail());
        Password password = Password.fromHashed(entity.getPassword());
        return User.restore(entity.getId(), entity.getName(), email, password);
    }
}

