package com.py.springbootrest.service;

import com.py.springbootrest.dto.JwtAuthenticationResponse;
import com.py.springbootrest.dto.SignUpRequest;
import com.py.springbootrest.dto.SigninRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse signup(SignUpRequest request);

    JwtAuthenticationResponse signin(SigninRequest request);
}
