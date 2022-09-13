package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.InterestedCourseKey;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.CourseService;
import org.json.simple.JSONArray;
import com.jointeams.backend.repositery.CourseRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UniversityRepository universityRepository;

    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private GroupUserRepository groupUserRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private InterestedCourseRepository interestedCourseRepository;

    @Override
    public JSONObject getAllCourse(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else {
            if (user.getUniversity() == null || universityRepository.findById(user.getUniversity().getId()) == null) {
                jsonResult.put("msg", "Unable to find the university!");
                jsonResult.put("data", null);
            } else {
                JSONObject data = new JSONObject();
                Optional<University> uni = universityRepository.findById(user.getUniversity().getId());

                JSONArray allCourse = new JSONArray();
                List<Course> allCourses = courseRepository.findAllCourse(uni.get().getId());
                for (Course course : allCourses) {
                    JSONObject newCourse = new JSONObject();
                    newCourse.put("id", course.getId());
                    newCourse.put("code", course.getCode());
                    newCourse.put("name", course.getName());
                    allCourse.add(newCourse);
                }
                data.put("allCourse", allCourse);

                jsonResult.put("msg", "success");
                jsonResult.put("data", data);
            }
        }

        return jsonResult;
    }

    @Override
    public JSONObject getAllPreviousStudent(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            JSONObject data = new JSONObject();
            List<User> previousStudents = enrollmentRepository.findAllPreviousStudentsByCourseId(courseId);
            JSONArray previousStudent = new JSONArray();

            for (User student : previousStudents) {
                JSONObject newStudent = new JSONObject();
                newStudent.put("id", student.getId());
                newStudent.put("firstName", student.getFirstName());
                newStudent.put("lastName", student.getLastName());
                newStudent.put("email", student.getEmail());
                previousStudent.add(newStudent);
            }
            data.put("previousStudent", previousStudent);

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    private JSONArray getCurrentStudents(Long courseId) {
        List<User> currentStudents = enrollmentRepository.findAllCurrentStudentsByCourseId(courseId);
        JSONArray currentStudent = new JSONArray();

        for (User student : currentStudents) {
            JSONObject newStudent = new JSONObject();
            newStudent.put("id", student.getId());
            newStudent.put("firstName", student.getFirstName());
            newStudent.put("lastName", student.getLastName());
            newStudent.put("email", student.getEmail());
            currentStudent.add(newStudent);
        }

        return currentStudent;
    }

    @Override
    public JSONObject getAllCurrentStudent(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            JSONObject data = new JSONObject();
            data.put("currentStudent", getCurrentStudents(courseId));

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    @Override
    public JSONObject getAllPreviousTeammates(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            JSONObject data = new JSONObject();
            JSONArray currentStudents = getCurrentStudents(courseId);

            List<Long> allTeammates = new ArrayList<Long>();
            List<Long> allGroups = groupUserRepository.getAllGroups(userId);
            for (Long groupId : allGroups) {
                List<Long> groupTeammate = groupUserRepository.getAllTeammates(userId, groupId);
                for (Long teammateId : groupTeammate) {
                    if (!allTeammates.contains(teammateId)) {
                        allTeammates.add(teammateId);
                    }
                }
            }

            JSONArray teammates = new JSONArray();
            for (Long teammateId : allTeammates) {
                for (int i = 0; i < currentStudents.size(); i ++) {
                    JSONObject currentStudent = (JSONObject) currentStudents.get(i);
                    if (currentStudent.get("id").toString().equals(teammateId.toString())) {
                        JSONObject teammate = new JSONObject();
                        teammate.put("id", currentStudent.get("id"));
                        teammate.put("firstName", currentStudent.get("firstName"));
                        teammate.put("lastName", currentStudent.get("lastName"));
                        teammates.add(teammate);
                        break;
                    }
                }
            }

            data.put("teammate", teammates);
            jsonResult.put("msg", "success");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    @Override
    public JSONObject enrollCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            if (course.getIsLocked()) {
                jsonResult.put("msg", "The course has been locked!");
                jsonResult.put("data", null);
            } else {
                Enrollment newEnrollment = new Enrollment();
                newEnrollment.setUser(user);
                newEnrollment.setCourse(course);
                newEnrollment.setSemester(semesterRepository.findCurrentSemester());

                enrollmentRepository.save(newEnrollment);

                jsonResult.put("msg", "success");
                jsonResult.put("data", new JSONObject());
            }
        }

        return jsonResult;
    }

    public List<Enrollment> getEnrollment(Long userId, Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByUserIdAndCourseId(userId, courseId);
        return enrollments;
    }

    @Override
    public JSONObject dropCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            if (getEnrollment(userId, courseId).size() == 0) {
                jsonResult.put("msg", "The student has not enrolled in this course!");
                jsonResult.put("data", null);
            } else {
                List<Enrollment> enrollments = getEnrollment(userId, courseId);
                for (Enrollment enrollment : enrollments) {
                    if (enrollment.getSemester().isCurrent()) {
                        enrollmentRepository.delete(enrollment);
                        break;
                    }
                }

                jsonResult.put("msg", "success");
                jsonResult.put("data", new JSONObject());
            }
        }

        return jsonResult;
    }

    public Boolean checkIfEnrolled(Long userId, Long courseId) {
        List<Enrollment> enrollments = getEnrollment(userId, courseId);
        if (enrollments.size() == 0) {
            return false;
        } else {
            Boolean found = false;
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getSemester().isCurrent()) {
                    found = true;
                }
            }

            if (found) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public JSONObject checkEnrollment(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            JSONObject data = new JSONObject();
            data.put("enrolled", checkIfEnrolled(userId, courseId));

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    @Override
    public JSONObject markCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            InterestedCourseKey interestedCourseKey = new InterestedCourseKey(userId, courseId);
            InterestedCourse newInterestedCourse = new InterestedCourse();
            newInterestedCourse.setId(interestedCourseKey);
            interestedCourseRepository.save(newInterestedCourse);

            jsonResult.put("msg", "success");
            jsonResult.put("data", new JSONObject());
        }

        return jsonResult;
    }

    @Override
    public JSONObject unmarkCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            InterestedCourse interestedCourse = interestedCourseRepository.findByUserIdAndCourseId(user.getId(), course.getId());
            interestedCourseRepository.delete(interestedCourse);

            jsonResult.put("msg", "success");
            jsonResult.put("data", new JSONObject());
        }

        return jsonResult;
    }

    @Override
    public JSONObject checkMarkedCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        }  else {
            JSONObject result = new JSONObject();
            InterestedCourse interestedCourse = interestedCourseRepository.findByUserIdAndCourseId(user.getId(), course.getId());
            result.put("marked", interestedCourse != null);

            jsonResult.put("msg", "success");
            jsonResult.put("data", result);
        }

        return jsonResult;
    }

    @Override
    public JSONObject getTutorial(Long userId, Long courseId) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        } else if (!checkIfEnrolled(userId, courseId)) {
            jsonResult.put("msg", "Student not enrolled!");
            jsonResult.put("data", null);
        }else {
            JSONObject result = new JSONObject();
            List<Enrollment> enrollments = getEnrollment(userId, courseId);
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getSemester().isCurrent()) {
                    result.put("tutorial", enrollment.getTutorial());
                    break;
                }
            }

            jsonResult.put("msg", "success");
            jsonResult.put("data", result);
        }

        return jsonResult;
    }

    @Override
    public JSONObject setTutorial(Long userId, Long courseId, String tutorial) {
        User user = userRepository.findById(userId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else if (course == null) {
            jsonResult.put("msg", "Unable to find the course!");
            jsonResult.put("data", null);
        } else if (!checkIfEnrolled(userId, courseId)) {
            jsonResult.put("msg", "Student not enrolled!");
            jsonResult.put("data", null);
        }else {
            JSONObject result = new JSONObject();
            List<Enrollment> enrollments = getEnrollment(userId, courseId);
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getSemester().isCurrent()) {
                    enrollment.setTutorial(tutorial);
                    enrollmentRepository.save(enrollment);
                    break;
                }
            }

            jsonResult.put("msg", "success");
            jsonResult.put("data", result);
        }

        return jsonResult;
    }
 //
//    @Override
//    public JSONObject getCurrentCourseById(Long userId);
//
//    @Override
//    public JSONObject getPreviousCourseById(Long userId);

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public JSONObject findAllFeedback() {
        List<Course> courses = (List<Course>) courseRepository.findAll();
        JSONObject jsonResult = new JSONObject();
        if(courses.size() == 0)
        {
            jsonResult.put("finding all courses status", 0);
            jsonResult.put("finding all courses status msg", "No comment is found!");
        }
        else {
            jsonResult.put("finding all courses status", 1);
            jsonResult.put("comments", courses);
        }
        return jsonResult;
    }

    @Override
    public int AddACourse() {
        return 0;
    }

    @Override
    public JSONObject AddACourseFeedback() {
        return null;
    }

    @Override
    public int deleteACourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null)
            return 0;
        else
        {
            courseRepository.deleteById(courseId);
            return 1;
        }
    }

    @Override
    public JSONObject deleteACourseFeedback(Long courseId) {
        return null;
    }

    @Override
    public int changeCourseLockStatus(Long courseId, boolean isLocked) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null)
            return 0;
        else
        {
            if(course.getIsLocked() != isLocked)
            {
                course.setIsLocked(isLocked);
                courseRepository.save(course);
                return 1;
            }
            else
            {
                return -1;
            }
        }
    }

    @Override
    public JSONObject changeCourseLockStatusFeedback(Long courseId, boolean isLocked) {
        int resultCode = changeCourseLockStatus(courseId, isLocked);
        JSONObject jsonResult = new JSONObject();
        jsonResult.put("changing the course lock status", resultCode);
        switch (resultCode) {
            case 1:
                jsonResult.put("changing the course lock status msg", "The course lock status is changed successfully.");
                break;
            case 0:
                jsonResult.put("changing the course lock status msg", "The course is not found!");
                break;
            case -1:
                jsonResult.put("changing the course lock status msg", "Fail to change the course lock status because "
                        + "the new lock status is the same to the old one");
                break;
        }
        return jsonResult;
    }
}
