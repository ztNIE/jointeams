package com.jointeams.backend.controller;

import com.jointeams.backend.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

@RestController
@PreAuthorize("hasRole('USER')")
@RequestMapping(path="/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceLoader resourceLoader;

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
    @PreAuthorize("hasAnyRole('ADMIN','USER') ")
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
    public ResponseEntity<JSONObject> editUserInfoById(@RequestParam("id") Long id, @RequestBody JSONObject newInfo) throws FileNotFoundException {
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

    @PostMapping(path = "/uploadAvatar")
    public ResponseEntity<JSONObject> uploadAvatar(@RequestParam(value = "file", required=false) MultipartFile file) {
        JSONObject result = userService.uploadAvatar(file);

        if(result.get("data") == null) {
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

    @GetMapping(path = "/getAvatar")
    public ResponseEntity<JSONObject> getAvatar(@RequestParam("fileName") String fileName) throws FileNotFoundException {
        JSONObject result = userService.getAvatar(fileName);

        if(result.get("data") == null) {
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

}
