DROP TABLE IF EXISTS `semester`;
CREATE TABLE `semester` (
                            `id` bigint NOT NULL,
                            `is_current` bit(1) NOT NULL,
                            `semester_number` int NOT NULL,
                            `year` int NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `university`;
CREATE TABLE `university` (
                              `id` bigint NOT NULL,
                              `email_url` varchar(50) DEFAULT NULL,
                              `name` varchar(50) NOT NULL,
                              `regex` varchar(50) NOT NULL,
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
                        `id` bigint NOT NULL,
                        `degree` varchar(50) DEFAULT NULL,
                        `description` varchar(300) DEFAULT NULL,
                        `email` varchar(65) NOT NULL,
                        `faculty` varchar(50) DEFAULT NULL,
                        `filename` varchar(50) DEFAULT NULL,
                        `first_name` varchar(50) DEFAULT NULL,
                        `is_activate` bit(1) NOT NULL,
                        `is_admin` bit(1) NOT NULL,
                        `last_name` varchar(50) DEFAULT NULL,
                        `password` varchar(64) DEFAULT NULL,
                        `self_tag` int DEFAULT NULL,
                        `university_id` bigint DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `FKmv2iu0v3unar6uq2paxkmv492` (`university_id`),
                        CONSTRAINT `FKmv2iu0v3unar6uq2paxkmv492` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `verification_token`;
CREATE TABLE `verification_token` (
                                      `id` bigint NOT NULL,
                                      `expiration_time` datetime(6) DEFAULT NULL,
                                      `token` varchar(255) DEFAULT NULL,
                                      `user_id` bigint DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      KEY `FK5xj84jyghtm3fyraxx7frbe4v` (`user_id`),
                                      CONSTRAINT `FK5xj84jyghtm3fyraxx7frbe4v` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `password_token`;
CREATE TABLE `password_token` (
                                  `id` bigint NOT NULL,
                                  `expiration_time` datetime(6) DEFAULT NULL,
                                  `token` varchar(255) DEFAULT NULL,
                                  `user_id` bigint DEFAULT NULL,
                                  PRIMARY KEY (`id`),
                                  KEY `FK6wyl8ctm3rf0nn17k2yt11vkp` (`user_id`),
                                  CONSTRAINT `FK6wyl8ctm3rf0nn17k2yt11vkp` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
                          `id` bigint NOT NULL,
                          `code` varchar(10) DEFAULT NULL,
                          `is_locked` bit(1) DEFAULT NULL,
                          `name` varchar(50) DEFAULT NULL,
                          `next_group_name_id` int DEFAULT NULL,
                          `university_id` bigint DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `FKeetk5fo25fi7j82pro6g7bnvy` (`university_id`),
                          CONSTRAINT `FKeetk5fo25fi7j82pro6g7bnvy` FOREIGN KEY (`university_id`) REFERENCES `university` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `enrollment`;
CREATE TABLE `enrollment` (
                              `id` bigint NOT NULL,
                              `tutorial` varchar(10) DEFAULT NULL,
                              `course_id` bigint DEFAULT NULL,
                              `semester_id` bigint DEFAULT NULL,
                              `user_id` bigint DEFAULT NULL,
                              PRIMARY KEY (`id`),
                              KEY `FK2hxg8clcc86qt2952ra9460gd` (`course_id`),
                              KEY `FKkfwfdhr62iw82nyly1ioxkjww` (`semester_id`),
                              KEY `FKchfdqw1jgq596paxnegbw8qqk` (`user_id`),
                              CONSTRAINT `FK2hxg8clcc86qt2952ra9460gd` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
                              CONSTRAINT `FKchfdqw1jgq596paxnegbw8qqk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                              CONSTRAINT `FKkfwfdhr62iw82nyly1ioxkjww` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `group`;
CREATE TABLE `group` (
                         `id` bigint NOT NULL,
                         `capacity` int DEFAULT NULL,
                         `description` varchar(300) DEFAULT NULL,
                         `name_id` int DEFAULT NULL,
                         `tutorial` varchar(10) DEFAULT NULL,
                         `course_id` bigint DEFAULT NULL,
                         `semester_id` bigint DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         KEY `FKbfknv2fcjud1f58j1mr5yuwwy` (`course_id`),
                         KEY `FKqcd56r0laxt6b1ohkavrp7saq` (`semester_id`),
                         CONSTRAINT `FKbfknv2fcjud1f58j1mr5yuwwy` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
                         CONSTRAINT `FKqcd56r0laxt6b1ohkavrp7saq` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `group_user`;
CREATE TABLE `group_user` (
                              `group_id` bigint NOT NULL,
                              `user_id` bigint NOT NULL,
                              `is_leader` bit(1) NOT NULL,
                              PRIMARY KEY (`group_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `interested_course`;
CREATE TABLE `interested_course` (
                                     `course_id` bigint NOT NULL,
                                     `user_id` bigint NOT NULL,
                                     PRIMARY KEY (`course_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
                           `id` bigint NOT NULL,
                           `content` varchar(300) DEFAULT NULL,
                           `is_hide` bit(1) DEFAULT NULL,
                           `tag` int DEFAULT NULL,
                           `timestamp` datetime(6) DEFAULT NULL,
                           `group_id` bigint DEFAULT NULL,
                           `receiver_id` bigint DEFAULT NULL,
                           `sender_id` bigint DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           KEY `FKr8jlllmcih63wcy5qji6g0e93` (`group_id`),
                           KEY `FK7fwumsgc8iihfl75piwsbwju4` (`receiver_id`),
                           KEY `FKm3nubr4qinci1lwkrw23b1274` (`sender_id`),
                           CONSTRAINT `FK7fwumsgc8iihfl75piwsbwju4` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
                           CONSTRAINT `FKm3nubr4qinci1lwkrw23b1274` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
                           CONSTRAINT `FKr8jlllmcih63wcy5qji6g0e93` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
                                `id` bigint NOT NULL,
                                `content` varchar(300) DEFAULT NULL,
                                `message` varchar(100) DEFAULT NULL,
                                `timestamp` datetime(6) DEFAULT NULL,
                                `type` int DEFAULT NULL,
                                `group_id` bigint DEFAULT NULL,
                                `user_id` bigint DEFAULT NULL,
                                PRIMARY KEY (`id`),
                                KEY `FKjewy4dthuhvng7nrkw52fwp1x` (`group_id`),
                                KEY `FK48udxa7826ty0m0sgvpjlvsd8` (`user_id`),
                                CONSTRAINT `FK48udxa7826ty0m0sgvpjlvsd8` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
                                CONSTRAINT `FKjewy4dthuhvng7nrkw52fwp1x` FOREIGN KEY (`group_id`) REFERENCES `group` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;