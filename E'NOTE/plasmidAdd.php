<?php
	//需要 $_POST["pname"] 
	include 'data.php';	
	$add= "INSERT INTO ".$_POST["pname"]."plasmid(location,pname,type,sequence,plength,bname,blength,hua,conservation)VALUES( '','','','','','','','','' )";
	mysql_query($add,$con);	
	echo mysql_insert_id($con);
	mysql_close($con);
?>