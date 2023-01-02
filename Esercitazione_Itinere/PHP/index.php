<!DOCTYPE html>
<html>
    <head>
        <title>FakeFlix ~ PHP </title>
        <style>
            #main, #insertForm {text-align: center;}
            .error { color: red; }
            input {width: 50%; height: 25%;}
        </style>
    </head>
    <body>
        <div id="main">
            <header> FakeFlix </header>
            <h1> Film consigliato </h1>
            <?php require("getFakeSuggestion.php"); ?>
        </div>
        <br>
        <br>
        <div id="insertForm">
            <form action="./searchHandler.php" method="POST" id="form1">
                <h3> [Film] </h3>
                <input type="text" name="Film">
                <h3> [Regista] </h3>
                <input type="text" name="Regista">
                <br>
                <br>
                <input type="submit" value="Cerca Film">
            </form>
        </div>
        <br>
        <div id="checkList">
            <?php
                // Loading up the wishlist
                require("getWlist.php");

            ?>
        </div>
    </body>
</html>
<!--
    Pesonal note -> JavaScript allows for better interactiveness but yet again, php is meant
    for dynamic web pages and NOT interactive web pages!
-->