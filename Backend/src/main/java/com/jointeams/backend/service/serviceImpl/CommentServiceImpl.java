package com.jointeams.backend.service.serviceImpl;

import com.jointeams.backend.model.response.responseData.CommentResponseData;
import com.jointeams.backend.pojo.Comment;
import com.jointeams.backend.pojo.User;
import com.jointeams.backend.repositery.CommentRepository;
import com.jointeams.backend.repositery.UserRepository;
import com.jointeams.backend.service.CommentService;
import com.jointeams.backend.springmail.SendAllEmailEvent;
import com.jointeams.backend.util.IsCommentAvailable;
import com.jointeams.backend.util.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

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
            List<CommentResponseData> commentResponseDataList = new ArrayList<>();
            comments.forEach(comment -> {
                CommentResponseData commentResponseData = new CommentResponseData();
                commentResponseData.setId(comment.getId());
                commentResponseData.setSenderId(comment.getSender().getId());
                commentResponseData.setReceiverId(comment.getReceiver().getId());
                commentResponseData.setSenderName(comment.getSender().getFirstName() + " "
                        + comment.getSender().getLastName());
                commentResponseData.setReceiverName(comment.getReceiver().getFirstName() + " "
                        + comment.getReceiver().getLastName());
                commentResponseData.setTag(comment.getTag());
                commentResponseData.setContent(comment.getContent());
                commentResponseData.setTimestamp(comment.getTimestamp());
                commentResponseData.setIsHidden(comment.getIsHide());
                commentResponseDataList.add(commentResponseData);
            });
            jsonResult.setStatus(1);
            jsonResult.setMsgAndData("Finding all course successful", commentResponseDataList);
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
            jsonResult.setMsgAndData("The comment has been deleted successful!", Optional.empty());
        }
        return jsonResult;
    }



    @Override
    public JsonResult changeIsCommentAvailableStatus(boolean isAvailable) {
        JsonResult jsonResult = new JsonResult();
        if(IsCommentAvailable.Flag.getValue() == isAvailable)
        {
            jsonResult.setStatus(0);
            jsonResult.setMsgAndData("Change IsCommentAvailable status failed, because the new status is the same to the current one!",
                    Optional.empty());
        } else {
            IsCommentAvailable.Flag.setValue(isAvailable);
            if (isAvailable) {
                jsonResult.setStatus(1);
                jsonResult.setMsgAndData("Comment feature has been opened successfully, and the reminders are sending.", Optional.empty());
                List<User> users = userRepository.findAllUsersHavingGroupsInTheCurrentSemester();
                publisher.publishEvent(new SendAllEmailEvent(users));
                log.info("Comments available notifications published");
            }
            else
            {
                jsonResult.setStatus(2);
                jsonResult.setMsgAndData("Comment feature has been closed successfully.", Optional.empty());
            }
            IsCommentAvailable.Flag.saveValue();
        }
        return jsonResult;
    }
}
