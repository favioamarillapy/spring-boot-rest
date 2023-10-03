package com.py.springbootrest.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token, HttpServletRequest request);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails, HttpServletRequest request);
}
