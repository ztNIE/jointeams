## Run Backend Application

1. Initialize database
   - Open mysql CLI in /Backend directory, run 
```
source emptyDatabase.sql
```
2. Check the application.properties, make sure that
```
spring.jpa.hibernate.ddl-auto=create
spring.sql.init.mode=always
```
3. Run the application. It's normal that you would see lots of warnings: spring are trying to drop non-exist tables.
4. If you refresh the database in Intellij now, you would see 13 tables created and data is loaded into user, tag and university
5. Test get method on UserService
```
localhost:your_port/user/greeting?id=1 # 2, 3 should also work
```
6. Change the application.properties like below if you want to run it again.
```
spring.sql.init.mode=never
spring.jpa.hibernate.ddl-auto=update
```
7. TODO:
    - Set cascade level for ManyToOne, OneToMany, ManyToMany annotations
	- Delete Tag Class(Redundant)
    - ......
