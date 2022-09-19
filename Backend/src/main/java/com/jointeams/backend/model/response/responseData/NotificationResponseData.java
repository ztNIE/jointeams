package com.jointeams.backend.model.response.responseData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponseData {
    Long id;
    int type;
    Long groupId;
    String groupName;
    Long userId;
    String userName;
    String email;
}
