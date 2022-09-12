package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Comment;
import org.json.simple.JSONObject;

import java.util.List;

public interface CommentService {
    public List<Comment> findAll();
    public JSONObject findAllFeedback();
}
