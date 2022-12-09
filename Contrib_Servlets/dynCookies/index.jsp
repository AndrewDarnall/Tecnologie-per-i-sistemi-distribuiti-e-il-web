<!DOCTYPE html>
<html>
    <head>
        <title>People tracker home page</title>
        <style>
            * {font-family: 'Courier New', Courier, monospace;}
        </style>
    </head>
    <body>
        <div id="main">
            <h1 id="greeterHeader"> Welcome to this website! </h1>

            <div id="formContainer">
                <form id="mainForm" method="POST" action="./track">
                    <input type="radio" name="button" value="clicked">Click me 
                    <input type="submit" value="submit">
                </form>
            </div>  

            

            <div id="recBox">
               <h2 id="resultHeader">You visited this page: <jsp:include page="/track"/> times.</h2> 
            </div>

        </div>
    </body>
</html>