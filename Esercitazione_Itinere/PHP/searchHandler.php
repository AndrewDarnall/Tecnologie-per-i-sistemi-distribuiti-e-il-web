<!DOCTYPE html>
<html>
    <head>
        <title> Query Result </title>
        <style>
            #main, #result {text-align: center;}
            .error {color: red;}
            input {width: 100%;}
        </style>
    </head>
    <body>
        <div id="result">
            <?php

                // Serach processor

                require("dbConnect.php");

                if($_SERVER['REQUEST_METHOD'] == "POST") {
                     $film = test_input($_POST['Film']);
                     $regista = test_input($_POST['Regista']);
                } else {
                    echo "<p class=\"error\"> ERROR INCOMPATIBLE METHOD! </p><br><br>";
                }

                function test_input($data) {
                    $data = trim($data);
                    $data = stripslashes($data);
                    $data = htmlspecialchars($data);
                    return $data;
                }

                $sql = "select * from flist where titolo = '" . $film . "' and regista = '" . $regista . "'";                
                $result = $conn->query($sql);
                $nRows = $result->num_rows;

                if($nRows > 0) {
                    echo "<p> There is a Movie in Flist </p>";
                    echo "<table> <tr> <th> Titolo </th> <th> Regista </th> </tr>";
                    while($row = $result->fetch_assoc()) {
                        // echo "<p> " . $row['titolo'] . " - " . $row['regista'] . " </p>";
                        echo "<tr> <td> " . $row['titolo'] . " </td> <td> " . $row['regista'] . " </td> </tr>";
                    }
                    echo "</table>";
                } else {
                    echo "<p class=\"error\"> There is no such movie in Flist </p>";
                }

                $sql2 = "select * from wlist where titolo = '$film' and regista = '$regista'";
                $result2 = $conn->query($sql2);
                $nRows2 = $result2->num_rows;

                if($nRows2 > 0) {
                    echo "<p> There is a Movie in Wlist </p>";
                    echo "<table> <tr> <th> Titolo </th> <th> Regista </th> </tr>";
                    while($row2 = $result2->fetch_assoc()) {
                        // echo "<p> " . $row2['titolo'] . " - " . $row2['regista'] . " </p>";
                        echo "<tr> <td> " . $row2['titolo'] . " </td> <td> " . $row2['regista'] . " </td> </tr>";
                    }
                    echo "</table>";
                } else {
                    echo "<p class=\"error\"> There is no such movie in Wlist </p>";
                    // Remember you already ARE in php! ~ Check for typos
                    echo "<form action=\"addMovie.php\" method=\"POST\"> <p> Vuoi aggiungere il film? </p> <br><br> <input type=\"submit\" value=\"Si\"> <input type=\"hidden\" name=\"titolo\" value=\"$film\"> <input type=\"hidden\" name=\"regista\" value=\"$regista\"> </form>";
                    echo "<br><br> <form action=\"index.php\" method=\"GET\"> <input type=\"submit\" value=\"No\"> </from>";
                }

                $conn->close();

            ?>
        </div>
        <div id="main">
            <form action="./index.php" method="GET">
                <input type="submit" value="Home">
            </form>
        </div>
    </body>
</html>