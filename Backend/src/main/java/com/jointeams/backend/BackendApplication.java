package com.jointeams.backend;


import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.InterestedCourseKey;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.util.IsCommentAvailable;
import com.jointeams.backend.util.UploadConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration.class)
@Import(UploadConfig.class)
public class BackendApplication {

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Autowired
//    private GroupRepository groupRepository;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private EnrollmentRepository enrollmentRepository;
//
//    @Autowired
//    private NotificationRepository notificationRepository;
//
//    @Autowired
//    private SemesterRepository semesterRepository;
//
//    @Autowired
//    private InterestedCourseRepository interestedCourseRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }


    @Bean
    public CommandLineRunner CommandLineRunnerBean() {
        return (args) -> {
//            // Add University data
//            University usydUni = new University();
//            usydUni.setName("University of Sydney");
//            usydUni.setRegex("^[a-z]{4}[0-9]{4}@uni.sydney.edu.au$");
//            if (universityRepository.findUniversityByName(usydUni.getName()).isEmpty()) {
//                universityRepository.save(usydUni);
//            }
//            University adminUni = new University();
//            adminUni.setName("Admin");
//            adminUni.setRegex("^.+$");
//            if (universityRepository.findUniversityByName(adminUni.getName()).isEmpty()) {
//                universityRepository.save(adminUni);
//            }
//
//            // Add User data
//            User devUser = new User();
//            devUser.setUniversity(usydUni);
//            devUser.setEmail("devu0001@uni.sydney.edu.au");
//            devUser.setAdmin(false);
//            devUser.setActivate(true);
//            devUser.setFirstName("firstname");
//            devUser.setLastName("lastname");
//            devUser.setPassword(passwordEncoder.encode("qwe123ASD"));
//            if (userRepository.findByEmail(devUser.getEmail()).isEmpty()) {
//                userRepository.save(devUser);
//            }
//            User adminUser = new User();
//            adminUser.setUniversity(adminUni);
//            adminUser.setAdmin(true);
//            adminUser.setActivate(true);
//            adminUser.setFirstName("admin");
//            adminUser.setEmail("jointeamsspring@gmail.com");
//            adminUser.setPassword(passwordEncoder.encode("qwe123ASD"));
//            if (userRepository.findByEmail(adminUser.getEmail()).isEmpty()) {
//                userRepository.save(adminUser);
//            }

        };
    }

}
