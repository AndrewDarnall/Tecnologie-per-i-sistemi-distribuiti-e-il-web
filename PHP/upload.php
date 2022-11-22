<?php

/**
 * File upload.php
 *
 * @category Esercizi
 * @package  Upload
 * @author   GP <gp@php.dmi.unict.it>
 * @license  http://www.php.net/license/3_01.txt  PHP License 3.01
 * @link     http://www.dmi.unict.it/phpcode
 */

// quando il browser sottomette form per upload di un file, il superglobal
// $_FILES viene popolato con info sul file da caricare

print('<pre>$_FILES = ' . print_r($_FILES, true) . "</pre>\n");
print('<pre>$_POST = ' . print_r($_POST, true) . "</pre>\n");

// costruiamo il pathname che il file prendera` sul server
$target_dir = "uploads/";
$target_file = $target_dir . basename($_FILES["fileDaCaricare"]["name"]);
echo "target_file: $target_file<BR>";
$file_ext = strtolower(pathinfo($target_file, PATHINFO_EXTENSION));
echo "file_ext (da target_file): $file_ext<BR>";

// Check if image file is actual image or fake image
if (isset($_POST["invia"]))   // scoraggia POST non provenienti dal form
{
    $temp_loc = $_FILES["fileDaCaricare"]["tmp_name"];
    echo "Temporary location: $temp_loc<BR><BR>";

    $check = getimagesize($temp_loc); // verifica con getimagesize() insufficiente per
    if ($check) {                     // https://www.php.net/manual/function.getimagesize
        echo "Output di getimagesize($temp_loc): ";
        print("<pre>" . print_r($check, true) . "</pre>\n");
        echo "per getimagesize() l'immagine e` di tipo " . 
             $check["mime"] . " (" . $check[2]. ")<BR>";
        $uploadOk = 1;
    } else {
        echo "getimagesize() fallisce, ";
        echo "il file non e` un immagine.<BR>";
        $uploadOk = 0;
    }
}

// Check if file already exists
if (file_exists($target_file)) {
    echo "Sorry, file already exists.<BR>";
    $uploadOk = 0;
}
// Check file size (dichiarata da cliente)
if ($_FILES["fileDaCaricare"]["size"] // fileDaCaricare e` il "name" scelto
    > 500000) {                       // nel file chooser del cliente
    echo "Sorry, your file is too large.<BR>";
    $uploadOk = 0;
}
// Controlla estensione (non il tipo del file)
if (   $file_ext != "jpg" && $file_ext != "png" && $file_ext != "jpeg"
    && $file_ext != "gif" ) {
    echo "Sorry, only JPG, JPEG, PNG & GIF files are allowed.<BR>";
    $uploadOk = 0;
}
// Check if $uploadOk is set to 0 by an error
if ($uploadOk == 0) {
    echo "<b><BR>Sorry, your file was not uploaded.<BR>";
// tutto ok, ora si sposta veramente il file
} else {
    if (move_uploaded_file($temp_loc, $target_file)) {
        echo "<h2>File " . basename($temp_loc) . " uploaded to $target_file.</h2><BR>";
    } else {
        echo "<b>Sorry, there was an error moving your uploaded file from temporary location.<BR>";
    }
}
