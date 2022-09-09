package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.*;
import com.jointeams.backend.repositery.CommentRepository;
import com.jointeams.backend.repositery.EnrollmentRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.service.UserService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public JSONObject getIsAdminById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if(user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else {
            JSONObject data = new JSONObject();
            data.put("isAdmin", user.isAdmin());

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    @Override
    public JSONObject getUserInfoById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if(user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else {
            JSONObject data = new JSONObject();

//            get personal info
            data.put("id", user.getId());
            data.put("firstName", user.getFirstName());
            data.put("lastName", user.getLastName());
            data.put("avatar", user.getFilename());
            data.put("email", user.getEmail());

            University university = user.getUniversity();
            data.put("email", university.getName());

            data.put("faculty", user.getFaculty());
            data.put("degree", user.getDegree());
            data.put("description", user.getDescription());
            data.put("selfTag", user.getSelfTag());

//            get current course
            JSONArray currentCourse = new JSONArray();
            List<Course> currentCourses = enrollmentRepository.findAllCurrentCourseByUserId(user.getId());
            for (Course course : currentCourses) {
                JSONObject newCourse = new JSONObject();
                newCourse.put("code", course.getCode());
                newCourse.put("name", course.getName());
                currentCourse.add(newCourse);
            }
            data.put("currentCourse", currentCourse);

//            get previous course
            JSONArray previousCourse = new JSONArray();
            List<Course> previousCourses = enrollmentRepository.findAllPreviousCourseByUserId(user.getId());
            for (Course course : previousCourses) {
                JSONObject newCourse = new JSONObject();
                newCourse.put("code", course.getCode());
                newCourse.put("name", course.getName());
                previousCourse.add(newCourse);
            }
            data.put("previousCourse", previousCourse);

//            get interested course
            JSONArray interestedCourse = new JSONArray();
            System.out.println(user.getInterestedCourses());
            for (Course course : user.getInterestedCourses()) {
                JSONObject newCourse = new JSONObject();
                newCourse.put("code", course.getCode());
                newCourse.put("name", course.getName());
                interestedCourse.add(newCourse);
            }
            data.put("interestedCourse", interestedCourse);

//            get comments
            JSONArray allComment = new JSONArray();
            List<Comment> comments = commentRepository.findAllCommentsByUserId(user.getId());
            for (Comment comment : comments) {
                JSONObject newComment = new JSONObject();
                newComment.put("senderName", comment.getSender().getFirstName() + ' ' + comment.getSender().getLastName());
                newComment.put("groupName", comment.getGroup().getCourse().getCode() + "_Group" + comment.getGroup().getNameId());
                newComment.put("content", comment.getContent());
                newComment.put("tag", comment.getTag());
                newComment.put("timeStamp", comment.getTimestamp());
                newComment.put("isHide", comment.getIsHide());
                allComment.add(newComment);
            }
            data.put("comment", allComment);

            jsonResult.put("msg", "success");
            jsonResult.put("data", data);
        }

        return jsonResult;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

//    @Override
//    public User findByLastName(String lastName) {
//        User user = userRepository.findByLastName(lastName).orElse(null);
//        return user;
//
//    }
//    @Override
//    public User findByFullName(String firstName, String lastName)
//    {
//        User user = userRepository.findByFirstNameAndLastName(firstName, lastName).orElse(null);
//        return user;
//    }
//
//    @Override
//    public User findByFullName2(String firstName, String lastName)
//    {
//        User user = userRepository.findByFullName(firstName, lastName).orElse(null);
//        return user;
//    }
//
//    @Override
//    public User findByIncompleteFullName(String firstName, String lastName)
//    {
//        User user = userRepository.findByIncompleteFullName(firstName, lastName).orElse(null);
//        return user;
//    }

//    @Override
//    public User verifyUserIdentityByEncryptedPassword(Long userId, String encryptedPassword) {
//        User user = findById(userId);
//        if(user != null)
//        {
//            try {
//                String passwordSHA1 = SHAUtils.sha1(user.getPassword());
//                if(passwordSHA1.equals(encryptedPassword))
//                    return user;// userId and encryptedPassword are matched
//                else
//                    return null;// userId and encryptedPassword are NOT matched
//            } catch (NoSuchAlgorithmException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        else
//            return null; // 1: no user is found
//    }



//    @Override
//    public User addNewUser(User user) {
//        return userRepository.save(user);
//    }
}

