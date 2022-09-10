package com.jointeams.backend.service;

import com.jointeams.backend.model.PasswordModel;
import com.jointeams.backend.model.RegisterUserModel;
import com.jointeams.backend.pojo.User;

public interface RegisterService {
    User registerUser (RegisterUserModel registerUserModel);

    void saveVerificationTokenForUser(String token, User user);

    String validateVerificationToken(String token);

    User deleteOldVerifyToken(String token);

    User findUserByEmail(String email);

    void savePasswordToken(User user, String token);

    String validatePasswordToken(String token);

    User deleteOldPasswordToken(String token);

    void savePassword(User user, PasswordModel passwordModel);

    String isUserModelValid(RegisterUserModel registerUserModel);
}
