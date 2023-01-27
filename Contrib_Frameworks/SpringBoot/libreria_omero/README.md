# Refactoring sistema informativo Spring Boot con Spring JPA e Spring Security

Sostanzialmente ho:

- unificato il controllore oltre a quello di index per le relative azioni
- aggiunto il sistema di login: Spring Security
- sostituito il codice JDBCTemplate con quello JPARepository (un solo file) 
- creato qualche template in piu'

# Users

- user:password
- guest:guest
- drew\_00:deus\_vult

Per modificare le credenziali, aggiungete / copiate quello che trovate nel file src/main/java/com/omero/libreria\_omero/config/WebSecurityConfig.java

# Tips

- Sfruttare le annotazioni rende la vita piu' semplice
- ATTENZIONE ai tipi di ritorno dei metodi nella BookRepository
- ATTENZIONE alle annotazioni usate per le query e sopra tutto al tipo di query in cui sono usate!
- ATTENZIONE ai parametri / ogetti usati dal template Thymeleaf poiche' se qualcosa non combacia, maven da molteplici errori
- Divertitevi e sbizzarritevi a modificare il codice che ho fatto, Spring Boot, come Laravel, hanno un potenziale creativo INFINITO!

# N.B.

Bisogna usare il db (script gia fatto) incluso nella directory sql
