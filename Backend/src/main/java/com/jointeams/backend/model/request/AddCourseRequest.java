package com.jointeams.backend.model.request;

import lombok.Getter;

@Getter
public class AddCourseRequest {
    private String code;
    private String name;
    private Long universityId;
}
