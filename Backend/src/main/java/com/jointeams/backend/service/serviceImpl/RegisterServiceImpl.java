package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.model.PasswordRequest;
import com.jointeams.backend.model.RegisterUserRequest;
import com.jointeams.backend.pojo.University;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.token.PasswordToken;
import com.jointeams.backend.pojo.token.VerificationToken;
import com.jointeams.backend.repositery.PasswordTokenRepository;
import com.jointeams.backend.repositery.UniversityRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.repositery.VerificationTokenRepository;
import com.jointeams.backend.service.RegisterService;
import com.jointeams.backend.util.JSONObjectParser;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
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

    @Autowired
    JSONObjectParser parser;

    @Override
    public String isUserModelValid(RegisterUserRequest registerUserRequest) {
        University university = universityRepository
                .findById(registerUserRequest.getUniversityId())
                .orElse(null);
        if (university == null) {
            log.error("No Such University Found, university_id: {}", registerUserRequest.getUniversityId());
            return "Bad University";
        }
        String email = registerUserRequest.getEmail();
        if (!isEmailMatchUniversity(email, university) || isEmailExist(email)) {
            log.info("Bad Email: {}",
                    registerUserRequest.getEmail());
            return "Bad Email";
        }

        return "Valid";
    }

    @Override
    public JSONObject registerUser(RegisterUserRequest registerUserRequest){
        User user = new User();

        user.setFirstName(registerUserRequest.getFirstName());
        user.setLastName(registerUserRequest.getLastName());
        user.setDegree(registerUserRequest.getDegree());
        University university = universityRepository.findById(registerUserRequest.getUniversityId()).orElse(null);
        if (university == null) {
            log.error("Cannot find university");
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("msg", "Cannot Find University");
            return jsonResult;
        }

        user.setEmail(registerUserRequest.getEmail());
        user.setUniversity(university);
        user.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.save(user);
        JSONObject jsonObject = parser.parseObject(user);
        return jsonObject;
    }

    private boolean isEmailMatchUniversity(String email, University university) {
        Pattern pattern = Pattern.compile(university.getRegex());
        log.debug("Email: {}, pattern: {}, isMatch: {}", email, pattern, pattern.matcher(email).matches());
        return pattern.matcher(email).matches();
    }

    private boolean isEmailExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public void saveVerificationTokenForUser(String token, User user) {
        VerificationToken verificationToken = new VerificationToken(token, user);
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
        return userRepository.findByEmail(email);
    }

    @Override
    public void savePasswordToken(User user, String token) {
        PasswordToken passwordToken = new PasswordToken(token, user);
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
