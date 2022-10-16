# ELEC5619_Group18 Jointeams

### Libraries
**Backend**

```
Maven: “4.0.0”
Java: “11”
mysql
org.springframework.boot: “2.7.3”
spring-boot-starter-data-jpa
spring-boot-starter-web
spring-boot-devtools
spring-boot-starter-security           
org.springframework.security
spring-boot-starter-mail
spring-boot-starter-validation
junit: “4.13.2”
com.googlecode.json-simple: “1.1.1”
javax.validation
com.fasterxml.jackson.core        
io.jsonwebtoken: “0.9.1”
commons-io: “2.6”
commons-fileupload: “1.4”
org.projectlombok
```

**Frontend**

```
@element-plus/icons-vue: "^2.0.9",
axios: "^0.27.2",
core-js: "^3.6.5",
element-plus: "^2.2.17",
js-cookie: "^3.0.1",
mitt: "^3.0.0",
nprogress: "^0.2.0",
vue: "^3.0.0",
vue-recaptcha-v3: "^2.0.1",
vue-router: "^4.1.5",
vuex: "^4.0.2"
```

### External API

* Gmail API: backend
* reCAPTCHA API: frontend & backend

### server-client communication
* Axios

### Working functionalities
1.  Login as normal user or admin
2. Validate login information and give proper feedback
3. Register a new account (activate the account via email verification)
4. Validate register information and give proper feedback
5. Reset password (send reset password link via email with unique token)
6. Detect mischief behaviours with reCAPTCHA
7. Display user information on the profile page
8. Edit profile and modify personal information
9. Hide/show the comments displayed on the profile
10. Display all courses, current courses, past courses and interested courses on the user dashboard
11. Search for a course on the user dashboard
12. Display the previous students and current students of a course on the course detail page
13. Search for a student on the course detail page
14. Display the current students of a course that are previous teammates of the current user on the course detail page
15. Enrol/drop a course
16. Mark/unmark a course as interested course
17. Edit tutorial session of a current course on the course detail page
18. Display group detail information and members
19. Enable user to edit group description
20. Enable user to leave a group (pass leader authorization to next member if leader leave; disband group if the group has no user)
21. Update/create a comment when the comment function available 
22. Send the invitation to a user (check if the group has already sent an invitation to this user or this user is already a member of another group or whether the group is full)
23. Display all current students in the current course that do not have a group 
24. Send a join request to a group (check if the user has already sent an invitation to this group or the user is already a member or whether the group is full)
25. Display all groups in this course in the current semester 
26. Add a new group 
27. Display all groups of the current user in the current semester. 
28. Display all notifications of a user 
29. Accept, decline & delete the specific notification 
30. Admin: Display all comments 
31. Admin: Delete comments 
32. Admin: Display all courses 
33. Admin: Add/delete a course 
34. Admin: Lock/Unlock a course 
35. Admin: Change the current semester 
36. Admin: Enable/disable comment feature & send reminder emails to users 
37. Admin: Find all universities

### Quick guide on how to run the application

**Backend**  
1. Open the root project folder with IntelliJ  
2. Enter the backend root project folder (ELEC5619\_Group18/Backend)  
3. Open mysql CLI by enter `mysql -u root` in terminal  
4. Run `mysql> source buildProdDB.sql`  
5. Run `mysql> source buildTestDB.sql`  
6. Start application using IntelliJ   
7. Run `mysql> source DB-init.sql`

**Frontend**  
1. Enter the frontend root project folder (ELEC5619_Group18/frontend)  
2. Run command `npm install` in the terminal  
3. Run command `npm run serve` in the terminal  
4. Enter `http://localhost:8888/` in the browser (Google Chrome) to access the website
