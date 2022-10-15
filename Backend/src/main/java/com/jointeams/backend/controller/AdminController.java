package com.jointeams.backend.controller;

import com.jointeams.backend.model.request.AddCourseRequest;
import com.jointeams.backend.service.CommentService;
import com.jointeams.backend.service.CourseService;
import com.jointeams.backend.service.SemesterService;
import com.jointeams.backend.service.UniversityService;
import com.jointeams.backend.util.JsonResult;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private SemesterService semesterService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CommentService commentService;
    @Autowired
    private UniversityService universityService;

    //including finding IsCommentAvailable
    @PreAuthorize("hasAnyRole('ADMIN','USER') ")
    @GetMapping(path = "/findCurrentSemester")
    public ResponseEntity<JSONObject> findCurrentSemester()
    {
        JsonResult jsonResult = semesterService.findCurrentSemesterFeedback();

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @PostMapping(path = "changeCurrentSemester")
    public ResponseEntity<JSONObject> changeCurrentSemester(@RequestParam("year") int year, @RequestParam("semesterNumber") int semesterNumber)
    {
        JsonResult jsonResult = semesterService.changeCurrentSemesterFeedback(year, semesterNumber);

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_ACCEPTABLE);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @GetMapping(path = "findAllCourses")
    public ResponseEntity<JSONObject> findAllCourses()
    {
        JsonResult jsonResult = courseService.findAllFeedback();

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @PostMapping(path = "addACourse")
    public ResponseEntity<JSONObject> addACourse(@RequestBody AddCourseRequest courseRequest)
    {
        JsonResult jsonResult = courseService.addACourseFeedback(courseRequest.getCode(), courseRequest.getName(),
                courseRequest.getUniversityId());

        if(jsonResult.getStatus() != 1) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }
    @PostMapping(path = "deleteACourse")
    public ResponseEntity<JSONObject> deleteACourse(@RequestParam("courseId") Long courseId) {

        JsonResult jsonResult = courseService.deleteACourseFeedback(courseId);

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @PostMapping(path = "changeACourseLockStatus")
    public ResponseEntity<JSONObject> changeACourseLockStatus(@RequestParam("courseId") Long courseId, @RequestParam("isLocked") boolean isLocked)
    {
        JsonResult jsonResult = courseService.changeCourseLockStatusFeedback(courseId, isLocked);

        if(jsonResult.getStatus() != 1) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @GetMapping(path = "findAllComments")
    public ResponseEntity<JSONObject> findAllComments()
    {
        JsonResult jsonResult = commentService.findAllFeedback();

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @PostMapping(path = "deleteAComment")
    public ResponseEntity<JSONObject> deleteAComment(@Param("commentId") Long commentId)
    {
        JsonResult jsonResult = commentService.deleteACommentFeedback(commentId);

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @PostMapping(path = "changeIsCommentAvailableStatus")
    public ResponseEntity<JSONObject> changeIsCommentAvailableStatus(@Param("isAvailable") boolean isAvailable)
    {
        JsonResult jsonResult = commentService.changeIsCommentAvailableStatus(isAvailable);

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

    @GetMapping(path = "findAllUniversities")
    public ResponseEntity<JSONObject> findAllUniversities()
    {
        JsonResult jsonResult = universityService.findAllFeedback();

        if(jsonResult.getStatus() == 0) {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        }
    }

}
