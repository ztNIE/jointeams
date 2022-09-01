package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}
