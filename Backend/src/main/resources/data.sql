INSERT INTO jointeams.semester (id, is_current, semester_number, year) VALUES (1, true, 2, 2022);
INSERT INTO jointeams.semester (id, is_current, semester_number, year) VALUES (2, false, 1, 2022);

INSERT INTO jointeams.university (id, name, regex) VALUES (1, 'The University of Sydney', '@uni.sydney.edu.au');

INSERT INTO jointeams.user (id, degree, description, email, faculty, filename, first_name, is_activate, is_admin, last_name, password, self_tag, university_id) VALUES (1, 'Bachelor of Engineering Honours (Software)', 'HD', 'yliu8378@uni.sydney.edu.au', 'School of Information and Engineering', null, 'Yuyun', true, false, 'Liu', '000000', null, 1);
INSERT INTO jointeams.user (id, degree, description, email, faculty, filename, first_name, is_activate, is_admin, last_name, password, self_tag, university_id) VALUES (2, 'Bachelor of Computer Science', 'D', 'abc1234@uni.sydney.edu.au', 'School of Science', null, 'Alice', true, false, 'Cole', '000000', null, 1);
INSERT INTO jointeams.user (id, degree, description, email, faculty, filename, first_name, is_activate, is_admin, last_name, password, self_tag, university_id) VALUES (3, 'Master of Computer Science', 'HD', 'efgh1234@uni.sydney.edu.au', 'School of Science', null, 'Jin', true, false, 'Ma', '000000', null, 1);
INSERT INTO jointeams.user (id, degree, description, email, faculty, filename, first_name, is_activate, is_admin, last_name, password, self_tag, university_id) VALUES (4, 'ABC', 'A', 'dcdncdcnd@uni.sydney.edu.au', '....', null, 'A', true, false, 'B', '000000', null, 1);
insert into user (degree, description, email,
                  faculty, filename, first_name,
                  is_activate, is_admin, last_name,
                  password, self_tag, university_id)
values
    ('IT', 'from IT', 'deve0001@uni.sydney.edu.au',
     'engineering', 'avator.jpg', 'dev',
     true, false, 'dev',
     '$2a$11$7RSfIct4qbJHVrsFoSEqmOosn7rCSfDL3QqL/DX2gWEccTL4hiH46',
     null, 1),
    (null, null, 'admin@gmail.com',
     null, null, 'admin',
     true, true, 'admin',
     '$2a$11$7RSfIct4qbJHVrsFoSEqmOosn7rCSfDL3QqL/DX2gWEccTL4hiH46',
     null, 1);

INSERT INTO jointeams.course (id, code, is_locked, name, next_group_name_id, university_id) VALUES (1, 'ELEC5619', false, 'Spring Boot Framework', 2, 1);
INSERT INTO jointeams.course (id, code, is_locked, name, next_group_name_id, university_id) VALUES (2, 'COMP3308', false, 'Introduction to Artificial Intelligence', 3, 1);

INSERT INTO jointeams.enrollment (id, tutorial, course_id, semester_id, user_id) VALUES (1, 'CC02', 1, 1, 1);
INSERT INTO jointeams.enrollment (id, tutorial, course_id, semester_id, user_id) VALUES (2, 'CC02', 1, 1, 2);
INSERT INTO jointeams.enrollment (id, tutorial, course_id, semester_id, user_id) VALUES (3, 'CC02', 1, 1, 3);
INSERT INTO jointeams.enrollment (id, tutorial, course_id, semester_id, user_id) VALUES (4, 'RE05', 2, 1, 4);
INSERT INTO jointeams.enrollment (id, tutorial, course_id, semester_id, user_id) VALUES (5, 'RE08', 1, 2, 1);
INSERT INTO jointeams.enrollment (id, tutorial, course_id, semester_id, user_id) VALUES (6, 'CC09', 2, 1, 1);

INSERT INTO jointeams.`group` (id, capacity, description, name_id, tutorial, course_id, semester_id) VALUES (1, 3, 'Good', 1, 'CC02', 1, 1);
INSERT INTO jointeams.`group` (id, capacity, description, name_id, tutorial, course_id, semester_id) VALUES (2, 3, 'Good', 1, 'RE05', 2, 1);
INSERT INTO jointeams.`group` (id, capacity, description, name_id, tutorial, course_id, semester_id) VALUES (3, 3, 'Good', 1, 'RE08', 1, 2);
INSERT INTO jointeams.`group` (id, capacity, description, name_id, tutorial, course_id, semester_id) VALUES (4, 3, 'Good', 2, 'CC09', 1, 1);
INSERT INTO jointeams.`group` (id, capacity, description, name_id, tutorial, course_id, semester_id) VALUES (5, 4, null, 2, 'CC09', 2, 1);

INSERT INTO jointeams.group_user (group_id, user_id, is_leader) VALUES (1, 1, true);
INSERT INTO jointeams.group_user (group_id, user_id, is_leader) VALUES (1, 2, false);
INSERT INTO jointeams.group_user (group_id, user_id, is_leader) VALUES (2, 4, true);
INSERT INTO jointeams.group_user (group_id, user_id, is_leader) VALUES (3, 1, true);
INSERT INTO jointeams.group_user (group_id, user_id, is_leader) VALUES (4, 3, true);
INSERT INTO jointeams.group_user (group_id, user_id, is_leader) VALUES (5, 1, true);

INSERT INTO jointeams.comment (id, content, is_hide, tag, timestamp, group_id, receiver_id, sender_id) VALUES (1, 'Good', false, 1, '2022-09-09 18:08:27', 1, 2, 1);

INSERT INTO jointeams.notification (id, content, message, timestamp, type, group_id, user_id) VALUES (3, 'Dear ELEC5619_Group1,
Jin Ma applies to join your group. For more information, please go to profile.', 'A Joining Request sent from Jin Ma.', '2022-09-09 23:23:27.721000', 1, 1, 3);