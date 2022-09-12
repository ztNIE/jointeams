package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Course;
import com.jointeams.backend.pojo.Semester;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface SemesterRepository extends CrudRepository<Semester, Long> {
    //    find the current semester
    @Query(value = "select s from Semester s where s.isCurrent=true")
    Semester findCurrentSemester();
}


