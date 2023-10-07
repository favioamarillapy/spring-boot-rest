package com.py.springbootrest.security;

import com.py.springbootrest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
 * Class to validate user login
 * */
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        if (username == null || username.isEmpty()) {
            throw new BadCredentialsException("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            throw new BadCredentialsException("Password cannot be empty");
        }

        return getUser(username, password);
    }


    private Authentication getUser(String username, String password) {
        var user = userRepository.findByEmail(username)
                .orElse(null);

        if (user != null) {
            if (user.getRole() == null) {
                throw new BadCredentialsException("The user does not have a role");
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Incorrect credentials");
            }

            List<GrantedAuthority> roleList = new ArrayList<>();
            roleList.add(new SimpleGrantedAuthority(user.getRole().name()));


            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user, password, roleList);

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(usernamePasswordAuthenticationToken);
            SecurityContextHolder.setContext(context);

            return usernamePasswordAuthenticationToken;
        } else {
            throw new BadCredentialsException("The user was not found");
        }
    }


}
