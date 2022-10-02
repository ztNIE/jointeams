package com.jointeams.backend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponse {
    private Long userId;
    private String email;
    private List<String> roles;
}
