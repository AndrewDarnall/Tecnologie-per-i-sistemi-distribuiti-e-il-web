<?php error_reporting(E_ALL ^ E_NOTICE);

// NB: sopprime warning in risposta a GET ?>

Scrivi una stringa e premi invio:
<form method="post" action=" <?php echo $_SERVER["PHP_SELF"]; ?>" >
<input type="text" name="una_stringa">
</form>

<?php echo "hai scritto {$_POST['una_stringa']}"; ?>

