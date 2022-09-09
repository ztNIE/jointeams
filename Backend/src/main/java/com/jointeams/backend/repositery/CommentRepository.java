package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.pojo.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    //    get current courses of a user
    @Query(value = "select c from Comment c where c.receiver.id = :userId")
    List<Comment> findAllCommentsByUserId(Long userId);
}
