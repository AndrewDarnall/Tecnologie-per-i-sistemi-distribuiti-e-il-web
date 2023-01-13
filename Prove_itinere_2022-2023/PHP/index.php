<!DOCTYPE html>
<html>
    <head> 
        <title> Book Store Page </title>
        <style>
            * {text-align: center;} input {width: 100%;} th, td {margin: 25px;}
        </style>
    </head>
    <body>
        <div id="main">
            <h1> The Lost Library of Alexandria </h1>

            <table>
                <tr>
                    <th> ISBN </th>
                    <th> TITLE </th>
                    <th> AUTHOR </th>
                </tr>
                <?php

                    require("dbConnect.php");

                    $sql = "select * from books";

                    $result = $conn->query($sql);

                    if($result->num_rows > 0) {
                    
                        while($row = $result->fetch_assoc()) {
                            echo "<tr>";
                            echo "<td> <form action=\"./update.php\" method=\"POST\"> <input type=\"submit\" value=\"" . $row['isbn'] . "\"> <input type=\"hidden\" name=\"isbn\" value=\"" . $row['isbn'] . "\"> <input type=\"hidden\" name=\"mode\" value=\"details\"></form></td>";
                            echo "<td>" . $row['title'] . "</td>";
                            echo "<td>" . $row['author'] . "</td>";
                            echo "</tr>";
                        }

                    } else {
                        echo "<p style=\"color: red;\"> No Records present in the database </p>";
                    }

                ?>
            </table>

            <br><br>
            <h1> Noticed a missing book? Add one! </h1>
            <br><br>
            <form action="./addBook.php" method="POST">
                <h3>{ ISBN }</h3>
                <input type="text" name="isbn">
                <h3>{ TITLE }</h3>
                <input type="text" name="title">
                <h3>{ AUTHOR }</h3>
                <input type="text" name="author">
                <h3>{ PUBLISHER }</h3>
                <input type="text" name="publisher">
                <h3>{ RANK }</h3>
                <input type="text" name="rank">
                <h3>{ YEAR }</h3>
                <input type="text" name="year">
                <h3>{ PRICE }</h3>
                <input type="text" name="price">
                <br>
                <input type="submit" value="Add Book">
                <br><br>
            </form>

        </div>
    </body>
    <?php

    $conn->close();

    ?>
</html>