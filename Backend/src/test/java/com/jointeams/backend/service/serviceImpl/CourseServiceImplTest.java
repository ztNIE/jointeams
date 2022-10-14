package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.pojo.*;
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
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
public class CourseServiceImplTest {
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
    private CourseServiceImpl courseService;

    @Before
    public void prepareTest() {
//        university
        University university = new University();
        university.setName("TestUniversityName");
        university.setRegex("TestUniversityRegex");
        university.setEmailUrl("uni.sydney.edu.au");
        universityRepository.save(university);

//        user 1
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
//        user 2
        User user2  = new User();
        user2.setFirstName("firstName2");
        user2.setLastName("lastName2");
        user2.setDescription("description2");
        user2.setDegree("Master of IT");
        user2.setFaculty("Engineering");
        user2.setSelfTag(2);
        user2.setActivate(true);
        user2.setAdmin(false);
        user2.setEmail("testEmail2");
        user2.setUniversity(university);
        user2.setPassword("passwordTest2");
        userRepository.save(user2);

//        course 1
        Course course1 = new Course();
        course1.setIsLocked(false);
        course1.setCode("ELEC1000");
        course1.setName("namePrevious");
        course1.setUniversity(university);
        course1.setNextGroupNameId(1);
        courseRepository.save(course1);
//        course2
        Course course2 = new Course();
        course2.setIsLocked(false);
        course2.setCode("ELEC1001");
        course2.setName("nameCurrent");
        course2.setUniversity(university);
        course2.setNextGroupNameId(1);
        courseRepository.save(course2);
//        course3
        Course course3 = new Course();
        course3.setIsLocked(false);
        course3.setCode("ELEC1002");
        course3.setName("nameSaved");
        course3.setUniversity(university);
        course3.setNextGroupNameId(1);
        courseRepository.save(course3);

//        current semester
        Semester currentSemester = new Semester();
        currentSemester.setCurrent(true);
        currentSemester.setYear(2022);
        currentSemester.setSemesterNumber(1);
        semesterRepository.save(currentSemester);
//        previous semester
        Semester previousSemester = new Semester();
        previousSemester.setCurrent(false);
        previousSemester.setYear(2021);
        previousSemester.setSemesterNumber(1);
        semesterRepository.save(previousSemester);

//        current enrollment
        Enrollment current = new Enrollment();
        current.setUser(user);
        current.setCourse(course1);
        current.setSemester(currentSemester);
        enrollmentRepository.save(current);
//        past enrollment
        Enrollment previous = new Enrollment();
        previous.setUser(user2);
        previous.setCourse(course1);
        previous.setSemester(previousSemester);
        enrollmentRepository.save(previous);
    }

    @Test
    @DirtiesContext
    public void getAllCourseTest() {
        JSONObject result = courseService.getAllCourse(1L);
        JSONObject data = (JSONObject) result.get("data");
        JSONArray allCourses = (JSONArray) data.get("allCourse");
        assertEquals(3, allCourses.size());
    }

    @Test
    @DirtiesContext
    public void getAllCourseInvalidTest() {
        JSONObject result = courseService.getAllCourse(2L);
        JSONObject data = (JSONObject) result.get("data");
        assertNull(data);
    }

    @Test
    @DirtiesContext
    public void getAllPreviousStudentTest() {
        JSONObject result = courseService.getAllPreviousStudent(1L);
        JSONObject data = (JSONObject) result.get("data");
        JSONArray previousStudents = (JSONArray) data.get("previousStudent");
        assertEquals(1, previousStudents.size());

        JSONObject previousStudent = (JSONObject) previousStudents.get(0);
        assertEquals(2L, previousStudent.get("id"));
        assertEquals("firstName2 lastName2", previousStudent.get("fullName").toString());
    }

    @Test
    @DirtiesContext
    public void getAllPreviousStudentInvalidTest() {
        JSONObject result = courseService.getAllPreviousStudent(4L);
        JSONObject data = (JSONObject) result.get("data");
        assertNull(data);
    }

    @Test
    @DirtiesContext
    public void getAllCurrentStudentTest() {
        JSONObject result = courseService.getAllCurrentStudent(1L);
        JSONObject data = (JSONObject) result.get("data");
        JSONArray currentStudents = (JSONArray) data.get("currentStudent");
        assertEquals(1, currentStudents.size());

        JSONObject currentStudent = (JSONObject) currentStudents.get(0);
        assertEquals(1L, currentStudent.get("id"));
        assertEquals("firstName lastName", currentStudent.get("fullName").toString());
    }

    @Test
    @DirtiesContext
    public void getAllCurrentStudentInvalidTest() {
        JSONObject result = courseService.getAllCurrentStudent(4L);
        JSONObject data = (JSONObject) result.get("data");
        assertNull(data);
    }
}
