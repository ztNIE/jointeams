package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.CourseGroupService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
public class CourseGroupServiceImpl implements CourseGroupService {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Override
    public JSONObject getAllGroupsByCourseId(Long courseId, Long userId) {
        JSONObject jsonResult = new JSONObject();
        List<Object[]> elements = groupRepository.getAllCurrentGroupsByCourseId(courseId).orElse(null);

        if(elements.size() == 0) {
            jsonResult.put("msg", "No group in this course in this semester!");
            jsonResult.put("data", null);
        } else {
            JSONObject currentUserGroupKey = null;
            Long targetGroupId = null;
            HashMap<JSONObject, ArrayList<JSONObject>> map = new HashMap<JSONObject, ArrayList<JSONObject>>();
            for(Object[] element : elements) {
                JSONObject group = new JSONObject();
                group.put("group_id", element[0]);
                group.put("group_name", element[7] + "_Group" + element[3]);
                group.put("tutorial", element[4]);
                group.put("capacity", element[1]);

                JSONObject member = new JSONObject();
                member.put("name", element[8] + " " + element[9]);
                member.put("avatar", element[10]);
                member.put("id", element[11]);

                if(((Long) element[11]).longValue() == userId) {
                    currentUserGroupKey = group;
                    targetGroupId = (Long) element[0];
                }

                if(map.get(group) == null) {
                    map.put(group, new ArrayList<>());
                }
                map.get(group).add(member);
            }

            JSONArray data = new JSONArray();
            // Deal with the group with the target user first.
            if(currentUserGroupKey != null) {
                JSONArray currentGroupMembers = new JSONArray();
                for(JSONObject v: map.get(currentUserGroupKey)) {
                    currentGroupMembers.add(v);
                }

                JSONObject targetGroup = new JSONObject();
                targetGroup.put("group_id", currentUserGroupKey.get("group_id"));
                targetGroup.put("group_name", currentUserGroupKey.get("group_name"));
                targetGroup.put("tutorial", currentUserGroupKey.get("tutorial"));
                targetGroup.put("capacity", currentUserGroupKey.get("capacity"));
                targetGroup.put("is_current_user_group", true);
                targetGroup.put("members", currentGroupMembers);
                data.add(targetGroup);
            }

            // Deal with other groups.
            for (HashMap.Entry<JSONObject, ArrayList<JSONObject>> entry : map.entrySet()) {
                JSONObject key = entry.getKey();
                if(currentUserGroupKey != null && key.equals(currentUserGroupKey)) {
                    continue;
                }
                ArrayList<JSONObject> value = entry.getValue();
                JSONArray members = new JSONArray();
                for(JSONObject v: value) {
                    members.add(v);
                }

                JSONObject element = key;
                element.put("members", members);
                element.put("is_current_user_group", false);
                data.add(element);
            }

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);

        }

        return jsonResult;
    }

    @Override
    public JSONObject addAGroup(Long courseId, Long userId, Integer capacity) {
        JSONObject jsonResult = new JSONObject();

        // Find corresponding tutorial and semester.
        List<Object[]> information = enrollmentRepository.getCurrentEnrollmentTutorialByCourseIdAndUserId(courseId, userId).orElse(null);
        String currentStudentTutorial = (String) null;
        Long currentSemester = (Long) null;
        if(information == null || information.size() == 0) {
            jsonResult.put("msg", "You do not enroll in this course this semester!");
            jsonResult.put("data", null);
            return jsonResult;
        } else {
            currentStudentTutorial = (String) information.get(0)[0];
            currentSemester = ((BigInteger) information.get(0)[1]).longValue();
        }

        // Check whether the student already have a group
        Course course = courseRepository.findById(courseId).orElse(null);
        Semester semester = semesterRepository.findById(currentSemester).orElse(null);

        List<Object[]> groupsExisted = groupRepository.isStudentAlreadyInAGroup(course.getId(), semester.getId(), userId).orElse(null);
        if(groupsExisted != null && groupsExisted.size() > 0) {
            jsonResult.put("msg", "You are already in a group!");
            jsonResult.put("data", null);
            return jsonResult;
        }

        // Add a group.
        Group newGroup = new Group();
        newGroup.setCapacity(capacity);
        newGroup.setNameId(course.getNextGroupNameId());
        newGroup.setTutorial(currentStudentTutorial);
        newGroup.setCourse(course);
        newGroup.setSemester(semester);
        Group result = groupRepository.save(newGroup);

        // Add a groupUser (is_leader: true).
        GroupUser groupUser = new GroupUser();
        GroupUserId groupUserId = new GroupUserId(result.getId(), userId);
        groupUser.setGroupUserId(groupUserId);
        groupUser.setLeader(true);
        GroupUser groupUserResult = groupUserRepository.save(groupUser);

        // Update next group name id in course.
        course.setNextGroupNameId(course.getNextGroupNameId() + 1);
        courseRepository.save(course);

        jsonResult.put("msg", "Success!");
        jsonResult.put("data", new JSONObject().toJSONString());
        return jsonResult;
    }
}
