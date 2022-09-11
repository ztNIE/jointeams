package com.jointeams.backend.service;

import com.jointeams.backend.pojo.User;

public interface GroupUserService {
    public void addAmember(Long groupId, Long userId);
}
