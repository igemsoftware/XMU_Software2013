<?php
	$mail = new SaeMail();
	sleep($_POST["time"]*60);
    $mail->quickSend( 
        $_POST["receiver"] ,
        'the remind from "'.$_POST["sendMan"].'"'." by E'NOTE" ,
        $_POST["content"],
        "XMUE-Note@hotmail.com" ,
        "LINshanshan" 
    );
?>