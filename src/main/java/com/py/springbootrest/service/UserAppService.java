package com.py.springbootrest.service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAppService {
    UserDetailsService userDetailsService();
}
