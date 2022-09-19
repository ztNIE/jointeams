package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.Semester;
import com.jointeams.backend.repositery.GroupRepository;
import com.jointeams.backend.service.GroupToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupToolServiceImpl implements GroupToolService {

    @Autowired
    private GroupRepository groupRepository;

    @Override
    public Group findByCourseAndSemesterAndUserId(Course course, Semester semester, Long userId)
    {
        return groupRepository.findByCourseAndSemesterAndUserId(course, semester, userId).orElse(null);
    }
}
