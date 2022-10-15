package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.id.GroupUserId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//id->groupUserId???
public interface GroupUserRepository extends CrudRepository<GroupUser, GroupUserId> {
    @Query("select g from GroupUser g where g.groupUserId.userId = ?1 and g.isLeader = true")
    public<T> List<T> findALLAsLeaderByUserId(Long userId);

    @Query("select g from GroupUser g where g.groupUserId.groupId = ?1")
    public Optional<List<GroupUser>> getGroupUserByGroupId(Long groupId);

    @Query("select u.id, gu.isLeader, u.firstName, u.lastName, u.filename, u.email, u.degree from GroupUser gu inner join User u on gu.groupUserId.userId = u.id where gu.groupUserId.groupId = ?1")
    public Optional<List<Object[]>> getGroupUserDetailByGroupId(Long groupId);

    @Query("select distinct u.id, u.firstName, u.lastName, u.filename\n" +
            "from User u\n" +
            "    inner join Enrollment e\n" +
            "        on u.id = e.user.id\n" +
            "    inner join Semester s\n" +
            "        on s.id = e.semester.id\n" +
            "where s.isCurrent = true\n" +
            "  and e.course.id = ?1\n" +
            "  and u.isActivate = true\n" +
            "  and u.isAdmin = false\n" +
            "  and u.id not in (select distinct u.id\n" +
            "  from User u\n" +
            "      inner join Enrollment e\n" +
            "          on u.id = e.user.id\n" +
            "      inner join Semester s\n" +
            "          on s.id = e.semester.id\n" +
            "      inner join GroupUser gu\n" +
            "          on u.id = gu.groupUserId.userId\n" +
            "      inner join Group g\n" +
            "          on g.id = gu.groupUserId.groupId\n" +
            "  where s.isCurrent = true\n" +
            "  and e.course.id = ?1\n" +
            "  and u.isActivate = true\n" +
            "  and g.course.id = ?1\n" +
            "  and s.id = g.semester.id\n" +
            "  and u.isAdmin = false)")
    public Optional<List<Object[]>> getUserEnrolledInACurrentSemesterCourseWithoutAGroup(Long courseId);

    @Query("select distinct u.id, u.firstName, u.lastName, u.filename\n" +
            "from User u\n" +
            "         inner join Enrollment e\n" +
            "                    on u.id = e.user.id\n" +
            "         inner join Semester s\n" +
            "                    on s.id = e.semester.id\n" +
            "         inner join GroupUser gu\n" +
            "                    on u.id = gu.groupUserId.userId\n" +
            "where s.isCurrent = true\n" +
            "  and e.course.id = ?1\n" +
            "  and u.isActivate = true\n" +
            "  and u.isAdmin = false")
    public Optional<List<Object[]>> getUserEnrolledInACurrentSemesterCourseWithAGroup(Long courseId);

    @Query("select g.id\n" +
            "from Group g\n" +
            "    inner join Semester s\n" +
            "        on g.semester.id = s.id\n" +
            "    inner join GroupUser gu\n" +
            "        on g.id = gu.groupUserId.groupId\n" +
            "where s.isCurrent = true\n" +
            "and gu.groupUserId.userId = ?1")
    public Optional<List<Long>> getCurrentGroupIdsOfAUser(Long userId);

    @Query("select g.id, g.capacity, g.nameId, c.code, u.firstName, u.lastName, u.filename\n" +
            "from Group g\n" +
            "    inner join Semester s\n" +
            "        on g.semester.id = s.id\n" +
            "    inner join Course c\n" +
            "        on g.course.id = c.id\n" +
            "    inner join GroupUser gu\n" +
            "        on g.id = gu.groupUserId.groupId\n" +
            "    inner join User u\n" +
            "        on gu.groupUserId.userId = u.id\n" +
            "where g.id in ?1\n" +
            "  and s.isCurrent = true")
    public Optional<List<Object[]>> getAllGroupDetailByIds(List<Long> ids);

    @Query("select count (g.groupUserId.userId) from GroupUser g where g.groupUserId.groupId = ?1")
    public int countByGroupId(Long groupId);

    @Query("select g.groupUserId.groupId from GroupUser g where g.groupUserId.userId = :userId")
    public List<Long> getAllGroups(Long userId);

    @Query("select g.groupUserId.userId from GroupUser g where g.groupUserId.groupId = :groupId and not g.groupUserId.userId = :userId")
    public List<Long> getAllTeammates(Long userId, Long groupId);

    @Modifying
    @Transactional
    @Query("delete from GroupUser gu where gu.groupUserId.groupId = ?1")
    void deleteAllByGroupId(Long groupId);
}


