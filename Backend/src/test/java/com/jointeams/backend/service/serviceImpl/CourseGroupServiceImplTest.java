package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.CourseGroupService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import javax.transaction.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseGroupServiceImplTest {
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseGroupService courseGroupService;

    private static Semester semester;
    private static University university;
    private static Course course1;
    private static Course course2;
    private static Enrollment enrollment1;
    private static Enrollment enrollment2;
    private static Enrollment enrollment3;
    private static User user1;
    private static User user2;
    private static User user3;
    private static User user4;
    private static Group group1;
    private static GroupUserId groupUserId1;
    private static GroupUserId groupUserId2;
    private static GroupUser groupUser1;
    private static GroupUser groupUser2;

    @BeforeClass
    public static void prepare(){
        semester = new Semester();
        semester.setId(1L);
        semester.setSemesterNumber(2);
        semester.setYear(2022);
        semester.setCurrent(true);

        university = new University();
        university.setId(1L);
        university.setName("The University of Sydney");
        university.setEmailUrl("https://www.outlook.com/uni.sydney.edu.au");
        university.setRegex("^[a-z]{4}[0-9]{4}@uni.sydney.edu.au$");

        course1 = new Course();
        course1.setId(1L);
        course1.setNextGroupNameId(2);
        course1.setName("Object Oriented Application Framework");
        course1.setCode("ELEC5619");
        course1.setIsLocked(false);
        course1.setUniversity(university);

        course2 = new Course();
        course2.setId(2L);
        course2.setNextGroupNameId(1);
        course2.setName("Capstone");
        course2.setCode("SOFT3888");
        course2.setIsLocked(false);
        course2.setUniversity(university);

        user1 = new User();
        user1.setActivate(true);
        user1.setAdmin(false);
        user1.setId(1L);
        user1.setUniversity(university);
        user1.setEmail("abcd1234@uni.sydney.edu.au");
        user1.setFirstName("a");
        user1.setLastName("a");
        user1.setPassword("password");
        user1.setSelfTag(1);

        user2 = new User();
        user2.setActivate(true);
        user2.setAdmin(false);
        user2.setId(2L);
        user2.setUniversity(university);
        user2.setEmail("bbcd1234@uni.sydney.edu.au");
        user2.setFirstName("b");
        user2.setLastName("b");
        user2.setPassword("password");
        user2.setSelfTag(2);

        user3 = new User();
        user3.setActivate(true);
        user3.setAdmin(false);
        user3.setId(3L);
        user3.setUniversity(university);
        user3.setEmail("cbcd1234@uni.sydney.edu.au");
        user3.setFirstName("c");
        user3.setLastName("c");
        user3.setPassword("password");
        user3.setSelfTag(3);

        user4 = new User();
        user4.setActivate(true);
        user4.setAdmin(false);
        user4.setId(4L);
        user4.setUniversity(university);
        user4.setEmail("dbcd1234@uni.sydney.edu.au");
        user4.setFirstName("d");
        user4.setLastName("d");
        user4.setPassword("password");
        user4.setSelfTag(4);

        enrollment1 = new Enrollment();
        enrollment1.setId(1L);
        enrollment1.setCourse(course1);
        enrollment1.setSemester(semester);
        enrollment1.setTutorial("CC02");
        enrollment1.setUser(user1);

        enrollment2 = new Enrollment();
        enrollment2.setId(2L);
        enrollment2.setCourse(course1);
        enrollment2.setSemester(semester);
        enrollment2.setTutorial("CC02");
        enrollment2.setUser(user2);

        enrollment3 = new Enrollment();
        enrollment3.setId(3L);
        enrollment3.setCourse(course1);
        enrollment3.setSemester(semester);
        enrollment3.setTutorial("CC02");
        enrollment3.setUser(user3);

        group1 = new Group();
        group1.setSemester(semester);
        group1.setCapacity(3);
        group1.setCourse(course1);
        group1.setTutorial("CC02");
        group1.setId(1L);
        group1.setNameId(1);

        groupUserId1 = new GroupUserId();
        groupUserId1.setGroupId(1L);
        groupUserId1.setUserId(1L);

        groupUserId2 = new GroupUserId();
        groupUserId2.setGroupId(1L);
        groupUserId2.setUserId(2L);

        groupUser1 = new GroupUser();
        groupUser1.setGroupUserId(groupUserId1);
        groupUser1.setLeader(true);

        groupUser2 = new GroupUser();
        groupUser2.setGroupUserId(groupUserId2);
        groupUser2.setLeader(false);
    }

    @Before
    public void setup() {
        this.semesterRepository.save(semester);
        this.universityRepository.save(university);
        this.courseRepository.save(course1);
        this.courseRepository.save(course2);
        this.userRepository.save(user1);
        this.userRepository.save(user2);
        this.userRepository.save(user3);
        this.userRepository.save(user4);
        this.enrollmentRepository.save(enrollment1);
        this.enrollmentRepository.save(enrollment2);
        this.enrollmentRepository.save(enrollment3);
        this.groupRepository.save(group1);
        this.groupUserRepository.save(groupUser1);
        this.groupUserRepository.save(groupUser2);
    }

    @Test
    public void getAllGroupsByCourseIdNoGroupInThisCourseTest() {
        JSONObject result = this.courseGroupService.getAllGroupsByCourseId(2L, 1L);
        assertEquals("No group in this course in this semester!", result.get("msg"));
    }

    @Test
    public void getAllGroupsByCourseIdValidHasNoGroupTest() {
        JSONObject result = this.courseGroupService.getAllGroupsByCourseId(1L, 3L);
        assertEquals("success", result.get("msg"));

        JSONArray data = (JSONArray) result.get("data");
        assertEquals(1, data.size());
        assertEquals((Long) 1L, (Long)((JSONObject)data.get(0)).get("group_id"));
    }

    @Test
    public void getAllGroupsByCourseIdValidHasGroupTest() {
        JSONObject result = this.courseGroupService.getAllGroupsByCourseId(1L, 1L);
        assertEquals("success", result.get("msg"));

        JSONArray data = (JSONArray) result.get("data");
        assertEquals(1, data.size());
        assertEquals((Long) 1L, (Long)((JSONObject)data.get(0)).get("group_id"));
    }

    @Test
    public void addAGroupNotEnrollTest() {
        JSONObject result1 = this.courseGroupService.addAGroup(1L, 4L, 1);
        assertEquals("You do not enroll in this course this semester!", result1.get("msg"));

        JSONObject result2 = this.courseGroupService.getAllGroupsByCourseId(1L, 1L);
        JSONArray data2 = (JSONArray) result2.get("data");
        assertEquals(1, data2.size());
    }

    @Test
    public void addAGroupAlreadyInAGroupTest() {
        JSONObject result1 = this.courseGroupService.addAGroup(1L, 1L, 1);
        assertEquals("You are already in a group!", result1.get("msg"));

        JSONObject result2 = this.courseGroupService.getAllGroupsByCourseId(1L, 1L);
        JSONArray data2 = (JSONArray) result2.get("data");
        assertEquals(1, data2.size());
    }

    @Test
    public void addAGroupValidTest() {
        JSONObject result1 = this.courseGroupService.addAGroup(1L, 3L, 1);
        assertEquals("Success!", result1.get("msg"));

        JSONObject result2 = this.courseGroupService.getAllGroupsByCourseId(1L, 3L);
        JSONArray data2 = (JSONArray) result2.get("data");
        assertEquals(2, data2.size());
    }
}
