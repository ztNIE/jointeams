package com.jointeams.backend.service.serviceImpl;


import com.jointeams.backend.model.response.responseData.NotificationResponseData;
import com.jointeams.backend.service.*;
import com.jointeams.backend.util.JsonResult;
import com.jointeams.backend.util.NotificationActionResultMsg;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.repositery.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserToolService userToolService;
    @Autowired
    private GroupToolService groupToolService;
    @Autowired
    private GroupUserToolService groupUserToolService;


    public List<Notification> findAllByUserId(Long userId) {
        List<Notification> result = notificationRepository.findAllByUserId(userId);
        List<GroupUser> groupUsers = groupUserToolService.findALLAsLeaderByUserId(userId);
        groupUsers.forEach(groupUser->result.addAll(notificationRepository.findAllByGroupId(groupUser.getGroupUserId().getGroupId())));
        return result;
    }

    @Override
    public JsonResult findAllByUserIdFeedback(Long userId) {
        JsonResult jsonResult = new JsonResult();
        if (!userToolService.checkIfUserExisted(userId))
        {
            List<Notification> notifications = findAllByUserId(userId);
            List<NotificationResponseData> notificationResponseDataList = new ArrayList<>();
            notifications.forEach(notification -> {NotificationResponseData notificationResponseData = new NotificationResponseData();
                Group group = notification.getGroup();
                User user = notification.getUser();
                notificationResponseData.setId(notification.getId());
                notificationResponseData.setType(notification.getType());
                notificationResponseData.setGroupId(group.getId());
                notificationResponseData.setGroupName(group.getCourse().getCode() + group.getNameId());
                notificationResponseData.setUserId(user.getId());
                notificationResponseData.setUserName(user.getFirstName()+user.getLastName());
                notificationResponseData.setEmail(user.getEmail());
                notificationResponseDataList.add(notificationResponseData);});
            if (notifications.size() == 0)
            {
                jsonResult.setStatus(0);
                jsonResult.setMsgAndData("No notification is found!", Optional.empty());
            }
            else
            {
                jsonResult.setStatus(1);
                jsonResult.setMsgAndData("Finding all notifications successful!", notificationResponseDataList);
            }
        }
        else
        {
            jsonResult.setStatus(-1);
            jsonResult.setMsgAndData("The user is not found!", Optional.empty());
        }
        return jsonResult;
    }

    public int actionOnNotification(Long notificationId, int action) //action: 1: accept, 2: decline, 3: delete
    {
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if(notification == null)
            return 0;
        int tempType = notification.getType();
        if(tempType >= 2 && tempType <= 5)
            tempType = 2;
        switch (tempType) {
            case 0: {
                switch (action) {
                    case 1: {//accept an invitation
                        return accept(notification, 4, new int[]{-4, 1, -3});
                    }
                    case 2: {//decline an invitation
                        notification.setType(5);
                        notificationRepository.save(notification);
                        return 2;//"The invitation has been declined";
                    }
                    case 3: {//delete an invitation
                        notificationRepository.delete(notification);
                        return 3;//"The invitation has been deleted"
                    }
                    default: {//invalided action
                        return -1;
                    }
                }
            }
            case 1: {
                switch (action) {
                    case 1: {//accept a join request
                        return accept(notification, 2, new int[]{-6, 4, -5});
                    }
                    case 2: {//decline a join request
                        notification.setType(3);
                        notificationRepository.save(notification);
                        return 5;
                    }
                    case 3: {//delete a join request
                        notificationRepository.delete(notification);
                        return 6;
                    }
                    default: {//invalided action
                        return -1;
                    }
                }
            }
            case 2:{
                if (action == 3)//delete a notification which has been read
                {
                    notificationRepository.delete(notification);
                    return 7;
                } else if (action == 1 || action == 2) {//the response cannot be accepted or declined
                    return -2;
                } else {//invalided action
                    return -1;
                }
            }
            default://error with notification type
                return -7;
        }
    }

    private int accept(Notification notification, int type, int[] returnValue){
        Group groupUserIn = groupToolService.findByCourseAndSemesterAndUserId(notification.getGroup().getCourse(), notification.getGroup().getSemester(), notification.getUser().getId());
        if(groupUserIn == null) {
            Group groupInNotification = notification.getGroup();
            if(groupUserToolService.checkIsGroupFull(groupInNotification))//the group is full
            {
                return returnValue[0];
            }else {//accept successfully
                groupUserToolService.addAmember(groupInNotification.getId(),notification.getUser().getId());
                notification.setType(type);
                notificationRepository.save(notification);
                return returnValue[1];//"The invitation/join request has been accepted";
            }
        }
        else//user has already been in a group
        {
            return returnValue[2];
        }
    }
    public JsonResult actionOnNotificationFeedback(Long notificationId, int action)
    {
        JsonResult jsonResult = new JsonResult();
        int resultCode = actionOnNotification(notificationId, action);
        jsonResult.setStatus(resultCode);
        NotificationActionResultMsg msg = Arrays.stream(NotificationActionResultMsg.values()).
                filter(value ->resultCode == value.getValue() ).findFirst().orElse(null);
        if(msg != null)
            jsonResult.setMsgAndData(msg.toString(),Optional.empty());
        else
            jsonResult.setMsgAndData("NotificationActionResultMsg missing",Optional.empty());
        return jsonResult;
    }
}
