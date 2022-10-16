i external API： gmail api (backend) / reCAPTCHA api(frontend & backend)
* server-client communication: axios http request & response

Libraries
Backend
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

Frontend
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
External API
Gmail API: backend
reCAPTCHA API: frontend & backend
server-client communication
Axios

Working functionalities
Login as normal user or admin
Validate login information and give proper feedback
Register a new account (activate the account via email verification)
Validate register information and give proper feedback
Reset password (send reset password link via email with unique token)
Detect mischief behaviours with reCAPTCHA
Display user information on the profile page
Edit profile and modify personal information
Hide / show the comments displayed on the profile
Display all courses, current courses, pastcourses and interested courses on the user dashboard
Search for a course on the user dashboard
Display the previous students and current students of a course on the course detail page
Search for a student on the course detail page
Display the the current students of a course that are previous teammates of the current user on the course detail page
Enrol / drop a course
Mark / unmark a course as intereterested course
Edit tutorial session of a current course on the course detail page
Display group detail information and members
Enable user to edit group description
Enable user to leave a group (pass leader authorization to next member if leader leave; disband group if the group has no user)
Update/create a comment when the comment function available
Send the invitation to a user (check if the group has already sent an invitation to this user or this user is already a member of another group or whether the group is full)
Display all current students in the current course that do not have a group
Send a join request to a group (check if the user has already sent an invitation to this group or the user is already a member or whether the group is full)
Display all groups in this course in the current semester
Add a new group
Display all groups of the current user in the current semester.
Display all notifications of a user
Accept, decline & delete the specific notification
Display all comments
Delete comments
Display all courses and course details
Add / delete a course
Lock / Unlock a course
Change the current semester
Enable/disable comment feature & send reminder emails to users
Find all universities

4. A quick guide on how to run the application
Backend
Open the root project folder with IntelliJ
Enter the backend root project folder (ELEC5619_Group18/Backend)
Enter mysql CLI mysql -u root
(mysql> means run commands in mysql CLI)
Run mysql> source buildProdDB.sql 
Run mysql> source buildTestDB.sql (create database for testing)
Start application through entry point in src/main/java/com.jointeams.backend/BackendApplication.java
Run mysql> source DB-init.sql
Frontend
Enter the frontend root project folder (ELEC5619_Group18/frontend)
Run command npm install in the terminal
Run command npm run serve in the terminal
Enter http://localhost:8888/ in the browser to access the website

