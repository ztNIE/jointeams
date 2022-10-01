package com.jointeams.backend.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CommentRequest {
    private Long groupId;
    private Long receiverId;
    private Long senderId;
    private Integer tag;
    private String content;
}
