<?php

    // Connection manager
    $server = "localhost";
    $user = "gb";
    $pwd = "gb";
    $db = "myDB";

    $conn = new mysqli($server, $user, $pwd, $db);

    if($conn->connect_error) {
        die("Error: " . $conn->connect_error);
    }
    
?>