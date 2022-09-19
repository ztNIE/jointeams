package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.repositery.CourseRepository;
import com.jointeams.backend.service.CourseToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseToolServiceImpl implements CourseToolService{

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void reSetNextGroupNameId() {
        List<Course> courses = (List<Course>) courseRepository.findAll();
        courses.forEach(course -> course.setNextGroupNameId(1));
        courseRepository.saveAll(courses);
    }
}
