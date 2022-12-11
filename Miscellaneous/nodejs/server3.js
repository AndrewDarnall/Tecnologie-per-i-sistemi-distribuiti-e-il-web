const http = require( 'http' );
const server = http.createServer();

const PORT = 4424;

// Data to be sent, in JSON format (a common way to comunicate for APIs)
const data = {
    "name": "Andrew",
    "age": "22",
    "hobby": {
        "reading": true,
        "guitar": true,
        "sport": "Weights"
    },
    "class": ["JavaScript", "HTML", "CSS"]
};

console.log("Server listening on port:\t" + PORT);

server.on('request', (request, response) => {
    console.log("A request just arrived!");
    response.setHeader("Content-Type","application/json");
    response.writeHead(200);
    response.end(JSON.stringify(data,3,null));
}).listen(PORT);

/**
 * Notice the difference between the server2.js and server3.js programs
 * one sends text and the other sends a JSON response, which is pretty 
 * common for APIs
 */