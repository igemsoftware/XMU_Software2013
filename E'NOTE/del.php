<?php
	include 'data.php';	
//while(1)
//{		
	//$qexpmark = mysql_query("SELECT mark FROM mark where id = '".$_GET["userid"]."'");
	//$expmark = mysql_fetch_array($qexpmark);
	//if($expmark["mark"]==0)
	//{
		// mysql_query("UPDATE mark SET mark = 1 WHERE id = '".$_GET["userid"]."'");

			$path=$_GET["pname"].".xml";
			$P='http://trysomething-block.stor.sinaapp.com/'.$path;
			$s = new SaeStorage();
		//修改路径文件
			//创建xml对象	
			$pathsDoc = new DOMDocument();
			//加载路径文件
			$pathsDoc->load($P);
			//取得item集
			$items= $pathsDoc->getElementsByTagName("item");
			//获取item的个数
			$length= $items->length;
			//找到要删除的item
			for($i=0;$i<$length;$i++)
			{
				//确定某个item
				$item= $items->item($i);
				//判断是否是要找的
				if( $item->nodeValue == $_GET["exp"] )
				{
					//找到该实验路径，删除这个item元素,然后结束
					$item->parentNode->removeChild($item);
					break;
				}
			}
			$s->write("block",$path,$pathsDoc->saveXML());
		//删除实验文件
			$s->delete("block",$_GET["exp"]);	

		// mysql_query("UPDATE mark SET mark = 0 WHERE id = '".$_GET["userid"]."'");	
		// break;
	//}
	//sleep(1);
//}	

//while(1)
//{	
	//$qexpmark = mysql_query("SELECT mark FROM ".$_GET["pname"]."mark where id = '".$_GET["sqlid"]."'");
	//if( $expmark = mysql_fetch_array($qexpmark) )
	//{
		//if($expmark["mark"]==0)
		//{
				$markdel= "DELETE FROM ".$_GET["pname"]."mark where id = '".$_GET["sqlid"]."'";
				mysql_query($markdel,$con);
		//		break;
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
