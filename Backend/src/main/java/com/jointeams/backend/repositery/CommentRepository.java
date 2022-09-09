package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends CrudRepository<Comment, Long> {
//    @Query(value = "select id, is_leader, first_name, last_name, filename, email, degree from group_user inner join user on group_user.user_id = user.id where group_id = ?1", nativeQuery = true)
//    public Optional<List<Object[]>> getGroupUserDetailByGroupId(Long groupId);

    @Query(value = "select * from comment where group_id = ?1 and sender_id = ?2 and receiver_id = ?3", nativeQuery = true)
    public Optional<List<Comment>> getCommentByIds(Long groupId, Long senderId, Long receiverId);
}
