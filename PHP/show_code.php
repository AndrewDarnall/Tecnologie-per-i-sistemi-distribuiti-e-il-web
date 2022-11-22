<?php
// formatta e colora il PHP sorgente nel file il cui nome
// viene passato come parametro 'which'

highlight_file(htmlspecialchars ($_GET['which']));
