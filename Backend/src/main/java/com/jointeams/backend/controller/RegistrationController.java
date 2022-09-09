package com.jointeams.backend.controller;

import com.jointeams.backend.event.SendSavePasswordEmailEvent;
import com.jointeams.backend.event.SendVerifyEmailEvent;
import com.jointeams.backend.model.PasswordModel;
import com.jointeams.backend.model.RegisterUserModel;
import com.jointeams.backend.pojo.User;
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
            User user = registerService.deleteOldVerifyToken(token);
            publisher.publishEvent(new SendVerifyEmailEvent(user, getApplicationUrl(request)));
            return "Token expired. Resend token";
        } else {
            return "Token not found";
        }
    }

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
        User user = registerService.findUserByEmail(passwordModel.getEmail());
        if (user == null) {
            return "User not exist";
        }
        publisher.publishEvent(new SendSavePasswordEmailEvent(user, getApplicationUrl(request)));
        return "Reset link sent";
    }

    @PostMapping("/savePassword")
    public String savePassword(@RequestParam("token") String token,
                               @RequestBody PasswordModel passwordModel,
                               HttpServletRequest request) {
        String result = registerService.validatePasswordToken(token);
        if (result.equalsIgnoreCase("notfound")) {
            return "bad token";
        }

        User user = registerService.deleteOldPasswordToken(token);

        if (result.equalsIgnoreCase("timeout")) {
            publisher.publishEvent(new SendSavePasswordEmailEvent(user, getApplicationUrl(request)));
            return "Token expired. Resend token";
        }
        registerService.savePassword(user, passwordModel);
        return "Reset Password Successfully";
    }

//    TODO
//    @GetMapping("verifyResetPassword")
//    public String verifyResetPassword(@RequestParam("token") String token, final HttpServletRequest request) {
//    }


    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getContextPath();
    }
}
