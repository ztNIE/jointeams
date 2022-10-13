package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.BackendApplication;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.GroupService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.BeforeClass;
import org.junit.Before;
import static org.junit.Assert.*;

//@Profile("test")
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@ExtendWith(SpringExtension.class)
//@TestPropertySource(locations="classpath:application-test.properties")

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
//@DataJpaTest
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

//    @Autowired
//    private EntityManagerFactory entityManagerFactory;

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
    private static Group group1;
    private static GroupUserId groupUserId1;
    private static GroupUserId groupUserId2;
    private static GroupUser groupUser1;
    private static GroupUser groupUser2;
    private static Comment comment1;

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

    @After
    public void destroy() {
        this.commentRepository.delete(comment1);
        this.groupUserRepository.delete(groupUser2);
        this.groupUserRepository.delete(groupUser1);
        this.groupRepository.delete(group1);
        this.enrollmentRepository.delete(enrollment3);
        this.enrollmentRepository.delete(enrollment2);
        this.enrollmentRepository.delete(enrollment1);
        this.userRepository.delete(user3);
        this.userRepository.delete(user2);
        this.userRepository.delete(user1);
        this.courseRepository.delete(course2);
        this.courseRepository.delete(course1);
        this.universityRepository.delete(university);
        this.semesterRepository.delete(semester);
    }

    @Test
    public void getGroupByIdValidTest() {
        JSONObject result = this.groupService.getGroupById(1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "success");

        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);
        assertEquals(data.get("id"), 1L);
        assertEquals(data.get("number of students"), 2);
    }

    @Test
    public void getGroupByIdInvalidTest() {
        JSONObject result = this.groupService.getGroupById(2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "Unable to find this group!");
        assertNull(result.get("data"));
    }

    @Test
    public void getAllMembersValidTest() {
        JSONObject result = this.groupService.getAllMembers(1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "success");

        JSONArray data = (JSONArray) result.get("data");
        assertNotNull(data);
        assertEquals(data.size(), 2);
    }

    @Test
    public void getAllMembersInvalidTest() {
        JSONObject result = this.groupService.getAllMembers(2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "Unable to find this group!");
        assertNull(result.get("data"));
    }

    @Test
    public void updateDescriptionValidTest() {
        JSONObject result1 = this.groupService.getGroupById(1L);
        JSONObject data1 = (JSONObject) result1.get("data");
        assertEquals(data1.get("id"), 1L);
        assertEquals(data1.get("description"), null);

        JSONObject result2 = this.groupService.updateDescription(1L, "New Description");
        JSONObject data2 = (JSONObject) result2.get("data");
        assertEquals(data2.get("description"), "New Description");

        JSONObject result3 = this.groupService.getGroupById(1L);
        JSONObject data3 = (JSONObject) result3.get("data");
        assertEquals(data3.get("id"), 1L);
        assertEquals(data3.get("description"), "New Description");
    }

    @Test
    public void updateDescriptionInvalidTest() {
        JSONObject result = this.groupService.updateDescription(2L, "New Description");
        assertEquals(result.get("msg"), "Unable to find this group!");
    }

    @Test
    public void deleteAMemberTest() {
        //
    }

    @Test
    public void isCommentFunctionAvailableTest() {
        JSONObject result = this.groupService.isCommentFunctionAvailable();
        assertEquals(result.get("msg"), "Success");
    }

    @Test
    public void getCommentByIdValidTest() {
        JSONObject result = this.groupService.getCommentById(1L, 1L, 2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "Comment exists.");

        JSONObject data = (JSONObject) result.get("data");
        assertNotNull(data);
        assertEquals(data.get("id"), 1L);
        assertEquals(data.get("content"), "Default");
    }

    @Test
    public void getCommentByIdInvalidTest() {
        JSONObject result = this.groupService.getCommentById(1L, 2L, 1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "No comment exists.");
        assertNull(result.get("data"));
    }

    @Test
    public void leaveCommentValidTest() {
        JSONObject result1 = this.groupService.getCommentById(1L, 1L, 2L);
        JSONObject data1 = (JSONObject) result1.get("data");
        assertEquals(data1.get("id"), 1L);
        assertEquals(data1.get("content"), "Default");

        JSONObject result2 = this.groupService.leaveComment(1L, 1L, 2L, 1, "New Comment");
        JSONObject data2 = (JSONObject) result2.get("data");
        assertEquals(data2.get("content"), "New Comment");

        JSONObject result3 = this.groupService.getCommentById(1L, 1L, 2L);
        JSONObject data3 = (JSONObject) result3.get("data");
        assertEquals(data3.get("id"), 2L);
        assertEquals(data3.get("content"), "New Comment");
    }

    @Test
    public void leaveCommentInvalidTest1() {
        JSONObject result = this.groupService.leaveComment(2L, 1L, 2L, 1, "New comment");
        assertNotNull(result);
        assertEquals(result.get("msg"), "Unable to find such a group.");
        assertNull(result.get("data"));
    }

    @Test
    public void leaveCommentInvalidTest2() {
        JSONObject result = this.groupService.leaveComment(1L, 4L, 2L, 1, "New comment");
        assertNotNull(result);
        assertEquals(result.get("msg"), "Unable to find such a user.");
        assertNull(result.get("data"));
    }

    @Test
    public void getStudentsNotInAGroupValidTest() {
        JSONObject result = this.groupService.getStudentsNotInAGroup(1L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "success");

        JSONArray data = (JSONArray) result.get("data");
        assertNotNull(data);
        assertEquals(data.size(), 1);
    }

    @Test
    public void getStudentsNotInAGroupInvalidTest() {
        JSONObject result = this.groupService.getStudentsNotInAGroup(2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "No such a student!");
        assertNull(result.get("data"));
    }

    @Test
    public void addInvitationNotificationValidTest() {
        JSONObject result = this.groupService.addInvitationNotification(1L, 3L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "An invitation sent!");

        JSONArray data = (JSONArray) result.get("data");
        assertNull(data);
    }

    @Test
    public void addInvitationNotificationStudentAlreadyInTheGroupTest() {
        JSONObject result = this.groupService.addInvitationNotification(1L, 2L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "This student is already in your group!");

        JSONArray data = (JSONArray) result.get("data");
        assertNull(data);
    }

    @Test
    public void addInvitationNotificationSendInvitationTwiceTest() {
        this.groupService.addInvitationNotification(1L, 3L);
        JSONObject result = this.groupService.addInvitationNotification(1L, 3L);
        assertNotNull(result);
        assertEquals(result.get("msg"), "You have already sent an invitation to this user!");

        JSONArray data = (JSONArray) result.get("data");
        assertNull(data);
    }

//    @Test
//    public void addInvitationNotificationGroupFullTest() {
//        this.groupService.addInvitationNotification(1L, 3L);
//        JSONObject result = this.groupService.addInvitationNotification(1L, 3L);
//        assertNotNull(result);
//        assertEquals(result.get("msg"), "You have already sent an invitation to this user!");
//
//        JSONArray data = (JSONArray) result.get("data");
//        assertNull(data);
//    }

    @Test
    public void addJoinRequestNotificationTest() {

    }

    @Test
    public void getAllCurrentGroupsOfAUserTest() {

    }
}
