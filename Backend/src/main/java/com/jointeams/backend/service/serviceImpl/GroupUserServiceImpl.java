package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.GroupUserRepository;
import com.jointeams.backend.service.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupUserServiceImpl implements GroupUserService {

    @Autowired
    private GroupUserRepository groupUserRepository;
    @Override
    public void addAmember(Long groupId, Long userId) {
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupUserId(new GroupUserId(groupId,userId));
        groupUser.setLeader(false);
        groupUserRepository.save(groupUser);
    }
}
