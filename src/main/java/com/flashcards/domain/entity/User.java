package com.flashcards.domain.entity;

import com.flashcards.domain.valueobject.Email;
import com.flashcards.domain.valueobject.Password;
import java.util.Objects;
import java.util.UUID;

public class User {
    private String id;
    private String name;
    private Email email;
    private Password password;
    
    private User() {
        // Private constructor for frameworks
    }
    
    private User(String id, String name, Email email, Password password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    public static User create(String name, Email email, Password password) {
        return new User(UUID.randomUUID().toString(), name, email, password);
    }
    
    public static User restore(String id, String name, Email email, Password password) {
        return new User(id, name, email, password);
    }
    
    public void changePassword(Password newPassword) {
        this.password = newPassword;
    }
    
    public void updateName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = newName.trim();
    }
    
    public boolean verifyPassword(String plainText, org.springframework.security.crypto.password.PasswordEncoder encoder) {
        return password.matches(plainText, encoder);
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public Password getPassword() {
        return password;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

