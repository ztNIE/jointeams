
package com.jointeams.backend.pojo;


import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Tag selfTag;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private List<Comment> receivedComments;

    @ManyToMany
    @JoinTable(name = "interested_course", joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<Course> interestedCourses;

    @OneToMany(fetch = FetchType.LAZY)
    private List<PastCourse> pastCourses;

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

    public String getName() {
        return firstName + " " + lastName;
    }
}
