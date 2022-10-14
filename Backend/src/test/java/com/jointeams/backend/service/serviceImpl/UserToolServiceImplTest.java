package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.University;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.UniversityRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.service.UserToolService;
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
public class UserToolServiceImplTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private UserToolService userToolService;

    @Test
    public void checkIfUserExistedTest()
    {
        Long id = 1L;
        assertFalse(userToolService.checkIfUserExisted(id));
        User user = new User();
        University university = new University();
        university.setName("TestUniversityName");
        university.setRegex("TestUniversityRegex");
        universityRepository.save(university);
        user.setUniversity(university);
        user.setEmail("Test Email");
        userRepository.save(user);
        assertTrue(userToolService.checkIfUserExisted(id));
    }
}
