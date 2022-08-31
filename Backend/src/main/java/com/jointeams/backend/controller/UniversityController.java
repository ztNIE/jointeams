package com.jointeams.backend.controller;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.UniversityService;
import com.jointeams.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/uni")
public class UniversityController {
    @Autowired
    private UniversityService universityService;
    @GetMapping(path="/saveUni")
    public void saveAUniversity(@RequestParam String name) {
        universityService.saveAUniversity(name);
    }

}
