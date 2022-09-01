package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.GroupUser;
import com.jointeams.backend.pojo.id.GroupUserId;
import org.springframework.data.repository.CrudRepository;

//id->groupUserId???
public interface GroupUserRepository extends CrudRepository<GroupUser, GroupUserId> {

}


