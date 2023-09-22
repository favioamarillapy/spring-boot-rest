package com.py.springbootrest.service;

import com.py.springbootrest.dto.AuthenticationResponse;
import com.py.springbootrest.dto.SignUpRequest;
import com.py.springbootrest.dto.SigninRequest;

public interface AuthenticationService {
    AuthenticationResponse signup(SignUpRequest request);

    AuthenticationResponse signin(SigninRequest request);
}
