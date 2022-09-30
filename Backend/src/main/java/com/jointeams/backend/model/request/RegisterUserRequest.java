package com.jointeams.backend.model.request;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegisterUserRequest {

    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    private String lastName;

    private String degree;
    @NotEmpty(message = "Email is required")
    private String email;

    private String faculty;
    private String filename;

    @NotNull(message = "University is required")
    private Long universityId;

    @NotEmpty(message = "password is required")
    private String password;

    private boolean isAdmin = false;
    private boolean isActive = false;
}
