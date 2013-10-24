//添加一个属性：步骤的，记录是否被冻结

function createXHR(){
	XHR=null;
	if ( window.XMLHttpRequest )
	{// code for all new browsers
		XHR= new XMLHttpRequest();
	}
	else if (window.ActiveXObject)
	{// code for IE5 and IE6
		XHR=new ActiveXObject("Microsoft.XMLHTTP");

	}
	if (XHR==null){
		alert("Your browser does not support XMLHTTP.");
	}
}
function state_Change(){
	if (XHR.readyState==4)
	{// 4 = "loaded"
		if (XHR.status==200)
		{// 200 = OK
		}
		else
		{
			alert("Problem retrieving XML data");
		}
	}
}
function loadXML(url){	
	XHR.onreadystatechange=state_Change;
	XHR.open("GET",url,false);
	XHR.send(null);
}	
//Q1
function initial(){
//E系列
	//E1:添加表格：表单获得焦点，消除警告
	$("#M3_A2_row").focus(function(){$("#M3_A2_m9").html("");});
	$("#M3_A2_col").focus(function(){$("#M3_A2_m9").html("");});
	//E2:添加实验：表单获得焦点，消除警告
	$("#M3_A4_m2").focus(function(){$("#M3_A4_m8").html("");});
	$("#M3_A4_m3").focus(function(){$("#M3_A4_m8").html("");});
	//E6:添加模板
	CPmark = 0;
	$("#M3_A5_m1").children().eq(CPmark).click();
	//E7:工具栏
	E7_M8_mark=1;
	//E11:自动保存  
	E11_mark=0;
	$("#M2_button3").click(E11);
	$("#M2_button3").click();
	//M3_A12删除全选
	$("#M3_A12_all").click(function(){
		if(this.checked==true)
		{
			$(".M3_A12_Box").each(function(){
				if(this.name!=0)
				{
					this.checked=true;
				}
			});
		}
		else
		{
			$(".M3_A12_Box").each(function(){
					this.checked=false;
			});
		}
	});	
	M3_A15_mark=1;
	//color变量创建=====
	color0='#dbeef4';		//navy实验（鼠标移过）
	color1="white";			//navy实验（平时）
	color2='#8eb4e3';		//navy实验（点击后）
	color3='#dbeef4';	//navy步骤（鼠标移过）
   	color4='#a6a6a6';	//navy步骤（平时）
	color5='#8eb4e3'; 	//navy步骤（点击后）
    color6='orange';	//记录板项（选中后）
	color7="#96b2d7";	//实验项（平时）
	color8="#c3d69b";	//步骤项（平时）
	color9="#a6a6a6";	//记录项（平时）
	color10="white";	//禁用背景
	//path变量创建======
	
	//XHR对象创建====== 
	createXHR();	
	
	//loadXML(P);
	XHRer  =$.ajax({
		url:P,
		cache: false,
		async:false
	});
	//pDoc对象创建======
	pDoc= XHRer.responseXML;	
	//enumber变量创建======
	enumber= pDoc.getElementsByTagName("enum")[0].childNodes[0].nodeValue;
	//mark变量创建======	
	emark= -1;
	smark1= -1;	//双击编辑步骤名时记录正在编辑的步骤的index
	//experiment数组创建======
	experiment= new Array();
	//label变量创建======
	var exps= pDoc.getElementsByTagName("item");
	var steps;
	var items;
	//navy模板变量创建=====
	navy_exp='\
		<div class="M5_exp_1">\
		</div>\
		<div class="M5_exp_2">\
		</div>\
		';	
	navy_step='\
		<div class="M5_exp_step">\
		</div>\
		';
	//记录板模板变量=====
	record_step='\
		<div class="M1_exp_stepInfo">\
			<button class="M1_exp_step_name_button" >\
				<input type="text" class="M1_exp_step_name" disabled="disabled" />\
			</button>\
			<span class="M1_exp_step_time"></span>\
		</div>\
		<table class="M1_exp_stepBoard"></table>\
		';
	record_item='\
		<tr class="M1_exp_stepItem">\
			<td class="M1_exp_item_time"></td>\
			<td class="M1_exp_item_content"></td>\
		</tr>\
		';
	//exp_num变量创建======用来标识Index
	exp_num= exps.length;
	//Q1单击选中实验名
	document.getElementById("M1_name_button").onclick=Q1;
	//Q2双击编辑实验名
    document.getElementById("M1_name_button").ondblclick=Q2_1;
	document.getElementById("M1_name").onblur=Q2_2;


    //E16:质粒库	
	XHR.onreadystatechange=state_Change;
	XHR.open("post","plasmidShow.php",false);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	XHR.send("pname="+pname);	
	$("#M3_A12_m2n1").append(XHR.responseText);
	$(".M3_A12_m2t4").blur(E16_Save);	
	$(".M3_A12_m2t5").click(E16_click);
	$(".M3_A12_m2t6").blur(E16_count);
	$(".M3_A12_m2t7").blur(E16_Save);
	$(".M3_A12_m2t5").each(function(){this.save=E16_Save});
	$(".M3_A12_m2t6").each(function(){this.save=E16_Save});
	$(".M3_A12_m2t7").each(function(){this.save=E16_Save});
	//对实验操作=====
	for(i = 0; i < exp_num ; i++)
	{		
	
	
		//experiment对象创建=====
		experiment[i]= new Object();
		//experiment对象docName属性获取======	
		experiment[i].docName = exps[i].childNodes[0].nodeValue;		
		//加载实验文档
		XHRer  =$.ajax({
			url:webPath+experiment[i].docName,
			cache: false,
			async:false
		});
		//experiment对象doc属性获取======
		experiment[i].doc = XHRer.responseXML;
		//获取steps======
		steps = experiment[i].doc.getElementsByTagName("step");  
		//experiment对象name属性获取======
		experiment[i].name=	experiment[i].doc.getElementsByTagName("ename")[0].childNodes[0].nodeValue;	
		//experiment对象time属性获取======
		experiment[i].time=	experiment[i].doc.getElementsByTagName("etime")[0].childNodes[0].nodeValue;	
		//experiment对象shortIntro属性获取======
		experiment[i].shortIntro= experiment[i].doc.getElementsByTagName("eshort")[0].childNodes[0].nodeValue;	
		experiment[i].sqlid= experiment[i].doc.getElementsByTagName("sqlid")[0].childNodes[0].nodeValue;	
		//experiment对象deleted属性获取======
		experiment[i].deleted = "no";
		//experiment对象stepNum属性（index）获取======
		experiment[i].stepNum= steps.length;	
		experiment[i].step= new Array(); 
		
						
  
		//-----记录板上的操作-----
		//添加实验记录板=====
		$("#M").append("<div class='M1_exp'></div>");	
		//-----navy上的操作-----
		//添加实验模块=====
		$("#M5_exp").append(navy_exp);
		//为模块赋值属性=====
		$(".M5_exp_1").get(i).index= i;	
		//为模块赋值id=====
		$(".M5_exp_1").eq(i).attr("id","ev"+i);					
		$(".M1_exp").eq(i).attr("id","eb"+i);		
		//为模块填充实验名=====
		$(".M5_exp_1").get(i).innerHTML= experiment[i].name;
		$(".M5_exp_1").get(i).title= experiment[i].name;
		//为模块添加鼠标移入移出效果=====
		$(".M5_exp_1").eq(i).mouseover( function(){ if(emark!=this.index){$(this).css("background-color",color0);} } );
		$(".M5_exp_1").eq(i).mouseout( function(){ if(emark!=this.index){$(this).css("background-color",color1);} } );
		//为模块添加点击效果=====
        $(".M5_exp_1").get(i).onclick= M5_exp_1_click;
		//为模块赋值缓存步骤点击属性（navy上的选中项）=====	
		$(".M5_exp_1").get(i).lastclick= 0;	
		//为模块赋值缓存选中项属性（记录板上的选中项）=====	
		$(".M5_exp_1").get(i).lastitem= -1;				
		//为模块赋值缓存选中项属性（记录板上的选中项）=====	
		$(".M5_exp_1").get(i).laststep= 0;				
        //对步骤操作=====	
			
		
		for(j=0;j<steps.length;j++)
		{
			//experiment对象step属性对象创建======
			experiment[i].step[j]= new Object();
			//experiment对象step属性对象name属性获取======
			experiment[i].step[j].name= experiment[i].doc.getElementsByTagName("sname")[j].childNodes[0].nodeValue;			
			//experiment对象step属性对象time属性获取======
			experiment[i].step[j].time= experiment[i].doc.getElementsByTagName("stime")[j].childNodes[0].nodeValue;
			//-----num 1-----//			
			experiment[i].step[j].member= experiment[i].doc.getElementsByTagName("member")[j].childNodes[0].nodeValue;			
			experiment[i].step[j].froze = experiment[i].doc.getElementsByTagName("froze")[j].childNodes[0].nodeValue;
			//-----num 1-----//
			//获取items======
			items= steps[j].getElementsByTagName("item");
			//experiment对象step属性对象itemNum属性获取======
			experiment[i].step[j].itemNum= items.length;
			//experiment对象step属性对象item属性获取======
			experiment[i].step[j].item= new Array();
			experiment[i].step[j].graph= new Array();	
			
			experiment[i].step[j].plasmid= experiment[i].doc.getElementsByTagName("plasmid")[j].childNodes[0].nodeValue;	
			experiment[i].step[j].plasmidback= experiment[i].doc.getElementsByTagName("plasmidback")[j].childNodes[0].nodeValue;
			for(var q=0; q<3; q++)
			{
				eval('\
					experiment[i].step[j].plasmid'+q+'= new Object();\
					experiment[i].step[j].plasmid'+q+'.pname="";\
					experiment[i].step[j].plasmid'+q+'.location="";\
					experiment[i].step[j].plasmid'+q+'.type="";\
					experiment[i].step[j].plasmid'+q+'.sequence="";\
					experiment[i].step[j].plasmid'+q+'.plength="";\
					experiment[i].step[j].plasmid'+q+'.bname="";\
					experiment[i].step[j].plasmid'+q+'.blength="";\
					experiment[i].step[j].plasmid'+q+'.id="";\
				');
			}
			//-----记录板上的操作-----
			//添加步骤框=====
			$(".M1_exp").eq(i).append(record_step);
			//步骤名填充=====     
			$(".M1_exp").eq(i).find(".M1_exp_step_name").eq(j).val(experiment[i].step[j].name);
			$(".M1_exp").eq(i).find(".M1_exp_stepInfo").get(j).title=experiment[i].step[j].name;
			//步骤创建时间填充=====
			$(".M1_exp").eq(i).find(".M1_exp_step_time").eq(j).append(experiment[i].step[j].time);
			//为步骤框赋值index属性=====	
		    $(".M1_exp").eq(i).children(".M1_exp_stepInfo").get(j).index=j;
			$(".M1_exp").eq(i).children(".M1_exp_stepBoard").get(j).index=j;
			//为步骤框赋值id属性=====
			$(".M1_exp").eq(i).children(".M1_exp_stepInfo").eq(j).attr("id","sb"+i+"s"+j);
			//为步骤框添加鼠标移入=====	
			$(".M1_exp").eq(i).children(".M1_exp_stepInfo").get(j).onmouseover=M1_exp_stepInfo_mouseover;
			$(".M1_exp").eq(i).children(".M1_exp_stepBoard").get(j).onmouseover=M1_exp_stepInfo_mouseover;	
		
			//-----navy上的操作-----
			//添加步骤框=====
			$(".M5_exp_2").eq(i).append(navy_step);		
			//步骤名填充=====
			$(".M5_exp_2").eq(i).children(".M5_exp_step").eq(j).append( experiment[i].step[j].name );
			$(".M5_exp_2").eq(i).children(".M5_exp_step").get(j).title= experiment[i].step[j].name;
			//为模块赋值index属性=====
			$(".M5_exp_2").eq(i).children(".M5_exp_step").get(j).index= j;
			//为模块赋值id属性=====
			$(".M5_exp_2").eq(i).children(".M5_exp_step").eq(j).attr("id","sv"+i+"s"+j);
			//为模块添加鼠标移入移出效果=====
			$(".M5_exp_2").eq(i).children(".M5_exp_step").eq(j).mouseover(Q6_1);
			$(".M5_exp_2").eq(i).children(".M5_exp_step").eq(j).mouseout(Q6_2);
			//为模块添加点击效果=====
			$(".M5_exp_2").eq(i).children(".M5_exp_step").get(j).onclick=M5_exp_step_click;	
//--------xiu--------只有当用户为当前用户时才赋予点击属性		
		if( (experiment[i].step[j].member == user.id) && (experiment[i].step[j].froze =="0") )
		{
			//Q3单击选中步骤
			$(".M1_exp").eq(i).find(".M1_exp_stepInfo").eq(j).click(Q3);
			//Q4双击编辑步骤名
			$(".M1_exp").eq(i).find(".M1_exp_step_name_button").eq(j).dblclick(Q4_1);
			$(".M1_exp").eq(i).find(".M1_exp_step_name").eq(j).blur(Q4_2);				
		}
		else
		{
			$(".M1_exp").eq(i).find(".M1_exp_stepInfo").get(j).style.backgroundColor= color9;
			$(".M1_exp").eq(i).find(".M1_exp_step_name").get(j).style.backgroundColor= color9;
			$(".M1_exp").eq(i).find(".M1_exp_step_name_button").get(j).style.backgroundColor= color9;
		}
//--------xiu--------			
			
			
			//对记录项操作=====
			for(k=0;k<items.length;k++)
			{
				//experiment对象step属性对象item属性赋值======
				experiment[i].step[j].item[k]= items[k].attributes[0].nodeValue;
				//-----记录板上的操作-----
                //添加记录项框=====
				$(".M1_exp").eq(i).children(".M1_exp_stepBoard").eq(j).append(record_item);
				//记录项创建时间填充=====
				$(".M1_exp").eq(i).children(".M1_exp_stepBoard").eq(j).find(".M1_exp_item_time").eq(k).append($(items[k]).children("time").eq(0).text());
				//为步骤框赋值id属性=====
				$(".M1_exp").eq(i).children(".M1_exp_stepBoard").eq(j).find(".M1_exp_item_time").eq(k).attr("id","ib"+i+"s"+j+"i"+k);	
				//记录项index属性
				$(".M1_exp").eq(i).children(".M1_exp_stepBoard").eq(j).find(".M1_exp_item_time").get(k).index= k;
				if( (experiment[i].step[j].member == user.id) && (experiment[i].step[j].froze == "0") )
				{
					//Q5单击选中记录项
					$(".M1_exp").eq(i).children(".M1_exp_stepBoard").eq(j).find(".M1_exp_item_time").eq(k).click(Q5);				
				}
				//else
				//{
					//$(".M1_exp").eq(i).children(".M1_exp_stepBoard").eq(j).find(".M1_exp_item_time").get(k).style.backgroundColor= color10;
					//$(".M1_exp").eq(i).children(".M1_exp_stepBoard").eq(j).find(".M1_exp_item_content").get(k).style.backgroundColor= color10;		
				//}				
				//记录项内容填充=====
                eval("C"+experiment[i].step[j].item[k]+"(i,j,k,items);");					
			} 
		} 

		$("#M3_A13_table2").append('\
 						<tr id="data'+i+'">\
							<th class="M3_A13_td">'+experiment[i].name+'</th>\
							<th class="M3_A13_td">\
								<a href="'+webPath+experiment[i].docName+'"  target="_blank">'+webPath+experiment[i].docName+'</a>\
							</th>\
							<th class="M3_A13_td"></th>\
						</tr>');
						
		var str = $("#eb"+i).html();
		$("#data"+i).children("th").eq(2).html(str);
		
		$("#data"+i).children("th").eq(2).find("input").each(function(index,element){
			 if($(this).attr("type")=="file" ||  $(this).attr("type")=="submit")
			 {
				this.disabled="disabled";
			 }
			 else
			  if( $(this).parents("table").eq(0).attr("class")!="C1" && $(this).attr("type")!="button"  &&  $(this).attr("type")!= "hidden")
			  {
				var p = $("#eb"+i).find("input").eq(index).val();
				var c = $("#eb"+i).find("input").eq(index).attr("class");
				$(this).parent().html("<input value='"+p+"' class='"+c+"'>");
			  }
		});
		str = $("#data"+i).children("th").eq(2).html();
		str = str.replace(/</g, "&lt;");
		$("#data"+i).children("th").eq(2).html("<textarea  class='M3_A13_textarea'>"+str+"</textarea>");
	
	}
}
//Q2
function M5_exp_1_click(){
	if(emark==-1)
	{
		//全局选定实验标识=====
		emark=this.index;	
		//显示=====
		M5_exp_1_click_done();
	}
	else
	{
		save(0);

	var str = $("#eb"+emark).html();
	$("#data"+emark).children("th").eq(2).html(str);
	
	$("#data"+emark).children("th").eq(2).find("input").each(function(index,element){
		 if($(this).attr("type")=="file" ||  $(this).attr("type")=="submit")
		 {
			this.disabled="disabled";
		 }
		 else
		  if( $(this).parents("table").eq(0).attr("class")!="C1" && $(this).attr("type")!="button" && $(this).attr("type")!= "hidden")
		  {
			var p = $("#eb"+emark).find("input").eq(index).val();
			var c = $("#eb"+emark).find("input").eq(index).attr("class");
			$(this).parent().html("<input value='"+p+"' class='"+c+"'>");
		  }
	});
	str = $("#data"+emark).children("th").eq(2).html();
	str = str.replace(/</g, "&lt;");
	$("#data"+emark).children("th").eq(2).html("<textarea  class='M3_A13_textarea'>"+str+"</textarea>");		
		
		
		//所有按钮消失=====
		$(".M1_board").fadeOut(500);
		//该实验记录板消失
		$("#eb"+emark).fadeOut(500);
		//navy步骤栏消失
		$("#ev"+emark).next().fadeOut(500);
		//navy实验名框变回原来的颜色
		$("#ev"+emark).css("background-color",color1);
		if(this.index==emark)
		{	
			//全局选定实验标识=====
			emark=-1;		
			//记录板实验名消失=====
			$("#M1_name").fadeOut(500);			
		}
		else
		{
			//全局选定实验标识=====
			emark= this.index;
			//显示=====
			M5_exp_1_click_done();
		}
	}
}
function M5_exp_1_click_done(){
		//-----记录板上的操作-----
		//记录板实验名显示=====
		$("#M1_name").val(experiment[emark].name);
		$("#M1_name").get(0).title= experiment[emark].shortIntro;
		$("#M1_name").fadeIn(500);
		//记录板实验按钮显示=====
		switch($("#ev"+emark).get(0).lastitem){
			case -1:
					$("#M1_board_e").fadeIn(500);
					document.getElementById("M1_name").style.backgroundColor=color6;
					break;
			case -2:
					$("#M1_board_s").fadeIn(500);
					document.getElementById("M1_name").style.backgroundColor=color7;
					break;
			default:
					$("#M1_board_i").fadeIn(500);
					document.getElementById("M1_name").style.backgroundColor=color7;
					break;
		}
		//记录板内容显示=====
		$("#eb"+emark).fadeIn(500);
		//-----navy上的操作-----
		//步骤列表显示=====
		$("#ev"+emark).next().fadeIn(500);	
		//实验名框变色=====
		$("#ev"+emark).css("background-color",color2);
		//跳到缓存步骤
		if($("#ev"+emark).next().children(".M5_exp_step").size())
		{
			//第一次进入时，步骤一选中
			$("#sv"+emark+"s"+$("#ev"+emark).get(0).lastclick).css("background-color",color5);
			//滑到相应步骤
			$("#sb"+emark+"s"+$("#ev"+emark).get(0).lastclick).scrollIntoView(true);
		}		
}
function M1_exp_stepInfo_mouseover(){
	if($("#ev"+emark).get(0).lastclick != this.index)
	{
		$("#sv"+emark+"s"+$("#ev"+emark).get(0).lastclick).css("background-color",color4);
		$("#ev"+emark).get(0).lastclick= this.index;
		$("#sv"+emark+"s"+this.index).css("background-color",color5);			
	}
}
function M5_exp_step_click(){
	$("#sv"+emark+"s"+$("#ev"+emark).get(0).lastclick).css("background-color",color4);
	$("#ev"+emark).get(0).lastclick=this.index;
	$(this).css("background-color",color5);
	$("#sb"+emark+"s"+$("#ev"+emark).get(0).lastclick).get(0).scrollIntoView(true);
}
function XOneClick(){
	switch($("#ev"+emark).get(0).lastitem){
	case -1:
			document.getElementById("M1_name").style.backgroundColor=color7;
			break;
	case -2:
			$("#sb"+emark+"s"+$("#ev"+emark).get(0).laststep).css("background-color",color8);
			$("#sb"+emark+"s"+$("#ev"+emark).get(0).laststep).find(".M1_exp_step_name").eq(0).css("background-color",color8);
			break;
    case -3:
			break;
	default:
			$("#ib"+emark+"s"+$("#ev"+emark).get(0).laststep+"i"+$("#ev"+emark).get(0).lastitem).css("background-color",color9);	
			break;
	}
}
//Q3
function Q1(){//Q1单击选中实验名
	//消除现选中项颜色
	XOneClick();
	//改变按钮板
	if($("#ev"+emark).get(0).lastitem != -1)
	{
		$(".M1_board").fadeOut(250);
		$("#M1_board_e").fadeIn(250);
	}
	//选中项颜色改变
	document.getElementById("M1_name").style.backgroundColor=color6;
	//标识选中项
	$("#ev"+emark).get(0).lastitem= -1;
};
function Q2_1(){//Q2_1双击编辑实验名
	document.getElementById("M1_name").disabled=false;
	$("#M1_name").focus();
}
function Q2_2(){//Q2_2双击编辑实验名
	document.getElementById("M1_name").disabled=true;
	experiment[emark].name= $("#M1_name").val();
	$("#ev"+emark).html(experiment[emark].name);
}
function Q3(){//Q3单击选中步骤
	//消除现选中项颜色
	XOneClick();
	//改变按钮板
	if($("#ev"+emark).get(0).lastitem != -2)
	{
	$(".M1_board").fadeOut(250);
	$("#M1_board_s").fadeIn(250);
	}
	//选中项颜色改变
	$("#sb"+emark+"s"+this.index).css("background-color",color6);
	$("#sb"+emark+"s"+this.index).find(".M1_exp_step_name").eq(0).css("background-color",color6);
	//标识选中项
	$("#ev"+emark).get(0).laststep=this.index;
	$("#ev"+emark).get(0).lastitem=-2;
}
function Q4_1(){//Q4_1双击编辑步骤名
	$("#sb"+emark+"s"+$("#ev"+emark).get(0).lastclick).find(".M1_exp_step_name").get(0).disabled=false;
	$("#sb"+emark+"s"+$("#ev"+emark).get(0).lastclick).find(".M1_exp_step_name").eq(0).focus();
	smark1= $("#ev"+emark).get(0).lastclick;
}
function Q4_2(){//Q4_2双击编辑步骤名
	$("#sb"+emark+"s"+smark1).find(".M1_exp_step_name").get(0).disabled=true;
	$("#sv"+emark+"s"+smark1).html($("#sb"+emark+"s"+smark1).find(".M1_exp_step_name").eq(0).val());
	experiment[emark].step[smark1].name= $("#sb"+emark+"s"+smark1).find(".M1_exp_step_name").eq(0).val();
}
function Q5(){//Q5单击选中记录项
	//消除现选中项颜色
	XOneClick();
	//改变按钮板
	if( ($("#ev"+emark).get(0).lastitem == -1)||($("#ev"+emark).get(0).lastitem == -2)||($("#ev"+emark).get(0).lastitem == -3) )
	{
		$(".M1_board").fadeOut(250);
		$("#M1_board_i").fadeIn(250);
	}
	//标识选中项
	$("#ev"+emark).get(0).laststep=this.parentNode.parentNode.index;
	$("#ev"+emark).get(0).lastitem=this.index;
	//选中项颜色改变
	$("#ib"+emark+"s"+$("#ev"+emark).get(0).laststep+"i"+$("#ev"+emark).get(0).lastitem).css("background-color",color6);	
}
function Q6_1(){//Q6_1 navy步骤框鼠标移入
	if( $("#ev"+emark).get(0).lastclick != this.index )
	{
		$(this).css("background-color",color3);
	} 
}
function Q6_2(){//Q6_2 navy步骤框鼠标移出
	if( $("#ev"+emark).get(0).lastclick != this.index )
	{
		$(this).css("background-color",color4);
	} 
}
//E0,增添步骤
function stepAdd(){
	var realStepNum= $("#ev"+emark).next().children(".M5_exp_step").size();
	var stepIndex= experiment[emark].stepNum;//just for index
	//experiment对象step属性对象创建======
	experiment[emark].step[stepIndex]= new Object();
	//experiment对象step属性对象name属性获取======
	experiment[emark].step[stepIndex].name=" no name";
	//experiment对象step属性对象itemNum属性（index）获取======
	experiment[emark].step[stepIndex].itemNum= 0;
	experiment[emark].step[stepIndex].member= user.id;	
	experiment[emark].step[stepIndex].froze	= "0";
	experiment[emark].step[stepIndex].plasmid= 0;				
	experiment[emark].step[stepIndex].plasmidback=0;
	for(var q=0; q<3; q++)
	{
		eval('\
			experiment[emark].step[stepIndex].plasmid'+q+'= new Object();\
			experiment[emark].step[stepIndex].plasmid'+q+'.pname="";\
			experiment[emark].step[stepIndex].plasmid'+q+'.location="";\
			experiment[emark].step[stepIndex].plasmid'+q+'.type="";\
			experiment[emark].step[stepIndex].plasmid'+q+'.sequence="";\
			experiment[emark].step[stepIndex].plasmid'+q+'.plength="";\
			experiment[emark].step[stepIndex].plasmid'+q+'.bname="";\
			experiment[emark].step[stepIndex].plasmid'+q+'.blength="";\
			experiment[emark].step[stepIndex].plasmid'+q+'.id="";\
		');
	}	
	//experiment对象step属性对象item属性获取======
	experiment[emark].step[stepIndex].item= new Array();	
	experiment[emark].step[stepIndex].graph= new Array();
	//experiment对象step属性对象time属性获取======
	var time = 	new Date();
	var day= 	time.getDate();
	var month=	time.getMonth() + 1;
	var year=	time.getFullYear();		
	var hour=		time.getHours();
	var minute=		time.getMinutes();
	var second=		time.getSeconds();	
	if(hour<10)
	{
		hour="0"+hour.toString();
	}
	if(minute<10)
	{
		minute="0"+minute.toString();
	}
	if(second<10)
	{
		second="0"+second.toString();
	} 	
	experiment[emark].step[stepIndex].time= year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second+"--"+user.name;
	//实验的步骤数加1
	experiment[emark].stepNum++;
	//添加记录板步骤框,navy步骤框=====
	if( $("#ev"+emark).get(0).lastitem == -1 )
	{
		$("#eb"+emark).append(record_step);
		$("#eb"+emark).children(".M1_exp_stepInfo").eq(realStepNum).attr("id","sb"+emark+"s"+stepIndex);
		$("#ev"+emark).next().append(navy_step);
		$("#ev"+emark).next().children(".M5_exp_step").eq(realStepNum).attr("id","sv"+emark+"s"+stepIndex);
	}
	else
	{
		$("#sb"+emark+"s"+$("#ev"+emark).get(0).laststep ).next().after(record_step);
		$("#sb"+emark+"s"+$("#ev"+emark).get(0).laststep ).next().next().attr("id","sb"+emark+"s"+stepIndex);
		$("#sv"+emark+"s"+$("#ev"+emark).get(0).laststep ).after(navy_step);
		$("#sv"+emark+"s"+$("#ev"+emark).get(0).laststep ).next().attr("id","sv"+emark+"s"+stepIndex);
	}
	//赋值index
	$("#sb"+emark+"s"+stepIndex).get(0).index=stepIndex;
	$("#sb"+emark+"s"+stepIndex).next().get(0).index=stepIndex;
	$("#sv"+emark+"s"+stepIndex).get(0).index=stepIndex;
	//步骤名填充=====     
	$("#sb"+emark+"s"+stepIndex).find(".M1_exp_step_name").eq(0).val(experiment[emark].step[stepIndex].name);
	$("#sv"+emark+"s"+stepIndex).html(experiment[emark].step[stepIndex].name);
	//步骤创建时间填充=====
	$("#sb"+emark+"s"+stepIndex).find(".M1_exp_step_time").eq(0).html(experiment[emark].step[stepIndex].time);
   //记录板步骤框添加鼠标移入=====	
	$("#sb"+emark+"s"+stepIndex).mouseover(M1_exp_stepInfo_mouseover);
	$("#sb"+emark+"s"+stepIndex).next().mouseover(M1_exp_stepInfo_mouseover);
	//Q6 navy步骤框鼠标移入移出效果=====
	$("#sv"+emark+"s"+stepIndex).mouseover(Q6_1);
	$("#sv"+emark+"s"+stepIndex).mouseout(Q6_2);  
	//Q4双击编辑步骤名
	$("#sb"+emark+"s"+stepIndex).children(".M1_exp_step_name_button").eq(0).dblclick(Q4_1);
	$("#sb"+emark+"s"+stepIndex).find(".M1_exp_step_name").eq(0).blur(Q4_2);
	//Q3单击选中步骤
	$("#sb"+emark+"s"+stepIndex).click(Q3);
	//navy步骤框点击=====
	$("#sv"+emark+"s"+stepIndex).click(M5_exp_step_click);
	//---选中---
	$("#sb"+emark+"s"+stepIndex).click();
	$("#sv"+emark+"s"+stepIndex).click();
	$("#sb"+emark+"s"+stepIndex).children(".M1_exp_step_name_button").eq(0).dblclick();


//-----num 3-----//
	XHR.onreadystatechange=state_Change;
	XHR.open("post","stepAdd.php",false);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	XHR.send("path="+experiment[emark].docName+"&member="+user.id+"&time="+experiment[emark].step[stepIndex].time+'&sqlid='+experiment[emark].sqlid+'&pname='+pname);	
//-----num 3-----//	
	E12_M4_M3();
}	
//E1:添加表格
function M3_A2_form_show(){//点击添加表格
	//显示幕布
	$("#M4").show();		
	//表单置空
	$("#M3_A2_col").val("");
	$("#M3_A2_row").val("");
	//显示弹框
	$("#M3_A2").show();	
	//消除警告，获得焦点
	$("#M3_A2_row").focus();	
}
function M3_A2_form_add(){//提交表格信息
	if( ($("#M3_A2_row").val()>0) && ($("#M3_A2_col").val()>0) )
	{
		 //创建表格
		 CGet(2);
		 //退出编辑
		 M3_A2_form_cancel();
	}
	else
	{
		//显示警告
		$("#M3_A2_m9").html("please enter the form's information~");
	}
}
function M3_A2_form_cancel(){//取消添加表格
	//弹框消失
	$("#M3_A2").hide();	
	//幕布消失
	$("#M4").hide();
}
//E2:添加实验
function M3_A4_exp_show(){//点击添加实验
	//显示幕布
	$("#M4").show();
	//表单置空
	$("#M3_A4_m2").val("");
	$("#M3_A4_m3").val("");
	//显示弹框
	$("#M3_A4").show();		
	//消除警告，获得焦点
	$("#M3_A4_m2").focus();		
}
function M3_A4_exp_add(){//提交实验信息
	if( $("#M3_A4_m2").val()&& $("#M3_A4_m3").val())
	{
		if(E7_M8_mark==0)
		{
			M8_show_hide();
		}
		//创建实验
		expAdd();
		//选中该实验
		$("#ev"+exp_num).click();
		//缓存数加1
		exp_num++;
		//退出编辑
		M3_A4_exp_cancel();
		E12_M4_M3();
	}
	else
	{
		//显示警告
		$("#M3_A4_m8").html("please enter the experiment's information~");	
	}
}
function expAdd(){//创建实验====== add.php =====
		//获取index
		var index = exp_num;
		//获取创建时间
		var time = 	new Date();
		var day= 	time.getDate();
		var month=	time.getMonth() + 1;
		var year=	time.getFullYear();		
		var hour=	time.getHours();
		var minute=	time.getMinutes();
		var second=	time.getSeconds();
		if(hour<10)
		{
			hour="0"+hour.toString();
		}
		if(minute<10)
		{
			minute="0"+minute.toString();
		}
		if(second<10)
		{
			second="0"+second.toString();
		} 		
		//experiment对象创建=====
		experiment[index]= new Object();
		//experiment对象time属性获取======
		experiment[index].time= year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;			
		//experiment对象name属性获取======
		experiment[index].name=	$("#M3_A4_m2").val();
		//experiment对象shortIntro属性获取======
		experiment[index].shortIntro= $("#M3_A4_m3").val();	
		//experiment对象deleted属性获取======
		experiment[index].deleted = "no";
		experiment[index].sqlid= enumber;
		//experiment对象stepNum属性获取======
		experiment[index].stepNum= 0;	
		//experiment对象step属性获取======
		experiment[index].step= new Array(); 
		//experiment对象docName属性获取======	
		experiment[index].docName = pname + "e" +enumber+".xml";	
		enumber++;		
		//创建实验文档，添加实验路径
///////encodeURIComponent

		var sendName=experiment[index].name.replace(/&/g, "#@$;");
		sendName= sendName.replace(/=/g, "%^*;");		

		
		var sendIntro=experiment[index].shortIntro.replace(/&/g, "#@$;");
		sendIntro= sendIntro.replace(/=/g, "%^*;");		

		
	XHR.onreadystatechange=state_Change;
	XHR.open("post","add.php",false);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	XHR.send("ename="+sendName+"&etime="+experiment[index].time+"&eshort="+sendIntro+"&pname="+pname+"&userid="+user.id);		
		
		//加载实验文档
		loadXML(webPath+experiment[index].docName);
		//experiment对象doc属性获取======
		experiment[index].doc = XHR.responseXML;		
	
		//-----记录板上的操作-----
		//获取现存实验数
		var i= $("#M5_exp").children(".M5_exp_1").size();
		//添加实验记录板=====
		$("#M").append("<div class='M1_exp'></div>");	
		//-----navy上的操作-----
		//添加实验模块=====
		$("#M5_exp").append(navy_exp);
		//为模块赋值属性=====
		$(".M5_exp_1").get(i).index= index;	
		//为模块赋值id=====
		$(".M5_exp_1").eq(i).attr("id","ev"+index);					
		$(".M1_exp").eq(i).attr("id","eb"+index);		
		//为模块填充实验名=====
		$(".M5_exp_1").get(i).innerHTML= experiment[index].name;
		//为模块添加鼠标移入移出效果=====
		$(".M5_exp_1").eq(i).mouseover( function(){ if(emark!=this.index){$(this).css("background-color",color0);} } );
		$(".M5_exp_1").eq(i).mouseout( function(){ if(emark!=this.index){$(this).css("background-color",color1);} } );
		//为模块添加点击效果=====
        $(".M5_exp_1").get(i).onclick= M5_exp_1_click;
		//为模块赋值缓存步骤点击属性（navy上的选中项）=====	
		$(".M5_exp_1").get(i).lastclick= 0;	
		//为模块赋值缓存选中项属性（记录板上的选中项）=====	
		$(".M5_exp_1").get(i).lastitem= -1;				
		//为模块赋值缓存选中项属性（记录板上的选中项）=====	
		$(".M5_exp_1").get(i).laststep= 0;		
		

		$("#M3_A13_table2").append('\
 						<tr id="data'+index+'">\
							<th class="M3_A13_td">'+experiment[index].name+'</th>\
							<th class="M3_A13_td">\
								<a href="'+webPath+experiment[index].docName+'"  target="_blank">'+webPath+experiment[index].docName+'</a>\
							</th>\
							<th class="M3_A13_td"></th>\
						</tr>');								
}
function M3_A4_exp_cancel(){//取消添加实验
	//弹框消失
	$("#M3_A4").hide();	
	//幕布消失
	$("#M4").hide();
}
//E3:删除
function M3_A3_delete_show(){//点击删除该项
	//显示幕布
	$("#M4").show();
	//显示弹框
	$("#M3_A3").show();	
}
function M3_A3_delete_ok(){//确认删除
	var i= emark;
	var j= $("#ev"+i).get(0).laststep;
	var k= $("#ev"+i).get(0).lastitem;
	switch(k){
		case -1:
				var scollect= $("#ev"+emark).next().children(".M5_exp_step");
				var stepNum=scollect.size();
				var graphLenth=0;
				var o=0;				
				for(var p=0; p<stepNum; p++)
				{
					graphLenth= experiment[i].step[scollect.get(p).index].graph.length;
					for( o=0; o<graphLenth; o++ )
					{
						if( $(experiment[i].step[scollect.get(p).index].graph[o]).val() )
						{
							XHR.onreadystatechange=state_Change;
							XHR.open("post","imgDel.php",false);//false
							XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
							XHR.send("oldgraph=" +$(experiment[i].step[scollect.get(p).index].graph[o]).val() );			
						}						
					}						
				}		
				//无点击项
				emark= -1;
				//删除navy
				$("#ev"+i).next().remove();
				$("#ev"+i).remove();
				//删除记录板
				$("#eb"+i).remove();
				$("#data"+i).remove();
				//其他东西
				$(".M1_board").fadeOut(500);
				$("#M1_name").fadeOut(500);	
				//标识已删除
				experiment[i].deleted = "yes";
				//后台删除
							XHR.onreadystatechange=state_Change;
							XHR.open("get","del.php?exp="+experiment[i].docName+"&pname="+pname+"&sqlid="+experiment[i].sqlid+"&userid="+user.id,true);
							XHR.send(null);					
				break;
		case -2:
				var delindex = $("#ev"+i).next().find(".M5_exp_step").index( $("#sv"+i+"s"+j) );
				
	//-----num 6-----//
				XHR.onreadystatechange=state_Change;
				XHR.open("post","delstep.php",false);
				XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				XHR.send("path="+experiment[i].docName+"&index="+delindex+'&sqlid='+experiment[i].sqlid+'&pname='+pname );		
	//-----num 6-----//	
				var graphLenth= experiment[i].step[j].graph.length;
				for(var o=0; o<graphLenth; o++)
				{
					if(  $(experiment[i].step[j].graph[o]).val()  )
					{
						XHR.onreadystatechange=state_Change;
						XHR.open("post","imgDel.php",false);
						XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
						XHR.send("oldgraph=" + $(experiment[i].step[j].graph[o]).val() );			
					}						
				}		
				//无点击项
				$("#ev"+i).get(0).lastitem= -3;
				//删除navy
				$("#sv"+i+"s"+j).remove();
				//删除记录板
				$("#sb"+i+"s"+j).next().remove();
				$("#sb"+i+"s"+j).remove();	
				break;
		default:
				if(experiment[i].step[j].item[k]==1)
				{
					if( $("#i"+i+"s"+j+"i"+k+"5").val() )
					{
						XHR.onreadystatechange=state_Change;
						XHR.open("post","imgDel.php",false);
						XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
						XHR.send("oldgraph=" + $("#i"+i+"s"+j+"i"+k+"5").val() );	
						experiment[i].step[j].graph.splice($("#i"+i+"s"+j+"i"+k).get(0).index,1);
					}
				}		
				//无点击项
				$("#ev"+i).get(0).lastitem= -3;
				//删除记录板
				$("#ib"+i+"s"+j+"i"+k).parent().remove();
	}
	M3_A3_delete_cancel();
	$(".M1_board").fadeOut(500);
	E12_M4_M3();
}
function M3_A3_delete_cancel(){//取消删除
	//弹框消失
	$("#M3_A3").hide();	
	//幕布消失
	$("#M4").hide();
}
//E4:保存
function save(saveMark){//保存实验=====save.php=====
//-----num 4-----//
	//获实验名
//////encodeURIComponent	
	saveExpName= experiment[emark].name.replace(/&/g, "#@$;");
	saveExpName= saveExpName.replace(/=/g, "%^*;");
	saveExpName= saveExpName.replace(/</g, "@@@;");
	//获实验简介（暂时不对其操作）
	//获步骤数snum
	var s= $("#ev"+emark).next().children(".M5_exp_step");
	var snum= s.size();

	//该用户拥有步骤数
	var membersum= 0;
	//获取步骤
	saveContent="";
	for(var i=0; i<snum; i++)
	{
		var jindex= s.get(i).index;
		if(experiment[emark].step[jindex].member==user.id)
		{
//////encodeURIComponent	
			//saveContent=saveContent+'name'+membersum+'='+experiment[emark].step[jindex].name.replace(/&amp;/g, "#a#m#p#;")+'&';
			content='   <sname>'+experiment[emark].step[jindex].name.replace(/</g, "@@@;")+'</sname>';
			content=content+'   <stime>'+experiment[emark].step[jindex].time+'</stime>';
			content=content+'   <member>'+experiment[emark].step[jindex].member+'</member>';
			content=content+'   <froze>'+experiment[emark].step[jindex].froze+'</froze>';
			content=content+'   <plasmid>'+experiment[emark].step[jindex].plasmid+'</plasmid>';
			content=content+'   <plasmidback>'+experiment[emark].step[jindex].plasmidback+'</plasmidback>';
			//获取记录项数inum
			var it= $("#sb"+emark+"s"+jindex).next().find(".M1_exp_item_time");
			var inum= it.size();
			
			//获取记录项
			for(var j=0; j<inum; j++)
			{
				var eeeeee;
				var kindex= it.get(j).index;
				//获属性
				var format= experiment[emark].step[jindex].item[kindex];
				content=content+'       <item format="'+format+'">\n';
				//获时间
				content=content+'           <time>'+$("#ib"+emark+"s"+jindex+"i"+kindex).html()+'</time>\n';
				//获内容
				format= Number( format );
				switch(format)
				{
					case  0:
							eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex).val();
							eeeeee=eeeeee.replace(/</g, "@@@;");
							content=content+'           <text>'+eeeeee+'</text>';
							break;
					case  1:
							eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+"0").get(0).src;
							eeeeee=eeeeee.replace(/</g, "@@@;");					
							content=content+'           <data>'+eeeeee+'</data>';
							eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+"1").val();
							eeeeee=eeeeee.replace(/</g, "@@@;");								
							content=content+'           <data>'+eeeeee+'</data>';
							eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+"5").val();
							eeeeee=eeeeee.replace(/</g, "@@@;");								
							content=content+'           <data>'+eeeeee+'</data>';
							break;
					case  2:
							var tdNum= document.getElementById("i"+emark+"s"+jindex+"i"+kindex).num;
							content=content+'           <rows>'+document.getElementById("i"+emark+"s"+jindex+"i"+kindex).rownum+'</rows>';
							content=content+'           <cols>'+document.getElementById("i"+emark+"s"+jindex+"i"+kindex).colnum+'</cols>';
							for(var z=0; z<tdNum; z++)
							{
								eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex).find(".C3_input").eq(z).val();
								eeeeee=eeeeee.replace(/</g, "@@@;");								
								content=content+'           <data>'+eeeeee+'</data>';
							}
							break;
					case 3:
							for(q=0;q<3;q++)
							{
								for(var x=0;x<11;x++)
								{
									eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+"q"+q+x).val();
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+'           <data>'+eeeeee+'</data>';
								}
							}
							break;
					case 4:
							for(var x=0;x<12;x++)
							{
									eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+x).val();
									eeeeee=eeeeee.replace(/</g, "@@@;");							
								content=content+'           <data>'+eeeeee+'</data>';
							}
							break;
					case 5:
							for(var x=0;x<29;x++)
							{
									eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+x).val();
									eeeeee=eeeeee.replace(/</g, "@@@;");								
								content=content+'           <data>'+eeeeee+'</data>';
							}
									eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+"31").val();
									eeeeee=eeeeee.replace(/</g, "@@@;");								
							content=content+'           <data>'+eeeeee+'</data>';
							break;		
					case 6:
							for(var x=0;x<11;x++)
							{
									eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+x).val();
									eeeeee=eeeeee.replace(/</g, "@@@;");								
								content=content+'           <data>'+eeeeee+'</data>';
							}
							break;	
					case 7:
							for(var x=0;x<26;x++)
							{
									eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+x).val();
									eeeeee=eeeeee.replace(/</g, "@@@;");							
								content=content+'           <data>'+eeeeee+'</data>';
							}
							break;			
					case 8:
							for(q=0;q<3;q++)
							{
								eval('\
									eeeeee= experiment[emark].step[jindex].plasmid'+q+'.pname;\
									');
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+"           <data>"+eeeeee+"</data>";
								eval('\
									eeeeee= experiment[emark].step[jindex].plasmid'+q+'.location;\
									');
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+"           <data>"+eeeeee+"</data>";
								eval('\
									eeeeee= experiment[emark].step[jindex].plasmid'+q+'.type;\
									');
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+"           <data>"+eeeeee+"</data>";
								eval('\
									eeeeee= experiment[emark].step[jindex].plasmid'+q+'.sequence;\
									');
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+"           <data>"+eeeeee+"</data>";
								eval('\
									eeeeee= experiment[emark].step[jindex].plasmid'+q+'.plength;\
									');
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+"           <data>"+eeeeee+"</data>";
								eval('\
									eeeeee= experiment[emark].step[jindex].plasmid'+q+'.bname;\
									');
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+"           <data>"+eeeeee+"</data>";
								eval('\
									eeeeee= experiment[emark].step[jindex].plasmid'+q+'.blength;\
									');
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+"           <data>"+eeeeee+"</data>";
								eval('\
									eeeeee= experiment[emark].step[jindex].plasmid'+q+'.id;\
									');
									eeeeee=eeeeee.replace(/</g, "@@@;");
									content=content+"           <data>"+eeeeee+"</data>";
							}
							break;	
					case 9:
							for(var x=0;x<15;x++)
							{
									eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+x).val();
									eeeeee=eeeeee.replace(/</g, "@@@;");
								content=content+'           <data>'+eeeeee+'</data>';
							}
							break;			
					case 10:
							for(var x=0;x<13;x++)
							{
									eeeeee=$("#i"+emark+"s"+jindex+"i"+kindex+x).val();
									eeeeee=eeeeee.replace(/</g, "@@@;");								
									content=content+'           <data>'+eeeeee+'</data>';
							}
							break;		
				}
				content=content+'       </item>\n';
			}
			
///////////encodeURIComponent	
			content=content.replace(/&/g, "#@$;");	
			content=content.replace(/=/g, "%^*;");	
			saveContent=saveContent+'data'+membersum+'='+content+'&';
			membersum++;
		}
	}
	saveContent=saveContent+'saveExpName='+saveExpName+'&member='+user.id+'&path='+experiment[emark].docName+'&sqlid='+experiment[emark].sqlid+'&pname='+pname;
	XHR.onreadystatechange=state_Change;
	XHR.open("post","save.php",true);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	XHR.send(saveContent);
	if(saveMark)
	{
		E12_M4_M3();
	}
	
	//var str = "<div class='M1_exp'>"+$("#eb"+emark).html()+'</div>';
	//str = str.replace(/</g, "&lt;");
	//$("#data"+emark).children("th").eq(2).html("<textarea  class='M3_A13_textarea'>"+str+"</textarea>");
	

}
//E5:item创建
function CGet( label ){//传入性质代号：text 0; graph 1;form 2;model 3...
	//获取时间

	var time = 	new Date();
	var day= 	time.getDate();
	var month=	time.getMonth() + 1;
	var year=	time.getFullYear();		
	var hour=		time.getHours();
	var minute=		time.getMinutes();
	var second=		time.getSeconds();	

	if(hour<10)
	{
		hour="0"+hour.toString();
	}
	if(minute<10)
	{
		minute="0"+minute.toString();
	}
	if(second<10)
	{
		second="0"+second.toString();
	} 	

	var data = year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;
	//获取i,j,k
	var i = emark;
	var j = $("#ev"+i).get(0).laststep;
	var k = experiment[i].step[j].itemNum;
	//对实验对象操作
	experiment[i].step[j].itemNum++;

	experiment[i].step[j].item[k]= label;
	//添加item
	if( $("#ev"+i).get(0).lastitem == -2 )
	{
		//-----step添加item-----
		//添加记录项框=====
		$("#sb"+i+"s"+j).next().prepend(record_item);
		//添加id
		$("#sb"+i+"s"+j).next().find(".M1_exp_item_time").eq(0).attr("id","ib"+i+"s"+j+"i"+k);	
	}
	else
	{
		//-----item添加item-----
		//添加记录项框=====
		$("#ib"+i+"s"+j+"i"+$("#ev"+emark).get(0).lastitem).parent().after(record_item);
		//添加id
		$("#ib"+i+"s"+j+"i"+$("#ev"+emark).get(0).lastitem).parent().next().children().eq(0).attr("id","ib"+i+"s"+j+"i"+k);
	}
	//添加index
	$("#ib"+i+"s"+j+"i"+k).get(0).index= k;
	//添加time
	$("#ib"+i+"s"+j+"i"+k).html(data);
	
	if( (experiment[i].step[j].member == user.id) && (experiment[i].step[j].froze == "0") )
	{
		//Q5单击选中记录项
		$("#ib"+i+"s"+j+"i"+k).click(Q5);			
	}
	
	//内容填充=====
	eval("C"+label+"(i,j,k,0);");	
}
//显示数据分为三步：1.获取 数据“样式” 2.导入 平台“样式” 3.填充数据
function C0(i,j,k,items){//text
	//获取 数据样式
	var dataStyle= "<textarea id='i"+i+"s"+j+"i"+k+"' class='C0' rows='1'></textarea>";
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});	
	//填充记录
	if(items)
	{	
		texts=$(items[k]).children("text");
		$("#i"+i+"s"+j+"i"+k).html(texts.eq(0).text());	
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			$("#i"+i+"s"+j+"i"+k).get(0).disabled="disabled";
			$("#i"+i+"s"+j+"i"+k).get(0).style.backgroundColor=color10;	
		}
	}
	else
	{
		$("#i"+i+"s"+j+"i"+k).html("no content!");
		$("#ib"+i+"s"+j+"i"+k).click();
		$("#i"+i+"s"+j+"i"+k).focus();
		E12_M4_M3();
	}
	
}
function C1(i,j,k,items){//graph
	//1
	var dataStyle= "\
	<table id='i"+i+"s"+j+"i"+k+"' class='C1'>\
		<tr>\
			<td class='C1_td'>\
				<img id='i"+i+"s"+j+"i"+k+"0' class='C1_graph' /><br/>\
				<form id='i"+i+"s"+j+"i"+k+"4' target='M7' action='img.php' method='post' enctype='multipart/form-data'></form>\
				<input name='img' 	type='file'   id='i"+i+"s"+j+"i"+k+"2'	 form='i"+i+"s"+j+"i"+k+"4' class='C1_button1' required='required'/>\
				<input name='pname' type='hidden' value='"+pname+"'  form='i"+i+"s"+j+"i"+k+"4' />\
				<input name='id'	type='hidden' value='i"+i+"s"+j+"i"+k+"' form='i"+i+"s"+j+"i"+k+"4' />\
				<input name='oldgraph'	type='hidden' id='i"+i+"s"+j+"i"+k+"5' form='i"+i+"s"+j+"i"+k+"4' />\
				<input  id='i"+i+"s"+j+"i"+k+"6' type='submit' value='submit' form='i"+i+"s"+j+"i"+k+"4' class='C1_button2'/>\
				<div id='i"+i+"s"+j+"i"+k+"3' class='M1_exp_img' ></div>\
				</td>\
			<td class='C1_td_text'>\
				<textarea id='i"+i+"s"+j+"i"+k+"1' class='C1_text'>\
				</textarea>\
				<br/>\
			</td>\
		</tr>\
	</table>\
	";
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();$("#i"+i+"s"+j+"i"+k+"3").html("");});
	//填充记录
	if(items)
	{	
		datas=$(items[k]).children("data");
		document.getElementById("i"+i+"s"+j+"i"+k+"0").src= datas.eq(0).text();
		$("#i"+i+"s"+j+"i"+k+"1").html(datas.eq(1).text());	
		$("#i"+i+"s"+j+"i"+k+"5").val( datas.eq(2).text() );
		$("#i"+i+"s"+j+"i"+k).get(0).index= experiment[i].step[j].graph.push("#i"+i+"s"+j+"i"+k+"5");
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			$("#i"+i+"s"+j+"i"+k+"1").get(0).disabled="disabled";
			$("#i"+i+"s"+j+"i"+k+"2").get(0).disabled="disabled";				
			$("#i"+i+"s"+j+"i"+k+"6").get(0).disabled="disabled";	
			$("#i"+i+"s"+j+"i"+k+"1").get(0).style.backgroundColor=color10;
			$("#i"+i+"s"+j+"i"+k+"2").get(0).style.backgroundColor=color10;		
			$("#i"+i+"s"+j+"i"+k+"6").get(0).style.backgroundColor=color10;			
		}
	}
	else
	{
		$("#i"+i+"s"+j+"i"+k+"1").html("choose the photo you want to submit");	
		$("#ib"+i+"s"+j+"i"+k).click();
		$("#i"+i+"s"+j+"i"+k).get(0).index= experiment[i].step[j].graph.push("#i"+i+"s"+j+"i"+k+"5");
		E12_M4_M3();
	}
	$("#i"+i+"s"+j+"i"+k).get(0).index--;
}
function C2(i,j,k,items){//form
	//3
	var rows;
	var cols;
	if(items)
	{
		rows= Number( $(items[k]).children("rows").eq(0).text() );
		cols= Number( $(items[k]).children("cols").eq(0).text() );
	}
	else
	{
		rows= $("#M3_A2_row").val();
		cols= $("#M3_A2_col").val();
	}
	tdNum= rows * cols;
	//1
	var x=0; var y=0;
	var dataStyle="<table id='i"+i+"s"+j+"i"+k+"' class='C3'>";
	while(x<rows)
	{
		dataStyle=dataStyle+"<tr>";
        y=0;
		while(y<cols)
		{
			dataStyle=dataStyle+"<td class='C3_td'><input type='text' class='C3_input' /></td>";
			y++;
		}
		dataStyle=dataStyle+"</tr>";
		x++;
	}
	dataStyle=dataStyle+"</table>";
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});
	document.getElementById("i"+i+"s"+j+"i"+k).num= tdNum;
	document.getElementById("i"+i+"s"+j+"i"+k).colnum= cols;
	document.getElementById("i"+i+"s"+j+"i"+k).rownum= rows;
	//3
	if(items)
	{
		var z=0;
		datas= $(items[k]).children("data");
		while(z<tdNum)
		{
			$("#i"+i+"s"+j+"i"+k).find(".C3_input").eq(z).val( datas.eq(z).text() );		
			z++;
		}
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			z=0;
			while(z<tdNum)
			{
				$("#i"+i+"s"+j+"i"+k).find(".C3_input").get(z).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k).find(".C3_input").get(z).style.backgroundColor=color10;
				z++;
			}	
		}
	}
	else
	{
		$("#ib"+i+"s"+j+"i"+k).click();
		E12_M4_M3();
	}
}
function C3(i,j,k,items){//foster:8
//获取 数据样
	var dataStyle='\
			<table id="i'+i+"s"+j+"i"+k+'" class="C3_ABC">\
					<tr>\
						<th colspan="3" class="C3_td_title">Cultivation</th>\
					</tr>\
					<tr>';
	for( var q=0; q<3; q++)
	{
		dataStyle=dataStyle+'\
						<td>\
							<table>\
								<tr>';
									
		switch( q )
		{
			case 0:dataStyle=dataStyle+'<th class="C3_td">Plasmid (A';break;
			case 1:dataStyle=dataStyle+'<th class="C3_td">Plasmid (B';break;
			case 2:dataStyle=dataStyle+'<th class="C3_td_C">Plasmid (C';break;
		}

		dataStyle=dataStyle+'\
								) </th>\
									<td colspan="2" class="C3_td">\
										<input id="i'+i+"s"+j+"i"+k+"q"+q+'0" class="C3_input_ABC" />\
									</td>\
								</tr>\
								<tr>\
									<th class="C3_td">Strain</th>\
									<td colspan="2" class="C3_td">\
										<input id="i'+i+"s"+j+"i"+k+"q"+q+'1" list="C3_strain" class="C3_input" />\
									</td>\
								</tr>\
									<tr>\
										<th class="C3_td">antibiotics</th>\
										<td class="C3_td" colspan="2">\
											<table>\
												<tr>\
													<td class="C8_td">\
														<input id="i'+i+"s"+j+"i"+k+"q"+q+'2" type="text" class="C3_input"  placeholder="none" />\
													</td>\
													<td class="C8_td">\
														<input id="i'+i+"s"+j+"i"+k+"q"+q+'3" type="text" class="C3_input"  placeholder="none" />\
													</td>\
													<td class="C8_td">\
														<input id="i'+i+"s"+j+"i"+k+"q"+q+'4" type="text" class="C3_input"  placeholder="none" />\
													</td>\
												</tr>\
											</table>\
										</td>\
									</tr>\
								<tr>\
									<th class="C3_td">Medium</th>\
									<td colspan="2" class="C3_td">\
										<input id="i'+i+"s"+j+"i"+k+"q"+q+'5" list="C3_Medium" class="C3_input" />\
									</td>\
								</tr>\
								<tr>\
									<th class="C3_td">Volume</th>\
									<td class="C3_td">\
										<input id="i'+i+"s"+j+"i"+k+"q"+q+'6"  type="text" value="20" class="C3_input">\
									</td>\
									<td class="C3_td">\
										<select id="i'+i+"s"+j+"i"+k+"q"+q+'7" class="C3_input">\
											<option value="uL">μL</option>\
											<option value="mL">mL</option>\
											<option value="L">L</option>\
										</select>\
									</td>\
								</tr>\
								<tr>\
									<th class="C3_td">Temperature</th>\
									<td class="C3_td">\
										<input id="i'+i+"s"+j+"i"+k+"q"+q+'8" type="text" value="37" class="C3_input">\
									</td>\
									<td class="C3_td">℃</td>\
									</tr>\
									<tr>\
										<th class="C3_td">Speed</th>\
										<td class="C3_td">\
											<input id="i'+i+"s"+j+"i"+k+"q"+q+'9" type="text" value="200" class="C3_input">\
										</td>\
										<td class="C3_td">\
											<select id="i'+i+"s"+j+"i"+k+"q"+q+'10" class="C3_input">\
											<option value="rpm">rpm</option>\
											<option value="rad/s">rad/s</option>\
											</select>\
										</td>\
									</tr>\
								</table>\
							</td>';	
			}
			dataStyle=dataStyle+'</tr></table>';	
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});
	//3
	if(items)
	{	     
		datas=$(items[k]).children("data");      
		for( var q=0; q<3;q++ )
		{
			for(var x=0;x<11;x++)
			{  
				$("#i"+i+"s"+j+"i"+k+"q"+q+x).val(datas.eq(x).text());
			}
			if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
			{
				x=0;
				while(x<11)
				{
					$("#i"+i+"s"+j+"i"+k+"q"+q+x).get(0).disabled="disabled";
					$("#i"+i+"s"+j+"i"+k+"q"+q+x).get(0).style.backgroundColor=color10;
					x++;
				}	
			}
		}
	}
	else
	{	
		$("#i"+i+"s"+j+"i"+k+"q00").val(experiment[i].step[j].plasmid0.pname);
		$("#i"+i+"s"+j+"i"+k+"q10").val(experiment[i].step[j].plasmid1.pname);
		$("#i"+i+"s"+j+"i"+k+"q20").val(experiment[i].step[j].plasmid2.pname);
		
		$("#ib"+i+"s"+j+"i"+k).click();
		modelCancel();
		E12_M4_M3();
	}	
		if( experiment[i].step[j].plasmid!=3)
		{
			$("#i"+i+"s"+j+"i"+k).find(".C3_td_C").each(function(){$(this).parents("td").eq(0).hide();});
		}	
}
function C4(i,j,k,items){//Extract plasmid:10
//获取 数据样式
	var dataStyle='\
				<table id="i'+i+"s"+j+"i"+k+'" class="C4">\
					<tr>\
						<th colspan="3">Plasmid Extraction</th>\
						<td rowspan="10"  class="C4_td_textarea">\
							<textarea class="C3_textarea" id="i'+i+"s"+j+"i"+k+'9">\
						</textarea></td>\
					</tr>\
					<tr>\
						<td class="C3_td">&nbsp&nbsp&nbsp&nbsp&nbsp </td>\
						<th class="C3_td">A<sub>260/280</sub></th>\
						<th class="C3_td">ng/μL</th>\
					</tr>\
					<tr>\
						<td class="C3_td" align="center">(A)</td>\
						<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'0" type="text" class="C3_input"></td>\
						<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'1" type="number" class="C3_input"></td>\
					</tr>\
					<tr>\
						<td class="C3_td" align="center">(B)</td>\
						<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'2" type="text" class="C3_input"></td>\
						<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'3" type="number" class="C3_input"></td>\
					</tr>\
					<tr class="C4_tr_C">\
						<td class="C3_td" align="center">(C)</td>\
						<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'10" type="text" class="C3_input"></td>\
						<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'11" type="number" class="C3_input"></td>\
					</tr>\
					<tr>\
						<th class="C3_td" colspan="3">\
							<button class="C8_button" id="i'+i+"s"+j+"i"+k+'12" >\
								Evaluate\
							</button>\
						</th>\
					</tr>\
					<tr>\
						<td colspan="3">\
							<table class="C3">\
								<tr>\
									<td class="C3_td">A<sub>260/280</sub>>1.8 and >200 ng/μL</td>\
									<td class="C3_td">great</td>\
									<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'4" type="text" class="C3_input"></td>\
								</tr>\
								<tr>\
									<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'5" type="text" class="C3_input"></td>\
									<td class="C3_td">fine</td>\
									<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'6" type="text" class="C3_input"></td>\
								</tr>\
								<tr>\
									<td class="C3_td" rowspan="2">A<sub>260/280</sub><1.6 or <80 ng/μL</td>\
									<td class="C3_td" rowspan="2">not good</td>\
									<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'7" type="text" class="C3_input"></td>\
								</tr>\
								<tr>\
									<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'8" type="text" class="C3_input"></td>\
								</tr>\
							</table>\
						</td>\
					</tr>\
				</table>';
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});
	
	$("#i"+i+"s"+j+"i"+k+"4").parents("table").eq(0).parents("tr").eq(0).toggle();
	$("#i"+i+"s"+j+"i"+k+"12").get(0).name= "#i"+i+"s"+j+"i"+k;
	$("#i"+i+"s"+j+"i"+k+"12").click(C4_button);
	//3
	if(items)
	{	     
		datas=$(items[k]).children("data");       
		for(var x=0;x<12;x++)
		{  
			$("#i"+i+"s"+j+"i"+k+x).val(datas.eq(x).text());
		}
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			x=0;
			while(x<12)
			{
				$("#i"+i+"s"+j+"i"+k+x).get(0).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k+x).get(0).style.backgroundColor=color10;
				x++;
			}	
		}		
	}
	else
	{	
		$("#i"+i+"s"+j+"i"+k).find(".C3_td").eq(3).html(experiment[i].step[j].plasmid0.pname+" (A)");
		$("#i"+i+"s"+j+"i"+k).find(".C3_td").eq(6).html(experiment[i].step[j].plasmid1.pname+" (B)");
		$("#i"+i+"s"+j+"i"+k).find(".C3_td").eq(9).html(experiment[i].step[j].plasmid2.pname+" (C)");	
	
		$("#ib"+i+"s"+j+"i"+k).click();
		modelCancel();
		E12_M4_M3();	
	}		
	if(experiment[i].step[j].plasmid!=3)
	{
		$("#i"+i+"s"+j+"i"+k+"11").parent().parent().hide();
		$("#i"+i+"s"+j+"i"+k+"9").parent().get(0).rowSpan=9;
	}		
}
function C4_button(){
	$(this.name+"4").parents("table").eq(0).parents("tr").eq(0).toggle();
}
function C5(i,j,k,items){//Extract plasmid:25  //性能方面的缺点：C的表单有两项被禁用项没必要存储数据，标志按钮是哪个质粒的第二个值没必要要
//获取 数据样
	var dataStyle='\
		<table class="C5" id="i'+i+"s"+j+"i"+k+'">\
			<tr>\
				<th colspan="4"  id="i'+i+"s"+j+"i"+k+'29" class="C5_th_C">Extract plasmid</th>\
				<td rowspan="10" class="C3_td_textarea">\
					<textarea class="C3_textarea" id="i'+i+"s"+j+"i"+k+'0" >\
					</textarea>\
				</td>\
			</tr>\
			<tr>\
				<th class="C5_canvas_td" colspan="4" id="i'+i+"s"+j+"i"+k+'30">\
					<div class="C5_canvas_matrix">\
					</div>\
					<div class="C5_TEXT1">EX</div>\
					<div class="C5_TEXT2">SP</div>\
					<input type="button" class="C5_canvas_button1" id="i'+i+"s"+j+"i"+k+'1">\
					<input type="button" class="C5_canvas_button2" id="i'+i+"s"+j+"i"+k+'2">\
					<input type="button" class="C5_canvas_button3" id="i'+i+"s"+j+"i"+k+'3">\
				</th>\
			</tr>\
				<tr>\
					<th class="C3_td" nowrap="nowrap">\
						&nbsp;&nbsp;\
						<input class="C5_system_input" type="number" id="i'+i+"s"+j+"i"+k+'4"/>\
						μL&nbsp;&nbsp;reaction&nbsp;&nbsp;\
					</th>\
					<th class="C3_td">(A)</th>\
					<th class="C3_td">(B)</th>\
					<th class="C3_td_C5_C">(C)</th>\
				</tr>\
				<tr>\
					<th class="C3_td">Plasmid</th>\
					<td class="C3_td"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'5"></td>\
					<td class="C3_td"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'6"></td>\
					<td class="C3_td_C5_C"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'7"></td>\
				</tr>\
				<tr>\
					<td class="C3_td" nowrap="nowrap">\
						&nbsp;&nbsp;\
						<input class="C5_system_input" type="number" value="10" id="i'+i+"s"+j+"i"+k+'31" />\
						*H Buffer&nbsp;&nbsp;\
					</td>\
					<td class="C3_td"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'8"></td>\
					<td class="C3_td"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'9"></td>\
					<td class="C3_td_C5_C"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'10"></td>\
				</tr>\
				<tr>\
					<th class="C3_td"><em>Eco</em>R I /μL</th>\
					<td class="C3_td"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'11"></td>\
					<td class="C3_td"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'12"></td>\
					<td class="C3_td_C5_C"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'13"></td>\
				</tr>\
				<tr>\
					<th class="C3_td"><em>Xba</em> I /μL</th>\
					<td class="C3_td"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'14"></td>\
					<td class="C3_td"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'15"></td>\
					<td class="C3_td_C5_C"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'16" disabled="disabled" value="0" ></td>\
				</tr>\
				<tr>\
					<th class="C3_td"><em>Spe</em> I /μL</th>\
					<td class="C3_td"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'17"></td>\
					<td class="C3_td"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'18"></td>\
					<td class="C3_td_C5_C"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'19" disabled="disabled" value="0" ></td>\
				</tr>\
				<tr>\
					<th class="C3_td"><em>Pst</em> I /μL</th>\
					<td class="C3_td"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'20"></td>\
					<td class="C3_td"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'21"></td>\
					<td class="C3_td_C5_C"><input class="C5_input" type="number" id="i'+i+"s"+j+"i"+k+'22"></td>\
				</tr>\
				<tr>\
					<th class="C3_td">H<sub>2</sub>0 /μL</th>\
					<td class="C3_td"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'23" disabled="disabled"></td>\
					<td class="C3_td"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'24" disabled="disabled" ></td>\
					<td class="C3_td_C5_C"><input class="C3_input" type="number" id="i'+i+"s"+j+"i"+k+'25" disabled="disabled"></td>\
					<input type="hidden" id="i'+i+"s"+j+"i"+k+'26"/>\
					<input type="hidden" id="i'+i+"s"+j+"i"+k+'27"/>\
					<input type="hidden" id="i'+i+"s"+j+"i"+k+'28"/>\
				</tr>\
		</table>';
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});	
//操作		
	$("#i"+i+"s"+j+"i"+k+"1").get(0).name="#i"+i+"s"+j+"i"+k;
	$("#i"+i+"s"+j+"i"+k+"2").get(0).name="#i"+i+"s"+j+"i"+k;
	$("#i"+i+"s"+j+"i"+k+"3").get(0).name="#i"+i+"s"+j+"i"+k;
	$("#i"+i+"s"+j+"i"+k+"1").click(C5_Btop);
	$("#i"+i+"s"+j+"i"+k+"2").click(C5_Btop);
	$("#i"+i+"s"+j+"i"+k+"3").click(C5_Bbottom);
//操作		
//操作	
	//所有表单失去焦点以后，计算水的值
	$("#i"+i+"s"+j+"i"+k+" .C5_input").each(function(){this.name="#i"+i+"s"+j+"i"+k;this.mark= 0;});
	$("#i"+i+"s"+j+"i"+k+" .C3_input").each(function(){this.name="#i"+i+"s"+j+"i"+k;this.mark= 0;});
	$("#i"+i+"s"+j+"i"+k+" .C5_system_input").each(function(){this.name="#i"+i+"s"+j+"i"+k;this.mark=1;});
	$("#i"+i+"s"+j+"i"+k+" .C5_input").blur(C5_calculate);
	$("#i"+i+"s"+j+"i"+k+" .C3_input").blur(C5_calculate);
	$("#i"+i+"s"+j+"i"+k+"4").blur(C5_calculate);
	//H Buffer失去焦点后先计算右边两个表单的值，再计算水的值	
	$("#i"+i+"s"+j+"i"+k+"31").blur(C5_mul);
	$("#i"+i+"s"+j+"i"+k+"31").get(0).cal=C5_calculate;
//操作	
	//3
	if(items)
	{	     
	//操作	
		switch( Number( $("#i"+i+"s"+j+"i"+k+'28').val() ) )
		{
			case 0:
					if( $("#i"+i+"s"+j+"i"+k+'26').val()=="0" )
					{
						C5_ABA("#i"+i+"s"+j+"i"+k);
					}
					else
					{
						C5_BAA("#i"+i+"s"+j+"i"+k);
					}
					break;
			case 1:
					if( $("#i"+i+"s"+j+"i"+k+'26').val()=="0" )
					{
						C5_ABB("#i"+i+"s"+j+"i"+k);
					}
					else
					{
						C5_BAB("#i"+i+"s"+j+"i"+k);
					}		
					break;
			case 2:
					if( $("#i"+i+"s"+j+"i"+k+'26').val()=="0" )
					{
						C5_ABC("#i"+i+"s"+j+"i"+k);
					}
					else
					{
						C5_BAC("#i"+i+"s"+j+"i"+k);
					}
					break;
		}
	//操作		
		datas=$(items[k]).children("data");       
		for(var x=0;x<29;x++)
		{  
			$("#i"+i+"s"+j+"i"+k+x).val(datas.eq(x).text());
		}
		$("#i"+i+"s"+j+"i"+k+"31").val(datas.eq(29).text());
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			x=0;
			while(x<29)
			{
				$("#i"+i+"s"+j+"i"+k+x).get(0).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k+x).get(0).style.backgroundColor=color10;
				x++;
			}	
			$("#i"+i+"s"+j+"i"+k+"31").get(0).disabled="disabled";
			$("#i"+i+"s"+j+"i"+k+"31").get(0).style.backgroundColor=color10;
		}	
		
	}
	else
	{	
//操作	ID 26、27、28 对应着三个按钮；它的值 0、1、2对应着A、B、C
		$("#i"+i+"s"+j+"i"+k+'1').val(experiment[i].step[j].plasmid0.pname+" (A)");
		$("#i"+i+"s"+j+"i"+k+'2').val(experiment[i].step[j].plasmid1.pname+" (B)");	
		$("#i"+i+"s"+j+"i"+k+'26').val(0);
		$("#i"+i+"s"+j+"i"+k+'27').val(1);	
		if(experiment[i].step[j].plasmid==3)
		{
			C5_ABC("#i"+i+"s"+j+"i"+k);
			$("#i"+i+"s"+j+"i"+k+'28').val(2);				
			$("#i"+i+"s"+j+"i"+k+'3').val(experiment[i].step[j].plasmid2.pname+" (C)");		
			experiment[i].step[j].plasmidback=3;		
		}
		else
		{
			$("#i"+i+"s"+j+"i"+k+'28').val(0);				
			$("#i"+i+"s"+j+"i"+k+'3').val(experiment[i].step[j].plasmid0.pname+" (A)");			
			C5_ABA("#i"+i+"s"+j+"i"+k);
			experiment[i].step[j].plasmidback=1;
		}
//操作			
		$("#i"+i+"s"+j+"i"+k).find(".C3_td").eq(1).html(experiment[i].step[j].plasmid0.pname+" (A)");
		$("#i"+i+"s"+j+"i"+k).find(".C3_td").eq(2).html(experiment[i].step[j].plasmid1.pname+" (B)");
		$("#i"+i+"s"+j+"i"+k).find(".C3_td_C5_C").eq(0).html(experiment[i].step[j].plasmid2.pname+" (C)");	


		$("#ib"+i+"s"+j+"i"+k).click();
		modelCancel();
		E12_M4_M3();
	}			
//操作	
	if($("#i"+i+"s"+j+"i"+k+'28').val()!="2")
	{
		$(".C3_td_C5_C").hide();
		$("#i"+i+"s"+j+"i"+k+'29').get(0).colSpan=3;
		$("#i"+i+"s"+j+"i"+k+'30').get(0).colSpan=3;
	}
//操作	
}
function C5_ini(index){
	$(index+'4').val(100);
	$(index+'5').val(62);
	$(index+'6').val(62);
	$(index+'7').val(62);
	$(index+'8').val(10);
	$(index+'9').val(10);
	$(index+'10').val(10);
	$(index+" .C5_input").each(function(){this.value=5;});
	$(index+'23').val(18);
	$(index+'24').val(18);
	$(index+'25').val(18);	
}
function C5_ABC(index){//传入"#i"+i+"s"+j+"i"+k;   
	C5_ini(index);
	$(index+'14').val(0);		
	$(index+'14').get(0).disabled=true;	
	$(index+'20').val(0);		
	$(index+'20').get(0).disabled=true;		
	$(index+'12').val(0);		
	$(index+'12').get(0).disabled=true;	
	$(index+'18').val(0);		
	$(index+'18').get(0).disabled=true;	
}
function C5_BAC(index){//传入"#i"+i+"s"+j+"i"+k;
	C5_ini(index);   
	$(index+'11').val(0);		
	$(index+'11').get(0).disabled=true;	
	$(index+'17').val(0);		
	$(index+'17').get(0).disabled=true;		
	$(index+'15').val(0);		
	$(index+'15').get(0).disabled=true;	
	$(index+'21').val(0);		
	$(index+'21').get(0).disabled=true;		
}
function C5_ABA(index){//传入"#i"+i+"s"+j+"i"+k;  
	C5_ini(index);
	$(index+'14').val(0);		
	$(index+'14').get(0).disabled=true;	
	$(index+'11').val(0);		
	$(index+'11').get(0).disabled=true;		
	$(index+'12').val(0);		
	$(index+'12').get(0).disabled=true;	
	$(index+'18').val(0);		
	$(index+'18').get(0).disabled=true;		
}
function C5_BAA(index){//传入"#i"+i+"s"+j+"i"+k;   
	C5_ini(index);
	$(index+'17').val(0);		
	$(index+'17').get(0).disabled=true;	
	$(index+'20').val(0);		
	$(index+'20').get(0).disabled=true;		
	$(index+'21').val(0);		
	$(index+'21').get(0).disabled=true;	
	$(index+'15').val(0);		
	$(index+'15').get(0).disabled=true;		
}
function C5_ABB(index){//传入"#i"+i+"s"+j+"i"+k;   
	C5_ini(index);
	$(index+'14').val(0);		
	$(index+'14').get(0).disabled=true;	
	$(index+'20').val(0);		
	$(index+'20').get(0).disabled=true;		
	$(index+'21').val(0);		
	$(index+'21').get(0).disabled=true;	
	$(index+'18').val(0);		
	$(index+'18').get(0).disabled=true;		
}
function C5_BAB(index){//传入"#i"+i+"s"+j+"i"+k;   
	C5_ini(index);
	$(index+'11').val(0);		
	$(index+'11').get(0).disabled=true;	
	$(index+'17').val(0);		
	$(index+'17').get(0).disabled=true;		
	$(index+'12').val(0);		
	$(index+'12').get(0).disabled=true;	
	$(index+'15').val(0);		
	$(index+'15').get(0).disabled=true;		
}
function C5_Btop(){
	var i= emark;
	var j = $("#ev"+i).get(0).laststep;	
	$(this.name+" .C5_input").each( function(){this.disabled=false;});
	if($(this.name+"26").val()=="0")
	{

		
		$(this.name+"26").val("1");
		$(this.name+"27").val("0");
		
		$(this.name+"1").val(experiment[i].step[j].plasmid1.pname+" (B)");
		$(this.name+"2").val(experiment[i].step[j].plasmid0.pname+" (A)");
		
		switch(  Number($(this.name+"28").val()) )
		{
			case 0:
					C5_BAA(this.name);break;
			case 1:
					C5_BAB(this.name);break;
			case 2:
					C5_BAC(this.name);break;
		}
	}
	else
	{

		$(this.name+"26").val("0");
		$(this.name+"27").val("1");
		
		$(this.name+"1").val(experiment[i].step[j].plasmid0.pname+" (A)");
		$(this.name+"2").val(experiment[i].step[j].plasmid1.pname+" (B)");	

		switch(  Number($(this.name+"28").val()) )
		{
			case 0:
					C5_ABA(this.name);break;
			case 1:
					C5_ABB(this.name);break;
			case 2:
					C5_ABC(this.name);break;
		}			
	}

}
function C5_Bbottom(){
	var i= emark;
	var j = $("#ev"+i).get(0).laststep;			
	if( $(this.name+"28").val()!="2" )
	{
		$(this.name+" .C5_input").each( function(){this.disabled=false;});
		if($(this.name+"28").val()=="0")
		{
			$(this.name+"28").val("1");
			$(this.name+"3").val(experiment[i].step[j].plasmid1.pname+" (B)");			
			experiment[i].step[j].plasmidback=2;
			$("#sb"+i+"s"+j).next().find(".C7").each(function(){
				$("#"+this.id+"26").html("A (insert) /μL");
				$("#"+this.id+"27").html("B (vector) /μL");
			});
		}
		else
		{
			$(this.name+"28").val("0");
			$(this.name+"3").val(experiment[i].step[j].plasmid0.pname+" (A)");
			experiment[i].step[j].plasmidback=1;
			$("#sb"+i+"s"+j).next().find(".C7").each(function(){
				$("#"+this.id+"26").html("A (vector) /μL");
				$("#"+this.id+"27").html("B (insert) /μL");
			});			
		}
		$("#sb"+i+"s"+j).next().find(".C6").each(C6_get_done);
		$("#sb"+i+"s"+j).next().find(".C7").each(C7_getValue);
		switch(  Number($(this.name+"28").val()) )
		{
			case 0:
					if($(this.name+"26").val()=="0")
					{
						C5_ABA(this.name);
					}
					else
					{
						C5_BAA(this.name);
					}
					break;
			case 1:
					if($(this.name+"26").val()=="0")
					{
						C5_ABB(this.name);
					}
					else
					{
						C5_BAB(this.name);
					}
					break;
		}	
	}
}
function C5_calculate(){
	if( this.mark==0)
	{
		var q= $(this).parent().index();
		q--;//  0/1/2
		$(this.name+(23+q)).val( $(this.name+"4").val()-$(this.name+(5+q)).val()-$(this.name+(8+q)).val()-$(this.name+(11+q)).val()-$(this.name+(14+q)).val()-$(this.name+(17+q)).val()-$(this.name+(20+q)).val() );			
	}
	else
	{
		for(var q=0;q<3;q++)
		{
			$(this.name+(23+q)).val( $(this.name+"4").val()-$(this.name+(5+q)).val()-$(this.name+(8+q)).val()-$(this.name+(11+q)).val()-$(this.name+(14+q)).val()-$(this.name+(17+q)).val()-$(this.name+(20+q)).val() );					
		}
	}
}
function C5_mul(){
	for(var q=8;q<11;q++)
	{
		$(this.name+q).val( $(this.name+"4").val()/$(this).val() );
	}
	this.cal();
}
function C6(i,j,k,items){//Electrophoretogram and recover:15    
//获取 数据样
	var dataStyle='\
			<table id="i'+i+"s"+j+"i"+k+'" class="C6">\
				<tr>\
					<th class="C3_td_title" colspan="5">electrophoretogram and gel extraction</th>\
					<td rowspan="10"  class="C6_td_textarea">\
						<textarea class="C3_textarea" id="i'+i+"s"+j+"i"+k+'6">\
						</textarea>\
					</td>\
				</tr>\
				<tr>\
					<th colspan="5"  class="C3_td">\
						Analyse Electrophoretogram\
					</th>\
				</tr>\
				<tr class="mdoelC5Correct'+i+"s"+j+"i"+k+'"> \
					<td class="C3_td"></td>\
					<th class="C3_td">A<sub>260/280</sub></th>\
					<th class="C3_td">ng/μL</th>\
					<th class="C3_td">bp</th>\
					<td class="C3_td" rowspan="4">\
						<input type="button" value="Correct"  class="C6_button" />\
					</td>\
				</tr>\
				<tr class="mdoelC5Correct'+i+"s"+j+"i"+k+'">\
					<td class="C3_td" nowrap="nowrap">(A)</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'0" class="C3_input" type="text"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'1" class="C6_input" type="number"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'2" class="C6_input" type="number"></td>\
				</tr>\
				<tr class="mdoelC5Correct'+i+"s"+j+"i"+k+'">\
					<td class="C3_td" nowrap="nowrap">(B)</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'3" class="C3_input" type="text"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'4" class="C6_input" type="number"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'5" class="C6_input" type="number"></td>\
				</tr>\
				<tr class="mdoelC5Correct'+i+"s"+j+"i"+k+'">\
					<td class="C3_td" nowrap="nowrap">(C)</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'8" class="C3_input" type="text"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'9" class="C3_input" type="number"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'10" class="C3_input" type="number"></td>\
				</tr>\
				<tr class="mdoelC5Wrong'+i+"s"+j+"i"+k+'">\
					<td class="C3_td" colspan="4" rowspan="3">\
						Possible reason:</br>\
						1. The validity or contamination of gel extraction kit and DD water.</br>\
						2. The validity of enzyme and buffer</br>\
						3. Incomplete dissolution of agar gel</br>\
					</td>\
					<td class="C3_td" rowspan="4">\
						<input type="button" value="Wrong"  class="C6_button" />\
					</td>\
				</tr>\
				<tr class="mdoelC5Wrong'+i+"s"+j+"i"+k+'">\
				</tr>\
				<tr class="mdoelC5Wrong'+i+"s"+j+"i"+k+'">\
				</tr>\
				<tr class="mdoelC5Wrong'+i+"s"+j+"i"+k+'">\
				</tr>\
			</table>\
			<input type="hidden" id="i'+i+"s"+j+"i"+k+'7" />';
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});
	$("#i"+i+"s"+j+"i"+k).get(0).get=C6_get_done;
	$("#i"+i+"s"+j+"i"+k+" .C6_button").click(C6_mdoel_show);
	$("#i"+i+"s"+j+"i"+k+" .C6_button").each(function(){this.name=i+"s"+j+"i"+k});
	$("#i"+i+"s"+j+"i"+k+" .C6_input").blur(function(){		
		$(this).parents(".M1_exp_stepBoard").eq(0).find(".C7").each(function(){
			this.getvalue();
		});
	});
	//3
	if(items)
	{	     
		datas=$(items[k]).children("data");       
		for(var x=0;x<11;x++)
		{  
			$("#i"+i+"s"+j+"i"+k+x).val(datas.eq(x).text());
		}
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			x=0;
			while(x<11)
			{
				$("#i"+i+"s"+j+"i"+k+x).get(0).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k+x).get(0).style.backgroundColor=color10;
				x++;
			}	
			$("#i"+i+"s"+j+"i"+k).find(".C6_button").get(0).disabled="disabled";
			$("#i"+i+"s"+j+"i"+k).find(".C6_button").get(1).disabled="disabled";
			$("#i"+i+"s"+j+"i"+k).find(".C6_button").get(0).style.backgroundColor=color10;
			$("#i"+i+"s"+j+"i"+k).find(".C6_button").get(1).style.backgroundColor=color10;		
		}		
		if( $("#i"+i+"s"+j+"i"+k+"7").val()==0)
		{
			$(".mdoelC5Correct"+i+"s"+j+"i"+k).hide();
		}
		else
		{
			$(".mdoelC5Wrong"+i+"s"+j+"i"+k).hide();
		}
		
	}
	else
	{	
		$("#i"+i+"s"+j+"i"+k).find(".C3_td").eq(6).html(experiment[i].step[j].plasmid0.pname+" (A)");
		$("#i"+i+"s"+j+"i"+k).find(".C3_td").eq(10).html(experiment[i].step[j].plasmid1.pname+" (B)");
		$("#i"+i+"s"+j+"i"+k).find(".C3_td").eq(14).html(experiment[i].step[j].plasmid2.pname+" (C)");	
	
	
		$("#i"+i+"s"+j+"i"+k).get(0).get();
		$("#i"+i+"s"+j+"i"+k+"7").val("1");
		$(".mdoelC5Wrong"+i+"s"+j+"i"+k).hide();
		$("#ib"+i+"s"+j+"i"+k).click();
		modelCancel();
		E12_M4_M3();
	}	
	if(experiment[i].step[j].plasmid!=3)
	{
		$("#i"+i+"s"+j+"i"+k+"8").parent().parent().hide();//C隐藏
		$("#i"+i+"s"+j+"i"+k).children().eq(0).children().last().hide();//错误隐藏
	}

	
}
function C6_mdoel_show(){
	var i= emark;
	var j = $("#ev"+i).get(0).laststep;
	if( $("#i"+this.name+"7").val()==0 )
	{
		$(".mdoelC5Wrong"+this.name).hide();
		$(".mdoelC5Correct"+this.name).show();
		$("#i"+this.name+"7").val("1");
		if(experiment[i].step[j].plasmid!=3)
		{
			$("#i"+this.name+"8").parent().parent().hide();//C隐藏
		}
	}
	else
	{
		$(".mdoelC5Correct"+this.name).hide();
		$(".mdoelC5Wrong"+this.name).show();
		$("#i"+this.name+"7").val("0");
		if(experiment[i].step[j].plasmid!=3)
		{
			$("#i"+this.name).children().eq(0).children().last().hide();//错误隐藏
		}
	}
}
function C6_get_done(){
		var i= emark;
		var j = $("#ev"+i).get(0).laststep;
		switch( Number(experiment[i].step[j].plasmidback) )
		{
			case 1:
					$("#"+this.id+"2").val(Number(experiment[i].step[j].plasmid0.plength)+Number(experiment[i].step[j].plasmid0.blength));
					$("#"+this.id+"5").val(Number(experiment[i].step[j].plasmid1.plength));
					break;
			case 2:
					$("#"+this.id+"5").val(Number(experiment[i].step[j].plasmid1.plength)+Number(experiment[i].step[j].plasmid1.blength));
					$("#"+this.id+"2").val(Number(experiment[i].step[j].plasmid0.plength));					
					break;
			case 3:
					$("#"+this.id+"2").val(Number(experiment[i].step[j].plasmid0.plength));
					$("#"+this.id+"5").val(Number(experiment[i].step[j].plasmid1.plength));
					$("#"+this.id+"10").val(Number(experiment[i].step[j].plasmid2.blength));
					break;
			case 0:
					break;		
		}		
}
function C7(i,j,k,items){//link:12
//获取 数据样
	var dataStyle='\
			<table id="i'+i+"s"+j+"i"+k+'" class="C7">\
				<tr>\
					<th class="C3_td_title" colspan="3">\
						Ligation\
					</th>\
					<td rowspan="8"  class="C3_td_textarea">\
						<textarea class="C3_textarea" id="i'+i+"s"+j+"i"+k+'0">\
						</textarea>\
					</td>\
				</tr>\
				<tr class="C7_tr_AB">\
					<th class="C3_td" nowrap="nowrap">\
						&nbsp;&nbsp;\
						<input type="number"  id="i'+i+"s"+j+"i"+k+'1" class="C7_blur_1"  value="10">\
						&nbsp;μL&nbsp;&nbsp;reaction&nbsp;&nbsp;\
					</th>\
					<td colspan="2" class="C3_td" nowrap="nowrap">\
						Insert/Vector= &nbsp;\
						<input type="number"  id="i'+i+"s"+j+"i"+k+'2" class="C7_blur_1"  value="3">\
						&nbsp;&nbsp;mol/mol&nbsp;&nbsp;\
					</td>\
				</tr>\
				<tr class="C7_tr_AB">\
					<td class="C3_td"></td>\
					<th class="C3_td">Recommend</th>\
					<th class="C3_td">Actual use</th>\
				</tr>\
				<tr class="C7_tr_AB">\
					<th class="C3_td" id="i'+i+"s"+j+"i"+k+'26" >A (vector) /μL</th>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'3" type="number" class="C3_input"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'4" type="number" class="C3_input"></td>\
				</tr>\
				<tr class="C7_tr_AB">\
					<th class="C3_td" id="i'+i+"s"+j+"i"+k+'27" >B (insert) /μL</th>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'5" type="number" class="C3_input"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'6" type="number" class="C3_input"></td>\
				</tr>\
				<tr class="C7_tr_AB">\
					<th class="C3_td" nowrap="nowrap">\
						&nbsp;&nbsp;\
						<input type="number"  id="i'+i+"s"+j+"i"+k+'7" class="C5_system_input" value="10" name="i'+i+"s"+j+"i"+k+'" />\
						&nbsp;*Buffer /μL&nbsp;&nbsp;\
					</th>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'8" type="number" class="C7_blur_2" value="1"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'9" type="number" class="C3_input"></td>\
				</tr>\
				<tr class="C7_tr_AB">\
					<th class="C3_td">ligase /μL</th>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'10" type="number" class="C7_blur_2"  value="1"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'11" type="number" class="C3_input"></td>\
				</tr>\
				\
				\
				\
				\
				<tr class="C7_tr_ABC">\
					<th class="C3_td" nowrap="nowrap">\
						&nbsp;&nbsp;\
						<input type="number"  id="i'+i+"s"+j+"i"+k+'12" class="C5_system_input" value="25">\
						&nbsp;μL&nbsp;&nbsp;reaction&nbsp;&nbsp;\
					</th>\
					<td class="C3_td">Recommend</td>\
					<td class="C3_td">Actual use</td>\
				</tr>\
				<tr class="C7_tr_ABC">\
					<td class="C3_td">A /μL</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'13" type="number" class="C7_input_C" value="1"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'14" type="number" class="C3_input"></td>\
				</tr>\
				<tr class="C7_tr_ABC">\
					<td class="C3_td">B /μL</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'15" type="number" class="C7_input_C" value="1"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'16" type="number" class="C3_input"></td>\
				</tr>\
				<tr class="C7_tr_ABC">\
					<td class="C3_td">C /μL</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'17" type="number" class="C7_input_C" value="1"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'18" type="number" class="C3_input"></td>\
				</tr>\
				<tr class="C7_tr_ABC">\
					<td class="C3_td" nowrap="nowrap">\
						&nbsp;&nbsp;\
						<input type="number"  id="i'+i+"s"+j+"i"+k+'19" class="C5_system_input" value="10" name="i'+i+"s"+j+"i"+k+'">\
						&nbsp;*Buffer /μL&nbsp;&nbsp;\
					</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'20" type="number" class="C7_input_C" value="1"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'21" type="number" class="C3_input"></td>\
				</tr>\
				<tr class="C7_tr_ABC">\
					<td class="C3_td">ligase /μL</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'22" type="number" class="C7_input_C" value="1"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'23" type="number" class="C3_input"></td>\
				</tr>\
				<tr class="C7_tr_ABC">\
					<td class="C3_td">H<sub>2</sub>O /μL</td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'24" type="number" class="C7_input_C" value="20" disabled="disabled"></td>\
					<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'25" type="number" class="C3_input"></td>\
				</tr>\
			</table>';
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();}); 
//操作	
	$("#i"+i+"s"+j+"i"+k).get(0).getvalue=	C7_getValue;
	$("#i"+i+"s"+j+"i"+k+" .C7_blur_1").blur(function(){$(this).parents("table").get(0).getvalue();});
	$("#i"+i+"s"+j+"i"+k+" .C7_blur_2").blur(function(){$(this).parents("table").get(0).getvalue();});
	$("#i"+i+"s"+j+"i"+k+"7").blur(function(){
		$("#i"+i+"s"+j+"i"+k+"8").val( $("#"+this.name+"1").val()/this.value );
		$(this).parents("table").get(0).getvalue();
	});
	$("#i"+i+"s"+j+"i"+k+"19").blur(function(){
		$("#i"+i+"s"+j+"i"+k+"20").val( $("#"+this.name+"12").val()/this.value );
		this.cal=C7_calculate;
		this.cal();
	});	
	$("#i"+i+"s"+j+"i"+k+" .C7_input_C").blur(C7_calculate);	
	$("#i"+i+"s"+j+"i"+k+"12").blur(C7_calculate);
//操作		
	//3
	if(items)
	{	     
		datas=$(items[k]).children("data");       
		for(var x=0;x<26;x++)
		{  
			$("#i"+i+"s"+j+"i"+k+x).val(datas.eq(x).text());
		}
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			x=0;
			while(x<26)
			{
				$("#i"+i+"s"+j+"i"+k+x).get(0).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k+x).get(0).style.backgroundColor=color10;
				x++;
			}	
		}			
	}
	else
	{	
//操作	
		if(experiment[i].step[j].plasmidback!=3)
		{
			$("#i"+i+"s"+j+"i"+k).get(0).getvalue();			
		}
//操作	
		$("#ib"+i+"s"+j+"i"+k).click();
		modelCancel();
		E12_M4_M3();
	}	
	if(experiment[i].step[j].plasmidback==3)
	{
		$(".C7_tr_AB").hide();
	}
	else
	{
		$(".C7_tr_ABC").hide();
		if(experiment[i].step[j].plasmidback==2)
		{
			$("#i"+i+"s"+j+"i"+k+"26").html("A (insert) /μL");
			$("#i"+i+"s"+j+"i"+k+"27").html("B (vector) /μL");
		}
	}
}
function C7_getValue(){
	//通过C7元素获得值
	var i= emark;
	var j = $("#ev"+i).get(0).laststep;	
	
	var X = $("#sb"+i+"s"+j).next().find(".C6").last();
	if(X.text())
	{
		var ngA = $("#"+X.get(0).id+"1").val();

		var ngB = $("#"+X.get(0).id+"4").val();

		var bpA = $("#"+X.get(0).id+"2").val();

		var bpB = $("#"+X.get(0).id+"5").val();

		var sys	= $("#"+this.id+"1").val();

		var ratio = $("#"+this.id+"2").val();

		var buffer = $("#"+this.id+"8").val();

		var ligase = $("#"+this.id+"10").val();

		var A;
		var B;
		if( ngA && ngB && bpA && bpB && sys && ratio && buffer && ligase)
		{
			switch( Number(experiment[i].step[j].plasmidback) )
			{
				//骨架A
				case 1:
					  A = sys-buffer-ligase;
					  B = ratio*(bpB/bpA)*(ngA/ngB);
					  A = A / ( 1 + B );
					  B = A * B;
					  $("#"+this.id+"3").val(A);
					  $("#"+this.id+"5").val(B);
					  break;
				//骨架B
				case 2:
					  B = sys-buffer-ligase;
					  A = ratio*(bpA/bpB)*(ngB/ngA);
					  B = B / ( 1 + A );
					  A = B * A ;
					  $("#"+this.id+"3").val(A);
					  $("#"+this.id+"5").val(B);	
					  break;
				//无骨架
				case 0:
					break;
			}
		} 
	}
}
function C7_calculate(){
	var id = $(this).parents("table").get(0).id;
	var a = $("#"+id+"12").val();
	var b = $("#"+id+"13").val();
	var c = $("#"+id+"15").val();
	var d = $("#"+id+"17").val();
	var e = $("#"+id+"20").val();
	var f = $("#"+id+"22").val();
	if( a && b && c && d && e && f)
	{
		$("#"+id+"24").val(a-b-c-d-e-f);
	}
}
function C8(i,j,k,items){
//获取 数据样
	var dataStyle='\
			<table id="i'+i+"s"+j+"i"+k+'" class="C8">\
				<tr>\
					<th colspan="2" class="C8_td_title">\
						Start to Ligate Plasmids\
					</th>\
				</tr>\
				<tr>';
	for(var q=0; q<3; q++)
	{
		dataStyle=dataStyle+'\
					<td>\
						<table class="C8">\
							<tr>\
								<th colspan="3" class="C8_td_title">\
									choose plasmid (';
		switch(q)
		{
			case 0:dataStyle=dataStyle+"A";break;
			case 1:dataStyle=dataStyle+"B";break;
			case 2:dataStyle=dataStyle+"C";break;
		}
		dataStyle=dataStyle+')\
				</th>\
							</tr>\
							<tr>\
								<th rowspan="2" class="C3_td">\
									plasmid\
								</th>\
								<th class="C3_td">\
									name\
								</th>\
								<td class="C3_td">\
									<input id="i'+i+"s"+j+"i"+k+"q"+q+'0" list="plasmidData" class="C3_input" name="'+q+'"/>\
								</td>\
							</tr>\
							<tr>\
								<th class="C3_td">\
									location\
								</th>\
								<td class="C3_td">\
									<input id="i'+i+"s"+j+"i"+k+"q"+q+'1"  class="C3_input" name="'+q+'" />\
								</td>\
							</tr>\
							<tr>\
								<th class="C3_td">\
									type\
								</th>\
								<td class="C3_td" colspan="2">\
									<input list="C3_RBS" id="i'+i+"s"+j+"i"+k+"q"+q+'2" class="C3_input" name="'+q+'"  value="Promoter">\
								</td>\
							</tr>\
							<tr>\
								<th rowspan="2" class="C3_td">\
									part-only\
								</th>\
								<th class="C3_td">\
									sequence\
								</th>\
								<td class="C3_td">\
									<input id="i'+i+"s"+j+"i"+k+"q"+q+'3" class="C3_input" name="'+q+'" />\
								</td>\
							</tr>\
							<tr>\
								<th class="C3_td">\
									length\
								</th>\
								<td class="C3_td">\
									<input id="i'+i+"s"+j+"i"+k+"q"+q+'4" type="number" class="C3_input" name="'+q+'" />\
								</td>\
							</tr>\
							<tr>\
								<th rowspan="2" class="C3_td">\
									backbone\
								</th>\
								<th class="C3_td">\
									name\
								</th>\
								<td class="C3_td">\
									<input id="i'+i+"s"+j+"i"+k+"q"+q+'5"  class="C3_input" name="'+q+'" />\
								</td>\
							</tr>\
							<tr>\
								<th class="C3_td">\
									length\
								</th>\
								<td class="C3_td">\
									<input id="i'+i+"s"+j+"i"+k+"q"+q+'6" type="number"  class="C3_input" name="'+q+'" />\
								</td>\
							</tr>\
						</table>\
					</td>';	
	}
	dataStyle=dataStyle+'\
				</tr>\
				<tr>\
					<td class="C3_td" colspan="2">\
						<input type="button" class="C8_button" value="if you need another plasmid (C) , double click here" />\
						<input type="button" class="C8_button" value="if you don\'t need the plasmid (C) , double click here" />\
					</td>\
				</tr>\
			</table>';
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});
	//3
	


	
	$("#i"+i+"s"+j+"i"+k).find(".C8_button").eq(1).hide();
	$("#i"+i+"s"+j+"i"+k).find(".C8_button").get(0).name="#i"+i+"s"+j+"i"+k;
	$("#i"+i+"s"+j+"i"+k).find(".C8_button").get(1).name="#i"+i+"s"+j+"i"+k;	
	$("#i"+i+"s"+j+"i"+k).find(".C8_button").eq(1).dblclick(C8_Bu);
	$("#i"+i+"s"+j+"i"+k).find(".C8_button").eq(0).dblclick(C8_Bu);
	
	
		for(var q=0;q<3;q++)
		{
			$("#i"+i+"s"+j+"i"+k+"q"+q+"4").blur(function(){
				eval('experiment['+i+'].step['+j+'].plasmid'+this.name+'.plength ="'+this.value+'";');
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C6").each(C6_get_done);
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C7").each(C7_getValue);
			});
			$("#i"+i+"s"+j+"i"+k+"q"+q+"1").blur(function(){eval('experiment['+i+'].step['+j+'].plasmid'+this.name+'.location ="'+this.value+'";');});
			$("#i"+i+"s"+j+"i"+k+"q"+q+"2").blur(function(){eval('experiment['+i+'].step['+j+'].plasmid'+this.name+'.type ="'+this.value+'";');});
			$("#i"+i+"s"+j+"i"+k+"q"+q+"3").blur(function(){eval('experiment['+i+'].step['+j+'].plasmid'+this.name+'.sequence ="'+this.value+'";');});
			$("#i"+i+"s"+j+"i"+k+"q"+q+"5").blur(function(){eval('experiment['+i+'].step['+j+'].plasmid'+this.name+'.bname ="'+this.value+'";');});
			$("#i"+i+"s"+j+"i"+k+"q"+q+"6").blur(function(){
				eval('experiment['+i+'].step['+j+'].plasmid'+this.name+'.blength ="'+this.value+'";');
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C6").each(C6_get_done);
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C7").each(C7_getValue);
			});		
			$("#i"+i+"s"+j+"i"+k+"q"+q+"0").blur(function(){
				var i = emark;
				var j = $("#ev"+i).get(0).laststep;
				var k = $("#ev"+i).get(0).lastitem;
				var zuo= this.value.lastIndexOf("(");
				var name= this.value.slice(0,zuo-1);
				var id= this.value.slice(zuo+1,-1);
				eval('\
					experiment['+i+'].step['+j+'].plasmid'+this.name+'.id = id;\
					experiment['+i+'].step['+j+'].plasmid'+this.name+'.pname = name;\
				');
				XHR.onreadystatechange=state_Change;
				XHR.open("post","plasmidSearch.php",false);
				XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				XHR.send("pname="+pname+"&id="+id+"&name="+name+"&q="+this.name+"&i="+i+"&j="+j+"&k="+k);	
				if( Number(XHR.responseText)!=0 )
				{		
					eval(XHR.responseText);	
					E8_thisName= name;/////////////////name
				}
				else
				{
					eval('\
						experiment['+i+'].step['+j+'].plasmid'+this.name+'.pname = "'+this.value+'";\
						experiment['+i+'].step['+j+'].plasmid'+this.name+'.id = "0";\
					');				
					E8_thisName= this.value;/////////////////name
				}
				E8_qindex=	this.name;/////////////////
				
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C3_ABC").each(function(){//////////////////////////////////
						$(this).find(".C3_input_ABC").eq(E8_qindex).val(E8_thisName);
					});////////////////////////////////////////////////////////////////////////////
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C5").each(function(){
					var add;
					switch(Number(E8_qindex))
					{
						case 0:add=" (A)";$(this).find(".C3_td").eq(1).html(E8_thisName+" (A)");break;
						case 1:add=" (B)";$(this).find(".C3_td").eq(2).html(E8_thisName+" (B)");break;
						case 2:add=" (C)";$(this).find(".C3_td_C5_C").eq(0).html(E8_thisName+" (C)");break;
					}
					if( $("#"+this.id+"26").val()==E8_qindex )
					{
						$("#"+this.id+"1").val(E8_thisName+add);
					}
					if( $("#"+this.id+"27").val()==E8_qindex )
					{
						$("#"+this.id+"2").val(E8_thisName+add);
					}
					if( $("#"+this.id+"28").val()==E8_qindex )
					{
						$("#"+this.id+"3").val(E8_thisName+add);
					}					
				});
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C6").each(function(){
					switch(Number(E8_qindex))
					{
						case 0:$(this).find(".C3_td").eq(6).html(E8_thisName+" (A)");break;
						case 1:$(this).find(".C3_td").eq(10).html(E8_thisName+" (B)");break;
						case 2:$(this).find(".C3_td").eq(14).html(E8_thisName+" (C)");break;
					}				
					this.get();
				});
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C7").each(C7_getValue);
				$(this).parents(".M1_exp_stepBoard").eq(0).find(".C4").each(function(){
					switch(Number(E8_qindex))
					{
						case 0:$(this).find(".C3_td").eq(3).html(E8_thisName+" (A)");break;
						case 1:$(this).find(".C3_td").eq(6).html(E8_thisName+" (B)");break;
						case 2:$(this).find(".C3_td").eq(9).html(E8_thisName+" (C)");break;
					}
				});
			});
		}		
	if(items)
	{	     
		
		datas=$(items[k]).children("data");      
		for(var q=0;q<3;q++)
		{
			for(var x=0;x<7;x++)
			{  
				$("#i"+i+"s"+j+"i"+k+"q"+q+x).val(datas.eq(q*8+x).text());
			}
			if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
			{
				x=0;
				while(x<7)
				{
					$("#i"+i+"s"+j+"i"+k+"q"+q+x).get(0).disabled="disabled";
					$("#i"+i+"s"+j+"i"+k+"q"+q+x).get(0).style.backgroundColor=color10;
					x++;
				}	
				
			}		
			
				eval('\
					experiment[i].step[j].plasmid'+q+'.pname="'+datas.eq(q*8+0).text()+'";\
					experiment[i].step[j].plasmid'+q+'.location="'+datas.eq(q*8+1).text()+'";\
					experiment[i].step[j].plasmid'+q+'.type="'+datas.eq(q*8+2).text()+'";\
					experiment[i].step[j].plasmid'+q+'.sequence="'+datas.eq(q*8+3).text()+'";\
					experiment[i].step[j].plasmid'+q+'.plength="'+datas.eq(q*8+4).text()+'";\
					experiment[i].step[j].plasmid'+q+'.bname="'+datas.eq(q*8+5).text()+'";\
					experiment[i].step[j].plasmid'+q+'.blength="'+datas.eq(q*8+6).text()+'";\
					experiment[i].step[j].plasmid'+q+'.id="'+datas.eq(q*8+7).text()+'";\
				');
			
		  }
		  if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		  {
				$("#i"+i+"s"+j+"i"+k).find(".C8_button").get(0).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k).find(".C8_button").get(1).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k).find(".C8_button").get(0).style.backgroundColor=color10;
				$("#i"+i+"s"+j+"i"+k).find(".C8_button").get(1).style.backgroundColor=color10;			
		  }
		  
		  if(experiment[i].step[j].plasmid=="2")
		  {
				$("#i"+i+"s"+j+"i"+k+"q20").parents("table").eq(0).parent().hide(); 
		  }
		  else
		  {
			 $("#i"+i+"s"+j+"i"+k).find("th").get(0).colSpan="3";	
			 $("#i"+i+"s"+j+"i"+k).find(".C8_button").eq(0).parent().get(0).colSpan="3";			 
		  }
	}
	else
	{	
		experiment[i].step[j].plasmid= "2";
		$("#i"+i+"s"+j+"i"+k+"q20").parents("table").eq(0).parent().hide(); 	
		$("#ib"+i+"s"+j+"i"+k).click();
		modelCancel();
		E12_M4_M3();
	}	
}
function C8_Bu(){
	var i= emark;
	var j = $("#ev"+i).get(0).laststep;

	if(  experiment[i].step[j].plasmid=="2" )
	{
		 experiment[i].step[j].plasmid="3";
		 
		 $(this.name).find(".C8_button").eq(0).hide();
		 $(this.name).find(".C8_button").eq(1).show();
		 $(this.name).find("tr").eq(1).children("td").eq(2).show();
		 this.parentNode.colSpan="3";
		 $(this.name).find("th").get(0).colSpan="3";
		 
		 $(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C4_tr_C").show();
		 $(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C4_td_textarea").each(function(){$(this).get(0).rowSpan=10;});
		 $(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C3_td_C").each(function(){$(this).parents("td").eq(0).show();});

		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C3_td_C5_C").each(function(){$(this).show();});
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C5_th_C").each(function(){this.colSpan=4});
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C5_canvas_td").each(function(){this.colSpan=4});		
 		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C5").each(function(){
			$("#"+this.id+'1').val(experiment[i].step[j].plasmid0.pname+" (A)");
			$("#"+this.id+'2').val(experiment[i].step[j].plasmid1.pname+" (B)");	
			$("#"+this.id+'3').val(experiment[i].step[j].plasmid2.pname+" (C)");
			$("#"+this.id+'26').val(0);
			$("#"+this.id+'27').val(1);	
			$("#"+this.id+'28').val(2);				
			$(this).find(".C5_input").each( function(){this.disabled=false;});
			C5_ABC("#"+this.id);
			experiment[i].step[j].plasmidback=3;
		});
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C6").each(function(){
			$("#"+this.id+"8").parent().parent().show();
			$(this).children().eq(0).children().last().show();
		});	
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C7_tr_AB").hide();
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C7_tr_ABC").show();
	}
	else
	{
		experiment[i].step[j].plasmid="2";
		
		 $(this.name).find(".C8_button").eq(1).hide();
		 $(this.name).find(".C8_button").eq(0).show();
		 $(this.name).find("tr").eq(1).children("td").eq(2).hide();
		 this.parentNode.colSpan="2";
		 $(this.name).find("th").get(0).colSpan="2";
		 $(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C4_tr_C").hide();
		 $(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C4_td_textarea").each(function(){$(this).get(0).rowSpan=9;});		 
		 $(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C3_td_C").each(function(){$(this).parents("td").eq(0).hide();});

		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C3_td_C5_C").each(function(){$(this).hide();});
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C5_th_C").each(function(){this.colSpan=3});
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C5_canvas_td").each(function(){this.colSpan=3});		
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C5").each(function(){
			$("#"+this.id+'1').val(experiment[i].step[j].plasmid0.pname+" (A)");
			$("#"+this.id+'2').val(experiment[i].step[j].plasmid1.pname+" (B)");	
			$("#"+this.id+'3').val(experiment[i].step[j].plasmid0.pname+" (A)");
			$("#"+this.id+'26').val(0);
			$("#"+this.id+'27').val(1);	
			$("#"+this.id+'28').val(0);		
			$(this).find(".C5_input").each( function(){this.disabled=false;});
			C5_ABA("#"+this.id);
			experiment[i].step[j].plasmidback=1;
		});
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C6").each(function(){
			$("#"+this.id+"8").parent().parent().hide();
			$(this).children().eq(0).children().last().hide();
		});
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C7_tr_AB").show();
		$(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C7_tr_ABC").hide();
	}
	 $("#sb"+i+"s"+j).next().find(".C6").each(C6_get_done);
	 $(this.name).parents(".M1_exp_stepBoard").eq(0).find(".C7").each(C7_getValue);	 
}
function C9(i,j,k,items){
	var dataStyle='\
				<table id="i'+i+"s"+j+"i"+k+'" class="C9">\
					<tr>\
						<th colspan="6">Template and Primers</th>\
					</tr>\
					<tr>\
						<th class="C3_td" colspan="3"> 	choose plasmid	</th>\
						<th class="C3_td" rowspan="4">	primer1			</th>\
						<th class="C3_td">				name			</th>\
						<td class="C3_td">\
							<input class="C3_input" id="i'+i+"s"+j+"i"+k+'0" type="text"  >\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td" rowspan="2">	plasmid			</th>\
						<th class="C3_td">				name			</th>\
						<td class="C3_td">\
							<input class="C3_input" id="i'+i+"s"+j+"i"+k+'1" list="plasmidData"  type="text" >\
						</td>\
						<th class="C3_td">				sequence		</th>\
						<td class="C3_td">\
							<input class="C3_input"  id="i'+i+"s"+j+"i"+k+'2" type="text">\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td">				location		</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'3" type="text" class="C3_input">\
						</td>\
						<th class="C3_td">				direction		</th>\
						<td class="C3_td">\
							<select id="i'+i+"s"+j+"i"+k+'4" class="C3_input">\
							  <option value ="left">left</option>\
							  <option value ="right">right</option>\
							</select>\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td" colspan="2">	type			</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'5" type="text" list="C3_RBS" class="C3_input">\
						</td>\
						<th class="C3_td">				position		</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'6" type="number" class="C3_input" >\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td" rowspan="2">\
							part-only\
						</th>\
						<th class="C3_td">				sequence			</th>\
						<td class="C3_td">\
							<input class="C3_input" id="i'+i+"s"+j+"i"+k+'7" type="text" >\
						</td>\
						<th class="C3_td" rowspan="4">\
							primer2\
						</th>\
						<th class="C3_td">				name				</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'8" type="text" class="C3_input" >\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td">				length			</th>\
						<td class="C3_td">\
							<input class="C3_input" id="i'+i+"s"+j+"i"+k+'9" type="number" >\
						</td>\
						<th class="C3_td">sequence</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'10" type="text" class="C3_input" >\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td" rowspan="2">\
							backbone\
						</th>\
						<th class="C3_td">name</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'11" type="text" class="C3_input" >\
						</td>\
						<th class="C3_td">direction</th>\
						<td class="C3_td">\
							<select id="i'+i+"s"+j+"i"+k+'12" class="C3_input">\
							  <option value ="left">left</option>\
							  <option value ="right">right</option>\
							</select>\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td">length</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'13" type="number" class="C3_input" >\
						</td>\
						<th class="C3_td">position</th>\
						<td class="C3_td"><input id="i'+i+"s"+j+"i"+k+'14" type="number" class="C3_input" ></td>\
					</tr>\
				</table>';	
				
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});	

	$("#i"+i+"s"+j+"i"+k+"1").blur(function(){
				var i = emark;
				var j = $("#ev"+i).get(0).laststep;
				var k = $("#ev"+i).get(0).lastitem;
				var zuo= this.value.lastIndexOf("(");
				var name= this.value.slice(0,zuo-1);
				var id= this.value.slice(zuo+1,-1);
				
				XHR.onreadystatechange=state_Change;
				XHR.open("post","PCRplasmidS.php",false);
				XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				XHR.send("pname="+pname+"&id="+id+"&name="+name);	
				if( XHR.responseText != "0" )
				{		
					eval(XHR.responseText);	
				}
	});
	$("#i"+i+"s"+j+"i"+k+"0").blur(function(){
		C10_primerName=this.value;
		$(this).parents(".M1_exp_stepBoard").eq(0).find(".C10").each(function(){
			$("#"+this.id+"6").val( C10_primerName );			
		});	
	});
	$("#i"+i+"s"+j+"i"+k+"8").blur(function(){
		C10_primerName=this.value;
		$(this).parents(".M1_exp_stepBoard").eq(0).find(".C10").each(function(){
			$("#"+this.id+"8").val( C10_primerName );			
		});	
	});	
					
						
						
	if(items)
	{	     
		datas=$(items[k]).children("data");       
		for(var x=0;x<15;x++)
		{  
			$("#i"+i+"s"+j+"i"+k+x).val(datas.eq(x).text());
		}
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			x=0;
			while(x<15)
			{
				$("#i"+i+"s"+j+"i"+k+x).get(0).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k+x).get(0).style.backgroundColor=color10;
				x++;
			}	
		}		
	}
	else
	{	
		$("#ib"+i+"s"+j+"i"+k).click();
		modelCancel();
		E12_M4_M3();	
	}		
}
function C10(i,j,k,items){
	var dataStyle='\
				<table id="i'+i+"s"+j+"i"+k+'" class="C10">\
					<tr>\
						<th colspan="3">PCR-Reaction</th>\
						<td rowspan="9"  class="C4_td_textarea">\
							<textarea class="C3_textarea" id="i'+i+"s"+j+"i"+k+'0">\
							</textarea>\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td">Reaction /μL</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'1" type="number" class="C3_input">\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td" nowrap="nowrap">\
							<input id="i'+i+"s"+j+"i"+k+'3" type="number" class="C10_system_input">\
							 *buffer /μL\
						</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'4" type="number" class="C10_input">\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td" nowrap="nowrap">dNTP ( Mixture（2.5 mM each） ) /μL</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'5" type="number" class="C10_input">\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td" nowrap="nowrap">\
							<input id="i'+i+"s"+j+"i"+k+'6" type="text" class="C10_system_input" placeholder="primer1">\
							 (20μM)  /μL\
						</th>\
						<td class="C3_td" nowrap="nowrap">\
							<input id="i'+i+"s"+j+"i"+k+'7" type="number" class="C10_input"/>\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td"  nowrap="nowrap">\
							<input id="i'+i+"s"+j+"i"+k+'8" type="text" class="C10_system_input"  placeholder="primer2" >\
							 (20μM)  /μL\
						</th>\
						<td class="C3_td" nowrap="nowrap">\
							<input id="i'+i+"s"+j+"i"+k+'9" type="number" class="C10_input">\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td">Template /μL\
							<select id="i'+i+"s"+j+"i"+k+'10">\
								  <option value ="colony">colony</option>\
								  <option value ="plasmid">plasmid</option>\
							</select>\
						</th>\
						<td class="C3_td" nowrap="nowrap">\
							<input id="i'+i+"s"+j+"i"+k+'11" type="number" class="C10_input">\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td">rTaq /μL</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'12" type="number" class="C10_input">\
						</td>\
					</tr>\
					<tr>\
						<th class="C3_td">H<sub>2</sub>O /μL</th>\
						<td class="C3_td">\
							<input id="i'+i+"s"+j+"i"+k+'2" type="number" class="C3_input" disabled="disabled">\
						</td>\
					</tr>\
				</table>';
	//导入 记录项框
	$("#ib"+i+"s"+j+"i"+k).next().html( dataStyle );
	$("#i"+i+"s"+j+"i"+k).click(function(){$("#ib"+i+"s"+j+"i"+k).click();});	
	
	$("#i"+i+"s"+j+"i"+k).find(".C10_input").blur(C10_cal);
	$("#i"+i+"s"+j+"i"+k+"3").blur(function(){
		$(this).parents("table").find(".C10_input").eq(0).val( Number( $(this).parents("table").find(".C3_input").eq(0).val() ) / Number( $(this).val() ) );
		$(this).parents("table").find(".C10_input").eq(0).blur();
	});
	
	if(items)
	{	     
		datas=$(items[k]).children("data");       
		for(var x=0;x<13;x++)
		{  
			$("#i"+i+"s"+j+"i"+k+x).val(datas.eq(x).text());
		}
		if( (experiment[i].step[j].member != user.id) || (experiment[i].step[j].froze =="1") )
		{
			x=0;
			while(x<13)
			{
				$("#i"+i+"s"+j+"i"+k+x).get(0).disabled="disabled";
				$("#i"+i+"s"+j+"i"+k+x).get(0).style.backgroundColor=color10;
				x++;
			}	
		}		
	}
	else
	{	
		if( $("#i"+i+"s"+j+"i"+k).parents(".M1_exp_stepBoard").eq(0).find(".C9").is(".C9") )
		{
			var DC9 = $("#i"+i+"s"+j+"i"+k).parents(".M1_exp_stepBoard").eq(0).find(".C9").last();
			$("#i"+i+"s"+j+"i"+k+"6").val( DC9.find(".C3_input").eq(0).val() );
			$("#i"+i+"s"+j+"i"+k+"8").val( DC9.find(".C3_input").eq(8).val() );
		}
		
		$("#ib"+i+"s"+j+"i"+k).click();
		modelCancel();
		E12_M4_M3();	
	}		
}
function C10_cal(){
	var sum = 0;
	$(this).parents("table").find(".C10_input").each(function(){
		sum = sum + Number( $(this).val() );
	});
	var cal = Number( $(this).parents("table").find(".C3_input").eq(0).val() ) - sum;
	$(this).parents("table").find(".C3_input").eq(1).val(cal);
}
//E6:添加模板
function CP(i){
	$("#M3_A5_m1").children().get(CPmark).style.backgroundColor="#c5eff7";
	$(".M3_A5_m2").eq(CPmark).hide();
	CPmark = i; 
	$("#M3_A5_m1").children().get(i).style.backgroundColor="#009eb6";
	$(".M3_A5_m2").eq(i).show();
}
function modelAdd(){
	//显示幕布
	$("#M4").show();
	//显示选择板
	$("#M3_A5").show();
}
function modelCancel(){
	$("#M3_A5").hide();
	$("#M4").hide();	
}
//E7:工具栏
function M8_show_hide(){
	if(E7_M8_mark==1)
	{
		$("#M8").show();
		$("#M8").animate({right:'95%'});
		$("#M5_exp").animate({left:'5%',right:'73%'});
		$("#M5_button").animate({left:'5%',right:'73%'});
		$(".M1_exp").animate({left:'27%'});
		$("#M1_name").animate({left:'27%',right:'39%'});
		E7_M8_mark=0;
	}
	else
	{
		$("#M8").animate({right:'99%'},function(){$("#M8").hide();});
		$("#M5_exp").animate({left:'1%',right:'75%'});
		$("#M5_button").animate({left:'1%',right:'75%'});
		$(".M1_exp").animate({left:'25%'});
		$("#M1_name").animate({left:'25%',right:'41%'});
		E7_M8_mark=1;
	}
}
//E8:邮件提醒
function M3_A1_mail_show(){
	//显示幕布
	$("#M4").show();		
	//弹框：删除栏
	$("#M3_A1").show();
	//表单置零
	document.getElementById("M3_A1_mail").reset();
}
function M3_A1_mail_submit(){
	var mail= document.getElementById("M3_A1_mail");
	var judge=0;
	for(var i = 2; i < 6; i++ )
	{
		if( mail.elements[i-2].value )
		{
			continue;
		}
		else
		{
			judge=1;
			$("#M3_A1_m"+i).focus();
			break;
		}
	}
	if(judge==0)
	{
		if( document.getElementById("M3_A1_m5").value >= 0 )
		{
			document.getElementById("M3_A1_mail").submit();
			M3_A1_mail_hide();
			E12_M4_M3();
		}
		else
		{
			$("#M3_A1_m5").focus();
		}
	}

}
function M3_A1_mail_hide(){
	//幕布消失
	$("#M4").hide();		
	//弹框消失
	$("#M3_A1").hide();	
}
//E9:标题栏效果
function M2_manager_show(){
		$("#M4_M2").show();
		$("#M2_button1").animate({top:'68px'},'50');
		setTimeout("$('#M2_button2').animate({top:'108px'},'50');",'50');
		setTimeout("$('#M2_button3').animate({top:'148px'},'50');",'100');
		setTimeout("$('#M2_button4').animate({top:'188px'},'50');",'150');
		setTimeout("$('#M2_button5').animate({top:'228px'},'50');",'200');
		setTimeout("$('#M2_button6').animate({top:'268px'},'50');",'250');
}
function M2_manager_hide(){
		$("#M4_M2").hide();
		$("#M2_button6").animate({top:'110%'},'50');
		setTimeout("$('#M2_button5').animate({top:'110%'},'50');",'50');
		setTimeout("$('#M2_button4').animate({top:'110%'},'50');",'100');
		setTimeout("$('#M2_button3').animate({top:'110%'},'50');",'150');
		setTimeout("$('#M2_button2').animate({top:'110%'},'50');",'200');
		setTimeout("$('#M2_button1').animate({top:'110%'},'50');",'250');
}
//E10:修改个人信息
function M3_A6_show(){
	//显示幕布
	$("#M4").show();	
	$("#M3_A6_m2").find(".M3_input").eq(0).val(user.name);	
	$("#M3_A6_m2").find(".M3_input").eq(1).val(user.rname);
	$("#M3_A6_m2").find(".M3_input").eq(2).val(user.address);
	$("#M3_A6_m2").find(".M3_input").eq(3).val(user.phone);
	$("#M3_A6_m2").find(".M3_input").eq(4).val(user.mail);
	//弹框：删除栏
	$("#M3_A6").show();
}
function M3_A6_done(){
	user.name= $("#M3_A6_m2").find(".M3_input").eq(0).val();
	user.rname= $("#M3_A6_m2").find(".M3_input").eq(1).val();
	user.address= $("#M3_A6_m2").find(".M3_input").eq(2).val();
	user.phone= $("#M3_A6_m2").find(".M3_input").eq(3).val();
	user.mail= $("#M3_A6_m2").find(".M3_input").eq(4).val();
	$("#M2_accound_man").html(user.name);
	XHR.onreadystatechange=state_Change;
	XHR.open("post","info.php",true);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	XHR.send("id="+user.id+"&pname="+pname+"&name="+user.name+"&rname="+user.rname+"&address="+user.address+"&phone="+user.phone+"&mail="+user.mail);	
	M3_A6_hide();
	E12_M4_M3();
}
function M3_A6_hide(){
	$("#M4").hide();		
	$("#M3_A6").hide();
}
//E10:修改用户密码
function M3_A7_show(){
	//显示幕布
	$("#M4").show();	
	//弹框：删除栏
	$("#M3_A7").show();
	for(var i=0;i<3;i++)
	{
		$("#M3_A7_m2").find(".M3_input").eq(i).val("");
	}
}
function M3_A7_done(){
	var oldcode= $("#M3_A7_m2").find(".M3_input").eq(0).val();
	var newcode1= $("#M3_A7_m2").find(".M3_input").eq(1).val();
	var newcode2= $("#M3_A7_m2").find(".M3_input").eq(2).val();
	if(oldcode!=user.code)
	{
		$("#M3_A7_m2").find(".M3_input").eq(0).focus();
		$("#M3_A7_m2").find(".M3_input").eq(0).val("error!");
	}
	else if( newcode1 )
		 {
			if(newcode1 == newcode2)
			{
				XHR.onreadystatechange=state_Change;
				XHR.open("post","code.php",false);
				XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				XHR.send("id="+user.id+"&newcode="+newcode1+"&pname="+pname);	
				M3_A7_hide();	
				E12_M4_M3();
			}
			else
			{
				$("#M3_A7_m2").find(".M3_input").eq(2).focus();
				$("#M3_A7_m2").find(".M3_input").eq(2).val("reinput the code please");			
			}
		 }
		 else
		 {
			$("#M3_A7_m2").find(".M3_input").eq(1).focus();
			$("#M3_A7_m2").find(".M3_input").eq(1).val("please enter new code");		 
		 }
}
function M3_A7_hide(){
	$("#M4").hide();		
	$("#M3_A7").hide();
}
//E10:反馈
function M3_A8_show(){
	//显示幕布
	$("#M4").show();	
	//弹框：删除栏
	$("#M3_A8").show();
}
function M3_A8_hide(){
	$("#M3_A8_feedback").val("");
	$("#M4").hide();		
	$("#M3_A8").hide();
}
function M3_A8_done(){
	XHR.onreadystatechange=state_Change;
	XHR.open("post","feedback.php",false);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	XHR.send("content="+$("#M3_A8_feedback").val()+"&team="+user.team+"&user="+user.name);	
	M3_A8_hide();
	E12_M4_M3();
}
//E11:自动保存
function E11(){
	if(E11_mark==0)
	{
		E11_mark=1;
		$("#M2_autoSave").html("on");
		$("#M2_autoSave").css("color","#eeece1");
		E11_autoSave();
	}
	else
	{
		E11_mark=0;
		$("#M2_autoSave").html("off");	
		$("#M2_autoSave").css("color","#f31659");
		clearTimeout(E11_time);
	}
}
function E11_autoSave(){
	E11_time= setTimeout("if(emark!=-1){save(0);}E11_autoSave();",10000);
}
//E12:操作成功显示
function E12_M4_M3(){
	$("#M4_M3").fadeToggle(500);
	$("#M4_M3").fadeToggle(500);
}
//E13:help显示
function E13_show(){
	M2_manager_hide();
	$("#M4_M4").show();
	$("#M3_A9").fadeToggle();
}
function E13_hide(){
	$("#M4_M4").hide();
	$("#M3_A9").fadeToggle();
}
//E14:软件集成显示
function E14_show(){
	$("#M4_M5").show();
	$("#M3_A10").fadeToggle();
}
function E14_hide(){
	$("#M4_M5").hide();
	$("#M3_A10").fadeToggle();
}
//E15:about us
function E15_show(){
	M2_manager_hide();
	$("#M4_M6").show();
	$("#M3_A11").fadeToggle();
}
function E15_hide(){
	$("#M4_M6").hide();
	$("#M3_A11").fadeToggle();
}
//E16:质粒库
function E16_show(){
	$("#M4_M7").show();
	$("#M3_A12").fadeToggle();	
}
function E16_hide(){
	$("#M4_M7").hide();
	$("#M3_A12").fadeToggle();
}
function E16_add(){
	XHR.onreadystatechange=state_Change;
	XHR.open("post","plasmidAdd.php",false);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	XHR.send("pname="+pname);
	$("#M3_A12_m2n1").append('\
	</br>\
		<tr>\
			<td>\
				<input type="checkbox" name="'+ Number(XHR.responseText) +'" class="M3_A12_Box" />\
			</td>\
			<td>\
				<input type="text" class="M3_A12_m2t4" name="id" disabled="disabled" value="'+ Number(XHR.responseText) +'" />\
			</td>\
			<td>\
				<input type="text" class="M3_A12_m2t4" name="pname" />\
			</td>\
			<td>\
				<input type="text" class="M3_A12_m2t4" name="location" />\
			</td>\
			<td>\
				<input type="text" class="M3_A12_m2t4" list="C3_RBS" name="type" />\
			</td>\
			<td>\
				<input type="text" class="M3_A12_m2t6" name="sequence" />\
			</td>\
			<td>\
				<input type="number" class="M3_A12_m2t7" name="plength" />\
			</td>\
			<td>\
				<input type="text" class="M3_A12_m2t4" name="bname" />\
			</td>\
			<td>\
				<input type="number" class="M3_A12_m2t4" name="blength" />\
			</td>\
			<td>\
				<input type="button" class="M3_A12_m2t5" name="hua" />\
			</td>\
			<td>\
				<input type="button" class="M3_A12_m2t5" name="conservation" />\
			</td>\
		</tr>');
		$("#plasmidData").append('<option id="optionData'+Number(XHR.responseText)+'" value="0 ('+Number(XHR.responseText)+')" />');	
		$("#M3_A12_m2n1").find("tr").last().find(".M3_A12_m2t4").blur(E16_Save);
		$("#M3_A12_m2n1").find("tr").last().find(".M3_A12_m2t5").click(E16_click);
		$("#M3_A12_m2n1").find("tr").last().find(".M3_A12_m2t6").blur(E16_count);
		$("#M3_A12_m2n1").find("tr").last().find(".M3_A12_m2t7").blur(E16_Save);
		$("#M3_A12_m2n1").find("tr").last().find(".M3_A12_m2t5").each(function(){this.save=E16_Save});
		$("#M3_A12_m2n1").find("tr").last().find(".M3_A12_m2t6").each(function(){this.save=E16_Save});
		$("#M3_A12_m2n1").find("tr").last().find(".M3_A12_m2t7").each(function(){this.save=E16_Save});
}
function E16_Save(){
	var content=this.value;
	content=content.replace(/&/g, "#@$;");	
	content=content.replace(/=/g, "%^*;");	
	XHR.onreadystatechange=state_Change;
	XHR.open("post","plasmidSave.php",true);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	XHR.send("pname="+pname+"&value="+content+"&id="+$(this.parentNode.parentNode).find("input").get(0).name+"&name="+this.name);
	if(this.name=="pname")
	{
		$("#optionData"+$(this.parentNode.parentNode).find("input").get(0).name).val(this.value+" ("+$(this.parentNode.parentNode).find("input").get(0).name+")");
	}
}
function E16_delete(){
	var count= $(".M3_A12_Box").size();

	for(var i=0;i<count;i++)
	{
		if( $(".M3_A12_Box").get(i).checked==true )
		{
			E16_delsend( $(".M3_A12_Box").get(i).name );
			$("#optionData"+$(".M3_A12_Box").get(i).name).remove();
			$(".M3_A12_Box").get(i).checked=false;
			$(".M3_A12_Box").get(i).name=0;
			$(".M3_A12_Box").eq(i).parent().parent().hide();
		}
	}
}
function E16_delsend(id){
	XHR.onreadystatechange=state_Change;
	XHR.open("post","plasmidDel.php",false);
	XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");	
	XHR.send("pname="+pname+"&id="+id);		
}
function E16_click(){
	var time = 	new Date();
	var day= 	time.getDate();
	var month=	time.getMonth() + 1;
	var year=	time.getFullYear();		
	var hour=	time.getHours();
	var minute=	time.getMinutes();
	var second=	time.getSeconds();	
	if(hour<10)
	{
		hour="0"+hour.toString();
	}
	if(minute<10)
	{
		minute="0"+minute.toString();
	}
	if(second<10)
	{
		second="0"+second.toString();
	} 
	this.value = year+"/"+month+"/"+day+" "+hour+":"+minute+":"+second;	
	this.save();
}
function E16_count(){
	$(this).parents("tr").find(".M3_A12_m2t7").eq(0).val( Number(this.value.length) );
	$(this).parents("tr").find(".M3_A12_m2t7").get(0).save();
	this.save();
}
function E17_show(){

	
	if(emark!=-1)
	{
		var str = $("#eb"+emark).html();
		$("#data"+emark).children("th").eq(2).html(str);
		
		$("#data"+emark).children("th").eq(2).find("input").each(function(index,element){
			 if($(this).attr("type")=="file" ||  $(this).attr("type")=="submit")
			 {
				this.disabled="disabled";
			 }
			 else
			  if( $(this).parents("table").eq(0).attr("class")!="C1" && $(this).attr("type")!="button"  &&  $(this).attr("type")!= "hidden")
			  {
				var p = $("#eb"+emark).find("input").eq(index).val();
				var c = $("#eb"+emark).find("input").eq(index).attr("class");
				$(this).parent().html("<input value='"+p+"' class='"+c+"'>");
			  }
		});
		str = $("#data"+emark).children("th").eq(2).html();
		str = str.replace(/</g, "&lt;");
		$("#data"+emark).children("th").eq(2).html("<textarea  class='M3_A13_textarea'>"+str+"</textarea>");
	}
	$("#M4_M8").show();
	$("#M3_A13").fadeToggle();	
}
function E17_hide(){
	$("#M4_M8").hide();
	$("#M3_A13").fadeToggle();
}
function M3_A14_show(){
	//显示幕布
	$("#M4").show();
	//显示弹框
	$("#M3_A14").show();		
}
function M3_A14_freeze_ok(){
	var i= emark;
	var j= $("#ev"+i).get(0).laststep;
	

		
			//获取记录项数inum
			experiment[i].step[j].froze="1";
			var it= $("#sb"+i+"s"+j).next().find(".M1_exp_item_time");
			var inum= it.size();
			//获取记录项
			for(var x=0; x<inum; x++)
			{
				var kindex= it.get(x).index;
				//获属性
				var format= experiment[i].step[j].item[kindex];
				//获内容
				format= Number( format );
				M3_A14_fr(format,i,j,kindex);
				
				$("#ib"+i+"s"+j+"i"+kindex).unbind("click");	
			}
			//Q3单击选中步骤
			$("#sb"+i+"s"+j).unbind("click");	
			//Q4双击编辑步骤名
			$("#sb"+i+"s"+j).children(".M1_exp_step_name_button").eq(0).unbind("dblclick");		



			
		
			
	Q1();
			$("#sb"+i+"s"+j).get(0).style.backgroundColor= color9;
			$("#sb"+i+"s"+j).children(".M1_exp_step_name_button").get(0).style.backgroundColor= color9;
			$("#sb"+i+"s"+j).find(".M1_exp_step_name").get(0).style.backgroundColor= color9;		
	M3_A14_hide();
}
function M3_A14_fr(format,i,j,kindex){
				switch(format)
				{
					case  0:
							$("#i"+i+"s"+j+"i"+kindex).get(0).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex).get(0).style.backgroundColor=color10;
							break;
					case  1:
							$("#i"+i+"s"+j+"i"+kindex+"1").get(0).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex+"2").get(0).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex+"6").get(0).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex+"1").get(0).style.backgroundColor=color10;
							$("#i"+i+"s"+j+"i"+kindex+"2").get(0).style.backgroundColor=color10;
							$("#i"+i+"s"+j+"i"+kindex+"6").get(0).style.backgroundColor=color10;
							break;
					case  2:
							var tdNum= document.getElementById("i"+i+"s"+j+"i"+kindex).num;
							for(var z=0; z<tdNum; z++)
							{
								$("#i"+i+"s"+j+"i"+kindex).find(".C3_input").get(z).disabled="disabled";
								$("#i"+i+"s"+j+"i"+kindex).find(".C3_input").get(z).style.backgroundColor=color10;
							}
							break;
					case 3:
							for(q=0;q<3;q++)
							{
								for(var x=0;x<11;x++)
								{
									$("#i"+i+"s"+j+"i"+kindex+"q"+q+x).get(0).disabled="disabled";
									$("#i"+i+"s"+j+"i"+kindex+"q"+q+x).get(0).style.backgroundColor=color10;
								}
							}
							break;
					case 4:
							for(var x=0;x<12;x++)
							{
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).disabled="disabled";
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).style.backgroundColor=color10;
							}
							break;
					case 5:
							for(var x=0;x<29;x++)
							{
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).disabled="disabled";
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).style.backgroundColor=color10;
							}
							$("#i"+i+"s"+j+"i"+kindex+"31").get(0).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex+"31").get(0).style.backgroundColor=color10;	
							break;		
					case 6:
							for(var x=0;x<11;x++)
							{
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).disabled="disabled";
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).style.backgroundColor=color10;
							}
							$("#i"+i+"s"+j+"i"+kindex).find(".C6_button").get(0).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex).find(".C6_button").get(1).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex).find(".C6_button").get(0).style.backgroundColor=color10;
							$("#i"+i+"s"+j+"i"+kindex).find(".C6_button").get(1).style.backgroundColor=color10;						
							break;	
					case 7:
							for(var x=0;x<26;x++)
							{
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).disabled="disabled";
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).style.backgroundColor=color10;	
							}
							break;			
					case 8:
							for(q=0;q<3;q++)
							{
								x=0;
								while(x<7)
								{
									$("#i"+i+"s"+j+"i"+kindex+"q"+q+x).get(0).disabled="disabled";
									$("#i"+i+"s"+j+"i"+kindex+"q"+q+x).get(0).style.backgroundColor=color10;	
									x++;
								}
							}
							$("#i"+i+"s"+j+"i"+kindex).find(".C8_button").get(0).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex).find(".C8_button").get(1).disabled="disabled";
							$("#i"+i+"s"+j+"i"+kindex).find(".C8_button").get(0).style.backgroundColor=color10;	
							$("#i"+i+"s"+j+"i"+kindex).find(".C8_button").get(1).style.backgroundColor=color10;							
							break;	
					case 9:
							for(var x=0;x<15;x++)
							{
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).disabled="disabled";
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).style.backgroundColor=color10;	
							}
							break;			
					case 10:
							for(var x=0;x<13;x++)
							{
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).disabled="disabled";
								$("#i"+i+"s"+j+"i"+kindex+x).get(0).style.backgroundColor=color10;	
							}
							break;		
				}		
}
function M3_A14_hide(){
	//显示幕布
	$("#M4").hide();
	//显示弹框
	$("#M3_A14").hide();
}
function E18_show(){
	$("#M4_M9").show();
	$("#M3_A15").fadeToggle();	
}
function E18_hide(){
	$("#M4_M9").hide();
	$("#M3_A15").fadeToggle();
}
function M3_A15_cal1(){
	if( $("#M3_A15_a").val() && $("#M3_A15_d").val())
	{
		$("#M3_A15_b").val( $("#M3_A15_d").val() / $("#M3_A15_a").val() );
		$("#M3_A15_c").val( $("#M3_A15_d").val() - $("#M3_A15_b").val() );
	}
}
function M3_A15_cal2(){
	$("#M3_A15_td_get3").html("C1 (mol/L)");
	$("#M3_A15_td_get1").html("C2 (mol/L)");	
	$("#M3_A15_td_get2").html("V2 (L)");
	$("#M3_A15_td_get4").html("V3 (L)");
	

}
function M3_A15_cal3(){
	$("#M3_A15_td_get3").html("V3 (L)");
	$("#M3_A15_td_get2").html("C2 (mol/L)");	
	$("#M3_A15_td_get1").html("V2 (L)");
	$("#M3_A15_td_get4").html("C1 ");
}
function M3_A15_cal4(){
	$("#M3_A15_td_get1").html("C1 (mol/L)");
	$("#M3_A15_td_get3").html("C2 (mol/L)");	
	$("#M3_A15_td_get2").html("V3 (L)");
	$("#M3_A15_td_get4").html("V2 (L)");
}
function M3_A15_cal5(){
	$("#M3_A15_td_get1").html("C1 (mol/L)");
	$("#M3_A15_td_get2").html("V3 (L)");	
	$("#M3_A15_td_get3").html("V2 (L))");
	$("#M3_A15_td_get4").html("C2 ");
}
function M3_A15_cal6(){
	var a = $("#M3_A15_td_get1").next().children(".M3_A15_input").eq(0).val();	
	var b = $("#M3_A15_td_get2").next().children(".M3_A15_input").eq(0).val();
	var c = $("#M3_A15_td_get3").next().children(".M3_A15_input").eq(0).val();

	if(M3_A15_mark==1)
	{
		$("#M3_A15_td_get4").next().children(".M3_A15_input").eq(0).val( (a * b) / c );	
	}
	else
	{
		var t=$("#M3_A15_mass").val();
		$("#M3_A15_td_get4").next().children(".M3_A15_input").eq(0).val( t * (a * b) / c );	
	}
}
function M3_A15_cal7(){
	if(M3_A15_mark==0)
	{
		M3_A15_mark=1;
		$("#M3_A15_buttonxx").html("mol/L");
		var h = $("#M3_A15_td_get4").next().children(".M3_A15_input").eq(0).val();
		var t=$("#M3_A15_mass").val();
		$("#M3_A15_td_get4").next().children(".M3_A15_input").eq(0).val( h / t)
	}
	else
	{
		M3_A15_mark=0;
		$("#M3_A15_buttonxx").html("g/L");
		var h = $("#M3_A15_td_get4").next().children(".M3_A15_input").eq(0).val();
		var t=$("#M3_A15_mass").val();
		$("#M3_A15_td_get4").next().children(".M3_A15_input").eq(0).val( h * t);		
	}
}