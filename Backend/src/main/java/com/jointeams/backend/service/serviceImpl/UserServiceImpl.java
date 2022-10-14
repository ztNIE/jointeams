package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.*;
import com.jointeams.backend.repositery.*;
import com.jointeams.backend.service.UserService;
import com.jointeams.backend.util.UpPhotoNameUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private InterestedCourseRepository interestedCourseRepository;

    @Override
    public JSONObject getIsAdminById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
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

        if (user == null) {
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
            data.put("university", user.getUniversity().getName());
            data.put("faculty", user.getFaculty());
            data.put("degree", user.getDegree());
            data.put("description", user.getDescription());
            data.put("selfTag", user.getSelfTag());

//            get current course
            JSONArray currentCourse = new JSONArray();
            List<Course> currentCourses = enrollmentRepository.findAllCurrentCourseByUserId(user.getId());
            for (Course course : currentCourses) {
                JSONObject newCourse = new JSONObject();
                newCourse.put("id", course.getId());
                newCourse.put("code", course.getCode());
                newCourse.put("name", course.getName());
                newCourse.put("is_lock", course.getIsLocked());
                currentCourse.add(newCourse);
            }
            data.put("currentCourse", currentCourse);

//            get previous course
            JSONArray previousCourse = new JSONArray();
            List<Course> previousCourses = enrollmentRepository.findAllPreviousCourseByUserId(user.getId());
            for (Course course : previousCourses) {
                JSONObject newCourse = new JSONObject();
                newCourse.put("id", course.getId());
                newCourse.put("code", course.getCode());
                newCourse.put("name", course.getName());
                newCourse.put("is_lock", course.getIsLocked());
                previousCourse.add(newCourse);
            }
            data.put("previousCourse", previousCourse);

//            get interested course
            JSONArray interestedCourse = new JSONArray();
            for (InterestedCourse ic : interestedCourseRepository.findByUserId(user.getId())) {
                JSONObject newCourse = new JSONObject();
                Optional<Course> course = courseRepository.findById(ic.getId().getCourseId());
                newCourse.put("id", course.get().getId());
                newCourse.put("code", course.get().getCode());
                newCourse.put("name", course.get().getName());
                newCourse.put("is_lock", course.get().getIsLocked());
                interestedCourse.add(newCourse);
            }
            data.put("interestedCourse", interestedCourse);

//            get comments
            JSONArray allComment = new JSONArray();
            List<Comment> comments = commentRepository.findAllCommentsByUserId(user.getId());
            for (Comment comment : comments) {
                JSONObject newComment = new JSONObject();
                newComment.put("id", comment.getId());
                newComment.put("courseCode", comment.getGroup().getCourse().getCode());
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
    public JSONObject updateUserInfoById(Long id, JSONObject newInfo) throws FileNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        JSONObject jsonResult = new JSONObject();

        if (user == null) {
            jsonResult.put("msg", "Unable to find the user!");
            jsonResult.put("data", null);
        } else {
            if ((Boolean) newInfo.get("updateAvatar") && newInfo.get("oldAvatar") != null) {
                String projectPath = ResourceUtils.getURL("").getPath();
                if (!projectPath.split("/")[projectPath.split("/").length - 1].equals("Backend")) {
                    projectPath += "Backend/";
                }
                String UPLOAD_PATH = projectPath + "src/main/resources/images/";
                FileSystemUtils.deleteRecursively(new File(UPLOAD_PATH + newInfo.get("oldAvatar")));
            }

            user.setFilename((String) newInfo.get("avatar"));
            user.setSelfTag((Integer) newInfo.get("selfTag"));
            user.setFirstName((String) newInfo.get("firstName"));
            user.setLastName((String) newInfo.get("lastName"));
            user.setFaculty((String) newInfo.get("faculty"));
            user.setDegree((String) newInfo.get("degree"));
            user.setDescription((String) newInfo.get("description"));

            List<Comment> comments = commentRepository.findAllCommentsByUserId(user.getId());
            List<LinkedHashMap> updatedComments = (ArrayList<LinkedHashMap>) newInfo.get("comment");
            for (int i = 0; i < updatedComments.size(); i++) {
                LinkedHashMap updatedComment = updatedComments.get(i);
                for (Comment comment : comments) {
                    if (comment.getId().toString().equals(updatedComment.get("id").toString())) {
                        comment.setIsHide((Boolean) updatedComment.get("isHide"));
                        commentRepository.save(comment);
                        break;
                    }
                }
            }

            userRepository.save(user);

            jsonResult.put("msg", "success");
            jsonResult.put("data", newInfo);
        }

        return jsonResult;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user;
    }

    @Override
    public JSONObject uploadAvatar(MultipartFile file) {
        JSONObject jsonResult = new JSONObject();
        try {
            String projectPath = ResourceUtils.getURL("").getPath();
            if (!projectPath.split("/")[projectPath.split("/").length - 1].equals("Backend")) {
                projectPath += "Backend/";
            }
            String UPLOAD_PATH = projectPath + "src/main/resources/images/";
            byte[] bytes = file.getBytes();
            String imageFileName = file.getOriginalFilename();
            String fileName = UpPhotoNameUtils.getPhotoName("img", imageFileName);
            Path path = Paths.get(UPLOAD_PATH + fileName);
            Files.write(path, bytes);

            JSONObject filePath = new JSONObject();
            filePath.put("path", fileName);
            jsonResult.put("msg", "success");
            jsonResult.put("data", filePath);
        } catch (IOException e) {
            e.printStackTrace();
            jsonResult.put("msg", "Failed to upload the avatar!");
            jsonResult.put("data", null);
        }

        return jsonResult;
    }

    @Override
    public JSONObject getAvatar(String fileName) throws FileNotFoundException {
        JSONObject jsonResult = new JSONObject();

        String projectPath = ResourceUtils.getURL("").getPath();
        if (!projectPath.split("/")[projectPath.split("/").length - 1].equals("Backend")) {
            projectPath += "Backend/";
        }
        String path = projectPath + "src/main/resources/images/" + fileName;
        File file = new File(path);
        InputStream in;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();

            String encodedString = Base64.getEncoder().encodeToString(data);
            JSONObject encoded = new JSONObject();
            encoded.put("image", encodedString);
            jsonResult.put("msg", "success");
            jsonResult.put("data", encoded);

        } catch (IOException e) {
            e.printStackTrace();
            jsonResult.put("msg", "Failed to get the avatar!");
            jsonResult.put("data", null);
        }

        return jsonResult;

    }
}

