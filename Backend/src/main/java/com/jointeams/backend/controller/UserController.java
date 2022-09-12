package com.jointeams.backend.controller;

import com.jointeams.backend.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "getIsAdmin")
    public ResponseEntity<JSONObject> getIsAdminById(@RequestParam("id") Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject isAdmin = userService.getIsAdminById(id);

        if(isAdmin.get("data") == null) {
            return new ResponseEntity<>(isAdmin, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(isAdmin, HttpStatus.OK);
        }
    }


    @GetMapping(path = "getUserInfoById")
    public ResponseEntity<JSONObject> getUserInfoById(@RequestParam("id") Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject userInfo = userService.getUserInfoById(id);

        if(userInfo.get("data") == null) {
            return new ResponseEntity<>(userInfo, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(userInfo, HttpStatus.OK);
        }
    }

    @PutMapping(path = "updateUserInfoById")
    public ResponseEntity<JSONObject> editUserInfoById(@RequestParam("id") Long id, @RequestBody JSONObject newInfo) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = userService.updateUserInfoById(id, newInfo);

        if(result.get("data") == null) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

//    @GetMapping(path="/greetingById")
//    public String greeting(@RequestParam Long id) {
//        User user = userService.findById(id);
//        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
//    }
//    @GetMapping(path="/greetingByLastName")
//    public String greeting(@RequestParam String lastName) {
//        User user = userService.findByLastName(lastName);
//        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
//    }
//
//    @GetMapping(path="/greetingByFullName")
//    public String greeting(@RequestParam String firstName, @RequestParam String lastName) {
//        User user = userService.findByFullName(firstName, lastName);
//        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
//    }
//
//    @GetMapping(path="/greetingByFullName2")
//    public String greeting2(@RequestParam String firstName, @RequestParam String lastName) {
//        User user = userService.findByFullName2(firstName, lastName);
//        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
//    }
//
//    @GetMapping(path="/greetingByIncompleteFullName")
//    public String greeting3(@RequestParam String firstName, @RequestParam String lastName) {
//        User user = userService.findByIncompleteFullName(firstName, lastName);
//        return "Hello " + user.getFirstName() + " " + user.getLastName() + "!";
//    }

//    @PostMapping(path="/add")
//    public User addUser(@RequestBody User user) {
//        return userService.addNewUser(user);
//    }

}
