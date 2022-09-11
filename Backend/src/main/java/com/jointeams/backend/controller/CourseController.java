package com.jointeams.backend.controller;

import com.jointeams.backend.service.CourseService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping(path = "getAllCourse")
    public ResponseEntity<JSONObject> getAllCourse(@RequestParam("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject allCourse = courseService.getAllCourse(id);

        if(allCourse.get("data") == null) {
            return new ResponseEntity<>(allCourse, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(allCourse, HttpStatus.OK);
        }
    }

    @GetMapping(path = "getAllPreviousStudent")
    public ResponseEntity<JSONObject> getAllPreviousStudent(@RequestParam("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject previousStudents = courseService.getAllPreviousStudent(id);

        if(previousStudents.get("data") == null) {
            return new ResponseEntity<>(previousStudents, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(previousStudents, HttpStatus.OK);
        }
    }

    @GetMapping(path = "getAllCurrentStudent")
    public ResponseEntity<JSONObject> getAllCurrentStudent(@RequestParam("id") Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject currentStudents = courseService.getAllCurrentStudent(id);

        if(currentStudents.get("data") == null) {
            return new ResponseEntity<>(currentStudents, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(currentStudents, HttpStatus.OK);
        }
    }

    @GetMapping(path = "getAllPreviousTeammates")
    public ResponseEntity<JSONObject> getAllCurrentStudent(@RequestParam("userId") Long userId, @RequestParam("courseId") Long courseId) {
        if (userId == null || courseId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject previousTeammates = courseService.getAllPreviousTeammates(userId, courseId);

        if(previousTeammates.get("data") == null) {
            return new ResponseEntity<>(previousTeammates, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(previousTeammates, HttpStatus.OK);
        }
    }

    @PostMapping(path = "enrollCourse")
    public ResponseEntity<JSONObject> enrollCourse(@RequestParam("userId") Long userId, @RequestParam("courseId") Long courseId) {
        if (userId == null || courseId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = courseService.enrollCourse(userId, courseId);

        if(result.get("data") == null) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

    @DeleteMapping(path = "dropCourse")
    public ResponseEntity<JSONObject> dropCourse(@RequestParam("userId") Long userId, @RequestParam("courseId") Long courseId) {
        if (userId == null || courseId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = courseService.dropCourse(userId, courseId);

        if(result.get("data") == null) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

    @GetMapping(path = "checkEnrollment")
    public ResponseEntity<JSONObject> checkEnrollment(@RequestParam("userId") Long userId, @RequestParam("courseId") Long courseId) {
        if (userId == null || courseId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = courseService.checkEnrollment(userId, courseId);

        if(result.get("data") == null) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }
}
