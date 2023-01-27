<!DOCTYPE html>
<html>
    <head>
        <title>@yield('title','Default Value, Just in case')</title>
        <!-- Sofia Google Fonts -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Sofia">
        <!-- BootStrap 5 template -->
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            * { text-align: center;}
            #space {margin: 25px;}
            .sofia {font-family: "Sofia", sans-serif;}
        </style>
    </head>
    <body class="container-fluid m3">
        <div id="main">
            @yield('content')
            @yield('home')
        </div>
    </body>
</html>
<!--
 The template engine makes it SOO easier to make tempalates
 and to make so much more, it pretty much introduces the
 OOP abstraction to it, now I get why even Laravel is considered
 a full stack framework
-->