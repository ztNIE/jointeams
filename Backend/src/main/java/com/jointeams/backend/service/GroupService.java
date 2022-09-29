package com.jointeams.backend.service;
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


    public JSONObject getAllCurrentGroupsOfAUser(Long userId);
}
