package com.jointeams.backend.service;

import com.jointeams.backend.util.JsonResult;

public interface CommentService {
    public JsonResult findAllFeedback();
    public JsonResult deleteACommentFeedback(Long commentId);

    public JsonResult changeIsCommentAvailableStatus(boolean flag);

}
