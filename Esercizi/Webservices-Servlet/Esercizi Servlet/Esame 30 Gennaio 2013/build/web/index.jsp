<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Client esercizio WebService</title>
    </head>
    <body>
        <center>
        <h1>Client esercizio WebService</h1>
        <form action="Read" method="post">
            <p><input type="submit" value="Leggi n-esimo messaggio">(<input type="text" name="user">, <input type="text" name="n">)</p>
        </form>
        <form action="Write" method="post">
            <p><input type="submit" value="Scrivi messaggio">(<input type="text" name="user">, <input type="text" name="message">)</p>
        </form>
        </center>
    </body>
</html>