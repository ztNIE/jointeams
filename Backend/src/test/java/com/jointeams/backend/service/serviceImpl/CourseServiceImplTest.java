package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.GroupUserId;
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
import java.util.List;

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

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private InterestedCourseRepository interestedCourseRepository;

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
        current.setTutorial("tut1");
        enrollmentRepository.save(current);

        Enrollment current2 = new Enrollment();
        current2.setUser(user2);
        current2.setCourse(course1);
        current2.setSemester(currentSemester);
        enrollmentRepository.save(current2);

//        past enrollment
        Enrollment previous = new Enrollment();
        previous.setUser(user2);
        previous.setCourse(course1);
        previous.setSemester(previousSemester);
        enrollmentRepository.save(previous);

//        previous teammates
        Group group = new Group();
        group.setCourse(course2);
        group.setSemester(previousSemester);
        group.setCapacity(5);
        group.setDescription("description");
        group.setTutorial("tut1");
        group.setNameId(1);
        groupRepository.save(group);

        GroupUser teammate1 = new GroupUser();
        GroupUserId id1 = new GroupUserId();
        id1.setGroupId(1L);
        id1.setUserId(1L);
        teammate1.setLeader(true);
        teammate1.setGroupUserId(id1);
        groupUserRepository.save(teammate1);

        GroupUser teammate2 = new GroupUser();
        GroupUserId id2 = new GroupUserId();
        id2.setGroupId(1L);
        id2.setUserId(2L);
        teammate2.setLeader(false);
        teammate2.setGroupUserId(id2);
        groupUserRepository.save(teammate2);

//        interested course
        InterestedCourseKey key = new InterestedCourseKey(user.getId(), course3.getId());
        InterestedCourse interestedCourse = new InterestedCourse();
        interestedCourse.setId(key);
        interestedCourseRepository.save(interestedCourse);
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
        JSONObject result = courseService.getAllCourse(4L);
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
        assertEquals(2, currentStudents.size());

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

    @Test
    @DirtiesContext
    public void getAllPreviousTeammatesTest() {
        JSONObject result = courseService.getAllPreviousTeammates(1L, 1L);
        JSONObject data = (JSONObject) result.get("data");
        JSONArray teammates = (JSONArray) data.get("teammate");

        assertEquals(1, teammates.size());

        JSONObject teammate = (JSONObject) teammates.get(0);
        assertEquals(2L, teammate.get("id"));
    }

    @Test
    @DirtiesContext
    public void getAllPreviousTeammatesInvalidTest1() {
        JSONObject result = courseService.getAllPreviousTeammates(4L, 1L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void getAllPreviousTeammatesInvalidTest2() {
        JSONObject result = courseService.getAllPreviousTeammates(1L, 4L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void enrollCourseTest() {
        JSONObject result = courseService.enrollCourse(2L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);

        List<Enrollment> enrollments = courseService.getEnrollment(2L, 3L);
        assertEquals(1, enrollments.size());
    }

    @Test
    @DirtiesContext
    public void enrollCourseInvalidTest1() {
        JSONObject result = courseService.enrollCourse(4L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void enrollCourseInvalidTest2() {
        JSONObject result = courseService.enrollCourse(2L, 4L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void dropCourseTest() {
        JSONObject result = courseService.dropCourse(1L, 1L);
        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);

        List<Enrollment> enrollments = courseService.getEnrollment(1L, 1L);
        assertEquals(0, enrollments.size());
    }

    @Test
    @DirtiesContext
    public void dropCourseInvalidTest1() {
        JSONObject result = courseService.dropCourse(4L, 1L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void dropCourseInvalidTest2() {
        JSONObject result = courseService.dropCourse(1L, 4L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void dropCourseInvalidTest3() {
        JSONObject result = courseService.dropCourse(1L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("The student has not enrolled in this course!", msg);
    }

    @Test
    @DirtiesContext
    public void checkEnrollmentTest1() {
        JSONObject result = courseService.checkEnrollment(1L, 1L);
        JSONObject data = (JSONObject) result.get("data");
        assertTrue((Boolean) data.get("enrolled"));
    }

    @Test
    @DirtiesContext
    public void checkEnrollmentTest2() {
        JSONObject result = courseService.checkEnrollment(1L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        assertFalse((Boolean) data.get("enrolled"));
    }

    @Test
    @DirtiesContext
    public void checkEnrollmentInvalidTest1() {
        JSONObject result = courseService.checkEnrollment(4L, 1L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void checkEnrollmentInvalidTest2() {
        JSONObject result = courseService.checkEnrollment(1L, 4L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void markCourseTest() {
        JSONObject result = courseService.markCourse(1L, 2L);
        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);

        JSONObject result2 = courseService.checkMarkedCourse(1L, 2L);
        JSONObject data2 = (JSONObject) result2.get("data");
        assertTrue((Boolean) data2.get("marked"));
    }

    @Test
    @DirtiesContext
    public void markCourseInvalidTest1() {
        JSONObject result = courseService.markCourse(4L, 2L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void markCourseInvalidTest2() {
        JSONObject result = courseService.markCourse(1L, 4L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void unmarkCourseTest() {
        JSONObject result = courseService.unmarkCourse(1L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);

        JSONObject result2 = courseService.checkMarkedCourse(1L, 3L);
        JSONObject data2 = (JSONObject) result2.get("data");
        assertFalse((Boolean) data2.get("marked"));
    }

    @Test
    @DirtiesContext
    public void unmarkCourseInvalidTest1() {
        JSONObject result = courseService.unmarkCourse(4L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void unmarkCourseInvalidTest2() {
        JSONObject result = courseService.unmarkCourse(1L, 4L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void checkMarkCourseTest1() {
        JSONObject result = courseService.checkMarkedCourse(1L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        assertTrue((Boolean) data.get("marked"));
    }

    @Test
    @DirtiesContext
    public void checkMarkCourseTest2() {
        JSONObject result = courseService.checkMarkedCourse(1L, 2L);
        JSONObject data = (JSONObject) result.get("data");
        assertFalse((Boolean) data.get("marked"));
    }

    @Test
    @DirtiesContext
    public void checkMarkCourseInvalidTest1() {
        JSONObject result = courseService.checkMarkedCourse(4L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void checkMarkCourseInvalidTest2() {
        JSONObject result = courseService.checkMarkedCourse(1L, 4L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void getTutorialTest() {
        JSONObject result = courseService.getTutorial(1L, 1L);
        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);
        assertEquals("tut1", data.get("tutorial"));
    }

    @Test
    @DirtiesContext
    public void getTutorialInvalidTest1() {
        JSONObject result = courseService.getTutorial(4L, 1L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void getTutorialInvalidTest2() {
        JSONObject result = courseService.getTutorial(1L, 4L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void getTutorialInvalidTest3() {
        JSONObject result = courseService.getTutorial(1L, 3L);
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Student not enrolled!", msg);
    }

    @Test
    @DirtiesContext
    public void setTutorialTest() {
        JSONObject result = courseService.setTutorial(1L, 1L, "tut2");
        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);

        JSONObject result2 = courseService.getTutorial(1L, 1L);
        JSONObject data2 = (JSONObject) result2.get("data");
        assertNotNull(data2);
        assertEquals("tut2", data2.get("tutorial"));
    }

    @Test
    @DirtiesContext
    public void setTutorialInvalidTest1() {
        JSONObject result = courseService.setTutorial(4L, 1L, "tut2");
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the user!", msg);
    }

    @Test
    @DirtiesContext
    public void setTutorialInvalidTest2() {
        JSONObject result = courseService.setTutorial(1L, 4L, "tut2");
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Unable to find the course!", msg);
    }

    @Test
    @DirtiesContext
    public void setTutorialInvalidTest3() {
        JSONObject result = courseService.setTutorial(1L, 3L, "tut2");
        JSONObject data = (JSONObject) result.get("data");
        String msg = result.get("msg").toString();
        assertNull(data);
        assertEquals("Student not enrolled!", msg);
    }
}
