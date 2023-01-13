drop schema exam if exists;

create schema exam;

use exam;

create table books
(
    id int auto_increment,
    isbn varchar(255) not null,
    title varchar(255) not null,
    author varchar(255) not null,
    publisher varchar(255) not null,
    ranking int not null,
    year int not null,
    price float not null,
    primary key(id)
);

insert into books(isbn, title, author, publisher, ranking, year, price) values ('1123344','La Divina Commedia','Dante Aligheri','Mondadori',5,1472,19.99);

grant all on exam.books to 'gb'@'localhost';