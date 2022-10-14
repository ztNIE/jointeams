package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.model.request.PasswordRequest;
import com.jointeams.backend.model.request.RegisterUserRequest;
import com.jointeams.backend.pojo.University;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.token.PasswordToken;
import com.jointeams.backend.pojo.token.VerificationToken;
import com.jointeams.backend.repositery.PasswordTokenRepository;
import com.jointeams.backend.repositery.UniversityRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.repositery.VerificationTokenRepository;
import com.jointeams.backend.security.service.CustomUserDetails;
import com.jointeams.backend.service.RegisterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
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

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    private static University university;

    private static User user1;

    @BeforeAll
    public static void prepare() {
        university = new University();
        university.setId(1L);
        university.setName("The University of Sydney");
        university.setEmailUrl("https://www.outlook.com/uni.sydney.edu.au");
        university.setRegex("^[a-z]{4}[0-9]{4}@uni.sydney.edu.au$");

        user1 = new User();
        user1.setActivate(true);
        user1.setAdmin(false);
        user1.setId(1L);
        user1.setUniversity(university);
        user1.setEmail("abcd1234@uni.sydney.edu.au");
        user1.setFirstName("a");
        user1.setLastName("a");
        user1.setPassword("password");
        user1.setSelfTag(1);
    }

    @BeforeEach
    void setUp() {
        this.universityRepository.save(university);
        this.userRepository.save(user1);
    }

    @Test
    @DirtiesContext
    void isUserModelValid() {
        RegisterUserRequest userModel = new RegisterUserRequest();
        assertEquals("University Not Found", registerService.isUserModelValid(userModel));
        userModel.setUniversityId(10L);
        assertEquals("University Not Found", registerService.isUserModelValid(userModel));
        userModel.setUniversityId(1L);
        userModel.setEmail("niezhengtong@gmail.com");
        assertEquals("Bad Email", registerService.isUserModelValid(userModel));
        userModel.setEmail("abcd1234@uni.sydney.edu.au");
        assertEquals("Email Exists", registerService.isUserModelValid(userModel));
        userModel.setEmail("safe1234@uni.sydney.edu.au");
        assertEquals("Valid", registerService.isUserModelValid(userModel));
    }

    @Test
    @DirtiesContext
    void registerUser() {
        RegisterUserRequest userModel = new RegisterUserRequest();
        String firstName = generateCharStringWithLength(7);
        String lastName = generateCharStringWithLength(6);
        String degree = generateCharStringWithLength(15);
        String faculty = generateCharStringWithLength(20);
        Long universityId = 1L;
        String email = generateCharStringWithLength(4) + "5678@uni.sydney.edu.au";
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
        assertThrows(DisabledException.class, () -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                userModel.getEmail(), userModel.getPassword()
        )));
    }

    private String generateCharStringWithLength(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }

    @Test
    @DirtiesContext
    void deleteOldVerifyTokenTest() {
        String token = "test_token";
        VerificationToken verificationToken = new VerificationToken(token);
        verificationToken.setUser(user1);
        verificationTokenRepository.save(verificationToken);
        User returnUser = registerService.deleteOldVerifyToken(token);
        assertNull(verificationTokenRepository.findByToken(token));
        assertEquals(user1.getId(), returnUser.getId());
    }

    @Test
    @DirtiesContext
    void validatePasswordTokenTest() {
        String token = "test_token";
        assertEquals("NOTFOUND", registerService.validatePasswordToken(token));
        PasswordToken passwordToken = new PasswordToken(token);
        passwordToken.setUser(user1);
        passwordTokenRepository.save(passwordToken);
        assertEquals("VALID", registerService.validatePasswordToken(token));
    }

    @Test
    @DirtiesContext
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
    }

    @Test
    @DirtiesContext
    void findUserByEmail() {
        String existEmail = "abcd1234@uni.sydney.edu.au";
        String nonExistEmail = "null0000@uni.sydney.edu.au";
        assertEquals(existEmail, registerService.findUserByEmail(existEmail).getEmail());
        assertNull(registerService.findUserByEmail(nonExistEmail));
    }

    @Test
    @DirtiesContext
    void savePasswordTokenAndDelete() {
        User user = new User();
        user.setEmail("passwordToken@gmail.com");
        userRepository.save(user);
        String token = "testPasswordToken";
        registerService.savePasswordToken(user, token);
        assertEquals("testPasswordToken", passwordTokenRepository.findByToken(token).getToken());
        registerService.deleteOldPasswordToken(token);
        assertNull(passwordTokenRepository.findByToken(token));
    }

    @Test
    @DirtiesContext
    void savePasswordTest() {
        PasswordRequest passwordRequest = new PasswordRequest();
        String newPassword = UUID.randomUUID().toString();
        assertThrows(BadCredentialsException.class,
                () -> authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        user1.getEmail(), newPassword)));
        passwordRequest.setNewPassword(newPassword);
        registerService.savePassword(user1, passwordRequest);
        Authentication validAuthentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user1.getEmail(), newPassword
        ));
        CustomUserDetails userDetails = (CustomUserDetails) validAuthentication.getPrincipal();
        assertTrue(userDetails.isEnabled());
    }
}