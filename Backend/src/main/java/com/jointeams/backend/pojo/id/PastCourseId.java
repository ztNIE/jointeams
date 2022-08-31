package com.jointeams.backend.pojo.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PastCourseId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "semester_id")
    private Long semesterId;

    public PastCourseId() {
    }

    public PastCourseId(Long userId, Long courseId, Long semesterId) {
        this.userId = userId;
        this.courseId = courseId;
        this.semesterId = semesterId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PastCourseId that = (PastCourseId) o;
        return userId.equals(that.userId) && courseId.equals(that.courseId) && semesterId.equals(that.semesterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, courseId, semesterId);
    }
}
