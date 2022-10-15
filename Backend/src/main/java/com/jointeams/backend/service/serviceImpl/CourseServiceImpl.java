package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.model.response.CourseDetailResponse;
import com.jointeams.backend.model.response.responseData.CourseResponseData;
import com.jointeams.backend.pojo.*;
import com.jointeams.backend.pojo.id.InterestedCourseKey;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.CourseService;
import com.jointeams.backend.service.UniversityToolService;
import com.jointeams.backend.util.JsonResult;
import org.json.simple.JSONArray;
import com.jointeams.backend.repositery.CourseRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
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

    @Autowired
    private UniversityToolService universityToolService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private GroupRepository groupRepository;

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
                    newCourse.put("is_lock", course.getIsLocked());
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
                newStudent.put("fullName", student.getFirstName()+ " " + student.getLastName());
                newStudent.put("email", student.getEmail());
                newStudent.put("fileName", student.getFilename());
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
            newStudent.put("fullName", student.getFirstName() + " " + student.getLastName());
            newStudent.put("email", student.getEmail());
            newStudent.put("fileName", student.getFilename());

            JSONArray tags = new JSONArray();
            if (student.getSelfTag() != null) {
                tags.add(student.getSelfTag());
            }
            List<Comment> comments = commentRepository.findAllCommentsByUserId(student.getId());
            for (Comment comment : comments) {
                if (!tags.contains(comment.getTag())) {
                    tags.add(comment.getTag());
                }
            }
            tags.sort(Comparator.comparing(obj -> ((int) obj)));
            newStudent.put("tags", tags);

            Enrollment currentEnrollment = null;
            List<Enrollment> enrollments = enrollmentRepository.findEnrollmentByUserIdAndCourseId(student.getId(), courseId);
            for (Enrollment enrollment : enrollments) {
                if (enrollment.getSemester().isCurrent()) {
                    currentEnrollment = enrollment;
                    break;
                }
            }
            newStudent.put("tutorial", currentEnrollment.getTutorial());

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
                        teammate.put("fullName", currentStudent.get("fullName"));
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
    public JsonResult findAllFeedback() {
        List<Course> courses = (List<Course>) courseRepository.findAll();
        ArrayList<CourseResponseData> courseResponseDataArrayList = new ArrayList<>();
        courses.forEach(course -> {
            CourseResponseData courseResponseData = new CourseResponseData();
            courseResponseData.setId(course.getId());
            courseResponseData.setCode(course.getCode());
            courseResponseData.setName(course.getName());
            courseResponseData.setUniversityId(course.getUniversity().getId());
            courseResponseData.setUniversityName(course.getUniversity().getName());
            courseResponseData.setIsLocked(course.getIsLocked());
            courseResponseDataArrayList.add(courseResponseData);
        });
        JsonResult jsonResult = new JsonResult();
        if(courses.size() == 0)
        {
            jsonResult.setStatus(0);
            jsonResult.setMsgAndData("No course is found!", Optional.empty());
        }
        else {
            jsonResult.setStatus(1);
            jsonResult.setMsgAndData("Finding all course successful", courseResponseDataArrayList);
        }
        return jsonResult;
    }

    private int addACourse(String code, String name, Long universityId)
    {
        University university = universityToolService.findById(universityId);
        if(university == null)
            return -1;

        Course course = (Course) courseRepository.findCourseByCodeAndUniversity(code, university).orElse(null);
        Course course1 = (Course) courseRepository.findCourseByNameAndUniversity(name, university).orElse(null);

        if(course == null && course1 == null)
        {
            course = new Course();
            course.setCode(code);
            course.setName(name);
            course.setNextGroupNameId(0);
            course.setIsLocked(false);
            course.setUniversity(university);
            courseRepository.save(course);
            return 1;
        }
        else
            return 0;
    }

    @Override
    public JsonResult addACourseFeedback(String code, String name, Long universityId) {
        int resultCode = addACourse(code, name, universityId);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(resultCode);
        switch (resultCode) {
            case -1:
                jsonResult.setMsgAndData("The university is not found!", Optional.empty());
                break;
            case 0:
                jsonResult.setMsgAndData("The course code or name is duplicated for this university!", Optional.empty());
                break;
            case 1:
                jsonResult.setMsgAndData( "The course, " + code + ": " + name +" is added successfully.",
                        Optional.empty());
                break;
        }
        return jsonResult;
    }

    private int deleteACourse(Long courseId) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null)
            return 0;
        else
        {
            List<Group> groups = this.groupRepository.getGroupsByCourseId(courseId);
            groups.forEach(group -> {
                notificationRepository.deleteAllByGroupId(group.getId());
                commentRepository.deleteAllByGroupId(group.getId());
                groupUserRepository.deleteAllByGroupId(group.getId());
            });

            // Enrollment
            this.enrollmentRepository.deleteEnrollmentByCourseId(courseId);

            groupRepository.deleteAll(groups);
            courseRepository.delete(course);
            return 1;
        }
    }

    @Override
    public JsonResult deleteACourseFeedback(Long courseId) {
        int resultCode = deleteACourse(courseId);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(resultCode);
        switch (resultCode) {
            case 0:
                jsonResult.setMsgAndData("The course is not found!", Optional.empty());
                break;
            case 1:
                jsonResult.setMsgAndData( "The course is deleted successfully.", Optional.empty());
                break;
        }
        return jsonResult;
    }

    private int changeCourseLockStatus(Long courseId, boolean isLocked) {
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
    public JsonResult changeCourseLockStatusFeedback(Long courseId, boolean isLocked) {
        int resultCode = changeCourseLockStatus(courseId, isLocked);
        JsonResult jsonResult = new JsonResult();
        jsonResult.setStatus(resultCode);
        switch (resultCode) {
            case -1:
                jsonResult.setMsgAndData( "Fail to change the course lock status because the new lock status is " +
                        "the same to the old one", Optional.empty());
                break;
            case 0:
                jsonResult.setMsgAndData( "The course is not found!", Optional.empty());
                break;
            case 1:
                jsonResult.setMsgAndData("The course lock status is changed successfully.", Optional.empty());
                break;
        }
        return jsonResult;
    }

    @Override
    public CourseDetailResponse getCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return null;
        }
        return new CourseDetailResponse(course);
    }
}
