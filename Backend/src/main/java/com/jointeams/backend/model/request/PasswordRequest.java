package com.jointeams.backend.model.request;

import lombok.Data;

@Data
public class PasswordRequest {

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