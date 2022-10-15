package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.University;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface CourseRepository extends CrudRepository<Course, Long> {
    //    find all course of a university
    @Query(value = "select c from Course c where c.university.id = :uniId")
    List<Course> findAllCourse(Long uniId);

    <T> Optional<T> findCourseByCodeAndUniversity(String code, University universityId);
    <T> Optional<T> findCourseByNameAndUniversity(String name, University universityId);

    @Query(value="alter table `course` AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetIncrement();
}


