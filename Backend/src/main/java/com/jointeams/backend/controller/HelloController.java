package com.jointeams.backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Welcome to jointeams";
    }

    @GetMapping("/hello/user")
    @PreAuthorize("hasRole('USER')")
    public String userHello() {
        return "User role hello page";
    }

    @GetMapping("/hello/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHello() {
        return "Admin role hello page";
    }
}
