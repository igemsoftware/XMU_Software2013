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
				$steps = $pathsDoc->getElementsByTagName("step"); 
				$pathsDoc->getElementsByTagName("ename")->item(0)->nodeValue= $_POST["saveExpName"];
				
				$eshort_file=str_replace("<","@@@;",$pathsDoc->getElementsByTagName("eshort")->item(0)->nodeValue);
				$eshort_file=str_replace("&","#@$;",$eshort_file);
				$pathsDoc->getElementsByTagName("eshort")->item(0)->nodeValue=$eshort_file;
				
				$count=0;
				foreach($steps as $step) 
				{
					if($step->getElementsByTagName("member")->item(0)->nodeValue == $_POST["member"])
					{
						$step->nodeValue= $_POST["data".$count];
						$count++;
					}		
				}
				
				$file = htmlspecialchars_decode($pathsDoc->saveXML(),ENT_COMPAT );
				$file=str_replace("&","&amp;",$file);
				$file=str_replace("@@@;","&lt;",$file);
				$file=str_replace("#@$;","&amp;",$file);				
				$file=str_replace("%^*;","=",$file);
				$s->write("block",$_POST["path"],$file);
			
			// mysql_query("UPDATE ".$_POST["pname"]."mark SET mark = 0 WHERE id = '".$_POST["sqlid"]."'");
			 //break;
		//}
	//}
	//else
	//{
	//	break;
	//}
	//sleep(1);
//}		



//mysql_close($con);
?>

