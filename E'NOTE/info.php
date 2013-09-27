<?php 
	include 'data.php';
	mysql_query("UPDATE ".$_POST["pname"]." SET email = '".$_POST["mail"]."' WHERE id = '".$_POST["id"]."'");
	mysql_query("UPDATE ".$_POST["pname"]." SET realname = '".$_POST["rname"]."' WHERE id = '".$_POST["id"]."'");
	mysql_query("UPDATE ".$_POST["pname"]." SET telephone = '".$_POST["phone"]."' WHERE id = '".$_POST["id"]."'");
	mysql_query("UPDATE ".$_POST["pname"]." SET address = '".$_POST["address"]."' WHERE id = '".$_POST["id"]."'");
	mysql_query("UPDATE ".$_POST["pname"]." SET name = '".$_POST["name"]."' WHERE id = '".$_POST["id"]."'"); 
	mysql_close($con);
?>