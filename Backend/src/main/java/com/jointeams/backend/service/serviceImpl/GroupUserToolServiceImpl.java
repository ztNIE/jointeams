package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.GroupUserRepository;
import com.jointeams.backend.service.GroupUserToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupUserToolServiceImpl implements GroupUserToolService {

    @Autowired
    private GroupUserRepository groupUserRepository;
    @Override
    public void addAmember(Long groupId, Long userId) {
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupUserId(new GroupUserId(groupId,userId));
        groupUser.setLeader(false);
        groupUserRepository.save(groupUser);
    }

    @Override
    public boolean checkIsGroupFull(Group group)
    {
        int currentNumOfMember = groupUserRepository.countByGroupId(group.getId());
        if(group.getCapacity() > currentNumOfMember) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public List<GroupUser> findALLAsLeaderByUserId(Long userId)
    {
        return groupUserRepository.findALLAsLeaderByUserId(userId);
    }
}
