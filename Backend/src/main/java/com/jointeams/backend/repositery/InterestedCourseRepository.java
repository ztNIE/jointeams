package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.InterestedCourseKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InterestedCourseRepository extends CrudRepository<InterestedCourse, InterestedCourseKey> {
    @Query("select i from InterestedCourse i where i.id.userId = :userId")
    public List<InterestedCourse> findByUserId(Long userId);

    @Query("select i from InterestedCourse i where i.id.userId = :userId and i.id.courseId = :courseId")
    public InterestedCourse findByUserIdAndCourseId(Long userId, Long courseId);
}
