package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.PastCourse;
import com.jointeams.backend.pojo.id.PastCourseId;
import org.springframework.data.repository.CrudRepository;

//id->pastCourseId?
public interface PastCourseRepository extends CrudRepository<PastCourse, PastCourseId> {

}


