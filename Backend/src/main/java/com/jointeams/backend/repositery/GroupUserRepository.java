package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.pojo.id.GroupUserId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

//id->groupUserId???
public interface GroupUserRepository extends CrudRepository<GroupUser, GroupUserId> {
    @Query("select g from GroupUser g where g.groupUserId.userId = ?1 and g.isLeader = true")
    public<T> List<T> findALLAsLeaderByUserId(Long userId);

    @Query(value = "select * from group_user where group_id = ?1", nativeQuery = true)
    public Optional<List<GroupUser>> getGroupUserByGroupId(Long groupId);

    @Query(value = "select id, is_leader, first_name, last_name, filename, email, degree from group_user inner join user on group_user.user_id = user.id where group_id = ?1", nativeQuery = true)
    public Optional<List<Object[]>> getGroupUserDetailByGroupId(Long groupId);

    @Query(value = "select u.id, u.first_name, u.last_name, u.filename\n" +
            "from user as u\n" +
            "    inner join enrollment as e\n" +
            "        on u.id = e.user_id\n" +
            "    inner join semester as s\n" +
            "        on s.id = e.semester_id\n" +
            "where s.is_current = true\n" +
            "  and e.course_id = ?1\n" +
            "  and u.is_activate = true\n" +
            "  and u.is_admin = false\n" +
            "  and u.id not in (select u.id\n" +
            "  from user as u\n" +
            "      inner join enrollment as e\n" +
            "          on u.id = e.user_id\n" +
            "      inner join semester as s\n" +
            "          on s.id = e.semester_id\n" +
            "      inner join group_user as gu\n" +
            "          on u.id = gu.user_id\n" +
            "  where s.is_current = true\n" +
            "  and e.course_id = ?1\n" +
            "  and u.is_activate = true\n" +
            "  and u.is_admin = false)", nativeQuery = true)
    public Optional<List<Object[]>> getUserEnrolledInACurrentSemesterCourseWithoutAGroup(Long courseId);

    @Query(value = "select u.id, u.first_name, u.last_name, u.filename\n" +
            "from user as u\n" +
            "         inner join enrollment as e\n" +
            "                    on u.id = e.user_id\n" +
            "         inner join semester as s\n" +
            "                    on s.id = e.semester_id\n" +
            "         inner join group_user as gu\n" +
            "                    on u.id = gu.user_id\n" +
            "where s.is_current = true\n" +
            "  and e.course_id = ?1\n" +
            "  and u.is_activate = true\n" +
            "  and u.is_admin = false", nativeQuery = true)
    public Optional<List<Object[]>> getUserEnrolledInACurrentSemesterCourseWithAGroup(Long courseId);

    @Query("select count (g.groupUserId.userId) from GroupUser g where g.groupUserId.groupId = ?1")
    public int countByGroupId(Long groupId);

    @Query("select g.groupUserId.groupId from GroupUser g where g.groupUserId.userId = :userId")
    public List<Long> getAllGroups(Long userId);

    @Query("select g.groupUserId.userId from GroupUser g where g.groupUserId.groupId = :groupId and not g.groupUserId.userId = :userId")
    public List<Long> getAllTeammates(Long userId, Long groupId);
}


