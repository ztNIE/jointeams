package com.jointeams.backend.controller;

import com.jointeams.backend.service.CourseGroupService;
import com.jointeams.backend.service.GroupService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/courseGroup")
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
}
