package com.jointeams.backend.service;

import com.jointeams.backend.util.JsonResult;

public interface SemesterService {
    public JsonResult findCurrentSemesterFeedback();
    public JsonResult changeCurrentSemesterFeedback(int year, int semesterNumber);
}
