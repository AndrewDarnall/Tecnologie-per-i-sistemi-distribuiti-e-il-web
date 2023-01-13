<?php

    $user = "gb";
    $password = "gb";
    $server = "localhost";
    $db = "exam";

    $conn = new mysqli($server, $user, $password, $db);

    if($conn->connect_error) {
        die("Error, something went wrong while trying to connect to the DataBase: " . $conn->connect_error);
    }

?>