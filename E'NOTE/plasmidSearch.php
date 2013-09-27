<?php
	//需要 $_POST["pname"] 
	include 'data.php';	
	$search= "SELECT * FROM ".$_POST["pname"]."plasmid where id='".$_POST["id"]."' AND pname='".$_POST["name"]."'";
	$search= mysql_query($search,$con);	
	if( $get = mysql_fetch_array($search) )
	{
		$content='
			experiment['.$_POST["i"].'].step['.$_POST["j"].'].plasmid'.$_POST["q"].'.location="'.$get["location"].'";
			experiment['.$_POST["i"].'].step['.$_POST["j"].'].plasmid'.$_POST["q"].'.type="'.$get["type"].'";
			experiment['.$_POST["i"].'].step['.$_POST["j"].'].plasmid'.$_POST["q"].'.plength="'.$get["plength"].'";
			experiment['.$_POST["i"].'].step['.$_POST["j"].'].plasmid'.$_POST["q"].'.bname="'.$get["bname"].'";				
			experiment['.$_POST["i"].'].step['.$_POST["j"].'].plasmid'.$_POST["q"].'.blength="'.$get["blength"].'";	
			experiment['.$_POST["i"].'].step['.$_POST["j"].'].plasmid'.$_POST["q"].'.sequence="'.$get["sequence"].'";
			$("#i'.$_POST["i"].'s'.$_POST["j"].'i'.$_POST["k"].'q'.$_POST["q"].'1").val("'.$get["location"].'");
			$("#i'.$_POST["i"].'s'.$_POST["j"].'i'.$_POST["k"].'q'.$_POST["q"].'2").val("'.$get["type"].'");
			$("#i'.$_POST["i"].'s'.$_POST["j"].'i'.$_POST["k"].'q'.$_POST["q"].'4").val("'.$get["plength"].'");
			$("#i'.$_POST["i"].'s'.$_POST["j"].'i'.$_POST["k"].'q'.$_POST["q"].'5").val("'.$get["bname"].'");
			$("#i'.$_POST["i"].'s'.$_POST["j"].'i'.$_POST["k"].'q'.$_POST["q"].'6").val("'.$get["blength"].'");
			$("#i'.$_POST["i"].'s'.$_POST["j"].'i'.$_POST["k"].'q'.$_POST["q"].'3").val("'.$get["sequence"].'");
		';
	}
	else
	{
		$content= 0;
	}
	echo $content;		
mysql_close($con);	
?>