package com.py.springbootrest.controller;

import com.py.springbootrest.dto.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<CustomResponse<String>> admin() {

        var response = CustomResponse.<String>builder()
                .message("Rest test admin")
                .error(false)
                .data("admin")
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<CustomResponse<String>> user() {

        var response = CustomResponse.<String>builder()
                .message("Rest test user")
                .error(false)
                .data("user")
                .build();

        return ResponseEntity.ok(response);
    }
}
