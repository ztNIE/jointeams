package com.jointeams.backend.service;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.pojo.User;

public interface UniversityService {
    public University findById(long id);
    public void saveAUniversity(String name);
}
