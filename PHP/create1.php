<a href="/..">Torna all'indice</a><br>
<?php
$myfile = fopen("newfile.txt", "w") or die("Non posso aprire il file!");
$txt = "John Doe\n";
fwrite($myfile, $txt);
$txt = "Jane Doe\n";
fwrite($myfile, $txt);
fclose($myfile);
?>
esegui (da shell): <tt>cat newfile.txt</tt><br> 
e nota il contenuto creato dall'esecuzione di questo script<br> 

<p>oppure:</p>

clicca qui: <a href="create2.php">create2.php</a><br>
