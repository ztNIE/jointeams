package com.jointeams.backend.service;

import org.json.simple.JSONObject;

public interface CourseGroupService {
    public JSONObject getAllGroupsByCourseId(Long courseId, Long userId);
    public JSONObject addAGroup(Long courseId, Long userId, Integer capacity);
}
