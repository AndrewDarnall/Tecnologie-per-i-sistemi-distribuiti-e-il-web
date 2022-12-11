const http = require( 'http' );
const server = http.createServer();

const PORT = 4423;

console.log("Server listening on port:\t" + PORT);

server.on('request', (request, response) => {
    console.log("A request just arrived!");
    response.setHeader("Content-Type","text/plain");
    response.writeHead(200);
    response.end("Request received!");
}).listen(PORT);

/**
 * As you can see, this is quite simpler when compared to other technologies that
 * we encountered during the course (this does NOT discredit their importance or purpose!)
 */