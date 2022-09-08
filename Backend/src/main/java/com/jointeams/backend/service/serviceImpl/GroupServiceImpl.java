package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.GroupRepository;
import com.jointeams.backend.repositery.GroupUserRepository;
import com.jointeams.backend.service.GroupService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Override
    public JSONObject getGroupById(Long id) {
        Group group = groupRepository.findById(id).orElse(null);
        List<GroupUser> groupUsers = groupUserRepository.getGroupUserByGroupId(id).orElse(null);
        if(group == null) {
            return null;
        } else {
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("id", id);
            jsonResult.put("name", group.getNameId());
            jsonResult.put("course", group.getCourse().getName());
            jsonResult.put("tutorial", group.getTutorial());
            jsonResult.put("capacity", group.getCapacity());
            jsonResult.put("description", group.getDescription());
            if(groupUsers == null) {
                jsonResult.put("number of students", 0);
            } else {
                jsonResult.put("number of students", groupUsers.size());
            }
            return jsonResult;
        }
    }

    @Override
    public List<User> getAllMembers(Long id) {
        return null;
    }

    @Override
    public Integer updateDescription(Long id, String newDescription) {
        return null;
    }

    @Override
    public Integer deleteAMember(Long groupId, Long userId) {
        return null;
    }

    @Override
    public Boolean isCommented(Long groupId, Long senderId, Long receiverId) {
        return null;
    }

    @Override
    public Comment getCommentbyId(Long groupId, Long userId, Long receiverId) {
        return null;
    }

    @Override
    public List<User> getStudentsNotInAGroup(Long courseId) {
        return null;
    }

    @Override
    public Integer addInvitationNotification(Long senderId, Long groupId) {
        return null;
    }

    @Override
    public Integer addJoinRequestNotification(Long senderId, Long groupId) {
        return null;
    }
}