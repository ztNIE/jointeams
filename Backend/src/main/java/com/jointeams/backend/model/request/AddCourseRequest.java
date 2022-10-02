package com.jointeams.backend.model.request;

import lombok.Data;

@Data
public class AddCourseRequest {
    private String code;
    private String name;
    private Long universityId;
}
