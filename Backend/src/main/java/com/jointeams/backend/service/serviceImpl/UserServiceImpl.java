package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;

    }

    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }
}
