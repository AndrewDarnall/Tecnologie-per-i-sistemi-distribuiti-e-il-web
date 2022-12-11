// Esempio babbo di rooting usando il framework backend - express.js

const PORT = 4426;
const express = require( 'express' );
const app = express();
const cors = require( 'cors' );

// La sintassi generica per gestire richieste a risorse particolari
// e' -> app.METODO(PAHT, HANDLER)
// express.js resnde semplice lo sviluppo di API REST

app.use(cors());

app.get('/', function (request, response) {
    console.log("User requested the '/' document");
    response.status(200).json("You have requested the root '/' page");
});

app.get('/stuff', function (request, response) {
    console.log("The user requested the '/stuff' document");
    response.status(200).json("You have requested the '/stuff' directory");
});

app.listen(PORT , () => console.log(`Server running on PORT : ${PORT}`));

/**
 * Da notare come express.js rende la creazione del backend molto effortless
 * il pacakge cors risolve l'eventuale errore -> failed access control checks.
 */