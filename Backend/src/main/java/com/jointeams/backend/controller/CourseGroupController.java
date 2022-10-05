package com.jointeams.backend.controller;

import com.jointeams.backend.service.CourseGroupService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/courseGroup")
@PreAuthorize("hasRole('USER')")
public class CourseGroupController {

    @Autowired
    private CourseGroupService courseGroupService;

    @GetMapping(path = "getAllGroupsInOneCourse")
    public ResponseEntity<JSONObject> getAllGroupsInOneCourse(@RequestParam("courseId") Long courseId, @RequestParam("userId") Long userId) {
        if(courseId == null || userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject groupObj = courseGroupService.getAllGroupsByCourseId(courseId, userId);

        if(groupObj.get("data") == null) {
            return new ResponseEntity<>(groupObj, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(groupObj, HttpStatus.OK);
        }
    }

    @PostMapping(path = "addAGroup")
    public ResponseEntity<JSONObject> addAGroup(@RequestParam("courseId") Long courseId, @RequestParam("userId") Long userId, @RequestParam("capacity") Integer capacity) {
        if(courseId == null || userId == null || capacity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject groupObj = courseGroupService.addAGroup(courseId, userId, capacity);

        if(groupObj.get("data") == null) {
            return new ResponseEntity<JSONObject>(groupObj, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<JSONObject>(groupObj, HttpStatus.OK);
        }
    }
}
