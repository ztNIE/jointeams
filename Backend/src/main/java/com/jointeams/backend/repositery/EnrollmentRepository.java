package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.Enrollment;
import com.jointeams.backend.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.Optional;

import java.util.List;


public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
    //    get current courses of a user
    @Query(value = "select e.course from Enrollment e where e.user.id = :userId and e.semester.isCurrent=true")
    List<Course> findAllCurrentCourseByUserId(Long userId);

    //    get previous courses of a user
    @Query(value = "select e.course from Enrollment e where e.user.id = :userId and e.semester.isCurrent=false")
    List<Course> findAllPreviousCourseByUserId(Long userId);

    // Get an enrollment tutorial information in current semester information by userId and courseId.
    @Query(value = "select e.tutorial, e.semester_id\n" +
            "from `enrollment` as e\n" +
            "inner join `semester` s\n" +
            "    on e.semester_id = s.id\n" +
            "where s.is_current = true\n" +
            "and e.user_id = ?2\n" +
            "and e.course_id = ?1", nativeQuery = true)
    Optional<List<Object[]>> getCurrentEnrollmentTutorialByCourseIdAndUserId(Long courseId, Long userId);

    //    get previous students of a course
    @Query(value = "select e.user from Enrollment e where e.course.id = :courseId and e.semester.isCurrent=false")
    List<User> findAllPreviousStudentsByCourseId(Long courseId);

    //    get current students of a course
    @Query(value = "select e.user from Enrollment e where e.course.id = :courseId and e.semester.isCurrent=true")
    List<User> findAllCurrentStudentsByCourseId(Long courseId);

    @Query("delete from Enrollment e where e.course.id=?1")
    @Modifying
    @Transactional
    void deleteEnrollmentByCourseId(Long courseId);

    //    check if a student enrolled in a course
    @Query(value = "select e from Enrollment e where e.user.id = :userId and e.course.id=:courseId")
    List<Enrollment> findEnrollmentByUserIdAndCourseId(Long userId, Long courseId);
}


