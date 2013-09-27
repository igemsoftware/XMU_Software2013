<?php
	include 'data.php';
	$file=str_replace("#@$;","&amp;",$_POST["value"]);	
	$file=str_replace("%^*;","=",$file);	
	mysql_query("UPDATE ".$_POST["pname"]."plasmid SET ".$_POST["name"]." = '".$file."' WHERE id = '".$_POST["id"]."'"); 
	mysql_close($con);
?>	