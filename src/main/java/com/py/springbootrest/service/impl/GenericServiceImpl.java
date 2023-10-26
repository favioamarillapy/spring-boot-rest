package com.py.springbootrest.service.impl;

import com.py.springbootrest.entity.UserApp;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GenericServiceImpl {

    public String getUserName() {
        UserApp userApp = (UserApp) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userApp.getUsername();
    }
}
