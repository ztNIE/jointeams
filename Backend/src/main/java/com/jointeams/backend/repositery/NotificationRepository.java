package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Group;
import com.jointeams.backend.pojo.Notification;
import com.jointeams.backend.pojo.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface NotificationRepository extends CrudRepository<Notification, Long> {
    @Query("select n from Notification n where n.type in (0, 2, 3) and n.user.id = ?1")
    public<T> List<T> findAllByUserId(Long userId);
    @Query("select n from Notification n where n.type in (1, 4, 5) and n.group.id = ?1")
    public<T> List<T> findAllByGroupId(Long groupId);

    @Query(value = "select count(id) from notification where group_id = ?1 and user_id = ?2", nativeQuery = true)
    public Optional<Integer> countNotificationsByIds(Long groupId, Long userId);
}


