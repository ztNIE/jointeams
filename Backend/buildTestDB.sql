DROP database jointeamstest;
DROP USER 'test'@'localhost';

CREATE database jointeamstest;
USE jointeamstest;

CREATE User 'test'@'localhost' IDENTIFIED BY '12345678';
GRANT ALL privileges ON jointeamstest.* TO 'test'@'localhost';