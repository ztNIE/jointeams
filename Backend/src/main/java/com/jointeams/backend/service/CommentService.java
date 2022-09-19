package com.jointeams.backend.service;

import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.util.JsonResult;
import org.json.simple.JSONObject;

import java.util.List;

public interface CommentService {
    public JsonResult findAllFeedback();
    public JsonResult deleteACommentFeedback(Long commentId);

}
