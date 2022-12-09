<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <style>
            * {font-family: 'Courier New', Courier, monospace;}
            #greeterHeader {text-align: center;}
        </style>
    </head>
    <body>
        <div id="main">
            <h1 id="greeterHeader">Greetings page</h1>
            <p>Welcome <?php echo $_POST["name"];?></p>
            <p>you have registered with e-mail: <?php echo $_POST["email"]; ?></p>
        </div>
    </body>
</html>