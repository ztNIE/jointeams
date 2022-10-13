package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Notification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface NotificationRepository extends CrudRepository<Notification, Long> {
    @Query("select n from Notification n where n.type in (0, 2, 3) and n.user.id = ?1")
    <E> List<E> findAllByUserId(Long userId);
    @Query("select n from Notification n where n.type in (1, 4, 5) and n.group.id = ?1")
    <E> List<E> findAllByGroupId(Long groupId);

    @Query("select count(n.id) from Notification n where n.group.id = ?1 and n.user.id = ?2")
    public Optional<Integer> countNotificationsByIds(Long groupId, Long userId);

    @Modifying
    @Transactional
    @Query("delete from Notification n where n.group.id = :groupId")
    void deleteAllByGroupId(long groupId);

    @Query(value="alter table `Notification` AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetIncrement();
}


