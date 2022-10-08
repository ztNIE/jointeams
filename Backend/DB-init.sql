insert into university (name, regex, email_url) values
                                         ('University of Sydney', '^[a-z]{4}[0-9]{4}@uni.sydney.edu.au$', 'https://www.outlook.com/uni.sydney.edu.au'),
                                         ('Test University', '^[a-z]+@gmail.com$', '');

insert into user (degree, description, email,
                  faculty, filename, first_name,
                  is_activate, is_admin, last_name,
                  password, self_tag, university_id)
values
    ('Bachelor of Engineering Honors (Software)', 'Hi, I am Emma Kwan :)', 'devu0001@uni.sydney.edu.au',
     'Engineering', null, 'Emma',
     true, false, 'Kwan',
     '$2a$10$4Y.YYVx0K2zVYGsMXiw2E.7lhEzuUmegm6/tcC8uNXIlfqoNnwyhy',
     1, 1),
    (null, null, 'jointeamsspring@gmail.com',
     null, null, 'admin',
     true, true, null,
     '$2a$10$krU.5/cHT0Zzi.o3dYgM6.yELqTPG6FKrhOuYEyNhglosd67GCRNG',
     null, 2),
    ('Bachelor of Advanced Computing', 'Hi, I am Sarah Lee :)', 'devu0002@uni.sydney.edu.au',
     'Engineering', null, 'Sarah',
     true, false, 'Lee',
     '$2a$10$vb7rO449.CdJDKGI24VgD.Qwl986ntbw7wlotLwOLOGjKiypH6yhO',
     null, 1),
    ('Master of Information Technology', 'Hi, I am John Davis :)', 'devu0003@uni.sydney.edu.au',
     'Engineering', null, 'John',
     true, false, 'Davis',
     '$2a$10$XmrZEyt/WDR5uz0fC8Y7l.T1o/VSCiO1GNcI1ME67.RPeXWLA4B1S',
     null, 1);

insert into semester (year, semester_number, is_current)
values
    (2022, 1, false),
    (2022, 2, true);

insert into course (code, name, university_id,
                    next_group_name_id, is_locked)
values
    ('ELEC5619', 'Object Oriented Application Framework', 1, 1, false),
    ('SOFT2201', 'Software Construction and Design 1', 1, 1, false),
    ('ELEC1601', 'Introduction to Computer Systems', 1, 1, false),
    ('MATH2069', 'Discrete Mathematics and Graph Theory', 1, 1, false),
    ('ELEC3609', 'Internet Software Platforms', 1, 1, false),
    ('COMP2017', 'Systems Programming', 1, 1, false),
    ('ISYS2120', 'Data and Information Management', 1, 1, false),
    ('SOFT3202', 'Software Construction and Design 2', 1, 1, false),
    ('SOFT2412', 'Agile Software Development Practices', 1, 1, false),
    ('MKTG1001', 'Marketing Principles', 1, 1, false),
    ('MATH1023', 'Multivariable Calculus and Modelling', 1, 1, false),
    ('MATH1021', 'Calculus Of One Variable', 1, 1, false),
    ('INFO1113', 'Object-Oriented Programming', 1, 1, false),
    ('COMP5347', 'Web Application Development', 1, 1, false),
    ('COMP2123', 'Data Structures and Algorithms', 1, 1, false),
    ('MATH1002', 'Linear Algebra', 1, 1, false),
    ('COMP2022', 'Models of Computation', 1, 1, false),
    ('COMP3308', 'Introduction to Artificial Intelligence', 1, 1, false);

insert into enrollment (tutorial, course_id, semester_id, user_id)
values (null, 1, 2, 1),
       (null, 2, 2, 1),
       (null, 3, 2, 1),
       (null, 4, 2, 1),
       (null, 5, 1, 1),
       (null, 6, 1, 1),
       (null, 7, 1, 1),
       (null, 8, 1, 1),
       (null, 9, 1, 1),
       (null, 10, 1, 1),
       (null, 11, 1, 1),
       (null, 12, 1, 1),
       (null, 1, 2, 3),
       (null, 2, 2, 3),
       (null, 1, 2, 4);

insert into interested_course (course_id, user_id)
values (13, 1),
       (14, 1),
       (15, 1),
       (16, 1),
       (18, 1);

insert into `group` (course_id, name_id, semester_id,
                   tutorial, capacity, description)
values
    (1, 1, 1, 'tutorial1', 4, 'description1'),
    (2, 2, 2, 'tutorial2', 5, 'description2'),
    (3, 3, 1, 'tutorial3', 6, 'description3'),
    (1, 1, 2, 'CC02', 3, 'description4');
-- 因为group是一个在sql种被定义了的单词，以必须用`符号包起来

update course set next_group_name_id = 2 where id = 1;

insert into group_user (group_id, user_id, is_leader)
values
    (1, 1, true),
    (1, 2, false),
    (2, 2, true),
    (2, 3, false),
    (3, 1, true),
    (4, 1, true),
    (4, 3, false);

-- insert into notification (content, message,
--                             timestamp, type, group_id, user_id)
-- values
--     (NULL, NULL, '2022-09-14 10:35:13.000000', 0, 1, 3),
--     (NULL, NULL, '2022-09-15 11:35:13.000000', 1, 2, 1),
--     (NULL, NULL, '2022-09-16 12:35:13.000000', 2, 2, 3),
--     (NULL, NULL, '2022-09-17 13:35:13.000000', 5, 3, 2);

insert into `comment` (content, is_hide, tag,
                       timestamp, group_id, receiver_id, sender_id)
values
    ('Great collaboration experience! Hope to team up next time :) ', false, 3, '2022-09-23 09:23:27.000000', 1, 1, 3),
    ('Good job!', true, 2, '2022-09-23 09:24:27.000000', 4, 1, 4);

