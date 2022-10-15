package com.jointeams.backend.service;

import com.jointeams.backend.model.request.PasswordRequest;
import com.jointeams.backend.model.request.RegisterUserRequest;
import com.jointeams.backend.pojo.User;

public interface RegisterService {
    User registerUser (RegisterUserRequest registerUserRequest);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    User deleteOldVerifyToken(String token);

    User findUserByEmail(String email);

    void savePasswordToken(User user, String token);

    String validatePasswordToken(String token);

    User deleteOldPasswordToken(String token);

    void savePassword(User user, PasswordRequest passwordRequest);

    String isUserModelValid(RegisterUserRequest registerUserRequest);

    Boolean postReCaptchaToken(String token, String action);
}
