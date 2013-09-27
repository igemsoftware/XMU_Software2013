<!DOCTYPE HTML>
<html>
	<head>
		<title>Welcome to Easy Note</title>
	</head>
	<body>
	<?php 
		//$judge初始值为0，返回1代表该团队名已被注册,返回2表示注册成功，返回3表示数据库出错,返回4个人注册时团队密码输入错误,返回5表示个人注册时团队不存在
		include 'data.php';
		$judge=0;
		$teamncs = mysql_query("SELECT name,code,id FROM teams");
		
		while($teamnc = mysql_fetch_array($teamncs))
		{
			if( $teamnc["name"]==$_POST["L2_T2_T"] )
			{
				//团队存在
				$judge = 1;
				if( $_POST['L2_T2_H']==1 )
				{
					//但用户想注册团队
					echo "<script>
							window.parent.document.getElementById('L2_T2_T').value='';
							window.parent.document.getElementById('L2_T2_T').placeholder='The team is existing';
							window.parent.document.getElementById('L2_T2_T').style.backgroundColor='RGB(55,96,146)';
						 </script>";
					
				}
				else
				{
					//用户想注册该团队的成员
					if( $teamnc["code"]==$_POST["L2_T2_C1"] )
					{
						//团队密码正确
						$table= "t".$teamnc["id"];
						
						$quser = mysql_query("SELECT * FROM ".$table." where name = '".$_POST["L2_T2_U"]."'");
						if($user = mysql_fetch_array($quser))		
						{
							//成员存在
								echo "<script>
										window.parent.document.getElementById('L2_T2_U').value='';
										window.parent.document.getElementById('L2_T2_U').placeholder='The user is existing';
										window.parent.document.getElementById('L2_T2_U').style.backgroundColor='RGB(55,96,146)';
									  </script>";	
						}
						else
						{
							//插入成员表
							$insert= "INSERT INTO ".$table."(name,code,email,realname,telephone,address)VALUES( '$_POST[L2_T2_U]', '$_POST[L2_T2_C3]', '$_POST[L2_T2_M]','0','0','0' )";
							if( mysql_query($insert,$con) )
							{
								//注册成功
								$judge= 2;
								echo "<script>
										window.parent.L2_F1();
										window.parent.document.getElementById('L1_T5').style.display='block';
										setTimeout('".'window.parent.document.getElementById("L1_T5").style.display="none";'."',2000);
										window.parent.document.getElementById('L2_form').reset();
									 </script>";
							}
							else
							{
								//注册失败，数据库出错
								$judge= 3;
								echo "<script>
										window.parent.document.getElementById('L2_T6').style.display='block';
										setTimeout('".'window.parent.document.getElementById("L2_T6").style.display="none";'."',2000);										
									 </script>";	
							}
						}
					}
					else
					{
						//团队密码输入错误
						$judge= 4;
						echo "<script>
									window.parent.document.getElementById('L2_T2_C1').value='';
									window.parent.document.getElementById('L2_T2_C1').placeholder='Error!';
									window.parent.document.getElementById('L2_T2_C1').style.backgroundColor='RGB(55,96,146)';
							  </script>";
					}
				}
				break;
			}
		}
		if($judge == 0)
		{
			//团队不存在
			if($_POST['L2_T2_H']==1)
			{
				$teamsconut_sql = "SELECT * FROM teams";
				$teamsconut_result = mysql_query($teamsconut_sql,$con);
				$teamsconut= mysql_num_rows($teamsconut_result);
				$teamsconut= $teamsconut+1;
				//用户想注册团队
				$table= "t".$teamsconut;
				//创建成员表
				$create= "CREATE TABLE ".$table."(id int(11) not null auto_increment, name varchar(255), code varchar(255), email varchar(255), realname varchar(255), telephone varchar(255), address varchar(255), primary key(id) )";
				//插入成员表
				$insert= "INSERT INTO ".$table."(name,code,email,realname,telephone,address)VALUES( '$_POST[L2_T2_U]', '$_POST[L2_T2_C3]', '$_POST[L2_T2_M]','0','0','0' )";
				$add= "INSERT INTO teams(name,code,captain,email)VALUES( '$_POST[L2_T2_T]', '$_POST[L2_T2_C1]', '$_POST[L2_T2_U]', '$_POST[L2_T2_M]' )";
				$markAdd= "INSERT INTO mark(mark)VALUES('0')";
				$markCreate= "CREATE TABLE ".$table."mark(id int(11) not null auto_increment, mark int(1), primary key(id) )";				
				$plasmidCreate= "CREATE TABLE ".$table."plasmid(id int(11) not null auto_increment, location text, pname text, type text, sequence text,  plength int(11), bname text,  blength int(11),  hua varchar(255),  conservation varchar(255),  primary key(id) )";		
		

		
				if( mysql_query($create,$con)  && mysql_query($insert,$con) && mysql_query($add,$con)&& mysql_query($markAdd,$con)&& mysql_query($markCreate,$con)&& mysql_query($plasmidCreate,$con) )
				{
					//注册成功
					
					$s = new SaeStorage();
					$s->write("block",$table.".xml",'<?xml version="1.0" encoding="UTF-8"?><exp_path><enum>1</enum></exp_path>');
					$judge= 2;
					echo "<script>
							window.parent.L2_F1();
							window.parent.document.getElementById('L2_form').reset();
							window.parent.document.getElementById('L1_T5').style.display='block';
							setTimeout('".'window.parent.document.getElementById("L1_T5").style.display="none";'."',2000);					
						 </script>";
				}
				else
				{
					//注册失败，数据库出错
					$judge= 3;
					echo "<script>
								window.parent.document.getElementById('L2_T6').style.display='block';
								setTimeout('".'window.parent.document.getElementById("L2_T6").style.display="none";'."',2000);	
						  </script>";			
				}
			}
			else
			{
				//但用户想注册成员
				$judge= 5;
				echo "<script>
						window.parent.document.getElementById('L2_T2_T').value='';
						window.parent.document.getElementById('L2_T2_T').placeholder='The team is not existing';
						window.parent.document.getElementById('L2_T2_T').style.backgroundColor='RGB(55,96,146)';
					 </script>";
			}
		}
		mysql_close($con);
	?>	
	</body>
</html>

