package com.jointeams.backend.controller;

import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.service.GroupService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        if(groupObj == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<JSONObject>(groupObj, HttpStatus.OK);
        }
    }
}
