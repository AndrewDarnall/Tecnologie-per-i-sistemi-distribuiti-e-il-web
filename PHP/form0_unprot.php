Scrivi una stringa e premi invio:
<form method="GET" action=" <?php echo $_SERVER["PHP_SELF"]; ?>" >
<input type="text" name="una_stringa">
</form>

<?php echo "hai scritto {$_GET['una_stringa']}"; ?>

