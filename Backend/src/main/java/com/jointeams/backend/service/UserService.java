package com.jointeams.backend.service;

import com.jointeams.backend.pojo.User;

public interface UserService {
    public User findById(Long id);
    public User addNewUser(User user);
    public User findByLastName(String lastName);
    public User findByFullName(String firstName, String lastName);
    public User findByFullName2(String firstName, String lastName);
    public User findByIncompleteFullName(String firstName, String lastName);
}
