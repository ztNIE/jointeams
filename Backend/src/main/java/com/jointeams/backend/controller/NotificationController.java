package com.jointeams.backend.controller;

import com.jointeams.backend.service.NotificationService;
import com.jointeams.backend.service.UserService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/notification")
@PreAuthorize("hasRole('USER')")
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

    @GetMapping(path="/findAllByUserId")
    public ResponseEntity<JSONObject> findAllByUserId(@RequestParam("userId") Long userId)
    {
        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonResult = notificationService.getAllByUserId(userId);
        if(jsonResult.get("user status").equals(0))
            return new ResponseEntity<>(jsonResult, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
    }

    @GetMapping(path="/actionOnNotification")
    public ResponseEntity<JSONObject> findAllByUserId(@RequestParam("notificationId") Long notificationId, @RequestParam("action") int action)
    {
        if(notificationId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JSONObject jsonResult = notificationService.actionResult(notificationId, action);
        if(jsonResult.get("notification status").equals(0))
            return new ResponseEntity<>(jsonResult, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(jsonResult, HttpStatus.OK);
    }
}
