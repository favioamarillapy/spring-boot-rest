package com.py.springbootrest.service.impl;

import com.py.springbootrest.dto.AuthenticationResponse;
import com.py.springbootrest.dto.SignUpRequest;
import com.py.springbootrest.dto.SigninRequest;
import com.py.springbootrest.model.Role;
import com.py.springbootrest.model.UserApp;
import com.py.springbootrest.repository.UserRepository;
import com.py.springbootrest.security.CustomAuthenticationManager;
import com.py.springbootrest.service.AuthenticationService;
import com.py.springbootrest.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    public AuthenticationResponse signup(SignUpRequest request) {
        var user = UserApp.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .userApp(user)
                .build();
    }

    @Override
    public AuthenticationResponse signin(SigninRequest request) {
        customAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        var jwt = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwt)
                .userApp(user)
                .build();
    }

}
