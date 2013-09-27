<?php
//include 'data.php';	
//while(1)
//{	
	//$qexpmark = mysql_query("SELECT mark FROM ".$_POST["pname"]."mark where id = '".$_POST["sqlid"]."'");
	//if( $expmark = mysql_fetch_array($qexpmark) )
	//{
		//if($expmark["mark"]==0)
		//{
			// mysql_query("UPDATE ".$_POST["pname"]."mark SET mark = 1 WHERE id = '".$_POST["sqlid"]."'");




	$s = new SaeStorage();	
	//创建xml对象
	$pathsDoc = new DOMDocument();
	//加载路径文件
	$path= 'http://trysomething-block.stor.sinaapp.com/'.$_POST["path"];
	$pathsDoc->load($path);
	//获取实验节点
	$exp= $pathsDoc->getElementsByTagName("exp")->item(0);	
	$steps = $exp->getElementsByTagName("step");
	$exp->removeChild( $steps->item( $_POST["index"] ) ); 

	
	$s->write("block",$_POST["path"],$pathsDoc->saveXML());
	
			// mysql_query("UPDATE ".$_POST["pname"]."mark SET mark = 0 WHERE id = '".$_POST["sqlid"]."'");
			 //break;
		//}
	//}
	//else
	//{
		//break;
	//}
	//sleep(1);
//}		
//mysql_close($con);	
?>