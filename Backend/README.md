## Run Backend Application

1. Initialize database
   - Open mysql CLI in /Backend directory, run 
```
source emptyDatabase.sql
```
2. Check the application.properties, make sure that the following property is NOT commended
```
spring.sql.init.mode=always
```
3. Run the application. 
4. If you refresh the database in Intellij now, you would see 13 tables created and data is loaded into user, tag and university
5. Test get method on UserService
```
localhost:your_port/user/greetingById?id=1 
# 2, 3 should also work
```
6. If you want to run the program again, commend the following property (or change it to embedded) in application.properties .
```
spring.sql.init.mode=always
```
7. TODO:
    - Set cascade level for ManyToOne, OneToMany, ManyToMany annotations
	- Delete Tag Class(Redundant)
    - ......
