package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.Semester;
import com.jointeams.backend.pojo.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface GroupRepository extends CrudRepository<Group, Long> {
    @Query("select g from Group g join GroupUser gu on g.id = gu.groupUserId.groupId where g.course = ?1 and g.semester = ?2 and gu.groupUserId.userId = ?3")
    public Optional<Group> findByCourseAndSemesterAndUserId(Course course, Semester semester, Long userId);
}


