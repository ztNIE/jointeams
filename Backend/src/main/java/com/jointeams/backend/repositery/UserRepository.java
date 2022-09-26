package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Iterable<User> findByIsAdminFalse();

    @Query("select distinct u from Group g " +
            "    inner join Semester s on g.semester.id = s.id " +
            "    inner join GroupUser gu on g.id = gu.groupUserId.groupId " +
            "    inner join User u on gu.groupUserId.userId = u.id "+
            "    where s.isCurrent = true")
    <E>List<E> findAllUsersHavingGroupsInTheCurrentSemester();
//    public Optional<User> findByLastName(String lastName);
//    public Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
//    @Query("select u from User u where u.firstName = ?1 and u.lastName = ?2")；
//    public Optional<User> findByFullName(String firstName, String lastName);

//    @Query("select u from User u where u.firstName like %?1% and u.lastName like %?2%")；
//    public Optional<User> findByIncompleteFullName(String firstName, String lastName);
}


