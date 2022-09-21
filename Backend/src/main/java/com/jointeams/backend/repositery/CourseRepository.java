package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends CrudRepository<Course, Long> {
    //    find all course of a university
    @Query(value = "select c from Course c where c.university.id = :uniId")
    List<Course> findAllCourse(Long uniId);

    public <T> Optional<T> findCourseByCode(String code);
    public <T> Optional<T> findCourseByName(String code);
}


