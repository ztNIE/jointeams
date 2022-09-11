package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface GroupRepository extends CrudRepository<Group, Long> {
    @Query(value = "select g.id as `group_id`, g.capacity, g.description, g.name_id, g.tutorial, g.course_id, g.semester_id, c.code, u.first_name, u.last_name, u.filename, u.id as `user_id`\n" +
            "from `group` as g\n" +
            "    inner join `semester` as s\n" +
            "        on g.semester_id = s.id\n" +
            "    inner join `course` c\n" +
            "        on g.course_id = c.id\n" +
            "    inner join group_user gu\n" +
            "        on g.id = gu.group_id\n" +
            "    inner join user u\n" +
            "        on gu.user_id = u.id\n" +
            "where g.course_id = ?1\n" +
            "  and s.is_current = true;", nativeQuery = true)
    public Optional<List<Object[]>> getAllCurrentGroupsByCourseId(Long courseId);
}


