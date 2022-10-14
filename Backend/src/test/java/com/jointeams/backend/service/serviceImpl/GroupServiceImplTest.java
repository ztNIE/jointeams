package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.GroupService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.BeforeClass;

import java.util.ArrayList;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    private static Comment comment1;
    private static Comment comment2;

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
        groupUser2.setLeader(true);

        comment1 = new Comment();
        comment1.setId(1L);
        comment1.setGroup(group1);
        comment1.setSender(user1);
        comment1.setReceiver(user2);
        comment1.setIsHide(false);
        comment1.setTag(1);
        comment1.setContent("Default");

        comment2 = new Comment();
        comment2.setId(2L);
        comment2.setGroup(group1);
        comment2.setSender(user2);
        comment2.setReceiver(user1);
        comment2.setIsHide(true);
        comment2.setTag(1);
        comment2.setContent("Default");
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
        this.enrollmentRepository.save(enrollment1);
        this.enrollmentRepository.save(enrollment2);
        this.enrollmentRepository.save(enrollment3);
        this.groupRepository.save(group1);
        this.groupUserRepository.save(groupUser1);
        this.groupUserRepository.save(groupUser2);
        this.commentRepository.save(comment1);
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getGroupByIdValidTest() {
//        setup();
        JSONObject result = this.groupService.getGroupById(1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "success");

        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);
        assertEquals(data.get("id"), 1L);
        assertEquals(data.get("number of students"), 2);
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getGroupByIdInvalidTest() {
//        setup();
        JSONObject result = this.groupService.getGroupById(2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "Unable to find this group!");
        assertNull(result.get("data"));
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllMembersValidTest() {
//        setup();
        JSONObject result = this.groupService.getAllMembers(1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "success");

        JSONArray data = (JSONArray) result.get("data");
        assertNotNull(data);
        assertEquals(data.size(), 2);
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getAllMembersInvalidTest() {
//        setup();
        JSONObject result = this.groupService.getAllMembers(2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "Unable to find this group!");
        assertNull(result.get("data"));
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateDescriptionValidTest() {
//        setup();
        JSONObject result1 = this.groupService.getGroupById(1L);
        JSONObject data1 = (JSONObject) result1.get("data");
        assertEquals(data1.get("id"), 1L);
        assertNull(data1.get("description"));

        JSONObject result2 = this.groupService.updateDescription(1L, "New Description");
        JSONObject data2 = (JSONObject) result2.get("data");
        assertEquals(data2.get("description"), "New Description");

        JSONObject result3 = this.groupService.getGroupById(1L);
        JSONObject data3 = (JSONObject) result3.get("data");
        assertEquals(data3.get("id"), 1L);
        assertEquals(data3.get("description"), "New Description");
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void updateDescriptionInvalidTest() {
//        setup();
        JSONObject result = this.groupService.updateDescription(2L, "New Description");
        assertEquals(result.get("msg"), "Unable to find this group!");
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAMemberTest() {
//        setup();
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void isCommentFunctionAvailableTest() {
//        setup();
        JSONObject result = this.groupService.isCommentFunctionAvailable();
        assertEquals(result.get("msg"), "Success");
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCommentByIdValidTest() {
//        JSONObject result = this.groupService.getCommentById(1L, 1L, 2L);
//        assertNotNull(result);
//        assertEquals(result.get("msg"), "Comment exists.");
//
//        JSONObject data = (JSONObject) result.get("data");
//        assertNotNull(data);
//        assertEquals(data.get("id"), 1L);
//        assertEquals(data.get("content"), "Default");
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCommentByIdInvalidTest() {
//        setup();
        JSONObject result = this.groupService.getCommentById(1L, 2L, 1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "No comment exists.");
        assertNull(result.get("data"));
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void leaveCommentValidTest() {
        JSONObject result2 = this.groupService.leaveComment(1L, 1L, 2L, 1, "New Comment");
        JSONObject data2 = (JSONObject) result2.get("data");
        assertEquals(data2.get("content"), "New Comment");

        JSONObject result3 = this.groupService.getCommentById(1L, 1L, 2L);
        JSONObject data3 = (JSONObject) result3.get("data");
        assertEquals(data3.get("id"), 2L);
        assertEquals(data3.get("content"), "New Comment");
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void leaveCommentInvalidTest1() {
//        setup();
        JSONObject result = this.groupService.leaveComment(2L, 1L, 2L, 1, "New comment");
        assertNotNull(result);
        assertEquals(result.get("msg"), "Unable to find such a group.");
        assertNull(result.get("data"));
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void leaveCommentInvalidTest2() {
//        setup();
        JSONObject result = this.groupService.leaveComment(1L, 4L, 2L, 1, "New comment");
        assertNotNull(result);
        assertEquals(result.get("msg"), "Unable to find such a user.");
        assertNull(result.get("data"));
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getStudentsNotInAGroupValidTest() {
//        setup();
        JSONObject result = this.groupService.getStudentsNotInAGroup(1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "success");

        JSONArray data = (JSONArray) result.get("data");
        assertNotNull(data);
        assertEquals(data.size(), 1);
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getStudentsNotInAGroupInvalidTest() {
//        setup();
        JSONObject result = this.groupService.getStudentsNotInAGroup(2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "No such a student!");
        assertNull(result.get("data"));
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addInvitationNotificationValidTest() {
//        setup();
        JSONObject result = this.groupService.addInvitationNotification(1L, 3L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "An invitation sent!");

        JSONArray data = (JSONArray) result.get("data");
        assertNull(data);
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addInvitationNotificationStudentAlreadyInTheGroupTest() {
//        setup();
        JSONObject result = this.groupService.addInvitationNotification(1L, 2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "This student is already in your group!");

        JSONArray data = (JSONArray) result.get("data");
        assertNull(data);
    }

    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addInvitationNotificationSendInvitationTwiceTest() {
//        setup();
        this.groupService.addInvitationNotification(1L, 3L);
        JSONObject result = this.groupService.addInvitationNotification(1L, 3L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "You have already sent an invitation to this user!");

        JSONArray data = (JSONArray) result.get("data");
        assertNull(data);
    }
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Test
    public void addInvitationNotificationGroupFullTest() {
        this.userRepository.save(user4);
        Enrollment enrollment4 = new Enrollment();
        enrollment4.setId(4L);
        enrollment4.setCourse(course1);
        enrollment4.setSemester(semester);
        enrollment4.setTutorial("CC02");
        enrollment4.setUser(user4);
        this.enrollmentRepository.save(enrollment4);

        GroupUserId groupUserId3 = new GroupUserId();
        groupUserId3.setGroupId(1L);
        groupUserId3.setUserId(3L);

        GroupUser groupUser3 = new GroupUser();
        groupUser3.setGroupUserId(groupUserId3);
        groupUser3.setLeader(true);
        this.groupUserRepository.save(groupUser3);

        JSONObject result = this.groupService.addInvitationNotification(1L, 4L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "Your group is already full!");

        JSONArray data = (JSONArray) result.get("data");
        assertNull(data);
    }

//    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    public void addJoinRequestNotificationTest() {
//
//    }
//
//    @Test
//    @Sql(scripts = "/scripts/init.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(scripts = "/scripts/drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
//    public void getAllCurrentGroupsOfAUserTest() {
//
//    }
}
