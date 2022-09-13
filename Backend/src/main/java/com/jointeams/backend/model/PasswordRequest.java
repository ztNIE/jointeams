package com.jointeams.backend.model;

import lombok.Data;

@Data
public class PasswordRequest {
    private String email;
    private String newPassword;
}