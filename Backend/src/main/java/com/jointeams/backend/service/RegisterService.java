package com.jointeams.backend.service;

import com.jointeams.backend.model.UserModel;
import com.jointeams.backend.pojo.User;

public interface RegisterService {
    User registerUser (UserModel userModel);
}
