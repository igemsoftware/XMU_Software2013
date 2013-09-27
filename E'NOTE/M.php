<?php
		include 'data.php';
		$team= mysql_query("SELECT id FROM teams where name = '".$_POST["L1_T2_T"]."'");
		$teamid=mysql_fetch_array($team);
		$person= mysql_query("SELECT id,email,realname,telephone,address FROM t".$teamid["id"]." where name = '".$_POST["L1_T2_U"]."' AND code = '".$_POST["L1_T2_C"]."'");
		$user= mysql_fetch_array($person);
		mysql_close($con);	    
?>
<!DOCTYPE HTML>
<html>
	<head>
		<title>welcome to E'Note</title>
		<meta charset="UTF-8" /> 
		<meta name="keywords" content="iGEM, XMU, XMU_software, web, HTML5" />
		<meta name="description" content="design for synthetic biology " />
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.0/jquery.min.js"></script>		
		<script src="M.js"></script>	
		<script>
				user= new Object();
				user.team= '<?php echo $_POST["L1_T2_T"] ?>';
				user.name= '<?php echo $_POST["L1_T2_U"] ?>';
				user.code= '<?php echo $_POST["L1_T2_C"] ?>';
				user.id= '<?php echo $user["id"]?>';
				user.mail= '<?php echo  $user["email"]?>';
				user.rname= '<?php echo $user["realname"]?>';
				user.phone= '<?php echo $user["telephone"]?>';
				user.address= '<?php echo $user["address"]?>';
				pname= "t"+'<?php echo $teamid["id"]?>';
                webPath = 'http://trysomething-block.stor.sinaapp.com/';
                paths = webPath + pname;	
                P= paths+".xml";        
		</script>		
		<link 	href="M.css"  rel="stylesheet" type="text/css" />			
	</head>	
	<body onload="initial();" id="M">	
<!--M1记录板-->   

			<button id="M1_name_button">
				<input type="text" id="M1_name" disabled="disabled" />
			</button>
			<div id="M1_board_e"  class="M1_board">
			<button title="save the experiment" class="M1_board_button_e"  onclick="save(1);"><img src="tool_icon/expSave.png"/></button>
					<button title="add a step in the end" class="M1_board_button_e"  onclick="stepAdd();"><img src="tool_icon/stepAdd.png"/></button>						
					<button title="show the tool board" class="M1_board_button_e" onclick="M8_show_hide();"><img src="tool_icon/tool.png" /></button>
					<button title="delete the experiment" class="M1_board_button_e"  onclick="M3_A3_delete_show();"><img src="tool_icon/delete.png"/></button>
			</div>		
			<div id="M1_board_s"  class="M1_board">
					<button title="save the experiment" class="M1_board_button_s"  onclick="save(1);"><img src="tool_icon/expSave.png"/></button>
					<button title="add a text in the head" class="M1_board_button_s"   onclick="CGet( 0 );" ><img src="tool_icon/textAdd.png"/></button>	
					<button title="add a form in the head" class="M1_board_button_s" onclick="M3_A2_form_show();"><img src="tool_icon/formAdd.png" /></button>	
					<button title="add a graph in the head" class="M1_board_button_s" onclick="CGet( 1 );" ><img src="tool_icon/graphAdd.png" /></button>				
					<button title="add a model from library in the head" class="M1_board_button_s" onclick="modelAdd();"><img src="tool_icon/modelAdd.png" /></button>										
					<button title="show the tool board" class="M1_board_button_s"  onclick="M8_show_hide();"><img src="tool_icon/tool.png"/></button>
					<button title="freeze the step" class="M1_board_button_s"  onclick="M3_A14_show();"><img src="tool_icon/freeze.png"/></button>
					<button title="delete the step" class="M1_board_button_s" onclick="M3_A3_delete_show();"><img src="tool_icon/delete.png" /></button>
			</div>	
			<div id="M1_board_i"  class="M1_board">
					<button title="save the experiment" class="M1_board_button_i" onclick="save(1);"><img src="tool_icon/expSave.png" /></button>
					<button title="add a text in the next" class="M1_board_button_i"  onclick="CGet( 0 );" ><img src="tool_icon/textAdd.png"/></button>						
					<button title="add a form in the next" class="M1_board_button_i" onclick="M3_A2_form_show();"><img src="tool_icon/formAdd.png" /></button>	
					<button title="add a graph in the next" class="M1_board_button_i" onclick="CGet( 1 );"><img src="tool_icon/graphAdd.png"  /></button>	
					<button title="add a model in the next" class="M1_board_button_i" onclick="modelAdd();"><img src="tool_icon/modelAdd.png" /></button>
					<button title="show the tool board" class="M1_board_button_i" onclick="M8_show_hide();"><img src="tool_icon/tool.png" /></button>
					<button title="delete the item" class="M1_board_button_i" onclick="M3_A3_delete_show();"><img src="tool_icon/delete.png" /></button>
			</div>			
<!--M2标题条-->         
			<div id="M2">
					<a id="M2_logo" href="http://2012.igem.org/Team:XMU-China" target="_blank" >
						<img src="tool_icon/logo.png" title="Go to XMU-software's wiki" />
					</a>
					<!--<input id="M2_search" type="text" />-->
					<!--<input id="M2_search_button" type="button" value="search" />-->
					<button id="M2_accound" onclick="M2_manager_show();" title="some help">
					<!--用户信息框-->
						<table>
							<tr>
								<td rowspan="2">
									<img src="tool_icon/per.png"/>
								</td>
								<td id="M2_accound_team">
									<?php echo $_POST["L1_T2_T"]; ?>
								</td>
							</tr>
							<tr>
								 <td id="M2_accound_man">
									<?php echo $_POST["L1_T2_U"]; ?>
								 </td>
							</tr>
						</table>
					</button>		
			</div>		
			<button id="M2_button1" class="M2_button" onClick="M3_A6_show();">change information</button>
			<button id="M2_button2" class="M2_button" onClick="M3_A7_show();">change password</button>
			<button id="M2_button3" class="M2_button" >auto save : <span id="M2_autoSave">on</span></button>
			<button id="M2_button4" class="M2_button" onClick="E15_show();">about us</button>
			<button id="M2_button5" class="M2_button" onClick="M3_A8_show();">feedback</button>		
			<button id="M2_button6" class="M2_button" onClick="E13_show();">help</button>					
<!--M3弹框--> 
			<div id="M3_A2">
			<!--添加表格-z4-->
				<div class="M3_TITLE">Add Table</div>
				<div class="M3_font">Please set the row and col</div>
				<div class="M3_font">Row			<input id="M3_A2_row" type="number"> </div>
				<div class="M3_font">Col&nbsp;	<input id="M3_A2_col" type="number"> </div>
				<div id="M3_A2_m9" class="M3_font"></div>
				<div>
					<button class="M3_OK" onClick="M3_A2_form_add();">		<img src="tool_icon/ok.png" 	class="M3_OK2" width="98px" height="33px;">	</button>
					<button class="M3_OK" onClick="M3_A2_form_cancel();">	<img src="tool_icon/cancel.png" class="M3_OK2" width="98px" height="33px;">	</button>
				</div>
			</div>
			<div id="M3_A4">
			<!--添加实验-z4-->
				<div class="M3_TITLE">Add Experiment</div>
				<div>	<input type="text" id="M3_A4_m2" placeholder="Experiment Name">	</div>
				<div>	<input type="text" id="M3_A4_m3" placeholder="Introduce">		</div>
				<div class="M3_warn"></div>
				<div>
					<button class="M3_OK" onClick="M3_A4_exp_add()">		<img src="tool_icon/ok.png"     class="M3_OK2" width="98px" height="33px;">		</button>
					<button class="M3_OK" onClick="M3_A4_exp_cancel();">	<img src="tool_icon/cancel.png" class="M3_OK2" width="98px" height="33px;">	 	</button>
				</div>
			</div>
			<div id="M3_A3">
			<!--删除实验-->
				<div id="M3_A3_m1">	<img src="tool_icon/warn.png" width="40px" height="40px">	</div>
				<div id="M3_A3_m2">Do you want to <span class="M3_warn">DELETE</span> it?</div>
				<div>
					<button class="M3_OK" onClick="M3_A3_delete_ok()"> 	<img src="tool_icon/ok.png" class="M3_OK2" width="98px" height="33px;">	 </button>
					<button class="M3_OK" onClick="M3_A3_delete_cancel();">	<img src="tool_icon/cancel.png" class="M3_OK2" width="98px" height="33px;"> </button>
				</div>
			</div>					
			<div id="M3_A5">
		<!--添加模板-->
			<div id="M3_A5_l">Add Formwork</div>
			<button id="M3_A5_i" onclick="modelCancel();">exit</button>
			<div id="M3_A5_m">
				<div id="M3_A5_m1">
					<button class="M3_A5_m1s" onclick="CP(0);">ligate plasmids</button>
					<button class="M3_A5_m1s" onclick="CP(1);">PCR			</button>
				</div>		
				<div class="M3_A5_m2">
					<button class="M3_A5_m2s" title="choose plasmids" onclick="CGet(8);" >Choose Plasmids</button>
					<button class="M3_A5_m2s" title="Cultivation bacterial" onclick="CGet(3);" >Cultivation</button>
					<button class="M3_A5_m2s" title="Analysis plasmid DNA purity and concentration" onclick="CGet(4);">Plasmid Extraction</button>
					<button class="M3_A5_m2s" title="Enzyme digestion system" onclick="CGet(5);">Digestion</button>
					<button class="M3_A5_m2s" title="recover" onclick="CGet(6);">Gel Extraction</button>
					<button class="M3_A5_m2s" title="ligation" onclick="CGet(7);">Ligation</button>
				</div>
				<div class="M3_A5_m2">
					<button class="M3_A5_m2s" title="Set primer information" onclick="CGet(9);">Template and Primers</button>	
					<button class="M3_A5_m2s" title="Primer sequence" onclick="CGet(10);">PCR-Reaction</button>
				</div>			
			</div>	
		</div>
			<div id="M3_A1">
			<!--邮件提醒-->
				<form id="M3_A1_mail" action="mail.php" method="post" target="M7"></form>
				<div  class="M3_TITLE">E-mail Reminder</div>    	
					<input name="receiver" type="text" id="M3_A1_m2" placeholder="Email address(such as:408902575@qq.com)" form="M3_A1_mail" /><br/>
					<input name="sendMan" type="text" id="M3_A1_m3" placeholder="Your name" form="M3_A1_mail" /><br/>
					<input name="content" type="text" id="M3_A1_m4" placeholder="content" form="M3_A1_mail" /><br/>
				<div class="M3_font">
					Send E-mail after
					<input name="time" type="number" id="M3_A1_m5" form="M3_A1_mail" />minute
				</div>
				<div>
					<button class="M3_OK" onClick="M3_A1_mail_submit()"  > <img src="tool_icon/ok.png" 	 class="M3_OK2" 	width="98px" height="33px;"></button>
					<button class="M3_OK" onClick="M3_A1_mail_hide();" > <img src="tool_icon/cancel.png" class="M3_OK2" 	width="98px" height="33px;"></button>
				</div>
			</div>		
			<div id="M3_A6">
			<!--个人信息设置-->
				<div class="M3_TITLE">Personal Information</div>
				<div id="M3_A6_m2">
					<table>
						<tr>
							<td class="M3_font">User Name</td>
							<td><input type="text" class="M3_input" /></td>
						</tr>					
						<tr>
							<td class="M3_font">Real Name</td>
							<td><input type="text" class="M3_input" /></td>
						</tr>
						<tr>
							<td class="M3_font">Address</td>
							<td><input type="text" class="M3_input" /></td>
						</tr>
						<tr>
							<td class="M3_font">Telephone</td>
							<td><input type="text" class="M3_input" /></td>
						</tr>
						<tr>
							<td class="M3_font">E-mail</td>
							<td><input type="text" class="M3_input" /></td>
						</tr>
					</table>
				</div>			
				<div>
					<button class="M3_OK" onClick="M3_A6_done()"><img class="M3_OK2" src="tool_icon/ok.png"  width="98px" height="33px;"></button>
					<button class="M3_OK" onClick="M3_A6_hide();"><img class="M3_OK2" src="tool_icon/cancel.png"  width="98px" height="33px;"></button>
				</div>
			</div>			
			<div id="M3_A7">
			<!--密码安全设置-->
				<div class="M3_TITLE">Change Password</div>
				<div id="M3_A7_m2">
					<table>
						<tr>
							<td class="M3_font">Old Password</td>
							<td><input type="text" class="M3_input" /></td>
						</tr>
						<tr>
							<td class="M3_font">New Password</td>
							<td><input type="text" class="M3_input" /></td>
						</tr>
						<tr>
							<td class="M3_font">New Password</td>
							<td><input type="text" class="M3_input" /></td>
						</tr>
					</table>
				</div>
				<div>
					<button class="M3_OK" onClick="M3_A7_done()"><img class="M3_OK2" src="tool_icon/ok.png"  width="98px" height="33px;"></button>
					<button class="M3_OK" onClick="M3_A7_hide();"><img class="M3_OK2" src="tool_icon/cancel.png"  width="98px" height="33px;"></button>
				</div>
			</div>			
			<div id="M3_A8">
			<!--反馈-->
				<div class="M3_TITLE">Thank you for your feedback</div>
				<textarea id="M3_A8_feedback"></textarea>
				<div>
					<button class="M3_OK" onClick="M3_A8_done()"><img class="M3_OK2" src="tool_icon/ok.png"  width="98px" height="33px;"></button>
					<button class="M3_OK" onClick="M3_A8_hide();"><img class="M3_OK2" src="tool_icon/cancel.png"  width="98px" height="33px;"></button>
				</div>				
			</div>	
			<div id="M3_A9">
			<!--使用说明-->
				<div id="M3_A9_m1">E’Note Instructions</div>
				<!--副标题-->
				<div id="M3_A9_m2">—— iGEM XMU-Software 2013</div>
				<!--段落1-->
				<div id="M3_A9_m3">
					<!--小标题-->
					<div class="M3_A9_h1">&nbsp&nbsp1.Website link: <a href="http://1.blocknote.sinaapp.com" target="_blank">http://1.blocknote.sinaapp.com</a></div>
					<!--段落文字-->
					<div class="M3_A9_t1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspYou can login in or sign in. If you do not have an account yet, go to register firstly. To register a person user account, both the team name and the team code are required.</div>
					<!--照片-->
					<div class="M3_A9_p1"><img src="tool_icon/intro1.jpg" width="700px" height="300px"></div>
				</div>
				<!--段落2-->
				<div id="M3_A9_m4">
					<div class="M3_A9_h1">&nbsp&nbsp2.Add experiments</div>
					<div class="M3_A9_t1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Frist, click the “+”button. Second, input your experiment’s name and introduction on the tedit.</div>
					<div class="M3_A9_p1"><img src="tool_icon/intro2.jpg" width="700px" height="300px"></div>
				</div>
				<!--段落3-->
				<div id="M3_A9_m5">
					<div class="M3_A9_h1">&nbsp&nbsp3.Add formwork</div>
					<div class="M3_A9_t1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp First, click your experiment’s name, then click “+”button on the right. </div>
					<div class="M3_A9_p1"><img src="tool_icon/intro3.jpg" width="700px" height="300px"></div>
					<div class="M3_A9_t1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Second, select the step column and modify the step name, then choose the function on the right.</div>
					<div class="M3_A9_p1"><img src="tool_icon/intro4.jpg" width="700px" height="500px"></div>
				</div>
				<!--段落4-->
				<div id="M3_A9_m6">
					<div class="M3_A9_h1">&nbsp&nbsp4.Information bank</div>
					<div class="M3_A9_t1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Click photo column and six edit option will pop out.</div>
					<div class="M3_A9_p1"><img src="tool_icon/intro5.jpg" width="700px" height="300px"></div>
				</div>
				<!--段落5-->
				<div id="M3_A9_m7">
					<div class="M3_A9_h1">&nbsp&nbsp5.Other functions</div>
					<div class="M3_A9_t1">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Shortcut:“Alt+q”:edit experiment name; “Alt+w”:open tool; “Alt+s”:save experiments</br>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Browser:Firefox, Opera, Chrome</div>
				</div></br></br></br></br>
			</div>
			<div id="M3_A10">
			<!--集成软件-->
				<div id="M3_A10_m1">
					<div class="M3_A10_t1">The Tool from iGEM Software</div>
					<!--一款软件-->
					<div class="M3_A10_m1n1">
						<!--软件名称-->
						<div class="M3_A10_m1p1">SYNBIO SEARCH</br><a href="http://www.synbiosearch.org/" target="_blank"><img src="tool_icon/pho1.jpg" width="240px" height="168px"></a></div>
						<div class="M3_A10_m1r1">
							<!--标题-->

							<!--介绍-->
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspSynBio Search is an online tool that generates data sheets for over 2700 biological parts by aggregating data from various publicly available resources. It integrates and links information from various data sources, including the Registry of Standard Biological Parts, the iGEM Archive, Google Scholar, andPubMed. SynBio Search builds on the collected sources by providing a structured view that relates heterogeneous information, links back to original data sources, and allows users to customize and organize the display. It enables researchers to discover the most comprehensive view of freely available data about biological parts from a single online search. SynBio Search allows users to search by keyword (e.g. qiagen) or by part name.</span></br></br>
							<!--出处-->
							<span class="M3_A10_p3">From:<a href="http://2012.igem.org/Team:Wellesley_HCI" target="_blank">2012 Wellesley HCI</a></span>
						</div>
					</div>
				</div>
				
				<div id="M3_A10_m2">
					<div class="M3_A10_t1">The Tool from Internet</div>
					<!--Double Digest Finder-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">Double Digest Finder</br><a href="https://www.neb.com/tools-and-resources/interactive-tools/double-digest-finder" target="_blank"><img src="tool_icon/pho2.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
						
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspUse this tool to guide your reaction buffer selection when setting up double-digests, a common timesaving procedure. Choosing the right buffers will help you to avoid star activity and loss of product.</span></br></br>
							<span class="M3_A10_p3">From:<a href="https://www.neb.com/tools-and-resources/interactive-tools/double-digest-finder" target="_blank">BioLabs:Double Digest Finder</a></span>
						</div>
					</div>
					<!--Enzyme Finder-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">Enzyme Finder</br><a href="https://www.neb.com/tools-and-resources/interactive-tools/enzyme-finder" target="_blank"><img src="tool_icon/pho3.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
						
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspUse this tool to select restriction enzymes by name, sequence, overhang or type. Enter your sequence using single letter code nomenclature, and Enzyme Finder will identify the right enzyme for the job.</span></br></br>
							<span class="M3_A10_p3">From:<a href="https://www.neb.com/tools-and-resources/interactive-tools/enzyme-finder" target="_blank">BioLabs:Enzyme Finder</a></span>
						</div>
					</div>
					<!--NEBcutter-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">NEBcutter</br><a href="http://tools.neb.com/NEBcutter2/index.php" target="_blank"><img src="tool_icon/pho4.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
						
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspUse this tool to identify the restriction sites within your DNA sequence. Choose between Type II and commercially available Type III restriction enzymes to digest your DNA. NEBcutter® V2.0 will indicate cut frequency and methylation state sensitivity.</span></br>
							<span class="M3_A10_p3">From:<a href="http://tools.neb.com/NEBcutter2/index.php" target="_blank">BioLabs:NEBcutter</a></span>
						</div>
					</div>
					<!--REBASE-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">REBASE</br><a href="https://www.neb.com/tools-and-resources/interactive-tools/double-digest-finder" target="_blank"><img src="tool_icon/pho5.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
						
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspUse this tool as a guide to the ever-changing landscape of restriction enzymes. REBASE, the Restriction Enzyme DataBASE, is a dynamic, curated database of restriction enzymes and related proteins.</span></br></br>
							<span class="M3_A10_p3">From:<a href="https://www.neb.com/tools-and-resources/interactive-tools/double-digest-finder" target="_blank">BioLabs:REBASE</a></span>
						</div>
					</div>
					<!--PCR Selection Tool-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">PCR Selection Tool</br><a href="https://www.neb.com/tools-and-resources/interactive-tools/pcr-selection-tool" target="_blank"><img src="tool_icon/pho6.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
					
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspUse this tool to help select the right DNA polymerase for your PCR setup. Whether your amplicon is long, complex, GC-rich or present in a single copy, the PCR selection tool will identify the perfect DNA polymerase for your reaction.</span></br>
							<span class="M3_A10_p3">From:<a href="https://www.neb.com/tools-and-resources/interactive-tools/pcr-selection-tool" target="_blank">BioLabs:PCR Selection Tool</a></span>
						</div>
					</div>
					<!--DNA Sequences and Maps Tool-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">DNA Sequences and Maps Tool</br><a href="https://www.neb.com/tools-and-resources/interactive-tools/dna-sequences-and-maps-tool" target="_blank"><img src="tool_icon/pho7.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
						
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspUse this tool to find the nucleotide sequence files for commonly used molecular biology tools, including plasmid, viral and bacteriophage vectors.</span></br></br>
							<span class="M3_A10_p3">From:<a href="https://www.neb.com/tools-and-resources/interactive-tools/dna-sequences-and-maps-tool" target="_blank">BioLabs:DNA Sequences and Maps Tool</a></span>
						</div>
					</div>
					<!--Tm Calculator-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">Tm Calculator</br><a href="https://www.neb.com/tools-and-resources/interactive-tools/tm-calculator" target="_blank"><img src="tool_icon/pho8.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
						
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspUse this tool when designing PCR reaction protocols to help determine the optimal annealing temperature for your amplicon. Simply input your DNA polymerase, primer concentration and your primer sequence and the Tm Calculator will guide you to successful reaction conditions.</span></br>
							<span class="M3_A10_p3">From:<a href="https://www.neb.com/tools-and-resources/interactive-tools/tm-calculator" target="_blank">BioLabs:Tm Calculator</a></span>
						</div>
					</div>
					<!--Polbase-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">Polbase</br><a href="http://polbase.neb.com/" target="_blank"><img src="tool_icon/pho9.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
					
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspPolbase is a repository of biochemical, genetic, and structural information about DNA Polymerases.</span></br></br></br>
							<span class="M3_A10_p3">From:<a href="http://polbase.neb.com/" target="_blank">BioLabs:Polbase</a></span>
						</div>
					</div>
					<!--NEBuilder-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">NEBuilder</br><a href="http://nebuilder.neb.com/#" target="_blank"><img src="tool_icon/pho10.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
						
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspNEBuilder can be used to design primers for you Gibson Assembly reaction, based on the entered fragment sequences and the polymerase being used for amplification.</span></br></br>
							<span class="M3_A10_p3">From:<a href="http://nebuilder.neb.com/#" target="_blank">BioLabs:NEBuilder</a></span>
						</div>
					</div>
					<!--InBase-->
					<div class="M3_A10_m1n1xiu">
						<div class="M3_A10_m1p1">InBase</br><a href="http://tools.neb.com/inbase/" target="_blank"><img src="tool_icon/pho11.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m1r1">
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspInteins are in-frame sequences that disrupt the coding region of a host gene. Unlike introns, inteins are excised post-translationally from a protein precursor by a self-catalytic protein splicing mechanism. InBase describes how inteins work and is home to the "Intein Registry" that includes a list of inteins and their properties, such as prototype intein, domain of life, endonuclease activity or motifs, locations of exteins, insertion site comments, discoverers (with contact information) and references.</span></br>
							<span class="M3_A10_p3">From:<a href="http://tools.neb.com/inbase/" target="_blank">BioLabs:InBase</a></span>
						</div>
					</div>
					<!--NEBaseChanger-->
					<div class="M3_A10_m2n1">
						<div class="M3_A10_m2p1">NEBaseChanger</br><a href="http://nebasechanger.neb.com/" target="_blank"><img src="tool_icon/pho12.png" width="55px" height="55px"></a></div>
						<div class="M3_A10_m2r1">
						
							<span class="M3_A10_p2">&nbsp&nbsp&nbsp&nbspNEBaseChanger can be used to design primers specific to the mutagenesis experiment you are performing using the Q5® Site-Directed Mutagenesis Kit. This tool will also calculate a recommended custom annealing temperature based on the sequence of the primers by taking into account any mismatches.</span></br>
							<span class="M3_A10_p3">From:<a href="http://nebasechanger.neb.com/" target="_blank">BioLabs:NEBaseChanger</a></span>
						</div>
					</div>
				</div>
			</div>
			<div id="M3_A11">
			<!--关于我们-->
				<div id="M3_A11_m1">About Us</div>
				<div id="M3_A11_m2">
					<table>
						<tr>
							<td>School</td>
							<td><a href="http://www.xmu.edu.cn/" target="_blank">Xiamen University</a></td>
						</tr>
						<tr>
							<td>Team</td>
							<td>XMU-Software</td>
						</tr>
						<tr>
							<td>E-mail</td>
							<td>XMUE-Note@hotmail.com</td>
						</tr>
						<tr>
							<td>Website</td>
							<td>XMU-Software wiki</td>
						</tr>
					</table>
				</div>
				<div id="M3_A11_m3">
					<img src="tool_icon/aboutUs.png" width="450px" height="170px">
				</div>
			</div>			
			<div id="M3_A12">
			<!--质粒库-->
				<div id="M3_A12_m1">
					<span class="M3_A12_m1t1">Plasmid library</span>
					<button class="M3_A12_m1b1" onClick="E16_add();">Add</button>
					<button class="M3_A12_m1b1" onclick="E16_delete();">Delete</button>
				</div>
				<div id="M3_A12_m2">
					<table border="1" id="M3_A12_m2n1">
						<tr>
							<td rowspan="2" class="M3_A12_m2t1"><input type="checkbox" id="M3_A12_all"></td>
							<td rowspan="2" class="M3_A12_m2t2">id</td><!---->							
							<td colspan="2" class="M3_A12_m2t3">Plasmid</td>
							<td rowspan="2" class="M3_A12_m2t2">Type</td>
							<td colspan="2" class="M3_A12_m2t3">Part-only</td>
							<td colspan="2" class="M3_A12_m2t3">Backbone</td>
							<td rowspan="2" class="M3_A12_m2t2"><img src="tool_icon/hua.png">date</td><!---->	
							<td rowspan="2" class="M3_A12_m2t2">Conservation date</td><!---->								
						</tr>	
						<tr>
							<td class="M3_A12_m2t2">Name</td>
							<td class="M3_A12_m2t2">Location</td>
							<td class="M3_A12_m2t2">Sequence</td>
							<td class="M3_A12_m2t2">Length</td>
							<td class="M3_A12_m2t2">Name</td>
							<td class="M3_A12_m2t2">Length</td>
						</tr>
					</table>
				</div>
			</div>
			<div id="M3_A13">
				<table id="M3_A13_table1">
					<tr>
						<th colspan="3" id="M3_A13_Title">File output</th>
					</tr>
					<tr>
						<th id="M3_A13_pat">Experiment name</th>
						<th id="M3_A13_pat">File path</th>
						<th id="M3_A13_pat">source code</th>
					</tr>
				</table>
				<div id="M3_A13_content">
					<table id="M3_A13_table2">					
					</table>	
				</div>	
				<div id="M3_A13_back">
					Please right click the path to download the data file
				</div>
			</div>			
			<div id="M3_A14">
				<div id="M3_A3_m1">	<img src="tool_icon/warn.png" width="40px" height="40px">	</div>
				<div id="M3_A3_m2">Do you want to <span class="M3_warn">FREEZE</span> it?</div>
				<div>
					<button class="M3_OK" onClick="M3_A14_freeze_ok()"> 	<img src="tool_icon/ok.png" class="M3_OK2" width="98px" height="33px;">	 </button>
					<button class="M3_OK" onClick="M3_A14_hide();">	<img src="tool_icon/cancel.png" class="M3_OK2" width="98px" height="33px;"> </button>
				</div>			
			</DIV>
			<div id="M3_A15">
				<div id="M3_A15_title">
					Calculate
				</div>
				<div id="M3_A15_intro">
					In this board we support two kinds of calculated tools
				</div>
				<table id="M3_A15_table1">
					<tr>
						<td id="M3_A15_small_title" colspan="2">
							To dilute the solution of aX
						</td>
					</tr>
					<tr>
						<td class="M3_A15_small_title_x" colspan="2">
							Please enter the prerequisite
						</td>
					</tr>	
					<tr>
						<td class="M3_A15_td_left">
							a =
						</td>
						<td class="M3_A15_td_right">
							<input type="number" class="M3_A15_input" id="M3_A15_a"/>
						</td>					
					</tr>
					<tr>
						<td class="M3_A15_td_left">
							The final volume (mL) =
						</td>
						<td class="M3_A15_td_right">
							<input type="number" class="M3_A15_input" id="M3_A15_d"/>
						</td>					
					</tr>	
					<tr>
						<td class="M3_A15_td_button" colspan="2">
							<button  id="M3_A15_button" onclick="M3_A15_cal1();">Calculate</button>
						</td>
					</tr>				
					<tr>
						<td class="M3_A15_small_title_x" colspan="2">
							Result:
						</td>
					</tr>	
					<tr>
						<td class="M3_A15_td_left">
							Desire the solution of ax
						</td>
						<td class="M3_A15_td_right">
							<input type="number" class="M3_A15_input" id="M3_A15_b"/>
						</td>					
					</tr>	
					<tr>
						<td class="M3_A15_td_left">
							Desire the solvent
						</td>
						<td class="M3_A15_td_right">
							<input type="number" class="M3_A15_input" id="M3_A15_c"/>
						</td>					
					</tr>	
				</table>
				<table id="M3_A15_table2">
					<tr>
						<td id="M3_A15_small_titlex" colspan="4">
							To dilute the stock solution
						</td>
					</tr>				
					<tr>
						<td class="M3_A15_small_title_xx" colspan="4">
							Please enter the prerequisite
						</td>
					</tr>	
					<tr>
						<td class="M3_A15_td_center" colspan="2">
							solute =
						</td>
						<td class="M3_A15_td_center" colspan="2">
							<input type="text" class="M3_A15_input"/>
						</td>					
					</tr>
					<tr>
						<td class="M3_A15_td_center" colspan="2">
							molar mass (g/mol) =
						</td>
						<td class="M3_A15_td_center" colspan="2">
							<input type="text" class="M3_A15_input" id="M3_A15_mass"/>
						</td>					
					</tr>	
					<tr>
						<td id="M3_A15_img_td" colspan="4">
							<img src="tool_icon/calax.png" id="M3_A15_img" />
						</td>
					</tr>
					<tr>
						<td class="M3_A15_small_title_xx" colspan="4">
							What do you want to cipher out
						</td>
					</tr>		
					<tr>
						<td class="M3_A15_td_center_BAN">
							<button class="M3_A15_td_center_button" onclick="M3_A15_cal2();">V3</button>
						</td>
						<td class="M3_A15_td_center_BAN">
							<button class="M3_A15_td_center_button" onclick="M3_A15_cal3();">C1</button>
						</td>					
						<td class="M3_A15_td_center_BAN">
							<button class="M3_A15_td_center_button" onclick="M3_A15_cal4();">V2</button>
						</td>
						<td class="M3_A15_td_center_BAN">
							<button class="M3_A15_td_center_button" onclick="M3_A15_cal5();">C2</button>
						</td>						
					</tr>			
					<tr>
						<td class="M3_A15_small_title_xx" colspan="4">
							Please enter the prerequisite
						</td>
					</tr>		
					<tr>
						<td class="M3_A15_td_center" id="M3_A15_td_get1" colspan="2">
							C2 (mol/L)
						</td>
						<td class="M3_A15_td_center" colspan="2">
							<input type="text" class="M3_A15_input"/>
						</td>					
					</tr>
					<tr>
						<td class="M3_A15_td_center" id="M3_A15_td_get2"  colspan="2">
							V2 (L)
						</td>
						<td class="M3_A15_td_center" colspan="2">
							<input type="text" class="M3_A15_input"/>
						</td>					
					</tr>		
					<tr>
						<td class="M3_A15_td_center" id="M3_A15_td_get3" colspan="2">
							V3 (L)
						</td>
						<td class="M3_A15_td_center" colspan="2">
							<input type="text" class="M3_A15_input"/>
						</td>					
					</tr>	
					<tr>
						<td class="M3_A15_td_button" colspan="4">
							<button  id="M3_A15_buttonx" onclick="M3_A15_cal6();">calculate</button>
						</td>
					</tr>	
					<tr>
						<td class="M3_A15_td_3fen1" id="M3_A15_td_get4" >
							C1 
						</td>
						<td class="M3_A15_td_center" colspan="2">
							<input type="text" class="M3_A15_input"/>
						</td>
						<td class="M3_A15_td_3fen1">
							<button  id="M3_A15_buttonxx" onclick="M3_A15_cal7();">mol/L</button>
						</td>					
					</tr>
				</table>
			</div>
			<!--M4遮屏幕布+操作成功时的显示-->   
			<div id="M4">
			<!--遮屏幕布-z3-->
			</div>
			<div id="M4_M2" onclick="M2_manager_hide();">
			<!--遮屏幕布-z3-->
			</div>					
			<div id="M4_M3" class="M3_warn">
				Success!
			</div>
			<div id="M4_M4" onclick="E13_hide();">
			<!--遮屏幕布-z3-->
			</div>		
			<div id="M4_M5" onclick="E14_hide();">
			<!--遮屏幕布-z3-->
			</div>	
			<div id="M4_M6" onclick="E15_hide();">
			<!--遮屏幕布-z3-->
			</div>				
			<div id="M4_M7" onclick="E16_hide();">
			</div>		
			<div id="M4_M8" onclick="E17_hide();">
			</div>		
			<div id="M4_M9" onclick="E18_hide();">
			</div>	
<!--M5导航板-->      
			<button id="M5_button" onclick="M3_A4_exp_show();" title="add a new experiment">
			<!--添加实验按钮-->
				<img id="M5_button_img" src="tool_icon/expAdd.png" />
			</button>
			<div id="M5_exp">
			<!--添加实验项-->
			</div>
<!--M7共用提交模块-->   
			<iframe id="M7"></iframe>
<!--M8工具栏-->   
			<div id="M8">
				<button class="M8_button" onclick="M3_A1_mail_show();"><img class="M8_img" src="tool_icon/mail.png" /></button><br/>
				<button class="M8_button" onclick="E16_show();"><img class="M8_img" src="tool_icon/dna.png" /></button><br/>
				<button class="M8_button" onclick="E14_show();"><img class="M8_img" src="tool_icon/images.png" /></button>
				<button class="M8_button" onclick="E17_show();"><img class="M8_img" src="tool_icon/dataout.png" /></button>
				<button class="M8_button" onclick="E18_show();"><img class="M8_img" src="tool_icon/cal.png" /></button>
			</div>
<!--M9快捷键-->	
			<button class="M9" accesskey="q" onclick="if(emark!=-1)Q2_1();"><!--编辑实验名--></button>
			<button class="M9" accesskey="a" onclick="M8_show_hide();"><!--打开工具栏--></button>
			<button class="M9" accesskey="s" onclick="if(emark!=-1)save();"><!--保存--></button>
<!--M10混杂-->
			<datalist id="C3_strain">
				<option value="JM109" />
				<option value="DH10B" />
				<option value="DH5a" />
				<option value="TOP10" />
				<option value="BL21" />
			</datalist>
			<datalist id="C3_Medium">
				<option value="LB" />
				<option value="M9" />
			</datalist>				
			<datalist id="C3_RBS">
				<option value="Promoter" />
				<option value="RBS" />
				<option value="Coding sequence" />
				<option value="Terminators" />	
			</datalist>		
		</body>
</html>

