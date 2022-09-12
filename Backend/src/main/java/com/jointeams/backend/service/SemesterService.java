package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Semester;
import org.json.simple.JSONObject;

public interface SemesterService {
    public JSONObject findCurrentSemesterFeedback();
    public int changeCurrentSemester(int year, int semesterNumber);
    public JSONObject changeCurrentSemesterFeedback(int year, int semesterNumber);
}
