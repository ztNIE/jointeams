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

    @Query(value = "select id, is_leader, first_name, last_name, filename, email, degree from group_user inner join user where group_id = ?1", nativeQuery = true)
    public Optional<List<Object[]>> getGroupUserDetailByGroupId(Long groupId);
}


