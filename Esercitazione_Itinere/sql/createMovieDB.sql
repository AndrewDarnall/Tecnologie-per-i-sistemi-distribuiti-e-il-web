-- Fake Flix movie DB creation script
create schema if not exists myDB;

use myDB;

create table if not exists flist
(
    titolo varchar(30) not null,
    regista varchar(30)
);

create table if not exists wlist
(
    titolo varchar(30) not null,
    regista varchar(30)    
);

create user if not exists 'gb'@localhost identified by 'gb';

-- La consegna non specifica alcun vincolo di integrita'

-- Granting privileges

grant select on myDB.* to 'gb'@localhost;
grant insert on myDB.* to 'gb'@localhost;

-- Accertatevi di usare lo script come root 