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
    @GetMapping(path="/greetingById")
    public String greeting(@RequestParam Long id) {
        User user = userService.findById(id);
        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
    }
    @GetMapping(path="/greetingByLastName")
    public String greeting(@RequestParam String lastName) {
        User user = userService.findByLastName(lastName);
        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
    }

    @GetMapping(path="/greetingByFullName")
    public String greeting(@RequestParam String firstName, @RequestParam String lastName) {
        User user = userService.findByFullName(firstName, lastName);
        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
    }

    @GetMapping(path="/greetingByFullName2")
    public String greeting2(@RequestParam String firstName, @RequestParam String lastName) {
        User user = userService.findByFullName2(firstName, lastName);
        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
    }

    @GetMapping(path="/greetingByIncompleteFullName")
    public String greeting3(@RequestParam String firstName, @RequestParam String lastName) {
        User user = userService.findByIncompleteFullName(firstName, lastName);
        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
    }

    @PostMapping(path="/add")
    public User addUser(@RequestBody User user) {
        return userService.addNewUser(user);
    }

}
