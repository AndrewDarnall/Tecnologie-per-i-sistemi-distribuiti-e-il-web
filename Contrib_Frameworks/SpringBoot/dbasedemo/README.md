# Esempio 'completo' di sistema informativo con operazioni CRUD ~ Spring Boot

Nella cartella sql c'e il codice per fare la base di dati da capo, so che potevo
fare MOLTO di piu' visto che Spring Boot e' POTENTISSIMO e notevolmente piu'
semplice da utilizzare rispetto a Spring, anche se come ha detto saggiamente il Professor Nicotra, per piccole imprese come una libreria locale, ci tocchera' usare un CMS come WordPress o Drupal "non andresti mai in paese con la ferrari" - cit

## Dipendenze usate

- Thymeleaf
- Spring JDBC Template

### Spiegazione 

Il progetto e' creato da Spring Initializr dove si scelgono i parametri appropriati quali il build tool, la versione, le dipendenze etc ...
Ad esempio se si vuole usare un template engine, bisogna includere la
dipendenza Thymeleaf

### Struttura directory

##### Java/com/drew/dbase/dbasedemo

- Config: contiene la componente che verra' gestita da Spring Boot per
          collegare/parlare con JdbcTempalte (se non si include tra le dipendenze allora il software NON funziona)

- Controllers: Contiene i controllori che gestiscono la mapaptura delle richeste
               esattamente come abbiamo visto con doGet, doPost e web.xml eccetto
               che tutto viene gestito piu' rapidamente con le annotazioni

- Data: Contiene gli oggetti che manipolano/lavorano con i dati in maniera 
        semplificata e se non erro sfruttano il Spring Data Layer

- Models: Sono i modelli con il quale avviene la mappatura tra tabelle 
          relazionali e oggetti (ORM)

##### Resources

- templates: Contiene tutti i templates (i views) che Spring Boot renderizzera'
             con i modelli (dati) che gli passera' il Controller, anche per questo Spring Boot e' considerato un framework full stack!

- static: E' per le pagine statiche come una index.html che NON va renderizzata
          quindi per contenuti NON dinamici.

- application.properties: contiene la configurazione per la connessione al DBMS
                          tramite il connettore jdbc

###### Per girare il tutto

- Accertatevi di essere nella root della directory (progetto)
- Nel terminale: mvn spring-boot:run
- Nel browser -> localhost:8080/
