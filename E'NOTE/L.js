function ini(){
	//XHR
	if (window.XMLHttpRequest){
		XHR = new XMLHttpRequest();
	}
	else{
		XHR = new ActiveXObject("Microsoft.XMLHTTP");
	}	
	
	$("#L2_T3_T").click(L2_F2);
	$("#L2_T3_U").click(L2_F2);	
	
	document.getElementById("L1_T5").style.top= ( window.innerHeight / 2 -100) + "px";
	document.getElementById("L1_T5").style.left= ( window.innerWidth / 2-150 ) + "px";
	document.getElementById("L2_T6").style.top= ( window.innerHeight / 2 ) + "px";
	document.getElementById("L2_T6").style.left= ( window.innerWidth / 2-50 ) + "px";	
	
}

function L1_F1(){//登录框：点击注册按钮	
	document.getElementById("L1").style.display="none";
	document.getElementById("L2").style.display="block";
}
function L1_F2(){//点击登录按钮
	var x,y,z;
	if(x=document.getElementById("L1_T2_T").value){
		if(y=document.getElementById("L1_T2_U").value){
			if(z=document.getElementById("L1_T2_C").value){
				XHR.onreadystatechange=function(){
					if (XHR.readyState==4 && XHR.status==200){
						if(XHR.responseText==1){
							document.getElementById("L1_T2_T").value="";
							document.getElementById("L1_T2_T").style.backgroundColor="RGB(55,96,146)";
							document.getElementById("L1_T2_T").placeholder="The team is not existing!";
						}
						else if(XHR.responseText==2){
							document.getElementById("L1_T2_U").value="";
							document.getElementById("L1_T2_U").style.backgroundColor="RGB(55,96,146)";
							document.getElementById("L1_T2_U").placeholder="The member is not existing!";
						}
						else if(XHR.responseText==3)
						{
							document.getElementById("L1_T2_C").value="";
							document.getElementById("L1_T2_C").style.backgroundColor="RGB(55,96,146)";
							document.getElementById("L1_T2_C").placeholder="The password is wrong!";
						}
						else if(XHR.responseText==4)
						{
							document.getElementById("L1_form").submit();
						}
						else
						{
							document.getElementById('L2_T6').style.display='block';
							setTimeout('document.getElementById("L2_T6").style.display="none";',2000);
						}
					}
				}			
				XHR.open("POST","L1_P1.php",false);
				XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				XHR.send("x="+x+"&y="+y+"&z="+z);
			}
			else{
				document.getElementById("L1_T2_C").style.backgroundColor="RGB(55,96,146)";
				document.getElementById("L1_T2_C").placeholder="Please enter value!";
			}
		}
		else{
			document.getElementById("L1_T2_U").style.backgroundColor="RGB(55,96,146)";
			document.getElementById("L1_T2_U").placeholder="Please enter value!";
		}	
	}
	else{
		document.getElementById("L1_T2_T").style.backgroundColor="RGB(55,96,146)";
		document.getElementById("L1_T2_T").placeholder="Please enter value!";
	}		
}
function L1_F3(){//登录框：复原由错误地输入团队名导致的结果
	document.getElementById("L1_T2_T").placeholder="Team name";
	document.getElementById("L1_T2_T").style.backgroundColor="WHITE";	
}
function L1_F4(){//登录框：复原由错误地输入用户名导致的结果
	document.getElementById("L1_T2_U").placeholder="User name";
	document.getElementById("L1_T2_U").style.backgroundColor="WHITE";
}
function L1_F5(){//登录框：复原由错误地输入用户密码导致的结果
	document.getElementById("L1_T2_C").placeholder="User password";
	document.getElementById("L1_T2_C").style.backgroundColor="WHITE";
}

function L2_F1(){//注册框：点击取消按钮
	document.getElementById("L2").style.display="none";	
	document.getElementById("L1").style.display="block";
}
function L2_F2(){//转换块
	$(this).hide();
	if(this.value=="person")
	{
		$("#L2_T3_T").show();
		document.getElementById("L2_T2_C2").required="required";
		document.getElementById("L2_T2_C2").style.display="block";
		document.getElementById("L2_T2_H").value="1";
		document.getElementById("L2_T2_U").placeholder="Captain name";
		document.getElementById("L2_T2_C3").placeholder="Captain password";
		document.getElementById("L2_T2_C4").placeholder="Re captain password";
		document.getElementById("L2_T2_M").placeholder="Captain E_mail:";	
		document.getElementById("L2").style.height="595px";		
	}
	else
	{
		$("#L2_T3_U").show();
		document.getElementById("L2_T2_C2").required="";
		document.getElementById("L2_T2_C2").style.display="none";
		document.getElementById("L2_T2_H").value="2";
		document.getElementById("L2_T2_U").placeholder="User name";
		document.getElementById("L2_T2_C3").placeholder="User password";
		document.getElementById("L2_T2_C4").placeholder="Re user password";
		document.getElementById("L2_T2_M").placeholder="User e_mail";	
		document.getElementById("L2").style.height="536px";
			
	}
}
function L2_F4(){//注册框：验证再次输入的团队密码是否匹配，不匹配，框变红	
	var x=document.getElementById("L2_T2_C1").value;
	var y=document.getElementById("L2_T2_C2").value;
	if(x!=y)
	{
		document.getElementById("L2_T2_C2").value="";	
		document.getElementById("L2_T2_C2").placeholder="Error! Re enter please!";
		document.getElementById("L2_T2_C2").style.backgroundColor="RGB(55,96,146)";
	}
}
function L2_F5(){//注册框：验证再次输入的个人密码是否匹配，不匹配，框变红
	var x=document.getElementById("L2_T2_C3").value;
	var y=document.getElementById("L2_T2_C4").value;
	if(x!=y)
	{
		document.getElementById("L2_T2_C4").value="";	
		document.getElementById("L2_T2_C4").placeholder="Error! Re enter please!";
		document.getElementById("L2_T2_C4").style.backgroundColor="RGB(55,96,146)";
	}
}
function L2_F6(){//注册框：复原由错误地再次输入团队密码导致的结果
	document.getElementById("L2_T2_C2").placeholder="Re team password";
	document.getElementById("L2_T2_C2").style.backgroundColor="WHITE";	
}
function L2_F7(){//注册框：复原由错误地再次输入个人密码导致的结果
	
	if(L2_T3 == 1)
	{
		document.getElementById("L2_T2_C4").placeholder="Re captain password";
	}
	else
	{
		document.getElementById("L2_T2_C4").placeholder="Re user password";
	}
	document.getElementById("L2_T2_C4").style.backgroundColor="WHITE";	
}

function L2_F9(){//注册框：复原由错误地输入团队名导致的结果
	document.getElementById("L2_T2_T").placeholder="Team name";
	document.getElementById("L2_T2_T").style.backgroundColor="WHITE";		
}
function L2_F10(){//注册框：复原由错误地输入团队密码导致的结果
	document.getElementById("L2_T2_C1").placeholder="Team password";
	document.getElementById("L2_T2_C1").style.backgroundColor="WHITE";	
}
function L2_F11(){
	document.getElementById("L2_T2_U").placeholder="User name";
	document.getElementById("L2_T2_U").style.backgroundColor="WHITE";	
}
