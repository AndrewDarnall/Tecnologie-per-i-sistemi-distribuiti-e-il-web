-- Script di creazione del DB

create schema Students;

use Students;

create table students 
(
    id int(6) unsigned auto_increment primary key,
    name varchar(45) not null,
    age int not null,
    degree_course varchar(45) not null
);

insert into students(name,age,degree_course) values ('Micheal',22,"Computer Science");
insert into students(name,age,degree_course) values ('Stephen',22,"Finance");
insert into students(name,age,degree_course) values ('Kylee',21,"Physics");
insert into students(name,age,degree_course) values ('Sophia',20,"Electronic Engineering");
insert into students(name,age,degree_course) values ('Evan',19,"Mechanical Engineering");
insert into students(name,age,degree_course) values ('Samantha',22,"Theoretical Physics");
insert into students(name,age,degree_course) values ('Josh',20,"Electrical Engineering");
insert into students(name,age,degree_course) values ('Martha',22,"Economics");
insert into students(name,age,degree_course) values ('Jim',21,"Dunder mifflin intern");

-- end of script