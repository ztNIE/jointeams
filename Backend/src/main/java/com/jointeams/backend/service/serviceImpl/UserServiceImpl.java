package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.SHAUtils;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

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
    public User findByLastName(String lastName) {
        User user = userRepository.findByLastName(lastName).orElse(null);
        return user;

    }
    @Override
    public User findByFullName(String firstName, String lastName)
    {
        User user = userRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);
        return user;
    }

    @Override
    public User findByFullName2(String firstName, String lastName)
    {
        User user = userRepository.findByFullName(firstName, lastName).orElse(null);
        return user;
    }

    @Override
    public User findByIncompleteFullName(String firstName, String lastName)
    {
        User user = userRepository.findByIncompleteFullName(firstName, lastName).orElse(null);
        return user;
    }

//    @Override
//    public User verifyUserIdentityByEncryptedPassword(Long userId, String encryptedPassword) {
//        User user = findById(userId);
//        if(user != null)
//        {
//            try {
//                String passwordSHA1 = SHAUtils.sha1(user.getPassword());
//                if(passwordSHA1.equals(encryptedPassword))
//                    return user;// userId and encryptedPassword are matched
//                else
//                    return null;// userId and encryptedPassword are NOT matched
//            } catch (NoSuchAlgorithmException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        else
//            return null; // 1: no user is found
//    }



    @Override
    public User addNewUser(User user) {
        return userRepository.save(user);
    }
}

