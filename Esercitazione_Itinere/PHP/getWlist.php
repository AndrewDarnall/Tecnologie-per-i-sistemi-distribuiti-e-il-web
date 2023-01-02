<?php

    // Loading up the wishlist
    require("dbConnect.php");

    $sel = $_SERVER['PHP_SELF'];

    if($_SERVER['REQUEST_METHOD'] == "POST") {
        
        $sql = "select * from wlist";
        $result = $conn->query($sql);

        // Trouble aligning the table ~ I would normally use BootStrap

        echo "<table style=\"tet-align: center;\"> <tr> <th> Titolo </th> <th> regista </th> </tr> ";
        while($row = $result->fetch_assoc()) {
            echo "<tr> <td> " . $row['titolo'] . " </td> <td> " . $row['regista'] . " </td> </tr>";
        }
        echo "</table>";

    }

    echo "<form style=\"text-align: center;\" action=\"$sel\" method=\"POST\"> <input type=\"submit\" value=\"Check WishList\"> </form>";
    echo "<br><form style=\"text-align: center;\" action=\"index.php\" method=\"GET\"> <input type=\"submit\" value=\"Hide WishList\"> </form>";

    $conn->close();

?>