package com.jointeams.backend.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class PasswordRequest {
    @NotEmpty(message = "Email is required")
    private String email;

    private String newPassword;
}