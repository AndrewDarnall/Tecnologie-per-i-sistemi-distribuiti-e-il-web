<?php

    // To get the fake suggestion
    require("dbConnect.php");

    $sql = "select * from flist";
    $result = $conn->query($sql);

    $nRows = $result->num_rows;

    if($nRows > 0) {
        $ran = rand(0, $nRows - 1);
        $i = 0;
        while($row = $result->fetch_assoc()) {
            if($i == $ran) {
                echo "<p> " . $row['titolo'] . " - " . $row['regista'] . " </p><br><br>";
            }
            $i += 1;
        }
    } else {
        echo "<p class=\"error\"> No films present in the database! </p><br><br>";
    }

    $conn->close();

?>