package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.model.request.RegisterUserRequest;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.PasswordTokenRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.repositery.VerificationTokenRepository;
import com.jointeams.backend.service.RegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterServiceImplTest {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isUserModelValid() {
        RegisterUserRequest userModel = new RegisterUserRequest();
        assertEquals("University Not Found", registerService.isUserModelValid(userModel));
        userModel.setUniversityId(10L);
        assertEquals("University Not Found", registerService.isUserModelValid(userModel));
        userModel.setUniversityId(1L);
        userModel.setEmail("niezhengtong@gmail.com");
        assertEquals("Bad Email", registerService.isUserModelValid(userModel));
        userModel.setEmail("devu0001@uni.sydney.edu.au");
        assertEquals("Email Exists", registerService.isUserModelValid(userModel));
        userModel.setEmail("safe1234@uni.sydney.edu.au");
        assertEquals("Valid", registerService.isUserModelValid(userModel));
    }

    @Test
    void registerUser() {
        RegisterUserRequest userModel = new RegisterUserRequest();
        String firstName = generateCharStringWithLength(7);
        String lastName = generateCharStringWithLength(6);
        String degree = generateCharStringWithLength(15);
        String faculty = generateCharStringWithLength(20);
        Long universityId = 1L;
        String email = generateCharStringWithLength(4)+"1234@uni.sydney.edu.au";
        String password = generateCharStringWithLength(15);

        userModel.setFirstName(firstName);
        userModel.setLastName(lastName);
        userModel.setDegree(degree);
        userModel.setFaculty(faculty);
        userModel.setUniversityId(10L);
        assertNull(registerService.registerUser(userModel));
        userModel.setUniversityId(universityId);
        userModel.setEmail(email);
        userModel.setPassword(passwordEncoder.encode(password));
        User returnUser = registerService.registerUser(userModel);
        assertEquals(firstName, returnUser.getFirstName());
        assertEquals(lastName, returnUser.getLastName());
        assertEquals(degree, returnUser.getDegree());
        assertEquals(faculty, returnUser.getFaculty());
        assertEquals(universityId, returnUser.getUniversity().getId());
        assertEquals(email, returnUser.getEmail());
    }

    private String generateCharStringWithLength(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

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
    void findUserByEmail() {
        String existEmail = "devu0001@uni.sydney.edu.au";
        String nonExistEmail = "null0000@uni.sydney.edu.au";
        assertEquals(existEmail, registerService.findUserByEmail(existEmail).getEmail());
        assertNull(registerService.findUserByEmail(nonExistEmail));
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