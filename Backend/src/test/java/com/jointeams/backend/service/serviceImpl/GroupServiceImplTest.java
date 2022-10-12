package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.GroupService;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@SpringBootTest(classes = BackendApplication.class)
//@ActiveProfiles("test")
//@AutoConfigureTestDatabase
//@TestPropertySource(locations = "classpath:application-test.properties")
public class GroupServiceImplTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private GroupService groupService;

    private static Semester semester;
    private static University university;
    private static Course course1;
    private static Enrollment enrollment1;
    private static User user1;
    private static Group group1;

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
        course1.setNextGroupNameId(2);
        course1.setName("Object Oriented Application Framework");
        course1.setCode("ELEC5619");
        course1.setIsLocked(false);
        course1.setUniversity(university);

        user1 = new User();
        user1.setActivate(true);
        user1.setAdmin(false);
        user1.setId(1L);
        user1.setUniversity(university);
        user1.setEmail("devu0001@uni.sydney.edu.au");
        user1.setFirstName("Emma");
        user1.setLastName("Kwan");
        user1.setPassword("$2a$10$4Y.YYVx0K2zVYGsMXiw2E.7lhEzuUmegm6/tcC8uNXIlfqoNnwyhy");
        user1.setSelfTag(1);

        enrollment1 = new Enrollment();
        enrollment1.setId(1L);
        enrollment1.setCourse(course1);
        enrollment1.setSemester(semester);
        enrollment1.setTutorial("CC02");
        enrollment1.setUser(user1);

        group1 = new Group();
        group1.setSemester(semester);
        group1.setCapacity(3);
        group1.setCourse(course1);
        group1.setTutorial("CC02");
        group1.setId(1L);
        group1.setNameId(1);
    }

    @BeforeEach
    private void setup() {
        this.semesterRepository.save(semester);
        this.universityRepository.save(university);
        this.courseRepository.save(course1);
        this.userRepository.save(user1);
        this.enrollmentRepository.save(enrollment1);
        this.groupRepository.save(group1);
    }

    @AfterEach
    private void destroy() {
        // Delete
        // Reset index
    }

    @Test
    @Transactional
    public void getGroupByIdTest() {
        JSONObject result = this.groupService.getGroupById(1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "success");
        assertNotNull(result.get("data"));
    }

    @Test
    @Transactional
    public void getAllMembersTest() {
        assertEquals(1, 1);
    }
//
//    @Test
//    public void updateDescriptionTest(Long id, String newDescription) {
//
//    }
//
//    @Test
//    public void deleteAMemberTest(Long groupId, Long userId) {
//
//    }
//
//    @Test
//    public void isCommentFunctionAvailableTest() {
//
//    }
//
//    @Test
//    public void getCommentByIdTest(Long groupId, Long senderId, Long receiverId) {
//
//    }
//
//    @Test
//    public void leaveCommentTest(Long groupId, Long senderId, Long receiverId, Integer tag, String content) {
//
//    }
//
//    @Test
//    public void getStudentsNotInAGroupTest(Long courseId) {
//
//    }
//
//    @Test
//    public void addInvitationNotificationTest(Long groupId, Long userId) {
//
//    }
//
//    @Test
//    public void addJoinRequestNotificationTest(Long groupId, Long userId) {
//
//    }
//
//    @Test
//    public void getAllCurrentGroupsOfAUserTest(Long userId) {
//
//    }
}
