package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Semester;
import com.jointeams.backend.pojo.User;

import java.util.List;

public interface UserToolService {
    public boolean checkIfUserExisted(Long id);

    public List<User> findAllUsersHavingGroupsInTheCurrentSemester();
}
