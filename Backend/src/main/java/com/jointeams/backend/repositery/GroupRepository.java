package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Group;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface GroupRepository extends CrudRepository<Group, Long> {
}


