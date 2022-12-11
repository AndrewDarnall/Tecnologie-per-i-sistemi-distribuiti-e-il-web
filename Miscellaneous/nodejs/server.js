var http = require( 'http' );
console.log("Server listenin on port 8088");
http.createServer(function (req, res) {
    res.writeHead(200,{'Content-Type':'text/html'});
    res.end(JSON.stringify(req.headers));
}).listen(8088);

/**
 * Esempio rudimentale per la connessione ad un server tramite runtime Node.js
 */