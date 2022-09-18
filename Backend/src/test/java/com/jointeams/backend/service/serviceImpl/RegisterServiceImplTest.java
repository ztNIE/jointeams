package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.token.VerificationToken;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.repositery.VerificationTokenRepository;
import com.jointeams.backend.service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterServiceImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    RegisterService registerService;

    @Test
    void saveVerificationTokenForUser() {
        User user = new User();
        user.setEmail("test@gmail.com");
        user = userRepository.save(user);
        System.out.println(user);
        String token = UUID.randomUUID().toString();
        registerService.saveVerificationTokenForUser(token, user);
    }

    @Test
    void validateVerificationToken() {
    }

    @Test
    void deleteOldVerifyToken() {
    }
}