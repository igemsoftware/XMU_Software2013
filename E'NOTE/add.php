<?php
// $_POST["ename"]   : 	实验名
// $_POST["etime"]	:	实验创建时间
// $_POST["eshort"]	:	实验简介
// $_POST["pname"]	:	t1
	//include 'data.php';	
//while(1)
//{	
	//$qexpmark = mysql_query("SELECT mark FROM mark where id = '".$_POST["userid"]."'");
	//$expmark = mysql_fetch_array($qexpmark);
	//if($expmark["mark"]==0)
	//{
		// mysql_query("UPDATE mark SET mark = 1 WHERE id = '".$_POST["userid"]."'");
		//创建sae对象
			$s = new SaeStorage();	
		//修改路径文件
			//http.../t1.xml
			$Path= 'http://trysomething-block.stor.sinaapp.com/'.$_POST["pname"].".xml";	
			//t1.xml
			$pDocName= $_POST["pname"].".xml";	
			//创建xml对象
			$pathsDoc = new DOMDocument();
			//加载路径文件
			$pathsDoc->load($Path);

			//获取enum值
			$enum= $pathsDoc->getElementsByTagName("enum")->item(0)->nodeValue;	
			//修改enum值
			$pathsDoc->getElementsByTagName("enum")->item(0)->nodeValue= $enum + 1;

			//t1e1.xml
			$eDocName= $_POST["pname"]."e".$enum.".xml";
			
			//获取根节点
			$exp_path= $pathsDoc->getElementsByTagName("exp_path");	
			//创建item节点
			$item= $pathsDoc->createElement("item");
			//为节点赋值
			$item->nodeValue= $eDocName;
			//添加节点
			$exp_path->item(0)->appendChild($item);		

			//保存xml
			$s->write("block",$pDocName,$pathsDoc->saveXML());
			
		 //mysql_query("UPDATE mark SET mark = 0 WHERE id = '".$_POST["userid"]."'");	
		// break;
	//}
	//sleep(1);
//}
	
//创建实验文件
		$ename_file=str_replace("<","@@@;",$_POST["ename"]);
		$eshort_file=str_replace("<","@@@;",$_POST["eshort"]);
	//创建xml对象
	$expDoc = new DOMDocument("1.0","UTF-8");
	//创建根节点
	$exp= $expDoc->createElement("exp");
	$expDoc->appendChild($exp);
	//创建实验名节点
	$ename= $expDoc->createElement("ename");
	$ename->nodeValue=$ename_file;
	$exp->appendChild($ename);
	//创建实验开始时间节点
	$etime= $expDoc->createElement("etime");		
	$etime->nodeValue=$_POST["etime"];		
	$exp->appendChild($etime);			
	//创建实验简介					
	$eshort= $expDoc->createElement("eshort");
	$eshort->nodeValue=$eshort_file;
	$exp->appendChild($eshort);	
	
	//创建sqlid
	$sqlid= $expDoc->createElement("sqlid");	
	$sqlid->nodeValue= $enum;	
	$exp->appendChild($sqlid);		
	
		$file = htmlspecialchars_decode($expDoc->saveXML(),ENT_COMPAT );	
		$file=str_replace("#@$;","&amp;",$file);	
		$file=str_replace("%^*;","=",$file);	
		$file=str_replace("@@@;","&lt;",$file);		
	//保存xml
	$s->write("block",$eDocName,$file);
	
//SQL

	//$markAdd= "INSERT INTO ".$_POST["pname"]."mark(mark)VALUES('0')";
	//mysql_query($markAdd,$con);
	
	

	
	//mysql_close($con);
?>