package com.jointeams.backend.model.response.responseData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseData {
    private Long id;
    private int type;
    private String content;
    private String message;
    private Timestamp timestamp;
    private Long groupId;
    private String groupName;
    private Long userId;
    private String userName;
    private String email;
}
