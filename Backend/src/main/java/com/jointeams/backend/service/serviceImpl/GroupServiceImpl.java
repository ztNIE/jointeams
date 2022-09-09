package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.GroupRepository;
import com.jointeams.backend.repositery.GroupUserRepository;
import com.jointeams.backend.service.GroupService;
import org.json.simple.JSONArray;
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
        JSONObject jsonResult = new JSONObject();

        List<GroupUser> groupUsers = groupUserRepository.getGroupUserByGroupId(id).orElse(null);
        if(group == null) {
            jsonResult.put("msg", "Unable to find this group!");
            jsonResult.put("data", null);
        } else {
            JSONObject data = new JSONObject();
            data.put("id", id);
            data.put("name", group.getNameId());
            data.put("course", group.getCourse().getName());
            data.put("tutorial", group.getTutorial());
            data.put("capacity", group.getCapacity());
            data.put("description", group.getDescription());
            if(groupUsers == null) {
                data.put("number of students", 0);
            } else {
                data.put("number of students", groupUsers.size());
            }

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    @Override
    public JSONObject getAllMembers(Long id) {
        List<Object[]> members = groupUserRepository.getGroupUserDetailByGroupId(id).orElse(null);

        JSONObject jsonResult = new JSONObject();
        if(members.size() == 0) {
            jsonResult.put("msg", "Unable to find this group!");
            jsonResult.put("data", null);
        } else {
            JSONArray jsonArray = new JSONArray();
            for(Object[] member : members) {
                JSONObject obj = new JSONObject();
                obj.put("id", member[0]);
                obj.put("is_leader", member[1]);
                obj.put("name", member[2] + " " + member[3]);
                obj.put("filename", member[4]);
                obj.put("email", member[5]);
                obj.put("degree", member[6]);

                jsonArray.add(obj);
            }
            jsonResult.put("msg", "success");
            jsonResult.put("data", jsonArray);

        }
        return jsonResult;
    }

    @Override
    public JSONObject updateDescription(Long id, String newDescription) {
        Group group = groupRepository.findById(id).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if(group == null) {
            jsonResult.put("msg", "Unable to find this group!");
            jsonResult.put("data", null);
        } else {
            group.setDescription(newDescription);
            Group result = groupRepository.save(group);
            JSONObject data = new JSONObject();
            data.put("description", result.getDescription());

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    @Override
    public JSONObject deleteAMember(Long groupId, Long userId) {
        JSONObject jsonResult = new JSONObject();
        // Check whether the current user is leader.
        // If yes, check the number of students in this group; otherwise, delete this student from this group directly.
        // If only one student in this group and is leader, delete this student and this group.
        // If more than one student in this group, delete this student and update is_leader state for other students.
        GroupUserId groupUserId = new GroupUserId(groupId, userId);
        GroupUser currentGroupUser = groupUserRepository.findById(groupUserId).orElse(null);
        if(currentGroupUser == null) {
            jsonResult.put("msg", "Unable to find this user in this group!");
            jsonResult.put("data", null);
        } else {
            if(currentGroupUser.isLeader() == false) {
                groupUserRepository.deleteById(groupUserId);
                jsonResult.put("msg", "Success!");
                jsonResult.put("data", null);
            } else {
                List<GroupUser> members = groupUserRepository.getGroupUserByGroupId(groupId).orElse(null);
                if(members.size() == 1) {
                    groupUserRepository.deleteById(groupUserId);
                    groupRepository.deleteById(groupId);
                    jsonResult.put("msg", "Success! The group is disband!");
                    jsonResult.put("data", null);
                } else {
                    groupUserRepository.deleteById(groupUserId);
                    List<GroupUser> remainingMembers = groupUserRepository.getGroupUserByGroupId(groupId).orElse(null);
                    GroupUser targetUser = remainingMembers.get(0);
                    targetUser.setLeader(true);
                    groupUserRepository.save(targetUser);

                    JSONObject data = new JSONObject();
                    data.put("new_leader", targetUser.getGroupUserId().getUserId());

                    jsonResult.put("msg", "Success! The group is disband!");
                    jsonResult.put("data", data);
                }
            }
        }

        return jsonResult;
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
