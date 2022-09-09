package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.model.RegisterUserModel;
import com.jointeams.backend.pojo.University;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.VerificationToken;
import com.jointeams.backend.repositery.UniversityRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.repositery.VerificationTokenRepository;
import com.jointeams.backend.service.RegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;

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

    @Override
    public User registerUser(RegisterUserModel registerUserModel) {
        User user = new User();
        user.setEmail(registerUserModel.getEmail());
        user.setFirstName(registerUserModel.getFirstName());
        user.setLastName(registerUserModel.getLastName());
        user.setDegree(registerUserModel.getDegree());

        University university = universityRepository
                .findById(registerUserModel.getUniversityId())
                .orElse(null);
        if (university == null) {
            log.error("No Such University Found, university_id: {}", registerUserModel.getUniversityId());
            return null;
        }
        user.setUniversity(university);

        user.setPassword(passwordEncoder.encode(registerUserModel.getPassword()));

        userRepository.save(user);
        return user;
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
}
