package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.pojo.Semester;
import com.jointeams.backend.repositery.SemesterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = BackendApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
public class SemesterServiceImplTest {

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SemesterServiceImpl semesterService;

    @Test
    public void findCurrentSemesterFeedbackTest() {
        assertEquals(0,semesterService.findCurrentSemesterFeedback().getStatus());
        Semester semester = new Semester();
        semesterRepository.save(semester);
        assertEquals(0,semesterService.findCurrentSemesterFeedback().getStatus());
        semester.setYear(2022);
        semester.setSemesterNumber(1);
        semester.setCurrent(true);
        semesterRepository.save(semester);
        assertEquals(1,semesterService.findCurrentSemesterFeedback().getStatus());
    }

    @Test
    public void changeCurrentSemesterFeedback() {
        Semester semester = new Semester();
        semester.setYear(2022);
        semester.setSemesterNumber(1);
        semester.setCurrent(true);
        semesterRepository.save(semester);
        assertEquals(0,semesterService.changeCurrentSemesterFeedback(2022,1).getStatus());
        assertEquals(1,semesterService.changeCurrentSemesterFeedback(2022,2).getStatus());
    }
}
