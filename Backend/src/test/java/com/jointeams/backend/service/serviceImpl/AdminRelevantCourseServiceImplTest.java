package com.jointeams.backend.service.serviceImpl;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.GroupUserId;
import com.jointeams.backend.repositery.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AdminRelevantCourseServiceImplTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private CommentRepository commentRepository;

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
    private static Group group2;
    private static GroupUserId groupUserId1;
    private static GroupUserId groupUserId2;
    private static GroupUserId groupUserId3;
    private static GroupUser groupUser1;
    private static GroupUser groupUser2;
    private static GroupUser groupUser3;
    private static Comment comment1;
    private static Comment comment2;
    private static Notification notification1;
    private static Notification notification2;

    @BeforeAll
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

        group2 = new Group();
        group2.setSemester(semester);
        group2.setCapacity(3);
        group2.setCourse(course1);
        group2.setTutorial("CC03");
        group2.setId(2L);
        group2.setNameId(2);

        groupUserId1 = new GroupUserId();
        groupUserId1.setGroupId(1L);
        groupUserId1.setUserId(1L);

        groupUserId2 = new GroupUserId();
        groupUserId2.setGroupId(1L);
        groupUserId2.setUserId(2L);

        groupUserId3 = new GroupUserId();
        groupUserId3.setGroupId(2L);
        groupUserId3.setUserId(3L);

        groupUser1 = new GroupUser();
        groupUser1.setGroupUserId(groupUserId1);
        groupUser1.setLeader(true);

        groupUser2 = new GroupUser();
        groupUser2.setGroupUserId(groupUserId2);
        groupUser2.setLeader(false);

        groupUser3 = new GroupUser();
        groupUser3.setGroupUserId(groupUserId3);
        groupUser3.setLeader(true);

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

        notification1 = new Notification();
        notification1.setId(1L);
        notification1.setType(0);
        notification1.setUser(user4);
        notification1.setGroup(group1);
        notification1.setMessage("testMessage1");
        notification1.setContent("testContent1");

        notification2 = new Notification();
        notification2.setId(2L);
        notification2.setType(1);
        notification2.setUser(user4);
        notification2.setGroup(group2);
        notification2.setMessage("testMessage2");
        notification2.setContent("testContent2");
    }

    private void setup() {
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
        this.groupRepository.save(group2);
        this.groupUserRepository.save(groupUser1);
        this.groupUserRepository.save(groupUser2);
        this.groupUserRepository.save(groupUser3);
        this.commentRepository.save(comment1);
        this.commentRepository.save(comment2);
        this.notificationRepository.save(notification1);
        this.notificationRepository.save(notification2);
    }
    @Test
    public void findAllFeedbackTest(){
        assertEquals(0,courseService.findAllFeedback().getStatus());
        setup();
        assertEquals(1,courseService.findAllFeedback().getStatus());
    }

    @Test
    public void addACourseFeedbackTest(){
        setup();
        assertEquals(0,courseService.addACourseFeedback("SOFT3888","XX",1L).getStatus());
        assertEquals(0,courseService.addACourseFeedback("XX","Capstone",1L).getStatus());
        assertEquals(-1,courseService.addACourseFeedback("XX","XXX",3L).getStatus());
        assertEquals(1,courseService.addACourseFeedback("XX","XXX",1L).getStatus());
    }
    @Test
    public void deleteACourseFeedbackTest(){
        setup();
        assertEquals(0,courseService.deleteACourseFeedback(3L).getStatus());
        assertEquals(1,courseService.deleteACourseFeedback(1L).getStatus());
    }

    @Test
    public void changeCourseLockStatusFeedbackTest(){
        setup();
        assertEquals(0,courseService.changeCourseLockStatusFeedback(3L, false).getStatus());
        assertEquals(-1,courseService.changeCourseLockStatusFeedback(1L, false).getStatus());
        assertEquals(1,courseService.changeCourseLockStatusFeedback(1L, true).getStatus());
    }
}
