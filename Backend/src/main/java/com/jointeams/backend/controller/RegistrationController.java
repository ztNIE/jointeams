package com.jointeams.backend.controller;

import com.jointeams.backend.model.UserModel;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private RegisterService registerService;

    @PostMapping("/")
    public String registerUser(@RequestBody UserModel userModel) {
        User user = registerService.registerUser(userModel);

        if (user == null) {
            return "Failed";
        }

        return "Success";
    }
}
