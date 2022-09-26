package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Iterable<User> findAllByAdminIsFalse();
//    public Optional<User> findByLastName(String lastName);
//    public Optional<User> findByFirstNameAndLastName(String firstName, String lastName);
//    @Query("select u from User u where u.firstName = ?1 and u.lastName = ?2")；
//    public Optional<User> findByFullName(String firstName, String lastName);

//    @Query("select u from User u where u.firstName like %?1% and u.lastName like %?2%")；
//    public Optional<User> findByIncompleteFullName(String firstName, String lastName);
}


