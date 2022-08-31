package com.jointeams.backend.pojo;

import com.jointeams.backend.pojo.id.PastCourseId;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PastCourse {

    @EmbeddedId
    private PastCourseId pastCourseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PastCourse that = (PastCourse) o;
        return pastCourseId != null && Objects.equals(pastCourseId, that.pastCourseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pastCourseId);
    }
}
