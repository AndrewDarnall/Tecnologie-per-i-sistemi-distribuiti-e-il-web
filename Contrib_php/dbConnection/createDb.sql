-- db creation script

create schema Libri;

use Libri;

create table libri
(
	id int primary key,
	name varchar(100) not null
);

insert into libri(id,name) values (01,"MySQL explained");
insert into libri(id,name) values (02,"PHP reloaded");
insert into libri(id,name) values (03,"JavaScript vs Java");
insert into libri(id,name) values (04,"C vs C++: who's better?");
insert into libri(id,name) values (05,"Objective-C to Swift: what did apple do to us?");
insert into libri(id,name) values (06,"SQL 3.0: why did it take soo long?");
insert into libri(id,name) values (07,"NoSQL vs MySQL: final showdown");
insert into libri(id,name) values (08,"MongoDB vs Oracle DB: who's best?");
insert into libri(id,name) values (09,"Apache2 vs Apache Tomcat: what's next?");

create user 'gb'@'localhost' identified by 'gb';

grant select on Libri.libri to 'gb'@'localhost';
