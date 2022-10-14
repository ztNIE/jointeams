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
-- select u from  notification;
drop table `unknown`;
drop table interested_course;
drop table notification;
drop table comment;
drop table group_user;
drop table `group`;
drop table enrollment;
drop table password_token;
drop table verification_token;
drop table `user`;
drop table course;
drop table university;
drop table semester;

-- select u from  university;

-- DROP SCHEMA `jointeams` ;