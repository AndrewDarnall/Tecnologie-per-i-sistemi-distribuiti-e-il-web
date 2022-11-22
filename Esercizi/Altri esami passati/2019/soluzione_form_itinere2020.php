<!-- Coded by daxcpp (Davide Carnemolla) -->
<html>
    <head>
        <title>NetSix</title>
    </head>
    <body>
        <h1>NetSix</h1>
        <form method="POST" action="<?php echo $_SERVER["PHP_SELF"]?>">
            <input type="text" name="name" placeholder="Nome serie">
            <input type="text" name="stagione" placeholder="Stagione">
            <input type="number" name="episodio" placeholder="Episodio">
            <input type="submit" value="Cerca">
        </form>

        <?php
            $serietv = array("iocu ro tronu"=>array("1"=>"10", "2"=>"10"),
            "a teoria ro big bang"=>array("1"=>"22"),
            "signuri robot"=>array("1"=>"10", "2"=>"10"),
            "l'accademia ri l'umbrello"=>array("1"=>"8"));

            if($_SERVER["REQUEST_METHOD"] == "POST"){
                $name = validate_input($_POST["name"]);
                $stagione = $_POST["stagione"];
                $number = $_POST["episodio"];


                if(empty($name) || !is_numeric($number) || !is_numeric($stagione))
                    echo "Errore nell'input";
                else{
                    if(search($serietv, $name, $number, $stagione))
                        echo "L'episodio " . $number . " delle stagione " . $stagione ." della serie " . $name . " è disponibile!";
                    else
                    echo "L'episodio " . $number . " delle stagione " . $stagione ." della serie " . $name . " NON è disponibile!";
                }
            }

            function search($list, $value, $episodio, $stagione){
                foreach($list as $elemento => $x){
                    if($elemento == $value){
                        foreach($x as $el => $nepisodi){
                            if($el == $stagione && $nepisodi >= $episodio)
                                return 1;
                        }
                    }
                }
                        
                return 0;
            }

            function validate_input($value){
                $value = trim($value);
                $value = stripslashes($value);
                $value = htmlspecialchars($value);
                return $value; 
            }
        ?>
    </body> 
</html>
