package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.GroupService;
import com.jointeams.backend.util.IsCommentAvailable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

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
            data.put("course", group.getCourse().getCode());
            data.put("course_id", group.getCourse().getId());
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
                    commentRepository.deleteAllByGroupId(groupId);
                    notificationRepository.deleteAllByGroupId(groupId);
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

                    jsonResult.put("msg", "Success! Leadership is handed over.");
                    jsonResult.put("data", data);
                }
            }
        }

        return jsonResult;
    }

    @Override
    public JSONObject isCommentFunctionAvailable() {
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("msg", "Success");
        JSONObject data = new JSONObject();
        data.put(IsCommentAvailable.Flag.getKey(),IsCommentAvailable.Flag.getValue());
        jsonResult.put("data", data);
        return jsonResult;
    }

    @Override
    public JSONObject getCommentById(Long groupId, Long senderId, Long receiverId) {
        List<Comment> comments = commentRepository.getCommentByIds(groupId, senderId, receiverId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if(comments.size() == 0) {
            jsonResult.put("msg", "No comment exists.");
            jsonResult.put("data", null);
        } else {
            Comment comment = comments.get(0);
            JSONObject data = new JSONObject();
            data.put("id", comment.getId());
            data.put("content", comment.getContent());
            data.put("tag", comment.getTag());
            data.put("receiver_name", comment.getReceiver().getFirstName() + " " + comment.getReceiver().getLastName());
            data.put("timestamp", comment.getTimestamp());
            data.put("is_hide", comment.getIsHide());
            data.put("group_id", comment.getGroup().getId());
            data.put("sender_id", comment.getSender().getId());
            data.put("receiver_id", comment.getReceiver().getId());

            jsonResult.put("msg", "Comment exists.");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    @Override
    public JSONObject leaveComment(Long groupId, Long senderId, Long receiverId, Integer tag, String content) {
        JSONObject jsonResult = new JSONObject();
        Group group = groupRepository.findById(groupId).orElse(null);
        if(group == null) {
            jsonResult.put("msg", "Unable to find such a group.");
            jsonResult.put("data", null);
            return jsonResult;
        }
        User sender = userRepository.findById(senderId).orElse(null);
        User receiver = userRepository.findById(receiverId).orElse(null);
        if(sender == null || receiver == null) {
            jsonResult.put("msg", "Unable to find such a user.");
            jsonResult.put("data", null);
            return jsonResult;
        }

        // If a comment with give group, sender and receiver is existing, delete it; else, nothing delete.
        commentRepository.deleteCommentByIds(groupId, senderId, receiverId);

        Comment comment = new Comment();
        comment.setGroup(group);
        comment.setReceiver(receiver);
        comment.setSender(sender);
        comment.setContent(content);
        comment.setTag(tag);
        comment.setIsHide(false);

        Comment result = commentRepository.save(comment);

        JSONObject data = new JSONObject();
        data.put("content", result.getContent());
        data.put("tag", result.getTag());
        data.put("timeStamp", result.getTimestamp());

        jsonResult.put("msg", "Success!");
        jsonResult.put("data", data);
        return jsonResult;
    }

    @Override
    public JSONObject getStudentsNotInAGroup(Long courseId) {
        JSONObject jsonResult = new JSONObject();
        List<Object[]> users = groupUserRepository.getUserEnrolledInACurrentSemesterCourseWithoutAGroup(courseId).orElse(null);

        if(users.size() == 0) {
            jsonResult.put("msg", "No such a student!");
            jsonResult.put("data", null);
        } else {
            JSONArray jsonArray = new JSONArray();
            for(Object[] user : users) {
                JSONObject obj = new JSONObject();
                obj.put("id", user[0]);
                obj.put("name", user[1] + " " + user[2]);
                obj.put("filename", user[3]);

                jsonArray.add(obj);
            }
            jsonResult.put("msg", "success");
            jsonResult.put("data", jsonArray);

        }

        return jsonResult;
    }

    @Override
    public JSONObject addInvitationNotification(Long groupId, Long userId) {
        JSONObject jsonResult = new JSONObject();

        // Check whether there is already a notification with the given groupId (sender) and userId (receiver).
        Integer num = notificationRepository.countNotificationsByIds(groupId, userId).orElse(null);
        if(num != 0) {
            jsonResult.put("msg", "You have already sent an invitation to this user!");
            jsonResult.put("data", null);
            return jsonResult;
        }

        // Check whether the user is already in this group.
        GroupUserId groupUserId = new GroupUserId(groupId, userId);
        GroupUser groupUser = groupUserRepository.findById(groupUserId).orElse(null);
        if(groupUser != null) {
            jsonResult.put("msg", "This student is already in your group!");
            jsonResult.put("data", null);
            return jsonResult;
        }

        // Check whether the group is full.
        Group group = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        Integer capacity = group.getCapacity();
        List<GroupUser> groupUsers = groupUserRepository.getGroupUserByGroupId(groupId).orElse(new ArrayList<GroupUser>());
        if(groupUsers.size() == capacity) {
            jsonResult.put("msg", "Your group is already full!");
            jsonResult.put("data", null);
            return jsonResult;
        }

        // Otherwise, add a notification sending from group to this user.
        Notification notification = new Notification();
        Integer type = 0;
        String message = "A Group Invitation sent from " + group.getCourse().getCode() + "_Group" + group.getNameId() + ".";
        String content = "Dear " + user.getFirstName() + ",\n" + "The Group " + group.getNameId() + " from Unit of Study " + group.getCourse().getCode() + " invites you to join there group. For more information, please go to their group page.";

        notification.setContent(content);
        notification.setMessage(message);
        notification.setType(type);
        notification.setGroup(group);
        notification.setUser(user);

        notificationRepository.save(notification);

        jsonResult.put("msg", "An invitation sent!");
        jsonResult.put("data", null);

        return jsonResult;
    }

    @Override
    public JSONObject addJoinRequestNotification(Long groupId, Long userId) {
        JSONObject jsonResult = new JSONObject();

        // Check whether there is already a notification with the given groupId (receiver) and userId (sender).
        Integer num = notificationRepository.countNotificationsByIds(groupId, userId).orElse(null);
        if(num != 0) {
            jsonResult.put("msg", "You have already sent a joining request to this group!");
            jsonResult.put("data", null);
            return jsonResult;
        }

        // Check whether the user is already in this group.
        GroupUserId groupUserId = new GroupUserId(groupId, userId);
        GroupUser groupUser = groupUserRepository.findById(groupUserId).orElse(null);
        if(groupUser != null) {
            jsonResult.put("msg", "You are already in this group!");
            jsonResult.put("data", null);
            return jsonResult;
        }

        // Check whether the group is full.
        Group group = groupRepository.findById(groupId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        Integer capacity = group.getCapacity();
        List<GroupUser> groupUsers = groupUserRepository.getGroupUserByGroupId(groupId).orElse(new ArrayList<GroupUser>());
        if(groupUsers.size() == capacity) {
            jsonResult.put("msg", "This group is already full!");
            jsonResult.put("data", null);
            return jsonResult;
        }

        // Otherwise, add a notification sending from this user to this group.
        Notification notification = new Notification();
        Integer type = 1;
        String message = "A Joining Request sent from " + user.getFirstName() + " " + user.getLastName() + ".";
        String content = "Dear " + group.getCourse().getCode() + "_Group" + group.getNameId() + ",\n" + user.getFirstName() + " " + user.getLastName() + " applies to join your group. For more information, please go to profile.";

        notification.setContent(content);
        notification.setMessage(message);
        notification.setType(type);
        notification.setGroup(group);
        notification.setUser(user);

        notificationRepository.save(notification);

        jsonResult.put("msg", "An invitation sent!");
        jsonResult.put("data", null);

        return jsonResult;
    }

    @Override
    public JSONObject getAllCurrentGroupsOfAUser(Long userId) {
        JSONObject jsonResult = new JSONObject();

        List<Long> groupIds = groupUserRepository.getCurrentGroupIdsOfAUser(userId).orElse(null);
        if(groupIds.size() == 0) {
            jsonResult.put("msg", "Success");
            jsonResult.put("data", new JSONArray());
        }

        List<Object[]> groupDetails = groupUserRepository.getAllGroupDetailByIds(groupIds).orElse(null);
        JSONArray data = new JSONArray();
        // select g.id, g.capacity, g.name_id, c.code, u.first_name, u.last_name, u.filename
        List<Long> addedGroupIds = new ArrayList<>();
        for(Object[] element: groupDetails) {
            Long groupId = ((Long) element[0]).longValue();
            if(!addedGroupIds.contains(groupId)) {
                JSONObject obj = new JSONObject();
                obj.put("group_id", groupId);
                obj.put("capacity", (Integer) element[1]);
                obj.put("group_name", element[3] + "_Group" + element[2]);
                JSONArray members = new JSONArray();
                JSONObject student = new JSONObject();
                student.put("name", element[4] + " " + element[5]);
                student.put("avatar", element[6]);
                members.add(student);
                obj.put("members", members);

                addedGroupIds.add(groupId);

                data.add(obj);
            } else {
                JSONObject student = new JSONObject();
                student.put("name", element[4] + " " + element[5]);
                student.put("avatar", element[6]);

                int index = addedGroupIds.indexOf(groupId);
                JSONObject temp = (JSONObject) data.get(index);
                JSONArray temp_members = (JSONArray) temp.get("members");
                temp_members.add(student);
                temp.put("members", temp_members);

                data.remove(index);
                data.add(index, temp);
            }
        }

        jsonResult.put("msg", "Success");
        jsonResult.put("data", data);

        return jsonResult;

    }
}
