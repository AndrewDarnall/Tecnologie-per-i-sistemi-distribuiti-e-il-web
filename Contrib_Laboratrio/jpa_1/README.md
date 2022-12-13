# Notes - Jakarta Persistence API

- JPA: e' lo standard per la mappatura di relazioni ad oggetti (Objet Relational Mapping = ORM)
- Hibernate: e' una implementazione dell standard JPA

### persistence.xml

va configurato adeguatamente e collocato nella directory META-INF, directory che usualmente contiene file di configurazione come il file sopra elencato.

# DataBase - setup

### dentro MySQL cli

1. source ~/jpa_1/jpaServlet/src/main/sql/pulisciDb.sql; 

Se da qualche errore vuol dire che non avevate il db o l'utente

2. source ~/jpa_1/jpaServlet/src/main/sql/createDb.sql; 
3. source ~/jpa_1/jpaServlet/src/main/sql/createUser.sql; 

Date una occhiata alle dipendenze aggiunte per mysql, hibernate e JPA.

Fate **attenzione** al modo in cui si esegue la query e a come e' inizializzata
la classe che funge da entita'.