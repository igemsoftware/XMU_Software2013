<?php
	$mail = new SaeMail();
	$title= "E'NOTE feedback from ". $_POST["user"]." of ". $_POST["team"];
    $mail->quickSend( 
		'178512378@qq.com' ,
		$title,
        $_POST["content"],
        "408902575@qq.com" ,
        "*****216765*****"  
    );
?>