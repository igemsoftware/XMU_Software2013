<?php 
	include 'data.php';
	mysql_query("UPDATE ".$_POST["pname"]." SET code = '".$_POST["newcode"]."' WHERE id = '".$_POST["id"]."'");
	mysql_close($con);
?>	
