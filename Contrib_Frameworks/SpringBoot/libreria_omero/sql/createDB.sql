drop schema if exists BootSpring;

create schema BootSpring;

use BootSpring;

create user 'gb'@'localhost' identified by 'gb';

grant all on BootSpring.* to 'gb'@'localhost';

create table Bbooks 
(
id int auto_increment,
title varchar(255) not null,
author varchar(255) not null,
rating int not null,
price double not null,
primary key(id,title)
);

insert into Bbooks(title,author,rating,price) values ('Illiade','Omero',5,13.50), ('Odissea','Omero',5,13.50);

-- Execute the script as ROOT
