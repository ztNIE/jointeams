## Run Backend Application

### IMPORTANT: If the schema has changed, please run the buildProdDB.sql in mysql

1. application.properties has been changed to
```
spring.jpa.hibernate.ddl-auto=create-drop
spring.sql.init.mode=always
```
Add default users are added to data.sql
```sql
insert into university (name, regex) value
    ('University of Sydney', '^[a-z]{4}[1-9]{4}@uni.sydney.edu.au$');

insert into user (degree, description, email,
                  faculty, filename, first_name,
                  is_activate, is_admin, last_name,
                  password, self_tag, university_id)
values
    ('IT', 'from IT', 'devu0001@uni.sydney.edu.au',
     'engineering', 'avator.jpg', 'dev',
     true, false, 'dev',
     '$2a$11$7RSfIct4qbJHVrsFoSEqmOosn7rCSfDL3QqL/DX2gWEccTL4hiH46',
     null, 1),
    (null, null, 'jointeamsspring@gmail.com',
     null, null, 'admin',
     true, true, 'admin',
     '$2a$11$7RSfIct4qbJHVrsFoSEqmOosn7rCSfDL3QqL/DX2gWEccTL4hiH46',
     null, 1);
```
where the password for user 'devu0001@uni.sydeny.edu.au' and 'admin' was set as 12345678. The authentications has been
added to apifox's group "UserAuth" and "AdminAuth".
