package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.User;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface GroupService {
    public JSONObject getGroupById(Long id);
    public JSONArray getAllMembers(Long id);
    public Integer updateDescription(Long id, String newDescription);
    public Integer deleteAMember(Long groupId, Long userId);
    public Boolean isCommented(Long groupId, Long senderId, Long receiverId);
    public Comment getCommentbyId(Long groupId, Long userId, Long receiverId);
    public List<User> getStudentsNotInAGroup(Long courseId);

    public Integer addInvitationNotification(Long senderId, Long groupId);
    public Integer addJoinRequestNotification(Long senderId, Long groupId);

}
