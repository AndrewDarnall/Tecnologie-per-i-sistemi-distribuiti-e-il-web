-- DataBase creation script

create user 'gb'@'localhost' identified by 'gb';

drop schema if not exists BootSpring;

create schema BootSpring;

use BootSpring;

create table Books
(
id int auto_increment,
title varchar(255) not null,
author varchar(255) not null,
isbn varchar(255),
rating int not null,
year int,
price double,
primary key(id)
);

grant all on BootSpring.Books to 'gb'@'localhost';

insert into Books(title,author,rating,price) values ('Introduction to Algorithms 4th edition','Cromen,Rivest,Stein,Leiserson',5,103.22), ('The Chronicles of Narnia: The Complete Saga','C.S.Lewis',5,24.00);

-- Make sure you execute the script as ROOT!
