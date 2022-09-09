package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.Enrollment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
    //    get current courses of a user
    @Query(value = "select e.course from Enrollment e where e.user.id = :userId and e.semester.isCurrent=true")
    List<Course> findAllCurrentCourseByUserId(Long userId);

    //    get previous courses of a user
    @Query(value = "select e.course from Enrollment e where e.user.id = :userId and e.semester.isCurrent=false")
    List<Course> findAllPreviousCourseByUserId(Long userId);
}


