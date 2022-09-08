package com.jointeams.backend.controller;

import com.jointeams.backend.Cookie;
import com.jointeams.backend.pojo.Notification;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.NotificationService;
import com.jointeams.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

//    @PostMapping(path="/findAllByUserId")
//    public List<Notification> findAllByUserId(@RequestBody Cookie cookie)
//    {
//        User user = userService.verifyUserIdentityByEncryptedPassword(cookie.getUserId(), cookie.getEncryptedPassword());
//        if(user != null)
//            return notificationService.findAllByUser(cookie.getUserId());
//        else
//            return null;
//    }

    @PostMapping(path="/findAllByUserId")
    public List<Notification> findAllByUserId(@RequestParam Long userId)
    {
        User user = userService.findById(userId);
        if(user != null)
            return notificationService.findAllByUser(userId);
        else
            return null;
    }

}
