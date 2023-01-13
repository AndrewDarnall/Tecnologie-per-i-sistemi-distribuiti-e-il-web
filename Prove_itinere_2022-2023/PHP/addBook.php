<!DOCTYPE html>
<html>
    <head>
        <title> Update Page </title>
        <style>
            * {text-align: center;}
        </style>
    </head>
<body>
    <div id="main">
        <h1 style="text-align: center;"> Update Page </h1>
<?php

    require('dbConnect.php');

    function test_input($data) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    if($_SERVER['REQUEST_METHOD'] != "POST") {
        die("Wrong HTTP method used!");
    }

    $sql = "insert into books (isbn,title,author,publisher,ranking,year,price) values('" . $_POST['isbn'] ."','" . $_POST['title'] . "','" . $_POST['author'] . "','" . $_POST['publisher'] . "'," . $_POST['rank'] . "," . $_POST['year'] . "," . $_POST['price'] . ")";
    
    
    if($conn->query($sql) === TRUE) {

        echo "<p style=\"color: green;\"> Succesfully added the book </p>";
        $conn->close();

    } else {

        echo "<p style=\"color: red;\"> Failed to add the record to the database! : " . $conn->error . " </p>";
        $conn->close();
    }


    echo "<a href=\"./index.php\"> <p>Go Back Home</p>"
    
?>
</div>
</body>
</html>