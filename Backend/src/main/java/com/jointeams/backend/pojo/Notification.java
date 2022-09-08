package com.jointeams.backend.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // set cascade level
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    // 0 -> send invitation: group leader -> sender, user -> receiver
    // 1 -> send join request: user -> sender, group leader -> receiver
    // 2 -> join request response(accepted): group leader -> sender, user -> receiver
    // 3 -> join request response(declined): group leader -> sender, user -> receiver
    // 4 -> invitation response(accepted): user -> sender, group leader -> receiver
    // 5 -> invitation response(declined): user -> sender, group leader -> receiver
    private Integer type;

    @Column(length = 100)
    private String message;

    @CreationTimestamp
    private Timestamp timestamp;

    @Column(length = 300)
    private String content;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Notification that = (Notification) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
