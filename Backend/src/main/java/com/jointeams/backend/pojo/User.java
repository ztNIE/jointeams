// TODO
// Fix starting exceptions
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
@Table(uniqueConstraints = @UniqueConstraint(
        name = "email_unique",
        columnNames = "email"
))
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String firstName;

    @Column(length = 50)
    private String lastName;

    @Column(
            length = 65,
            nullable = false
    )
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private University university;

    @Column(length = 50)
    private String degree;

    @Column(length = 50)
    private String faculty;

    @Column(length = 64)
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
    @ToString.Exclude
    private List<Comment> receivedComments;

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
