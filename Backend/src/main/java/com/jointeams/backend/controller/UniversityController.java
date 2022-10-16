package com.jointeams.backend.controller;

import com.jointeams.backend.model.response.StandardResponse;
import com.jointeams.backend.pojo.University;
import com.jointeams.backend.service.UniversityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/university")

public class UniversityController {

    @Autowired
    private UniversityService universityService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path="/add")
    public University addUniversity(@RequestBody University university) {
        return universityService.addUniversity(university);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getAllUniversities() {
        return ResponseEntity.ok().body(new StandardResponse<>("success", universityService.getAllUniversities()));
    }
}
