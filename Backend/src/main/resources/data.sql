insert into university (name, regex) values
    ('University of Sydney', '^[a-z]{4}[0-9]{4}@uni.sydney.edu.au$'),
    ('Test University', '^[a-z]+@gmail.com$');

insert into user (degree, description, email,
                  faculty, filename, first_name,
                  is_activate, is_admin, last_name,
                  password, self_tag, university_id)
values
    ('IT', 'from IT', 'devu0001@uni.sydney.edu.au',
     'faculty1', 'avator1.jpg', 'devFName1',
     true, false, 'devLName1',
     '$2a$11$7RSfIct4qbJHVrsFoSEqmOosn7rCSfDL3QqL/DX2gWEccTL4hiH46',
     null, 1),
    ('IT', 'from IT', 'devu0002@uni.sydney.edu.au',
     'faculty2', 'avator2.jpg', 'devFName2',
     true, false, 'devLName2',
     '$2a$11$7RSfIct4qbJHVrsFoSEqmOosn7rCSfDL3QqL/DX2gWEccTL4hiH46',
     null, 1),
    ('IT', 'from IT', 'devu0003@uni.sydney.edu.au',
     'faculty2', 'avator3.jpg', 'devFName3',
     true, false, 'devLName3',
     '$2a$11$7RSfIct4qbJHVrsFoSEqmOosn7rCSfDL3QqL/DX2gWEccTL4hiH46',
     null, 1),
    (null, null, 'jointeamsspring@gmail.com',
     null, null, 'admin',
     true, true, 'admin',
     '$2a$11$7RSfIct4qbJHVrsFoSEqmOosn7rCSfDL3QqL/DX2gWEccTL4hiH46',
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
--因为group是一个在sql种被定义了的单词，以必须用`符号包起来

insert into group_user (group_id, user_id, is_leader)
values
    (1, 1, true),
    (1, 2, true),
    (2, 2, true),
    (3, 1, true);

insert into notification (content, message,
                            timestamp, type, group_id, user_id)
values
    (NULL, NULL, '2022-09-14 10:35:13.000000', 0, 1, 3),
    (NULL, NULL, '2022-09-15 11:35:13.000000', 1, 2, 3),
    (NULL, NULL, '2022-09-16 12:35:13.000000', 3, 2, 3),
    (NULL, NULL, '2022-09-17 13:35:13.000000', 4, 2, 3);
