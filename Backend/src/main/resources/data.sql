insert into university (id, name) value (1, 'University of Sydney');
insert into tag (id, type) values (1, 1),
                                  (2, 2);
insert into user (id, degree, description, email,
                  faculty, filename, first_name,
                  is_activate, is_admin, last_name,
                  password, self_tag_id, university_id)
values
    (1, 'MSC', 'hahahaha', '12314@edu.au',
     'faculty1', 'avator1.jpg', 'firstname1',
     true, false, 'lastname1',
     'password', 1, 1),
    (2, 'BSc', 'lallalala', '544321@edu.au',
     'faculty2', 'avator2.jpg', 'firstname2',
     false, false, 'lastname2',
     'password', null, 1),
    (3, 'Admin', 'is admin', 'admin@edu.au',
     null, null, 'admin',
     true, true, 'admin',
     '1234', null, null);