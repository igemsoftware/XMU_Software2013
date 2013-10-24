<?php
	//1初始值，1队伍不存在，2队员不存在,3密码错误，4准许登录
	include 'data.php';
	$judge=1;
	
	$x= strtolower($_POST["x"]);
	$y= strtolower($_POST["y"]);
	$qteam = mysql_query("SELECT id FROM teams where name = '".$x."'");
	if($team = mysql_fetch_array($qteam))
	{
			$judge=2;
			$table= "t".$team["id"];
			$quser = mysql_query("SELECT code FROM ".$table." where name = '".$y."'");
			if($user = mysql_fetch_array($quser))
			{
				$judge=3;	
				if($user["code"]==$_POST["z"])
				{
						$judge=4;
				}
			}		
	}
	mysql_close($con);
	echo $judge;
?>
		
