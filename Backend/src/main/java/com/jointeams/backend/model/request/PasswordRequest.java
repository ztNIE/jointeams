package com.jointeams.backend.model.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordRequest {
    @NotEmpty(message = "Email is required")
    private String email;
    private String newPassword;

//    public PasswordRequest(String email) {
//        this.email = email;
//    }
//
//    public PasswordRequest(String email, String newPassword) {
//        this.email = email;
//        this.newPassword = newPassword;
//    }
}