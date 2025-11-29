package com.flashcards.infrastructure.persistence.jpa.adapter;

import com.flashcards.domain.entity.User;
import com.flashcards.domain.repository.UserRepository;
import com.flashcards.domain.valueobject.Email;
import com.flashcards.infrastructure.persistence.jpa.entity.UserEntity;
import com.flashcards.infrastructure.persistence.jpa.mapper.UserEntityMapper;
import com.flashcards.infrastructure.persistence.jpa.repository.JpaUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepository {
    
    private final JpaUserRepository jpaUserRepository;
    private final UserEntityMapper mapper;
    
    public UserRepositoryAdapter(JpaUserRepository jpaUserRepository, PasswordEncoder passwordEncoder) {
        this.jpaUserRepository = jpaUserRepository;
        this.mapper = new UserEntityMapper(passwordEncoder);
    }
    
    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaUserRepository.findByEmail(email.getValue())
                .map(mapper::toDomain);
    }
    
    @Override
    public Optional<User> findById(String id) {
        return jpaUserRepository.findById(id)
                .map(mapper::toDomain);
    }
    
    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }
    
    @Override
    public boolean existsByEmail(Email email) {
        return jpaUserRepository.existsByEmail(email.getValue());
    }
}

