package com.jointeams.backend.service;

import com.jointeams.backend.pojo.User;

public interface UserService {
    public User findById(Long id);
    public User addNewUser(User user);
}
