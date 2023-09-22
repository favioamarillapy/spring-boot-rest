package com.py.springbootrest.service;

import com.py.springbootrest.dto.AuthenticationResponse;
import com.py.springbootrest.dto.CustomResponse;
import com.py.springbootrest.dto.SignUpRequest;
import com.py.springbootrest.dto.SigninRequest;

public interface AuthenticationService {
    CustomResponse<AuthenticationResponse> signup(SignUpRequest request);

    CustomResponse<AuthenticationResponse> signin(SigninRequest request);
}
