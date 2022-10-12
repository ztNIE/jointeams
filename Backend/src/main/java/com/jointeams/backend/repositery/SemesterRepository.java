package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.Semester;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface SemesterRepository extends CrudRepository<Semester, Long> {
    //    find the current semester
    @Query(value = "select s from Semester s where s.isCurrent=true")
    Semester findCurrentSemester();
    public Optional<Semester> findSemesterByYearAndSemesterNumber(int year, int semesterNumber);
    public Optional<Semester> findSemestersByIsCurrent(boolean isCurrent);

    @Query(value="alter table `semester` AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetIncrement();
}

