package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.repositery.CommentRepository;
import com.jointeams.backend.service.CommentService;
import com.jointeams.backend.util.JsonResult;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public JsonResult findAllFeedback() {
        JsonResult jsonResult = new JsonResult();
        List<Comment> comments = (List<Comment>) commentRepository.findAll();
        if(comments.size() == 0)
        {
            jsonResult.setStatus(0);
            jsonResult.setMsgAndData("No comment is found!", Optional.empty());
        }
        else {
            jsonResult.setStatus(1);
            jsonResult.setMsgAndData("Finding all course successful", comments);
        }
        return jsonResult;
    }

    @Override
    public JsonResult deleteACommentFeedback(Long commentId) {
        JsonResult jsonResult = new JsonResult();
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if(comment == null)
        {
            jsonResult.setStatus(0);
            jsonResult.setMsgAndData("Deleting the comment fails, because the comment is not found!", Optional.empty());
        }
        else {
            commentRepository.delete(comment);
            jsonResult.setStatus(1);
            jsonResult.setMsgAndData("Deleting the comment successful!", Optional.empty());
        }
        return jsonResult;
    }
}
