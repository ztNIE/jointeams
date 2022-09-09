package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Notification;
import com.jointeams.backend.pojo.User;

import java.util.List;

public interface NotificationService {
    public <T> List<T> findAllByUser(Long userId);
}
