package com.jointeams.backend.service.serviceImpl;


import com.jointeams.backend.NotificationActionResultMsg;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.repositery.GroupUserRepository;
import com.jointeams.backend.repositery.NotificationRepository;
import com.jointeams.backend.service.GroupService;
import com.jointeams.backend.service.GroupUserService;
import com.jointeams.backend.service.NotificationService;
import com.jointeams.backend.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private GroupUserRepository groupuserRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupUserService groupUserService;

    @Override
    public List<Notification> findAllByUserId(Long userId) {
        List<Notification> result = notificationRepository.findAllByUserId(userId);
        List<GroupUser> groupUsers = groupuserRepository.findALLAsLeaderByUserId(userId);
        groupUsers.forEach(groupUser->result.addAll(notificationRepository.findAllByGroupId(groupUser.getGroupUserId().getGroupId())));
        return result;
    }

    @Override
    public JSONObject findAllByUserIdFeedback(Long userId) {
        JSONObject jsonResult = userService.checkIfUserExisted(userId);
        if (jsonResult.get("user status").equals(1))
        {
            List<Notification> notifications = findAllByUserId(userId);
            if (notifications.size() == 0)
            {
                jsonResult.put("finding the notifications status", 0);
                jsonResult.put("finding the notifications status msg", "No notification is found!");
            }
            else
            {
                jsonResult.put("finding the notifications status", 1);
                jsonResult.put("notifications",notifications);
            }
        }
        return jsonResult;
    }

    public int actionOnNotification(Notification notification, int action) //action: 1: accept, 2: decline, 3: delete
    {
        int tempType = notification.getType();
        if(tempType == 0)
        {
            switch (action) {
                case 1:{//accept an invitation
                    return accept(notification, 4, new int[]{-4,1,-3});
                }
                case 2:{//decline an invitation
                    notification.setType(5);
                    notificationRepository.save(notification);
                    return 2;//"The invitation has been declined";
                }
                case 3:{//delete an invitation
                    notificationRepository.delete(notification);
                    return 3;//"The invitation has been deleted"
                }
                default:{//invalided action
                    return -1;
                }
            }
        } else if (tempType == 1) {
            switch (action) {
                case 1:{//accept a join request
                    return accept(notification, 2, new int[]{-6,4,-5});
                }
                case 2:{//decline a join request
                    notification.setType(3);
                    notificationRepository.save(notification);
                    return 5;
                }
                case 3:{//delete a join request
                    notificationRepository.delete(notification);
                    return 6;
                }
                default:{//invalided action
                    return -1;
                }
            }
        }else if (tempType >= 2 && tempType <= 5) {
            if (action == 3)//delete a notification which has been read
            {
                notificationRepository.delete(notification);
                return 7;
            }else if (action == 1 || action == 2) {//the response cannot be accepted or declined
                return -2;
            }else{//invalided action
                return -1;
            }
        }else {//error with notification type
            return -7;
        }
    }

    private int accept(Notification notification, int type, int[] returnValue){
        Group groupUserIn = groupService.findByCourseAndSemesterAndUserId(notification.getGroup().getCourse(), notification.getGroup().getSemester(), notification.getUser().getId());
        if(groupUserIn == null) {
            Group groupInNotification = notification.getGroup();
            if(groupService.checkIsGroupFull(groupInNotification))//the group is full
            {
                return returnValue[0];
            }else {//accept successfully
                groupUserService.addAmember(groupInNotification.getId(),notification.getUser().getId());
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
    public JSONObject actionOnNotificationFeedback(Long notificationId, int action)
    {
        JSONObject jsonResult = new JSONObject();
        Notification notification = notificationRepository.findById(notificationId).orElse(null);
        if (notification == null)
        {
            jsonResult.put("finding the notification status", 0);
            jsonResult.put("finding the notification status msg", "The notification isn't found!");
        }
        else{
            int resultCode = actionOnNotification(notification, action);
            jsonResult.put("finding the notification status", 1);
            jsonResult.put("action status", resultCode);
            switch (resultCode){
                case 1:
                    jsonResult.put("action status msg", NotificationActionResultMsg.InvitationAccepted.toString());
                    break;
                case 2:
                    jsonResult.put("action status msg", NotificationActionResultMsg.InvitationDeclined.toString());
                    break;
                case 3:
                    jsonResult.put("action status msg", NotificationActionResultMsg.InvitationDelete.toString());
                    break;
                case 4:
                    jsonResult.put("action status msg", NotificationActionResultMsg.JoinRequestAccepted.toString());
                    break;
                case 5:
                    jsonResult.put("action status msg", NotificationActionResultMsg.JoinRequestDeclined.toString());
                    break;
                case 6:
                    jsonResult.put("action status msg", NotificationActionResultMsg.JoinRequestDelete.toString());
                    break;
                case 7:
                    jsonResult.put("action status msg", NotificationActionResultMsg.NotificationDeleted.toString());
                    break;
                case -1:
                    jsonResult.put("action status msg", NotificationActionResultMsg.InvalidedAction.toString());
                    break;
                case -2:
                    jsonResult.put("action status msg", NotificationActionResultMsg.ResponseLimitation.toString());
                    break;
                case -3:
                    jsonResult.put("action status msg", NotificationActionResultMsg.AcceptingInvitationFailedCozAnotherGroup.toString());
                    break;
                case -4:
                    jsonResult.put("action status msg", NotificationActionResultMsg.AcceptingInvitationFailedCozGroupFull.toString());
                    break;
                case -5:
                    jsonResult.put("action status msg", NotificationActionResultMsg.AcceptingJoinRequestFailedCozAnotherGroup.toString());
                    break;
                case -6:
                    jsonResult.put("action status msg", NotificationActionResultMsg.AcceptingJoinRequestFailedCozGroupFull.toString());
                    break;
                case -7:
                    jsonResult.put("action status msg", NotificationActionResultMsg.InvalidedNotificationType.toString());
                    break;
            }
        }
        return jsonResult;
    }
}
