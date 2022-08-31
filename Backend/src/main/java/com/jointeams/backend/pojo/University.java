package com.jointeams.backend.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//CREATE TABLE `University` (
//        `id` bigint,
//        `name` varchar(50),
//        PRIMARY KEY (`id`)
//        );
@Entity
@Data
public class University {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
}
