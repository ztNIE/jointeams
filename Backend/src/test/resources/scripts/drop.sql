-- this.commentRepository.delete(comment1);
--         this.groupUserRepository.delete(groupUser2);
--         this.groupUserRepository.delete(groupUser1);
--         this.groupRepository.delete(group1);
--         this.enrollmentRepository.delete(enrollment3);
--         this.enrollmentRepository.delete(enrollment2);
--         this.enrollmentRepository.delete(enrollment1);
--         this.userRepository.delete(user3);
--         this.userRepository.delete(user2);
--         this.userRepository.delete(user1);
--         this.courseRepository.delete(course2);
--         this.courseRepository.delete(course1);
--         this.universityRepository.delete(university);
--         this.semesterRepository.delete(semester);

-- delete from `comment`;
-- delete from `group_user`;
-- delete from `group`;
-- delete from `enrollment`;
-- delete from `user`;
-- delete from `course`;
-- delete from `university`;
-- delete from `semester`;
--
-- alter table `comment` auto_increment=1;
-- alter table `group` auto_increment=1;
-- alter table `enrollment` auto_increment=1;
-- alter table `user` auto_increment=1;
-- alter table `course` auto_increment=1;
-- alter table `university` auto_increment=1;
-- alter table `semester` auto_increment=1;

-- DROP SCHEMA jointeams;
--
drop table if exists interested_course;
drop table if exists notification;
drop table if exists comment;
drop table if exists group_user;
drop table if exists `group`;
drop table if exists enrollment;
drop table if exists password_token;
drop table if exists verification_token;
drop table if exists `user`;
drop table if exists course;
drop table if exists university;
drop table if exists semester;

-- DROP SCHEMA `jointeams` ;