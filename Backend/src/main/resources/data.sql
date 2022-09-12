insert into university (name, regex) values
    ('University of Sydney', '^[a-z]{4}[1-9]{4}@uni.sydney.edu.au$'),
    ('Test University', '^[a-z]+@gmail.com$');

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