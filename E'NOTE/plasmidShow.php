<?php
	//需要 $_POST["pname"] 
	include 'data.php';	
	$search= "SELECT * FROM ".$_POST["pname"]."plasmid";
	$search= mysql_query($search,$con);	
	$content='';					
	$plasmid='<tr id="plasmidData_tr"><td><datalist id="plasmidData">';
	while( $get = mysql_fetch_array($search) )
	{
		$content=$content.'
		</br>
		<tr>
			<td>
				<input type="checkbox" name="'.$get["id"].'" class="M3_A12_Box" />
			</td>
			<td>
				<input type="text" class="M3_A12_m2t4"  name="id"  value="'.$get["id"].'" disabled="disabled"/>
			</td>			
			<td>
				<input type="text" class="M3_A12_m2t4"  name="pname"  value="'.$get["pname"].'" />
			</td>
			<td>
				<input type="text" class="M3_A12_m2t4"  name="location"  value="'.$get["location"].'" />
			</td>
			<td>
				<input type="text" class="M3_A12_m2t4" list="C3_RBS"  name="type"  value="'.$get["type"].'" />
			</td>
			<td>
				<input type="text" class="M3_A12_m2t6"  name="sequence"  value="'.$get["sequence"].'" />
			</td>
			<td>
				<input type="number" class="M3_A12_m2t7"  name="plength"  value="'.$get["plength"].'" />
			</td>
			<td>
				<input type="text" class="M3_A12_m2t4"  name="bname"  value="'.$get["bname"].'" />
			</td>
			<td>
				<input type="number" class="M3_A12_m2t4"  name="blength"  value="'.$get["blength"].'" />
			</td>
			<td>
				<input type="button" class="M3_A12_m2t5"  name="hua"  value="'.$get["hua"].'" />
			</td>
			<td>
				<input type="button" class="M3_A12_m2t5"  name="conservation"  value="'.$get["conservation"].'" />
			</td>
		</tr>';
		$plasmid=$plasmid.'<option id="optionData'.$get["id"].'" value="'.$get["pname"].' ('.$get["id"].')" />';// 
	}
	$plasmid=$plasmid.'</datalist></td></tr>';
	$content=$content.$plasmid;
	echo $content;
mysql_close($con);	
?>	