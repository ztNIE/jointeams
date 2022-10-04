-- Use DB-init.sql instead

insert into university (name, regex, email_url)
values
    ('University of Sydney', '^[a-z]{4}[0-9]{4}@uni.sydney.edu.au$','https://www.outlook.com/uni.sydney.edu.au'),
    ('Admin', '^.+$','https://test.edu.au');

insert into user (degree, description, email,
                  faculty, filename, first_name,
                  is_activate, is_admin, last_name,
                  password, self_tag, university_id)
values
    ('IT', 'from IT', 'devu0001@uni.sydney.edu.au',
     'faculty1', 'avator1.jpg', 'devFName1',
     true, false, 'devLName1',
     '$2a$10$4Y.YYVx0K2zVYGsMXiw2E.7lhEzuUmegm6/tcC8uNXIlfqoNnwyhy',
     null, 1),
    ('IT', 'from IT', 'devu0002@uni.sydney.edu.au',
     'faculty2', 'avator2.jpg', 'devFName2',
     true, false, 'devLName2',
     '$2a$10$4Y.YYVx0K2zVYGsMXiw2E.7lhEzuUmegm6/tcC8uNXIlfqoNnwyhy',
     null, 1),
    ('IT', 'from IT', 'devu0003@uni.sydney.edu.au',
     'faculty2', 'avator3.jpg', 'devFName3',
     true, false, 'devLName3',
     '$2a$10$4Y.YYVx0K2zVYGsMXiw2E.7lhEzuUmegm6/tcC8uNXIlfqoNnwyhy',
     null, 1),
    (null, null, 'jointeamsspring@gmail.com',
     null, null, 'admin',
     true, true, 'admin',
     '$2a$10$krU.5/cHT0Zzi.o3dYgM6.yELqTPG6FKrhOuYEyNhglosd67GCRNG',
     null, 2);

insert into semester (year, semester_number, is_current)
values
    (2022, 1, false),
    (2022, 2, true);

insert into course (code, name, university_id,
                    next_group_name_id, is_locked)
values
    ('CCode1', 'CourseName1', 1, 2, false),
    ('CCode2', 'CourseName2', 1, 12, false),
    ('CCode3', 'CourseName3', 1, 6, true);

insert into `group` (course_id, name_id, semester_id,
                     tutorial, capacity, description)
values
    (1, 1, 1, 'tutorial1', 4, 'description1'),
    (2, 2, 2, 'tutorial2', 5, 'description2'),
    (3, 3, 1, 'tutorial3', 6, 'description3');
-- 因为group是一个在sql种被定义了的单词，以必须用`符号包起来

insert into group_user (group_id, user_id, is_leader)
values
    (1, 1, true),
    (1, 2, false),
    (2, 2, true),
    (2, 3, false),
    (3, 1, true);

insert into notification (content, message,
                          timestamp, type, group_id, user_id)
values
    (NULL, NULL, '2022-09-14 10:35:13.000000', 0, 1, 3),
    (NULL, NULL, '2022-09-15 11:35:13.000000', 1, 2, 1),
    (NULL, NULL, '2022-09-16 12:35:13.000000', 2, 2, 3),
    (NULL, NULL, '2022-09-17 13:35:13.000000', 5, 3, 2);

insert into comment (content, is_hide, tag, timestamp,
                     group_id, receiver_id, sender_id)
values
    ('content1', false, 1, '2022-09-14 10:35:13.000000', 1, 1, 3),
    ('content2', false, 1, '2022-09-14 10:35:13.000000', 1, 3, 1),
    ('content3', true, 2, '2022-09-14 10:35:13.000000', 2, 2, 1),
    ('content4', true, 2, '2022-09-14 10:35:13.000000', 3, 3, 2);
