package com.jointeams.backend.service.serviceImpl;

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
import com.jointeams.backend.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.regex.Pattern;

@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UniversityRepository universityRepository;

    @Autowired
    VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    PasswordTokenRepository passwordTokenRepository;

    @Override
    public String isUserModelValid(RegisterUserRequest registerUserRequest) {

        University university = universityRepository
                .findById(registerUserRequest.getUniversityId())
                .orElse(null);

        if (university == null) {
            log.error("No Such University Found, university_id: {}", registerUserRequest.getUniversityId());
            return "University Not Found";
        }
        String email = registerUserRequest.getEmail();
        if (!isEmailMatchUniversity(email, university)) {
            log.info("Bad Email: {}", email);
            return "Bad Email";
        }
        if (isEmailExist(email)) {
            log.info("Duplicate register: {}", email);
            return "Email Exists";
        }
        return "Valid";
    }

    @Override
    public User registerUser(RegisterUserRequest registerUserRequest){
        User user = new User();

        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        user.setDegree(registerUserRequest.getDegree());
        user.setFaculty(registerUserRequest.getFaculty());
        University university = universityRepository.findById(registerUserRequest.getUniversityId()).orElse(null);
        if (university == null) {
            log.error("Cannot find university");
            return null;
        }

        user.setEmail(registerUserRequest.getEmail());
        user.setUniversity(university);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.save(user);
        return user;
    }

    private boolean isEmailMatchUniversity(String email, University university) {
        Pattern pattern = Pattern.compile(university.getRegex());
        log.debug("Email: {}, pattern: {}, isMatch: {}", email, pattern, pattern.matcher(email).matches());
        return pattern.matcher(email).matches();
    }

    private boolean isEmailExist(String email) {
        return userRepository.findByEmail(email).orElse(null) != null;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(token);
        verificationToken = verificationTokenRepository.save(verificationToken);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken == null) {
            return "NOTFOUND";
        }
        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "TIMEOUT";
        }
        user.setActivate(true);
        userRepository.save(user);
        verificationTokenRepository.delete(verificationToken);
        return "VALID";
    }

    @Override
    public User deleteOldVerifyToken(String token) {
        User user = null;
        VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
        if (verificationToken != null) {
            user = verificationToken.getUser();
            verificationTokenRepository.delete(verificationToken);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return (User) userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public void savePasswordToken(User user, String token) {
        PasswordToken passwordToken = new PasswordToken(token);
        passwordTokenRepository.save(passwordToken);
        passwordToken.setUser(user);
        passwordTokenRepository.save(passwordToken);
    }

    @Override
    public String validatePasswordToken(String token) {
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token);
        if (passwordToken == null) {
            return "NOTFOUND";
        }
        Calendar calendar = Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            return "TIMEOUT";
        }
        return "VALID";
    }

    @Override
    public User deleteOldPasswordToken(String token) {
        User user = null;
        PasswordToken passwordToken = passwordTokenRepository.findByToken(token);
        if (passwordToken != null) {
            user = passwordToken.getUser();
            passwordTokenRepository.delete(passwordToken);
        }
        return user;
    }

    @Override
    public void savePassword(User user, PasswordRequest passwordRequest) {
        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        userRepository.save(user);
    }
}
