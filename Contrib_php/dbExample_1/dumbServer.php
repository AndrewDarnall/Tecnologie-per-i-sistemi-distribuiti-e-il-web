<?php

    // Simple php server with CORS enabled --> fixes the control checks error
    header("Access-Control-Allow-Origin: *");

    /**
     * Heads up --> use the require() clause for cleaner code 
     * php gives some powerful tools for the backend
     * 
     * now that the cors problem is solved -> have all the fun in the world building websites!
     */

    echo "You have established a connection to the server!";

?>