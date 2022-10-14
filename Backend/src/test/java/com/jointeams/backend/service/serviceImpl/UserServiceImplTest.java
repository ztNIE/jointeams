package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.InterestedCourseKey;
import com.jointeams.backend.repositery.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
public class UserServiceImplTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private InterestedCourseRepository interestedCourseRepository;

    @Autowired
    private UserServiceImpl userService;

    @Before
    public void prepareTest() {
        University university = new University();
        university.setName("TestUniversityName");
        university.setRegex("TestUniversityRegex");
        university.setEmailUrl("uni.sydney.edu.au");
        universityRepository.save(university);

        User user  = new User();
        user.setFirstName("firstName");
        user.setLastName("lastName");
        user.setDescription("description");
        user.setDegree("Master of IT");
        user.setFaculty("Engineering");
        user.setSelfTag(1);
        user.setActivate(true);
        user.setAdmin(false);
        user.setEmail("testEmail");
        user.setUniversity(university);
        user.setPassword("passwordTest");
        userRepository.save(user);

        Course previousCourse = new Course();
        previousCourse.setIsLocked(false);
        previousCourse.setCode("ELEC1000");
        previousCourse.setName("namePrevious");
        previousCourse.setUniversity(university);
        previousCourse.setNextGroupNameId(1);
        courseRepository.save(previousCourse);

        Course currentCourse = new Course();
        currentCourse.setIsLocked(false);
        currentCourse.setCode("ELEC1001");
        currentCourse.setName("nameCurrent");
        currentCourse.setUniversity(university);
        currentCourse.setNextGroupNameId(1);
        courseRepository.save(currentCourse);

        Course savedCourse = new Course();
        savedCourse.setIsLocked(false);
        savedCourse.setCode("ELEC1002");
        savedCourse.setName("nameSaved");
        savedCourse.setUniversity(university);
        savedCourse.setNextGroupNameId(1);
        courseRepository.save(savedCourse);

        Semester currentSemester = new Semester();
        currentSemester.setCurrent(true);
        currentSemester.setYear(2022);
        currentSemester.setSemesterNumber(1);
        semesterRepository.save(currentSemester);

        Semester previousSemester = new Semester();
        previousSemester.setCurrent(false);
        previousSemester.setYear(2021);
        previousSemester.setSemesterNumber(1);
        semesterRepository.save(previousSemester);

        Enrollment current = new Enrollment();
        current.setUser(user);
        current.setCourse(currentCourse);
        current.setSemester(currentSemester);
        enrollmentRepository.save(current);

        Enrollment previous = new Enrollment();
        previous.setUser(user);
        previous.setCourse(previousCourse);
        previous.setSemester(previousSemester);
        enrollmentRepository.save(previous);

        InterestedCourseKey key = new InterestedCourseKey(user.getId(), savedCourse.getId());
        InterestedCourse interestedCourse = new InterestedCourse();
        interestedCourse.setId(key);
        interestedCourseRepository.save(interestedCourse);
    }

    @Test
    @DirtiesContext
    public void getUserByIdTest() {
        JSONObject ret = userService.getUserInfoById(1L);
        assertNotNull(ret.get("data"));

        JSONObject userInfo = (JSONObject) ret.get("data");
        assertEquals("firstName", userInfo.get("firstName"));
        assertEquals("lastName", userInfo.get("lastName"));

        JSONArray currentCourses = (JSONArray) userInfo.get("currentCourse");
        assertEquals(1, currentCourses.size());
        JSONObject currentCourse = (JSONObject) currentCourses.get(0);
        assertEquals("ELEC1001", currentCourse.get("code"));

        JSONArray previousCourses = (JSONArray) userInfo.get("previousCourse");
        assertEquals(previousCourses.size(), 1);
        JSONObject previousCourse = (JSONObject) previousCourses.get(0);
        assertEquals("ELEC1000", previousCourse.get("code"));

        JSONArray interestedCourses = (JSONArray) userInfo.get("interestedCourse");
        assertEquals(1, interestedCourses.size());
        JSONObject interestedCourse = (JSONObject) interestedCourses.get(0);
        assertEquals("ELEC1002", interestedCourse.get("code"));

        JSONArray comments = (JSONArray) userInfo.get("comment");
        assertEquals(comments.size(), 0);
    }

    @Test
    @DirtiesContext
    public void getUserByIdInvalidTest() {
        JSONObject ret = userService.getUserInfoById(2L);
        assertNull(ret.get("data"));
    }

    @Test
    @DirtiesContext
    public void updateUserInfoByIdTest() throws FileNotFoundException {
        JSONObject ret = userService.getUserInfoById(1L);
        JSONObject userInfo = (JSONObject) ret.get("data");
        userInfo.put("firstName", "modified firstName");
        userInfo.put("lastName", "modified lastName");
        userInfo.put("updateAvatar", false);

        userService.updateUserInfoById(1L, userInfo);
        JSONObject newRet = userService.getUserInfoById(1L);
        JSONObject newUserInfo = (JSONObject) newRet.get("data");

        assertEquals("modified firstName", newUserInfo.get("firstName"));
        assertEquals("modified lastName", newUserInfo.get("lastName"));
    }

    @Test
    @DirtiesContext
    public void updateUserInfoByIdInvalidTest() throws FileNotFoundException {
        JSONObject ret = userService.getUserInfoById(1L);
        JSONObject userInfo = (JSONObject) ret.get("data");
        userInfo.put("firstName", "modified firstName");
        userInfo.put("lastName", "modified lastName");
        userInfo.put("updateAvatar", false);

        JSONObject result = userService.updateUserInfoById(2L, userInfo);
        assertNull(result.get("data"));
    }

    @Test
    @DirtiesContext
    public void findByIdTest() {
        User user = userService.findById(1L);
        assertNotNull(user);
        assertEquals("firstName", user.getFirstName());
    }

    @Test
    @DirtiesContext
    public void findByIdInvalidTest() {
        User user = userService.findById(2L);
        assertNull(user);
    }
}
