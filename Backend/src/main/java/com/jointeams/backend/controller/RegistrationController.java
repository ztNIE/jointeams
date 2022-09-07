package com.jointeams.backend.controller;

import com.jointeams.backend.event.SendVerifyEmailEvent;
import com.jointeams.backend.model.UserModel;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping({"/", ""})
    public String registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) {
        User user = registerService.registerUser(userModel);

        if (user == null) {
            // TODO
            // Add status code
            return "Failed";
        }

        publisher.publishEvent(new SendVerifyEmailEvent(user, getApplicationUrl(request)));

        return "Success";
    }

    private String getApplicationUrl(HttpServletRequest request) {
        return "http://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getServletPath();
    }
}
