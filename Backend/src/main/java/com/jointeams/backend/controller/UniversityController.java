package com.jointeams.backend.controller;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/university")
@PreAuthorize("hasRole('ADMIN')")
public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @PostMapping(path="/add")
    public University addUniversity(@RequestBody University university) {
        return universityService.addUniversity(university);
    }
}
