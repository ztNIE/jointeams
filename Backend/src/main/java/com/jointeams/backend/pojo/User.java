
package com.jointeams.backend.pojo;

//CREATE TABLE `User` (
//        `id` bigint,
//        `first_name` varchar(50),
//        `last_name` varchar(50),
//        `email` varchar(65),
//        `university_id` bigint,
//        `degree` varchar(50),
//        `faculty` varchar(50),
//        `password` varchar(32),
//        `is_admin` boolean,
//        `description` varchar(300),
//        `filename` varchar(50),
//        `is_activate` boolean,
//        PRIMARY KEY (`id`),
//        FOREIGN KEY (`university_id`) REFERENCES `Univerisity`(`id`)
//        );


import lombok.Data;

import javax.persistence.*;

@Entity
//@Table(name = "User")
@Data
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
//    @Column(name="id")
    private long id;
//    @Column(name = "first_name")
    private String firstName;
//    @Column(name = "last_name")
    private String lastName;
//    @Column(name = "email")
    private String email;
//    @Column(name = "university_id")
    private long universityId;
//    @Column(name = "degree")
    private String degree;
    private String faculty;
    private String password;
    private boolean isAdmin;
    private String description;
    private String filename;
    private boolean isActivate;

    public String getName() {
        return firstName + " " + lastName;
    }
}
