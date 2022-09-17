package com.jointeams.backend.service;

import com.jointeams.backend.pojo.*;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public interface GroupService {
    public JSONObject getGroupById(Long id);
    public JSONObject getAllMembers(Long id);
    public JSONObject updateDescription(Long id, String newDescription);
    public JSONObject deleteAMember(Long groupId, Long userId);
    public JSONObject isCommentFunctionAvailable();
    public JSONObject getCommentById(Long groupId, Long senderId, Long receiverId);
    public JSONObject leaveComment(Long groupId, Long senderId, Long receiverId, Integer tag, String content);
    public JSONObject getStudentsNotInAGroup(Long courseId);

    public JSONObject addInvitationNotification(Long groupId, Long userId);
    public JSONObject addJoinRequestNotification(Long groupId, Long userId);

    public Group findByCourseAndSemesterAndUserId(Course course, Semester semester, Long userId);
    public boolean checkIsGroupFull(Group group);

    public JSONObject getAllCurrentGroupsOfAUser(Long userId);
}
