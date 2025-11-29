package com.flashcards.infrastructure.security;

import com.flashcards.domain.entity.User;
import com.flashcards.domain.repository.UserRepository;
import com.flashcards.domain.valueobject.Email;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }
    
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        
        String token = recoverToken(request);
        
        if (token != null) {
            String email = jwtTokenProvider.validateToken(token);
            
            if (email != null) {
                userRepository.findByEmail(Email.of(email))
                        .ifPresent(user -> {
                            var authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
                            var authentication = new UsernamePasswordAuthenticationToken(
                                    user, null, authorities);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
            }
        }
        
        filterChain.doFilter(request, response);
    }
    
    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
        return authHeader.replace("Bearer ", "");
    }
}

