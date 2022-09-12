package com.jointeams.backend.controller;

import com.jointeams.backend.service.GroupService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @GetMapping(path = "getGroupInformationById")
    public ResponseEntity<JSONObject> getGroupInformationById(@RequestParam("id") Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject groupObj = groupService.getGroupById(id);

        if(groupObj.get("data") == null) {
            return new ResponseEntity<>(groupObj, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(groupObj, HttpStatus.OK);
        }
    }

    @GetMapping(path = "getAllMembers")
    public  ResponseEntity<JSONObject> getAllMembersByGroupId(@RequestParam("id") Long id) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject allMembers = groupService.getAllMembers(id);

        if(allMembers.get("data") == null) {
            return new ResponseEntity<>(allMembers, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(allMembers, HttpStatus.OK);
        }
    }

    @PutMapping(path = "updateDescription")
    public ResponseEntity<JSONObject> updateDescription(@RequestParam("id") Long id, @RequestParam("newDescription") String newDescription) {
        if(id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = groupService.updateDescription(id, newDescription);

        if(result.get("data") == null) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

    @PutMapping(path = "leaveGroup")
    public ResponseEntity<JSONObject> leaveGroup(@RequestParam("groupId") Long groupId, @RequestParam("userId") Long userId) {
        if(groupId == null || userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = groupService.deleteAMember(groupId, userId);

        if(result.get("msg").equals("Unable to find this user in this group!")) {
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

    @GetMapping(path = "isCommentFunctionOpen")
    public ResponseEntity<JSONObject> isCommentFunctionOpen() {
        JSONObject result = groupService.isCommentFunctionAvailable();

        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @GetMapping(path = "checkCommentExistence")
    public ResponseEntity<JSONObject> checkCommentExistence(@RequestParam("groupId") Long groupId, @RequestParam("senderId") Long senderId, @RequestParam("receiverId") Long receiverId) {
        if(groupId == null || senderId == null || receiverId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if(senderId.equals(receiverId)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = groupService.getCommentById(groupId, senderId, receiverId);

        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @PostMapping(path = "sendInvitation")
    public ResponseEntity<JSONObject> sendInvitation(@RequestParam("groupId") Long groupId, @RequestParam("userId") Long userId) {
        if(groupId == null || userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = groupService.addInvitationNotification(groupId, userId);

        if(result.get("msg").equals("You have already sent an invitation to this user!")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if(result.get("msg").equals("This student is already in your group!")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }else if(result.get("msg").equals("Your group is already full!")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

    @PostMapping(path = "sendJoinRequest")
    public ResponseEntity<JSONObject> sendJoinRequest(@RequestParam("groupId") Long groupId, @RequestParam("userId") Long userId) {
        if(groupId == null || userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = groupService.addJoinRequestNotification(groupId, userId);

        if(result.get("msg").equals("You have already sent a joining request to this group!")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else if(result.get("msg").equals("You are already in this group!")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }else if(result.get("msg").equals("This group is already full!")) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
        }
    }

    @GetMapping(path = "listStudentsNotInAGroup")
    public ResponseEntity<JSONObject> listStudentsNotInAGroup(@RequestParam("courseId") Long courseId) {
        if(courseId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = groupService.getStudentsNotInAGroup(courseId);
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

    @GetMapping(path = "myGroups")
    public ResponseEntity<JSONObject> getAllCurrentGroupsOfAUser(@RequestParam("userId") Long userId) {
        if(userId == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        JSONObject result = groupService.getAllCurrentGroupsOfAUser(userId);
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
}
