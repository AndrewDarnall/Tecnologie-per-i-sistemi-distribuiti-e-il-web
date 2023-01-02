<?php

    // To get the fake suggestion
    require("dbConnect.php");

    $sql = "select * from flist";
    $result = $conn->query($sql);

    $nRows = $result->num_rows;

    if($nRows > 0) {
        $row = $result->fetch_assoc();
        // $film = $arr[rand(0, $nRows)];
        echo "<p> " . $row['titolo'] . " - " . $row['regista'] . " </p><br><br>";
    } else {
        echo "<p class=\"error\"> No films present in the database! </p><br><br>";
    }

    $conn->close();

?>