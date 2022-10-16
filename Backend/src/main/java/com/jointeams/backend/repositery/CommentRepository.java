package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Comment;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    @Query(value = "select c from Comment c where c.receiver.id = :userId")
    List<Comment> findAllCommentsByUserId(Long userId);

    @Query("select c from Comment c where c.group.id = ?1 and c.sender.id = ?2 and c.receiver.id = ?3")
    Optional<List<Comment>> getCommentByIds(Long groupId, Long senderId, Long receiverId);

    @Modifying
    @Transactional
    @Query("delete from Comment c where c.group.id = ?1 and c.sender.id = ?2 and c.receiver.id = ?3")
    public void deleteCommentByIds(Long groupId, Long senderId, Long receiverId);

    @Modifying
    @Transactional
    @Query("delete from Comment c where c.group.id = ?1")
    void deleteAllByGroupId(Long groupId);
}
