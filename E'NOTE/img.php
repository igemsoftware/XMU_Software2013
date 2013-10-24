<?php
	//$_POST["pname"] :t1
	//$_POST["id"] 	  :i+ijk
	$s = new SaeStorage();
	$name= $_POST["pname"].$_POST["id"].$_FILES["img"]["name"];//t1g图片名
	$path= "http://trysomething-block.stor.sinaapp.com/".$name;
	//存储图片

	if(	$s->upload( 'block' ,$name, $_FILES["img"]["tmp_name"] ) )
	{	
		$s->delete("block",$_POST["oldgraph"]);
		echo "<script>parent.document.getElementById('".$_POST["id"]."0').src='".$path."';parent.document.getElementById('".$_POST["id"]."5').value='".$name."';</script>";
	}
	else
	{
		echo "<script>parent.document.getElementById('".$_POST["id"]."3').innerHTML='I am sorry, something bad happened, submit again or check your graph library';</script>";
	}
?>	  
