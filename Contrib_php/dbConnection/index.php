<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <style>
            * {font-family: 'Courier New', Courier, monospace;}
            #greeterHeader {text-align: center;}
        </style>
    </head>
    <body>
        <div id="main">
            <h1 id="greeterHeader">Welcome to a simple example to database connection</h1>
            <?php

                echo "Welcome to the page";

                $servername = "localhost";
                $username = "gb";
                $password = "gb";

                // Creating the connection
                $conn = new mysqli($servername, $username, $password);

                // Check connections
                if($conn->connect_error) {
                    die("Connection faild: " . $conn->connect_error);
                }

                echo "DataBase connection succesful!";

            ?>
        </div>
    </body>
</html>