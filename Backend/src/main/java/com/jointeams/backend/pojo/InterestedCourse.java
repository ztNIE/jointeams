package com.jointeams.backend.pojo;

import com.jointeams.backend.pojo.id.InterestedCourseKey;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class InterestedCourse {
    @EmbeddedId
    InterestedCourseKey id;

//    @ManyToOne
//    @MapsId("userId")
//    @JoinColumn(name = "user_id")
//    User user;
//
//    @ManyToOne
//    @MapsId("courseId")
//    @JoinColumn(name = "course_id")
//    Course course;
}
