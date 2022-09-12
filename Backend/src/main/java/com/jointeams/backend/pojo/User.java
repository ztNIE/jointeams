
package com.jointeams.backend.pojo;


import lombok.*;
import org.hibernate.Hibernate;

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
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(length = 65, nullable = false)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    private University university;

    @Column(length = 50)
    private String degree;

    @Column(length = 50)
    private String faculty;

    @Column(length = 32)
    private String password;

    private boolean isAdmin;

    @Column(length = 300)
    private String description;

    @Column(length = 50)
    private String filename;

    private boolean isActivate;

    private Integer selfTag;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private List<Comment> receivedComments;

//    @OneToMany(mappedBy = "user")
//    private Set<InterestedCourse> interestedCourses;
//    @ManyToMany (cascade = {
//            CascadeType.PERSIST,
//            CascadeType.MERGE
//    })
//    @JoinTable(name = "interested_course",
//            joinColumns = @JoinColumn(name = "user_id"),
//            inverseJoinColumns = @JoinColumn(name = "course_id"))
////    @MapsId("id")
//    Set<Course> interestedCourses = new HashSet<>();
//    private List<Course> interestedCourses;

//    @OneToMany(mappedBy = "student")
//    Set<CourseRating> ratings;

//    @OneToMany(fetch = FetchType.LAZY)
//    private List<PastCourse> pastCourses;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
