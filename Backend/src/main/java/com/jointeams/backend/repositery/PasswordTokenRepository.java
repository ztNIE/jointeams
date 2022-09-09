package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.token.PasswordToken;
import org.springframework.data.repository.CrudRepository;

public interface PasswordTokenRepository extends CrudRepository<PasswordToken, Long> {
}
