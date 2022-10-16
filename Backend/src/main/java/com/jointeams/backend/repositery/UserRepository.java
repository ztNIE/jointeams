package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);

    Boolean existsByEmailAndIsActivateTrue(String email);

    @Query("select distinct u from Group g " +
            "    inner join Semester s on g.semester.id = s.id " +
            "    inner join GroupUser gu on g.id = gu.groupUserId.groupId " +
            "    inner join User u on gu.groupUserId.userId = u.id "+
            "    where s.isCurrent = true")
    <E>List<E> findAllUsersHavingGroupsInTheCurrentSemester();
/**************************如果用mysql原生写法则如下*************************/
//    @Query(value = "select distinct u.* " +
//            "    from `group` as g"  +
//            "    inner join `semester` as s on g.semester_id = s.id " +
//            "    inner join `group_user` as gu on g.id = gu.group_id " +
//            "    inner join `user` as u on gu.user_id = u.id "+
//            "    where s.is_current = true;", nativeQuery = true)
//    nativeQuery必须为true
/************************************************************************/

    @Query(value="alter table `user` AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetIncrement();
}


