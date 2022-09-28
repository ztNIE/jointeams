package com.jointeams.backend.service;

import com.jointeams.backend.util.JsonResult;

public interface NotificationService {
    public JsonResult findAllByUserIdFeedback(Long userId);
    public JsonResult actionOnNotificationFeedback(Long notificationId, int action);
}
