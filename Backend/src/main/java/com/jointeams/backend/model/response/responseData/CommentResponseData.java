package com.jointeams.backend.model.response.responseData;

import com.jointeams.backend.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseData {
    private Long id;
    private Long senderId;
    private String senderName;
    private Long receiverId;
    private String receiverName;
    private Integer tag;
    private String content;
    private Timestamp timestamp;
    private Boolean isHidden;
}
