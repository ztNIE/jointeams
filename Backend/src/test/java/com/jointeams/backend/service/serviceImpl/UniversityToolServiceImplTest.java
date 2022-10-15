package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.UniversityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UniversityToolServiceImplTest {
    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private UniversityToolServiceImpl universityToolService;

    @Test
    public void findByIdTest() {
        Long id = 1L;
        assertNull(universityToolService.findById(id));
        University university = new University();
        university.setName("TestUniversityName");
        university.setRegex("TestUniversityRegex");
        universityRepository.save(university);
        assertNotNull(universityToolService.findById(id));
    }
}
