<?php

    // Movie inserter processor

    require("dbConnect.php");

    if($_SERVER["REQUEST_METHOD"] == "POST") {
        $titolo = test_input($_POST['titolo']);
        $regista = test_input($_POST['regista']);
    } else {
        echo "<h1 stlye=\"text-align: center; color: red;\"> ERROR INVALID METHOD </h1>";
    }

    function test_input($data) {
        $data = trim($data);
        $data = stripslashes($data);
        $data = htmlspecialchars($data);
        return $data;
    }

    $sql = "insert into wlist(titolo, regista) values ('$titolo','$regista')";
    if($conn->query($sql)) {
        echo "<p style=\"text-align: center;\"> Film aggiunto con successo! </p>";
    } else {
        echo "<p style=\"text-align: center; color: red;\"> Errore nell'aggiungere il film! </p>";
    }

    $conn->close();

    echo "<form action=\"index.php\" method=\"GET\"> <input type=\"submit\" value=\"Home\"> </form>";

?>