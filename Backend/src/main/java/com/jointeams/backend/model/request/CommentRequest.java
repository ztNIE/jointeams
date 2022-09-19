package com.jointeams.backend.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private Long groupId;
    private Long receiverId;
    private Long senderId;
    private Integer tag;
    private String content;
}
