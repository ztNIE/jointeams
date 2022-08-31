package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}

