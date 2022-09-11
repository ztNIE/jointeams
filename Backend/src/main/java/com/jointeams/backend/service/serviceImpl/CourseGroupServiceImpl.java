package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.GroupRepository;
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

    @Override
    public JSONObject getAllGroupsByCourseId(Long courseId, Long userId) {
        JSONObject jsonResult = new JSONObject();
        List<Object[]> elements = groupRepository.getAllCurrentGroupsByCourseId(courseId).orElse(null);

        if(elements.size() == 0) {
            jsonResult.put("msg", "No group in this course in this semester!");
            jsonResult.put("data", null);
        } else {
            JSONObject currentUserGroupKey = null;
            BigInteger targetGroupId = null;
            HashMap<JSONObject, ArrayList<JSONObject>> map = new HashMap<JSONObject, ArrayList<JSONObject>>();
            for(Object[] element : elements) {
                JSONObject group = new JSONObject();
                group.put("group_id", element[0]);
                group.put("group_name", element[7] + "_Group" + element[3]);
                group.put("tutorial", element[4]);
                group.put("capacity", element[1]);
                group.put("is_current_user_group", false);

                JSONObject member = new JSONObject();
                member.put("name", element[8] + " " + element[9]);
                member.put("avatar", element[10]);
                member.put("id", element[11]);

                if(((BigInteger) element[11]).longValue() == userId) {
                    group.put("is_current_user_group", true);
                    currentUserGroupKey = group;
                    targetGroupId = (BigInteger) element[0];
                } else if(((BigInteger) element[0]).equals(targetGroupId)) {
                    group.put("is_current_user_group", true);
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

                JSONObject targetGroup = currentUserGroupKey;
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
                data.add(element);
            }

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);

        }

        return jsonResult;
    }

    @Override
    public JSONObject addAGroup(Integer capacity, Long course_id, Long user_id) {
        return null;
    }
}
