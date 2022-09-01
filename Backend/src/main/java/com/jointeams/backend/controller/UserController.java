package com.jointeams.backend.controller;

import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping(path="/greeting")
    public String greeting(@RequestParam Long id) {
        User user = userService.findById(id);
        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
    }

    @PostMapping(path="/add")
    public User addUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

}
