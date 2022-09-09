package com.jointeams.backend.controller;

import com.jointeams.backend.event.SendVerifyEmailEvent;
import com.jointeams.backend.model.RegisterUserModel;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.VerificationToken;
import com.jointeams.backend.repositery.VerificationTokenRepository;
import com.jointeams.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @PostMapping({"/", ""})
    public String registerUser(@RequestBody RegisterUserModel registerUserModel, final HttpServletRequest request) {
        User user = registerService.registerUser(registerUserModel);
        // TODO
        // Check whether email exists
        if (user == null) {
            return "Failed";
        }
        publisher.publishEvent(new SendVerifyEmailEvent(user, getApplicationUrl(request)));
        return "Success";
    }

    @GetMapping("/verify")
    public String verifyRegistration(@RequestParam("token") String token, final HttpServletRequest request) {
        String result = registerService.validateVerificationToken(token);
        if (result.equalsIgnoreCase("valid")) {
            return "User verified successfully";
        } else if (result.equalsIgnoreCase("timeout")) {
            VerificationToken verificationToken = verificationTokenRepository.findByToken(token);
            User user = verificationToken.getUser();
            publisher.publishEvent(new SendVerifyEmailEvent(user, getApplicationUrl(request)));
            verificationTokenRepository.delete(verificationToken);
            return "Token expired. Resend token";
        } else {
            return "Token not found";
        }
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
