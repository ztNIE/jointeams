package com.jointeams.backend.service;

import com.jointeams.backend.pojo.User;
import org.json.simple.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

public interface UserService {
    public JSONObject getIsAdminById(Long id);
    public JSONObject getUserInfoById(Long id);
    public JSONObject updateUserInfoById(Long id, JSONObject newInfo) throws FileNotFoundException;
    public User findById(Long id);
    public JSONObject uploadAvatar(MultipartFile file);
    public JSONObject getAvatar(String fileName) throws FileNotFoundException;
//    public User addNewUser(User user);
//    public User findByLastName(String lastName);
//    public User findByFullName(String firstName, String lastName);
//    public User findByFullName2(String firstName, String lastName);
//    public User findByIncompleteFullName(String firstName, String lastName);

//    public User verifyUserIdentityByEncryptedPassword(Long userId, String encryptedPassword);
}
