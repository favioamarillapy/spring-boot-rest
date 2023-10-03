package com.py.springbootrest.service.impl;

import com.py.springbootrest.dto.AuthenticationResponse;
import com.py.springbootrest.dto.CustomResponse;
import com.py.springbootrest.dto.SignUpRequest;
import com.py.springbootrest.dto.SigninRequest;
import com.py.springbootrest.entity.Role;
import com.py.springbootrest.entity.UserApp;
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
    public CustomResponse<AuthenticationResponse> signup(SignUpRequest request) {
        var existUser = userRepository.findByEmail(request.getEmail()).isPresent();

        if (existUser) {
            return CustomResponse.<AuthenticationResponse>builder()
                    .message("User with email already exists")
                    .error(true)
                    .data(null)
                    .build();
        }

        var user = UserApp.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);

        var authenticationResponse = AuthenticationResponse.builder()
                .token(jwt)
                .userApp(user)
                .build();


        return CustomResponse.<AuthenticationResponse>builder()
                .message("User registered successfully")
                .error(false)
                .data(authenticationResponse)
                .build();
    }

    @Override
    public CustomResponse<AuthenticationResponse> signin(SigninRequest request) {
        var user = userRepository.findByEmail(request.getEmail())
                .orElse(null);

        if (user == null) {
            return CustomResponse.<AuthenticationResponse>builder()
                    .message("The user was not found")
                    .error(true)
                    .data(null)
                    .build();
        }

        customAuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        var jwt = jwtService.generateToken(user);

        var authenticationResponse = AuthenticationResponse.builder()
                .token(jwt)
                .userApp(user)
                .build();


        return CustomResponse.<AuthenticationResponse>builder()
                .message("User login successfully")
                .error(false)
                .data(authenticationResponse)
                .build();
    }

}
