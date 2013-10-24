<!DOCTYPE HTML>
<html>
	<head>
		<title>Welcome to Easy Note</title>
		<meta charset="UTF-8" /> 
		<meta name="keywords" content="iGEM, XMU, XMU_software, web, HTML5" />
		<meta name="description" content="design for synthetic biology " />		
		<link href="L.css" rel="stylesheet" type="text/css" />
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>	
		<script src="L.js"></script>
	</head>
	<body onload="ini();" id="L">
		<div id="L0_0">
		<!--1366*768-->	
		
			<div id="L0_1">
			<!--蓝色部位-->
			</div>	
			
			<img id="L0_2" src="tool_icon/LOGIN.png"/>
			<img id="L0_3" src="tool_icon/NOTE.png"/>
			<img id="L0_4" src="tool_icon/iGEM.png"/>
			
			<div id="L1" >
				<div id="L1_T1">
						Log In
				</div>			
				<div id="L1_T2">
					<form  id="L1_form" action="M.php" method="POST" target="_self"></form>						
					<input id="L1_T2_T" name="L1_T2_T" type= "text"     class="L1_T2_class"   form="L1_form"   	placeholder="Team name"	 onfocus="L1_F3();"	/>						
					<input id="L1_T2_U" name="L1_T2_U" type= "text"     class="L1_T2_class"   form="L1_form"	placeholder="User name"	onfocus="L1_F4();"	/>
					<input id="L1_T2_C" name="L1_T2_C" type= "password" class="L1_T2_class"   form="L1_form"   	placeholder="User password" onfocus="L1_F5();"/>
				</div>
				<input id="L1_T3"	type= "button"   form="L1_form"   value="Log In"	onclick="L1_F2();"/>				
				<input id="L1_T4" 	type= "button" 	 value="Sign Up" onclick="L1_F1();"/>
			</div>
			
			<div id="L2" >
				<div id="L2_T1">
						Sign Up
				</div>			
				<div id="L2_T3">
					<input type= "reset" id="L2_T3_T" value="team" 	 form="L2_form"	/>
					<input type= "reset" id="L2_T3_U" value="person" form="L2_form" />	
				</div>			
				<div id="L2_T2">
					<form  id="L2_form" target="L2_iframe" method="post" action="L2_P1.php"></form>
					<iframe id="L2_iframe"></iframe>				
					<input id="L2_T2_T"		type= "text"  	 name="L2_T2_T" class="L2_T2_class"   form="L2_form" required="required"   placeholder="Team name" 	onfocus="L2_F9();"	 />					
					<input id="L2_T2_C1"	type= "password" name="L2_T2_C1" class="L2_T2_class"   form="L2_form" required="required"   placeholder="Team password" 		onfocus="L2_F10();"	 	 />				
					<input id="L2_T2_C2"	type= "password" name="L2_T2_C2" class="L2_T2_class"   form="L2_form" required="required"   placeholder="Re team password"  	 onblur="L2_F4();" onfocus="L2_F6();"	 />				
					<input id="L2_T2_U"		type= "text"     name="L2_T2_U" class="L2_T2_class"   form="L2_form" required="required"   placeholder="Captain name"	onfocus="L2_F11();"		 />					 
					<input id="L2_T2_C3"	type= "password" name="L2_T2_C3" class="L2_T2_class"   form="L2_form" required="required"   placeholder="Captain password"		 />				
					<input id="L2_T2_C4"	type= "password" name="L2_T2_C4" class="L2_T2_class"   form="L2_form" required="required"   placeholder="Re captain password"	 onblur="L2_F5();" onfocus="L2_F7();" 	 />				
					<input id="L2_T2_M"		type= "email"    name="L2_T2_M" class="L2_T2_class"   form="L2_form" required="required"   placeholder="Captain e-mail"			 />				
					<input id="L2_T2_H"		type= "hidden"     name="L2_T2_H"   form="L2_form" value="1" />
				</div>
				<input id="L2_T5_S"	type= "submit"  form="L2_form"   value="Sign Up" />
				<input id="L2_T5_C" type= "reset" 	form="L2_form"	 value="cancel"  onclick="L2_F1();" />		
				<div id="L2_T4">
					<input  id="L2_T4_C" type="checkbox" form="L2_form" required="required"  />I have read it:
					<a id="L2_T4_T" href="policy.html" target="_blank">"User Terms of Service."</a>			
				</div>
			</div>			
				
		</div>
		<script>
				//定位1366*768
				var x = window.innerWidth-1366;
				if(x>0)
				{
					x = x / 2;
					document.getElementById("L0_0").style.left= x+"px";
				}
				var y = window.innerHeight-768;
				if(y>0)
				{
					y = y / 2;
					document.getElementById("L0_0").style.top=y+"px";
				}				
		</script>	
		<div id="L1_T5">Success!</div>
		<div id="L2_T6">Database Error!</div>	
	</body>
</html>

