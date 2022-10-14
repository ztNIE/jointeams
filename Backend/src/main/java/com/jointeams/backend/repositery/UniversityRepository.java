package com.jointeams.backend.repositery;

import com.jointeams.backend.pojo.University;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UniversityRepository extends CrudRepository<University, Long> {

    Optional<University> findUniversityByName(String name);

    @Query(value="alter table `university` AUTO_INCREMENT = 1", nativeQuery = true)
    public void resetIncrement();
}