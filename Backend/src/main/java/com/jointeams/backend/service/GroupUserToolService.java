package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.GroupUser;

import java.util.List;

public interface GroupUserToolService {
    public void addAmember(Long groupId, Long userId);

    public boolean checkIsGroupFull(Group group);

    public List<GroupUser> findALLAsLeaderByUserId(Long userId);
}
