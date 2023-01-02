<?php

    // Form insertion processor

    require("dbConnect.php");

    if($_SERVER['REQUEST_METHOD'] == "POST") {
        $film = test_input($_POST['Film']);
        $regista = test_input($_POST['Regista']);
    } else {
        echo "Incompatible method type!";
    }

    function test_input($data) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    $sql = "insert into flist(titolo, regista) values ('$film','$regista')";
    $conn->query($sql);

    $conn->close();

?>