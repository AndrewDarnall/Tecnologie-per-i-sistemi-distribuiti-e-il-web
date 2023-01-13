<!DOCTYPE html>
<html>
    <head>
        <title> 
            Details Page
        </title>
        <style>
            * {text-align: center;} th, tr, {margin: 25px;} input {width: 100%;}
        </style>
    </head>
    <body>
        <?php

            require('dbConnect.php');

            if($_SERVER['REQUEST_METHOD'] != "POST") {
                die("Page accessed with the wrong method!");
            }

        ?>
        <div id="main">
            <h1> Book Details </h1>
            <br><br>
            <?php


                if($_POST['mode'] == "details") {
                    $sql = "select * from books where isbn = '" . $_POST['isbn'] . "'";

                    $result = $conn->query($sql);
                    if($result->num_rows > 0) {
                    echo "<table>";
                    echo "<tr> <th> ISBN </th> <th> TITLE </th> <th> AUTHOR </th> <th> PUBLISHER </th> <th> RANK </th> <th> YEAR </th> <th> PRICE </th> </tr>";
                    while($row = $result->fetch_assoc()) {
                        echo "<tr>";
                        echo "<td>" . $row['isbn'] . "</td>";
                        echo "<td>" . $row['title'] . "</td>";
                        echo "<td>" . $row['author'] . "</td>";
                        echo "<td>" . $row['publisher'] . "</td>";
                        echo "<td>" . $row['ranking'] . "</td>";
                        echo "<td>" . $row['year'] . "</td>";
                        echo "<td>" . $row['price'] . "</td>";
                        echo "</tr>";
                    }
                    echo "</table>"; 
                    } else {
                        echo "<p style=\"color: red;\"> Record NOT present in DataBase! </p>";
                    }

                    echo "<br><br>";

                    echo "<h3> Want to delete the selected book? </h3>";
                    echo "<form action=\"./update.php\" method=\"POST\"> <input type=\"submit\" value=\"Delet Book\"> <input type=\"hidden\" name=\"mode\" value=\"delete\"> <input type=\"hidden\" name=\"isbn\" value=\"" . $_POST['isbn'] . "\"> </form>";
                } else if($_POST['mode'] == "delete") {

                    $sql = "delete from books where isbn = '" . $_POST['isbn'] . "'";
                    $result = $conn->query($sql);

                    if($result === TRUE) {
                        echo "<p style=\"color: green;\"> Book deleted! </p>";
                    } else {
                        echo "<p style=\"color: red;\"> Failed to delete the record! </p>";
                    }

                }

                echo "<a href=\"./index.php\"> Home";

                $conn->close();

            ?>

        </div>
    </body>
</html>