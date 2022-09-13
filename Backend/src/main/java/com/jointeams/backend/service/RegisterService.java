package com.jointeams.backend.service;

import com.jointeams.backend.model.PasswordRequest;
import com.jointeams.backend.model.RegisterUserRequest;
import com.jointeams.backend.pojo.User;
import org.json.simple.JSONObject;

public interface RegisterService {
    JSONObject registerUser (RegisterUserRequest registerUserRequest);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    User deleteOldVerifyToken(String token);

    User findUserByEmail(String email);

    void savePasswordToken(User user, String token);

    String validatePasswordToken(String token);

    User deleteOldPasswordToken(String token);

    void savePassword(User user, PasswordRequest passwordRequest);

    String isUserModelValid(RegisterUserRequest registerUserRequest);
}
