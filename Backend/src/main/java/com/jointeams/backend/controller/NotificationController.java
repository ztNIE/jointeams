package com.jointeams.backend.controller;

import com.jointeams.backend.pojo.Cookie;
import com.jointeams.backend.pojo.Notification;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.service.NotificationService;
import com.jointeams.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping(path="/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserService userService;

//    @PostMapping(path="/notification/findAllByUser")
//    public List<Notification> findAllByUser(@RequestBody User user)
//    {
//        return notificationService.findAllByUser(user);
//    }
//
//    @PostMapping(path="/notification/findAllByUserId")
//    public List<Notification> findAllByUserId(@RequestBody Long userId)
//    {
//        User user = new User();
//        user.setId(userId);
//        return notificationService.findAllByUser(user);
//    }

    @PostMapping(path="/findAllByUserId")
    public List<Notification> findAllByUserId(@RequestBody Cookie cookie)
    {
        User user = userService.verifyUserIdentityByJsonWebToken(cookie.getUserId(), cookie.getEncryptedPassword());
        if(user != null)
            return notificationService.findAllByUser(cookie.getUserId());
        else
            return null;
    }

}
