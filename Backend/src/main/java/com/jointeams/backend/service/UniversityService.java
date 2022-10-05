package com.jointeams.backend.service;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.util.JsonResult;

import java.util.List;

public interface UniversityService {
    public University addUniversity(University university);
    public JsonResult findAllFeedback();

    Iterable<University> getAllUniversities();
}
