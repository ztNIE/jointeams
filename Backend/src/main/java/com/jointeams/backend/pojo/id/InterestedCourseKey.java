package com.jointeams.backend.pojo.id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InterestedCourseKey implements Serializable {

    @Column(name = "user_id")
    Long userId;

    @Column(name = "course_id")
    Long courseId;

    public InterestedCourseKey() {}

    public InterestedCourseKey(Long userId, Long courseId) {
        this.userId = userId;
        this.courseId = courseId;
    }

    public Long getUserId() {
        return userId;
    }

    public Long getCourseId() {
        return courseId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestedCourseKey that = (InterestedCourseKey) o;
        return courseId.equals(that.courseId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, userId);
    }
    // standard constructors, getters, and setters
    // hashcode and equals implementation
}
