<?php
if ($_SERVER["REQUEST_METHOD"] == "GET") { ?>
Scrivi una stringa e premi invio:
<form method="post" action=" <?php echo $_SERVER["PHP_SELF"]; ?>" >
<input type="text" name="una_stringa">
</form>
<?php } ?>

<?php

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $str = $_POST['una_stringa'];
    echo "hai scritto $str";
}
?>

