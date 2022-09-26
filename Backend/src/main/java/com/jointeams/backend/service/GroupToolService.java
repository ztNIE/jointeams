package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.Semester;

import java.util.List;

public interface GroupToolService {
    public Group findByCourseAndSemesterAndUserId(Course course, Semester semester, Long userId);
}
