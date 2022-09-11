package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.Enrollment;
import com.jointeams.backend.pojo.User;
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

    //    get previous students of a course
    @Query(value = "select e.user from Enrollment e where e.course.id = :courseId and e.semester.isCurrent=false")
    List<User> findAllPreviousStudentsByCourseId(Long courseId);

    //    get current students of a course
    @Query(value = "select e.user from Enrollment e where e.course.id = :courseId and e.semester.isCurrent=true")
    List<User> findAllCurrentStudentsByCourseId(Long courseId);

    //    check if a student enrolled in a course
    @Query(value = "select e from Enrollment e where e.user.id = :userId and e.course.id=:courseId")
    List<Enrollment> findEnrollmentByUserIdAndCourseId(Long userId, Long courseId);
}


