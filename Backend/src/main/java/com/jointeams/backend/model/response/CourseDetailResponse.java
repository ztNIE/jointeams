package com.jointeams.backend.model.response;

import com.jointeams.backend.pojo.Course;
import lombok.Data;

@Data
public class CourseDetailResponse {
    private Long id;
    private String code;
    private Boolean isLocked;
    private String name;
    private Integer nextGroupNameId;

    public CourseDetailResponse(Course course) {
        this.id = course.getId();
        this.code = course.getCode();
        this.isLocked = course.getIsLocked();
        this.name = course.getName();
        this.nextGroupNameId = course.getNextGroupNameId();
    }
}
