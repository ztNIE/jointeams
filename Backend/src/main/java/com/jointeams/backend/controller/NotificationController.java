package com.jointeams.backend.controller;

import com.jointeams.backend.service.NotificationService;
import com.jointeams.backend.util.JsonResult;
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


    @GetMapping(path="/findAllByUserId")
    public ResponseEntity<JSONObject> findAllByUserId(@RequestParam("userId") Long userId)
    {
        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JsonResult jsonResult = notificationService.findAllByUserIdFeedback(userId);
        if(jsonResult.getStatus() == 0)
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
    }

    @PostMapping(path="/actionOnNotification")
    public ResponseEntity<JSONObject> actionOnNotification(@RequestParam("notificationId") Long notificationId, @RequestParam("action") Integer action)
    {
        if(notificationId == null || action == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        JsonResult jsonResult = notificationService.actionOnNotificationFeedback(notificationId, action);
        int status = jsonResult.getStatus();
        if(status >= 1 && status <= 7)
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.OK);
        else if (status == 0)
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(jsonResult.getMsgAndData(), HttpStatus.NOT_ACCEPTABLE);
    }
}
