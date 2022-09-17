package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.pojo.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentRepository extends CrudRepository<Comment, Long> {
    //    get current courses of a user
    @Query(value = "select c from Comment c where c.receiver.id = :userId")
    List<Comment> findAllCommentsByUserId(Long userId);

    @Query(value = "select * from comment where group_id = ?1 and sender_id = ?2 and receiver_id = ?3", nativeQuery = true)
    public Optional<List<Comment>> getCommentByIds(Long groupId, Long senderId, Long receiverId);

    @Modifying
    @Query(value = "delete from comment where group_id = ?1 and sender_id = ?2 and receiver_id = ?3", nativeQuery = true)
    public void deleteCommentByIds(Long groupId, Long senderId, Long receiverId);
}
