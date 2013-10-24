<?php
			include 'data.php';	
//while(1)
//{	
	//$qexpmark = mysql_query("SELECT mark FROM ".$_POST["pname"]."mark where id = '".$_POST["sqlid"]."'");
	//if( $expmark = mysql_fetch_array($qexpmark) )
	//{
		//if($expmark["mark"]==0)
		//{
			//mysql_query("UPDATE ".$_POST["pname"]."mark SET mark = 1 WHERE id = '".$_POST["sqlid"]."'");


			$path= 'http://trysomething-block.stor.sinaapp.com/'.$_POST["path"];
			$s = new SaeStorage();	
			//创建xml对象
			$pathsDoc = new DOMDocument();
			//加载路径文件
			$pathsDoc->load($path);
			//获取实验节点
			$exp= $pathsDoc->getElementsByTagName("exp")->item(0);	
			
			//创建步骤节点
			$step=$pathsDoc->createElement("step");
			//步骤节点加入实验节点
			$exp->appendChild($step);	
			
			//创建步骤时间节点
			$stime=$pathsDoc->createElement("stime");
			//步骤节点加入实验节点
			$step->appendChild($stime);	
			$stime->nodeValue= $_POST["time"];
			
			//创建步骤名节点
			$sname=$pathsDoc->createElement("sname");
			//步骤节点加入实验节点
			$step->appendChild($sname);		
			$sname->nodeValue= "no name";
			
			//创建步骤member节点
			$member=$pathsDoc->createElement("member");
			//步骤节点加入实验节点
			$step->appendChild($member);	
			//member节点赋值
			$member->nodeValue= $_POST["member"];
			
			//创建步骤plasmid节点
			$plasmid=$pathsDoc->createElement("plasmid");
			//步骤节点加入实验节点
			$step->appendChild($plasmid);	
			//member节点赋值
			$plasmid->nodeValue= 0;			

			//创建步骤plasmidback节点
			$plasmidback=$pathsDoc->createElement("plasmidback");
			//步骤节点加入实验节点
			$step->appendChild($plasmidback);	
			//member节点赋值
			$plasmidback->nodeValue= 0;	
			
			$s->write("block",$_POST["path"],$pathsDoc->saveXML());
			
			// mysql_query("UPDATE ".$_POST["pname"]."mark SET mark = 0 WHERE id = '".$_POST["sqlid"]."'");
			// break;
		//}
	//}
	//else
	//{
	//	break;
	//}
	//sleep(1);
//}			
			mysql_close($con);		
?>