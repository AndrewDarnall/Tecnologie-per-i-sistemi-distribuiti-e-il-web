-- User creator scirpt

create user 'gb'@'localhost' identified by 'gb';

grant select on Students.students to 'gb'@'localhost';