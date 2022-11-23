# -- Script creazione db, eseguitelo dopo che siete loggati nel DBMS, MySQL e' il DMBS usato

create user 'gb'@'localhost' identified by 'gb';

create database libri;

use libri;

create table Libri
(
    AuthorID int primary key,
    FirstName varchar(45) not null,
    LastName varchar(45) not null,
    BookName varchar(45) not null,
    Rating int not null,
    Price float not null,
    check (Rating >= 0 AND Rating <= 6)
);

insert into Libri(AuthorID, FirstName, LastName, BookName, Rating, Price) values (01,"Giovanni","Verga","I malavoglia",5,10.99);
insert into Libri(AuthorID, FirstName, LastName, BookName, Rating, Price) values (02,"Dante","Aligheri","La divina commedia",5,3.33);
insert into Libri(AuthorID, FirstName, LastName, BookName, Rating, Price) values (03, "Luigi","Pirandello","Uno, nessuno, centomila",4,12.34);
insert into Libri(AuthorID, FirstName, LastName, BookName, Rating, Price) values (04,"Oscar","Wilde","Dorian Gray",5,12.12);
insert into Libri(AuthorID, FirstName, LastName, BookName, Rating, Price) values (05,"George","Orwell","1984",6,12.35);
insert into Libri(AuthorID, FirstName, LastName, BookName, Rating, Price) values (06,"Francis Scott","Fitzgerald","The great gatzby",6,14.56);

grant select on libri.Libri to 'gb'@'localhost';

# -- Da notare che la base di dati e' dummy e' va hostata localmente affinche' lo script funzioni
# -- drop schema libri; (per cancellare il DB)
# -- drop table Libri; (per cancellare la tabella)
# -- drop user 'gb'@'localhost'; (per cancellare l'utente ed automaticamente revocare i privilegi)