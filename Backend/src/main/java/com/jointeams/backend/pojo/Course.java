package com.jointeams.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10)
    private String code;

    @Column(length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private University university;

    @OneToMany(mappedBy = "id")
    @ToString.Exclude
    private List<Group> groups;

    private Integer nextGroupNameId;

    private Boolean isLocked;

//    @ManyToMany (mappedBy = "interestedCourses")
////    List<User> likes;
//    Set<User> interestedUsers = new HashSet<>();
//    @OneToMany(mappedBy = "course")
//    private Set<InterestedCourse> interestedUsers;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Course course = (Course) o;
        return id != null && Objects.equals(id, course.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
