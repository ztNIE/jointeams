package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.id.GroupUserId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

//id->groupUserId???
public interface GroupUserRepository extends CrudRepository<GroupUser, GroupUserId> {

    @Query(value = "select * from group_user where group_id = ?1", nativeQuery = true)
    public Optional<List<GroupUser>> getGroupUserByGroupId(Long groupId);
}


