# Esempio di connessione ad un DBMS ~ MySQL 

Per poter utilizzare la estensione MySQL di PHP bisogna abilitarla nel file php.ini
dovreste trovare -> ;extension=mysqli, rimuovete il ';'

Ho personalmente avuto difficolta' su macOS tuttavia in un VM Debian 
(quella usata al corso di reti) funziona perfettamente, vi consiglio di usare Apache
e riavviarlo dopo aver effettuato la modifica, usate lo script .sql per la creazione del db.

#### Consiglio:

usate una VM debian e fate girare tutto li, altrimenti per windows usate XAMPP.
per facilitare la scrittura di codice sulla vm, usate la funzione ssh 
di VS Code, oppure scrivete il codice locale e mandatelo tramite il programma
scp --> 'man scp' per ulteriori info.