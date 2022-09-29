package com.jointeams.backend.service;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.util.JsonResult;

public interface UniversityService {
    public University addUniversity(University university);
    public JsonResult findAll();
}
