package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.service.UserToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserToolServiceImpl implements UserToolService {

    @Autowired
    private UserRepository userRepository;


    public boolean checkIfUserExisted(Long id)
    {
        User user = userRepository.findById(id).orElse(null);
        if(user != null)
            return true;
        else
            return false;
    }
}
