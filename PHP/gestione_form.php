<!DOCTYPE HTML>
<html>
<head>
</head>
<body>

<!-- Script PHP che gestisce Form

In risposta a GET:
1.  resetta variabili PHP
2.  mostra form e
3.  ne consente invio

In risposta a POST:
1.  come per GET, resetta variabili PHP;
1'. assegna il dato ricevuto col form a una_stringa e, se
    il form era vuoto, prepara un messaggio di errore;
2'. mostra il form con il valore precedente o eventuale messaggio di errore
3.  come per GET, consente invio dati
4.  mostra un output dinamico, dipendente dal dato ricevuto (in questo caso
    mostra la lunghezza della stringa ricevuta)
5.  mostra un bottone per ricaricare i form puliti (cioe` per effettuare
    una nuova GET)
I punti 1-5 sono evidenziati nel codice sotto come commenti
-->

<?php // (1)
$una_stringa = "";
$una_stringa_err = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") { // (1'), solo in risposta a POST
    if (empty($_POST["una_stringa"])) {
        $una_stringa_err = "!!! Inserisci una stringa";
    } else {
        $una_stringa = $_POST["una_stringa"];
    }
}
?>

<!-- (2) o (2'), poi (3) (HTML seguente in risposta sia a GET che a POST) -->
<h2>Form da riempire</h2>
<form method="post" action="<?php echo $_SERVER["PHP_SELF"]; ?>">
  Stringa: <input type="text" name="una_stringa" value="<?php echo $una_stringa ?>">
  <?php echo $una_stringa_err; ?>
  <br><br>
  <input type="submit" value="Invia">
</form>
<br>

<?php // (4) solo in risposta a POST
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    echo "La stringa \"$una_stringa\" ha lunghezza " . strlen($una_stringa);
// l'HTML seguente sia incorporato solo in risposta a un POST e non a un GET
?>
<br><br> <!-- (5) solo in risposta a POST -->
<form method="get" action="<?php echo $_SERVER["PHP_SELF"]; ?>">
  <input type="submit" value="Ricarica">
</form>

<?php // graffa di chiusura dell'if sopra
}
?>
</body>
</html>
