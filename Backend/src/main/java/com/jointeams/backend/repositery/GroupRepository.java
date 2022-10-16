package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.Semester;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface GroupRepository extends CrudRepository<Group, Long> {
    @Query("select g from Group g join GroupUser gu on g.id = gu.groupUserId.groupId where g.course = ?1 and g.semester = ?2 and gu.groupUserId.userId = ?3")
    public Optional<Group> findByCourseAndSemesterAndUserId(Course course, Semester semester, Long userId);

    @Query("select g.id as group_id, g.capacity, g.description, g.nameId, g.tutorial, g.course.id, g.semester.id, c.code, u.firstName, u.lastName, u.filename, u.id as user_id\n" +
            "from Group g\n" +
            "    inner join Semester s\n" +
            "        on g.semester.id = s.id\n" +
            "    inner join Course c\n" +
            "        on g.course.id = c.id\n" +
            "    inner join GroupUser gu\n" +
            "        on g.id = gu.groupUserId.groupId\n" +
            "    inner join User u\n" +
            "        on gu.groupUserId.userId = u.id\n" +
            "where g.course.id = ?1\n" +
            "  and s.isCurrent = true")
    public Optional<List<Object[]>> getAllCurrentGroupsByCourseId(Long courseId);

    @Query("select g.id \n" +
            "from Group g\n" +
            "inner join GroupUser gu on g.id = gu.groupUserId.groupId\n" +
            "where gu.groupUserId.userId = ?3\n" +
            "and g.semester.id = ?2\n" +
            "and g.course.id = ?1")
    public Optional<List<Object[]>> isStudentAlreadyInAGroup(Long courseId, Long semesterId, Long userId);

    @Modifying
    @Query(nativeQuery = true, value = "ALTER TABLE Group ALTER COLUMN Group.id RESTART WITH 1")
    public void resetIncrement();

    @Query("select g from Group g where g.course.id = ?1")
    List<Group> getGroupsByCourseId(Long courseId);
}


