package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Notification;
import org.json.simple.JSONObject;

import java.util.List;

public interface NotificationService {
    public <T> List<T> findAllByUserId(Long userId);
    public JSONObject getAllByUserId(Long userId);
    public int actionOnNotification(Notification notification, int action);
    public JSONObject actionResult(Long notificationId, int action);
}
