package com.jointeams.backend.service;

import org.json.simple.JSONObject;

public interface CourseService {
    public JSONObject getAllCourse(Long userId);
    public JSONObject getAllPreviousStudent(Long courseId);
    public JSONObject getAllCurrentStudent(Long courseId);
    public JSONObject getAllPreviousTeammates(Long userId, Long courseId);
    public JSONObject enrollCourse(Long userId, Long courseId);
    public JSONObject dropCourse(Long userId, Long courseId);
    public JSONObject checkEnrollment(Long userId, Long courseId);
//    public JSONObject getCurrentCourseById(Long userId);
//    public JSONObject getPreviousCourseById(Long userId);
}
