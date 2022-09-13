package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Notification;
import org.json.simple.JSONObject;

import java.util.List;

public interface NotificationService {
    public List<Notification> findAllByUserId(Long userId);
    public JSONObject findAllByUserIdFeedback(Long userId);
    public int actionOnNotification(Notification notification, int action);
    public JSONObject actionOnNotificationFeedback(Long notificationId, int action);
}
