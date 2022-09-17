package com.jointeams.backend.controller;

import com.jointeams.backend.service.CommentService;
import com.jointeams.backend.service.CourseService;
import com.jointeams.backend.service.SemesterService;
import com.jointeams.backend.util.JsonResult;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
//@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private SemesterService semesterService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CommentService commentService;

    @GetMapping(path = "/findCurrentSemester")
    public ResponseEntity<JSONObject> findCurrentSemester() {
        JsonResult jsonResult = semesterService.findCurrentSemesterFeedback();

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @GetMapping(path = "changeCurrentSemester")
    public ResponseEntity<JSONObject> changeCurrentSemester(@RequestParam("year") int year, @RequestParam("semesterNumber") int semesterNumber) {
        JsonResult jsonResult = semesterService.changeCurrentSemesterFeedback(year, semesterNumber);

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

//    @GetMapping(path = "findAllCourse")
//    public ResponseEntity<JSONObject> findAllCourse() {
//
//        JSONObject jsonResult = courseService.findAllFeedback();
//
//        if(jsonResult.get("finding all courses status").equals(0)) {
//            return new ResponseEntity<>(jsonResult, HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<JSONObject>(jsonResult, HttpStatus.OK);
//        }
//    }
//
//    @GetMapping(path = "addACourse")
//    public ResponseEntity<JSONObject> addACourse() {
//
//        JSONObject jsonResult = courseService.AddACourseFeedback();
//
//        if(jsonResult.get("finding all courses status").equals(0)) {
//            return new ResponseEntity<>(jsonResult, HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<JSONObject>(jsonResult, HttpStatus.OK);
//        }
//    }
//    @GetMapping(path = "deleteACourse")
//    public ResponseEntity<JSONObject> deleteACourse() {
//
//        JSONObject jsonResult = courseService.findAllFeedback();
//
//        if(jsonResult.get("finding all courses status").equals(0)) {
//            return new ResponseEntity<>(jsonResult, HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<JSONObject>(jsonResult, HttpStatus.OK);
//        }
//    }
//
//    @GetMapping(path = "changeCourseLockStatus")
//    public ResponseEntity<JSONObject> changeACourseLockStatus(@RequestParam("courseId") Long courseId, @RequestParam("isLocked") boolean isLocked) {
//
//        JsonResult jsonResult = courseService.changeCourseLockStatusFeedback(courseId, isLocked);
//
//        if(jsonResult.getStatus() == 0) {
//            return new ResponseEntity<>(jsonResult.getDataAndMsg(), HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<JSONObject>(jsonResult.getDataAndMsg(), HttpStatus.OK);
//        }
//    }
}
