package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.token.VerificationToken;
import com.jointeams.backend.repositery.PasswordTokenRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.repositery.VerificationTokenRepository;
import com.jointeams.backend.service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterServiceImplTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Autowired
    RegisterService registerService;

    @Test
    void saveVerificationTokenForUserAndValidate() {
        User user = new User();
        user.setEmail("test@gmail.com");
        userRepository.save(user);
        String token = "testVerifyToken";
        registerService.saveVerificationTokenForUser(token, user);
        assertEquals("testVerifyToken", verificationTokenRepository.findByToken(token).getToken());
        registerService.validateVerificationToken("testVerifyToken");
        assertTrue(Objects.requireNonNull(userRepository.findByEmail(user.getEmail()).orElse(null)).isActivate());
        assertNull(verificationTokenRepository.findByToken(token));
        userRepository.delete(user);
    }

    @Test
    void savePasswordTokenAndDelete() {
        User user = new User();
        user.setEmail("passwordToken@gmail.com");
        userRepository.save(user);
        String token = "testPasswordToken";
        registerService.savePasswordToken(user, token);
        assertEquals("testPasswordToken", passwordTokenRepository.findByToken(token).getToken());
        registerService.deleteOldPasswordToken(token);
        assertNull(passwordTokenRepository.findByToken(token));
        userRepository.delete(user);
    }
}