package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Semester;
import com.jointeams.backend.util.JsonResult;
import org.json.simple.JSONObject;

public interface SemesterService {
    public JsonResult findCurrentSemesterFeedback();
    public JsonResult changeCurrentSemesterFeedback(int year, int semesterNumber);
}
