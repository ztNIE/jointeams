package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Course;
import org.json.simple.JSONObject;

import java.util.List;

public interface CourseService {
    public JSONObject getAllCourse(Long userId);
    public JSONObject getAllPreviousStudent(Long courseId);
    public JSONObject getAllCurrentStudent(Long courseId);
    public JSONObject getAllPreviousTeammates(Long userId, Long courseId);
    public JSONObject enrollCourse(Long userId, Long courseId);
    public JSONObject dropCourse(Long userId, Long courseId);
    public JSONObject checkEnrollment(Long userId, Long courseId);
    public JSONObject markCourse(Long userId, Long courseId);
    public JSONObject unmarkCourse(Long userId, Long courseId);
    public JSONObject checkMarkedCourse(Long userId, Long courseId);
    public JSONObject getTutorial(Long userId, Long courseId);
    public JSONObject setTutorial(Long userId, Long courseId, String tutorial);
//    public JSONObject getCurrentCourseById(Long userId);
//    public JSONObject getPreviousCourseById(Long userId);
    public List<Course> findAll();

    public JSONObject findAllFeedback();

    public int AddACourse();

    public JSONObject AddACourseFeedback();

    public int deleteACourse(Long courseId);

    public JSONObject deleteACourseFeedback(Long courseId);

    public int changeCourseLockStatus(Long courseId, boolean isLocked);

    public JSONObject changeCourseLockStatusFeedback(Long courseId, boolean isLocked);
}
