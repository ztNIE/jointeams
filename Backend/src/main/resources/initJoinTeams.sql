CREATE database jointeams;
USE jointeams;

CREATE User 'jointeams'@'localhost' IDENTIFIED BY '12345678';

CREATE TABLE `Semester` (
  `id` bigint,
  `year` int,
  `semester_number` int,
  `is_current` boolean,
  PRIMARY KEY (`id`)
);

CREATE TABLE `University` (
  `id` bigint,
  `name` varchar(50),
  PRIMARY KEY (`id`)
);

CREATE TABLE `User` (
  `id` bigint,
  `first_name` varchar(50),
  `last_name` varchar(50),
  `email` varchar(65),
  `university_id` bigint,
  `degree` varchar(50),
  `faculty` varchar(50),
  `password` varchar(32),
  `is_admin` boolean,
  `description` varchar(300),
  `filename` varchar(50),
  `is_activate` boolean,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`university_id`) REFERENCES `Univerisity`(`id`)
);

CREATE TABLE `Course` (
  `id` bigint,
  `code` varchar(10),
  `name` varchar(50),
  `uni_id` bigint,
  `is_locked` boolean,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`uni_id`) REFERENCES `Univerisity`(`id`)
);

CREATE TABLE `Group` (
  `id` bigint,
  `course_id` bigint,
  `semester_id` bigint,
  `name_id` int,
  `tutorial` varchar(10),
  `description` varchar(300),
  `capacity` int,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`semester_id`) REFERENCES `Semester`(`id`),
  FOREIGN KEY (`course_id`) REFERENCES `Course`(`id`)
);

CREATE TABLE `GroupUser` (
  `group_id` bigint,
  `user_id` bigint,
  `is_leader` boolean,
  PRIMARY KEY (`group_id`, `user_id`),
  FOREIGN KEY (`user_id`) REFERENCES `User`(`id`),
  FOREIGN KEY (`group_id`) REFERENCES `Group`(`id`)
);

CREATE TABLE `Comment` (
  `id` bigint,
  `sender_id` bigint,
  `receiver_id` bigint,
  `is_hide` boolean,
  `content` varchar(160),
  `create_time` datetime,
  `course_id` bigint,
  PRIMARY KEY (`id`)
);

CREATE TABLE `Tag` (
  `id` bigint,
  `type` int,
  PRIMARY KEY (`id`)
);

CREATE TABLE `TagComment` (
  `id` bigint,
  `comment_id` bigint,
  `tag_id` bigint,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`comment_id`) REFERENCES `Comment`(`id`),
  FOREIGN KEY (`tag_id`) REFERENCES `Tag`(`id`)
);

CREATE TABLE `Enrollment` (
  `id` bigint,
  `user_id` bigint,
  `course_id` bigint,
  `semester_id` bigint,
  `tutorial` varchar(10),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`semester_id`) REFERENCES `Semester`(`id`),
  FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);

CREATE TABLE `InterestedCourse` (
  `user_id` bigint,
  `course_id` bigint,
  PRIMARY KEY (`user_id`, `course_id`),
  FOREIGN KEY (`user_id`) REFERENCES `User`(`id`),
  FOREIGN KEY (`course_id`) REFERENCES `Course`(`id`)
);

CREATE TABLE `PastCourse` (
  `user_id` bigint,
  `course_id` bigint,
  `semester_id` bigint,
  PRIMARY KEY (`user_id`, `course_id`, `semester_id`),
  FOREIGN KEY (`course_id`) REFERENCES `Course`(`id`),
  FOREIGN KEY (`user_id`) REFERENCES `User`(`id`),
  FOREIGN KEY (`semester_id`) REFERENCES `Semester`(`id`)
);

CREATE TABLE `Notification` (
  `id` bigint,
  `sender_id` bigint,
  `receiver_id` bigint,
  `type` int,
  `create_time` datetime,
  `content` varchar(300),
  PRIMARY KEY (`id`),
  FOREIGN KEY (`receiver_id`) REFERENCES `User`(`id`)
);

CREATE TABLE `TagUserSelf` (
  `user_id` bigint,
  `tag_id` bigint,
  PRIMARY KEY (`user_id`, `tag_id`),
  FOREIGN KEY (`tag_id`) REFERENCES `Tag`(`id`),
  FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);

GRANT ALL privileges ON jointeams.* TO 'jointeams'@'localhost';