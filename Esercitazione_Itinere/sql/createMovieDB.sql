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

-- La consegna non specifica alcun vincolo di integrita'

-- Granting privileges

grant select on myDB.* to 'gb'@localhost;
grant insert on myDB.* to 'gb'@localhost;

-- Lo script presuppone la esistenza di credenziali per l'accesso moderato al DB