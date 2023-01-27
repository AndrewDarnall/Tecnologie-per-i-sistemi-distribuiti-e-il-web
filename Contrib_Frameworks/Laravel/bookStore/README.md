# Esempio completo di 'sistema informativo' sviluppato con Laravel

Nota Bene, in produzione non si utilizzerebbero MAI le credenziali gia' aggiunte
al file .env ma gli si metterebbero delle variabili di ambiente che risiedono nella macchina locale di sviluppo

Entrambi Spring Boot e Laravel sono dei frameworks full stack eccezionali, spetta a noi quale scegliere, o alla azienda per cui lavoreremo

Devo pero' ammettere che Blade mi appaga molto piu' di Thymeleaf

# Comandi utili usati durante la generazione del progetto

- export PATH=~/.composer/vendor/bin:$PATH
- laravel new bookStore
- php artisan migrate
- php artisan make:migration create_books_table
- php artisan make:controller BookController -r
- php artisan make:model Book

# IMPORTANTE 

Il nome con il quale si crea la tabella delle migrazioni, il controllore e modello HA rilevanza, altrimenti riceverete errori!

# Spiegazioni

- Route::resources() -> si usa per gestire in maniera appropriata (e RESTful) le risorse
- Il controllore gestisce le funzioni a cui corrispondono i metodi e le mappature appropriate
- Blade e' il template engine che permette di costruire dinamicamente le views
- Il modello Book e' utilizzato per interagire con il DBMS (e' il Design Pattern Architetturale 'Active Records' {buono per business logic di media complessita', per logica piu' complessa occorrono altri patterns})

# NON

- Non scordatevi di aggiungere i {{csrf_field()}} che mandano dei tokens che verificano che la richiesta POST puo' essere accettata
- Non scordatevi di mappare correttamente i nomi delle tabelle di migrazione (che ci semplificano la vita)

# Pro Tip

Tenete due shell aperte, una che gira l'app e l'altra con il risultato del comando: php artisan route:list, in tal mododo potrete sviluppare meglio la manipolazione base (CRUD) delle risorse