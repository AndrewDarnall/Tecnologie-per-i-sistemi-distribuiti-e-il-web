<?php
$draw = new \ImagickDraw();
$draw->circle(200, 200, 300, 300);

$image = new \Imagick();
$image->newImage(400, 400, 'blue');
$image->setImageFormat("png");
$image->drawImage($draw);

header("Content-Type: image/png");
echo $image->getImageBlob();
