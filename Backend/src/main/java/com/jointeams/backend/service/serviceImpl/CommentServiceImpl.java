package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.repositery.CommentRepository;
import com.jointeams.backend.service.CommentService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return (List<Comment>) commentRepository.findAll();
    }

    @Override
    public JSONObject findAllFeedback() {
        JSONObject jsonResult = new JSONObject();
        List<Comment> comments = findAll();
        if(comments.size() == 0)
        {
            jsonResult.put("finding all comments status", 0);
            jsonResult.put("finding all comments status msg", "No comment is found!");
        }
        else {
            jsonResult.put("finding all comment status", 1);
            jsonResult.put("comments", comments);
        }
        return jsonResult;
    }
}
