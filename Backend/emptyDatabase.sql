# Usage: source /path/to/sql/file (in mysql CLI)
# UPDATED:
# 1. Just create the database

DROP database jointeams;
DROP USER 'jointeams'@'localhost';

CREATE database jointeams;
USE jointeams;

CREATE User 'jointeams'@'localhost' IDENTIFIED BY '12345678';
GRANT ALL privileges ON jointeams.* TO 'jointeams'@'localhost';
