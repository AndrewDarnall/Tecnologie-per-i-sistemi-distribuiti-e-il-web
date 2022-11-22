<?php
$GLOBALS['FileName'] = "JSONFAKEDB.txt";
$GLOBALS['ArrayFakeDB'] = array();

function LoadFromJson()
{
    if($file = @fopen($GLOBALS['FileName'], "r"))
    {
        $GLOBALS['ArrayFakeDB'] = json_decode(fread($file, filesize($GLOBALS['FileName'])), true);
        
        fclose($file);
        return true;
    }
    else
    {
        return false;
    }
}

function SaveToJson()
{
    if($file = fopen($GLOBALS['FileName'], "w"))
    {
        $s = json_encode($GLOBALS['ArrayFakeDB']);
        if(fwrite($file, $s))
        {
            fclose($file);
            return true;
        }
        else
        {
            //print "<b>error: can't write on $FileName</b><br>";
            fclose($file);
            return false;
        }
    }
    else
    {
        //print "<b>error: can't open $FileName</b><br>";
        return false;
    }
}

function CreateFilm($title, $details)
{
    LoadFromJson();
    $array=$GLOBALS['ArrayFakeDB'];
    $array[$title]=$details;
    $GLOBALS['ArrayFakeDB']=$array;
    SaveToJson();
}

function ReadAllFilms()
{
    LoadFromJson();
    return $GLOBALS['ArrayFakeDB']; 
}

function UpdateFilm($old_title, $title, $details)
{
    DeleteFilm($old_title);
    CreateFilm($title, $details);
}

function DeleteFilm($title)
{
    LoadFromJson();
    $array=$GLOBALS['ArrayFakeDB'];
    unset($array[$title]);
    $GLOBALS['ArrayFakeDB']=$array;
    SaveToJson();
}
?>