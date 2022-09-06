package com.jointeams.backend.service.serviceImpl;


import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.Notification;
import com.jointeams.backend.repositery.GroupUserRepository;
import com.jointeams.backend.repositery.NotificationRepository;
import com.jointeams.backend.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private GroupUserRepository groupuserRepository;

    @Override
    public List<Notification> findAllByUser(Long userId) {
        List<Notification> result = notificationRepository.findAllByUserId(userId);
        List<GroupUser> groupUsers = groupuserRepository.findALLAsLeaderByUserId(userId);
        for(GroupUser groupUser: groupUsers)
        {
            result.addAll(notificationRepository.findAllByGroupId(groupUser.getGroupUserId().getGroupId()));
        }
        return result;
    }
}
