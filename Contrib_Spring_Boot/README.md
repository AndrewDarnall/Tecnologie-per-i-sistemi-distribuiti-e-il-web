# Usare l'estensione Spring Initalizr di VS Code

- Semplifica la vita, una volta scaricata: cmd+shift+p e cercare 'Spring Initializr'

### Esempio di configurazione iniziale di progetto Spring Boot

- Project: Maven
- Language: Java
- Spring Boot: 3.0 (requires Java 17)
- Packaging: Jar
- Add Spring Web as dependency
- Group: edu.unict.tswd.springboot
- Artifact: helloworld

Per il mio progetto 'FirstSample' ho usato informazioni diverse!


+ L'applicazione, grazie a maven, viene impacchettata in un .jar/.war e successivamente puo' essere eseguita con java -jar ./target/demo...jar

### N.B. 

Se Thymeleaf non e' aggiunta come dipendenza, l'app Spring Boot NON trovera'
la dipendenza e dara' un errore sulla View!