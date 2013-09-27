<?php
	//需要 $_POST["pname"] 
	include 'data.php';	
	while(1)
	{
		if( mysql_query("DELETE FROM ".$_POST["pname"]."plasmid WHERE id ='".$_POST["id"]."'") )
		{
			break;
		}
	}
	mysql_close($con);
?>