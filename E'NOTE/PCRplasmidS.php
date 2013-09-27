<?php
	//需要 $_POST["pname"] 
	include 'data.php';	
	$search= "SELECT * FROM ".$_POST["pname"]."plasmid where id='".$_POST["id"]."' AND pname='".$_POST["name"]."'";
	$search= mysql_query($search,$con);	
	if( $get = mysql_fetch_array($search) )
	{
		$content='
			$("#i"+i+"s"+j+"i"+k+"3").val("'.$get["location"].'");
			$("#i"+i+"s"+j+"i"+k+"5").val("'.$get["type"].'");
			$("#i"+i+"s"+j+"i"+k+"7").val("'.$get["sequence"].'");
			$("#i"+i+"s"+j+"i"+k+"9").val("'.$get["plength"].'");
			$("#i"+i+"s"+j+"i"+k+"11").val("'.$get["bname"].'");
			$("#i"+i+"s"+j+"i"+k+"13").val("'.$get["blength"].'");
		';
	}
	else
	{
		$content= 0;
	}
	mysql_close($con);		
	echo $content;		
?>