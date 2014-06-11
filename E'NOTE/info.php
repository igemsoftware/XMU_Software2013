<?php 
	include 'data.php';
	mysql_query("UPDATE ".mysql_real_escape_string($_POST["pname"])." SET email = '".mysql_real_escape_string($_POST["mail"])."' WHERE id = '".mysql_real_escape_string($_POST["id"])."'");
	mysql_query("UPDATE ".mysql_real_escape_string($_POST["pname"])." SET realname = '".mysql_real_escape_string($_POST["rname"])."' WHERE id = '".mysql_real_escape_string($_POST["id"])."'");
	mysql_query("UPDATE ".mysql_real_escape_string($_POST["pname"])." SET telephone = '".mysql_real_escape_string($_POST["phone"])."' WHERE id = '".mysql_real_escape_string($_POST["id"])."'");
	mysql_query("UPDATE ".mysql_real_escape_string($_POST["pname"])." SET address = '".mysql_real_escape_string($_POST["address"])."' WHERE id = '".mysql_real_escape_string($_POST["id"])."'");
	mysql_query("UPDATE ".mysql_real_escape_string($_POST["pname"])." SET name = '".mysql_real_escape_string($_POST["name"])."' WHERE id = '".mysql_real_escape_string($_POST["id"])."'"); 
	mysql_close($con);
?>