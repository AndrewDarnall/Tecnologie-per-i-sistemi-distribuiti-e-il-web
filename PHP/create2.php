<?php
$myfile = fopen("newfile.txt", "w") or die("Non posso aprire il file!");
$txt = "Mickey Mouse\n";
fwrite($myfile, $txt);
$txt = "Minnie Mouse\n";
fwrite($myfile, $txt);
fclose($myfile);
?>
esegui <tt>cat newfile.txt</tt><br>
e nota nuovo contenuto di <tt>newfile.txt</tt>
<br><br>
ora esegui (da shell): <tt>chmod -w newfile.txt</tt><br>
per osservare errore, clicca qui: <a href="create1.php">create1.php</a><br>

