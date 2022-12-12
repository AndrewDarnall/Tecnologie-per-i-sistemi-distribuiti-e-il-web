<?php

    // to solve the access checks conflict --> send the raw php header
    header("Access-Control-Allow-Origin: *");

    echo "The php server responded, yay!";

?>