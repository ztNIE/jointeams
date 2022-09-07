package com.jointeams.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private String firstName;
    private String lastName;
    private String degree;
    private String email;
    private String faculty;
    private String filename;
    private Long universityId;
    private String password;
    private String matchingPassword;
    private boolean isAdmin = false;
    private boolean isActive = false;
}
