package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Notification;
import com.jointeams.backend.util.JsonResult;
import org.json.simple.JSONObject;

import java.util.List;

public interface NotificationService {
    public JsonResult findAllByUserIdFeedback(Long userId);
    public JsonResult actionOnNotificationFeedback(Long notificationId, int action);
}
