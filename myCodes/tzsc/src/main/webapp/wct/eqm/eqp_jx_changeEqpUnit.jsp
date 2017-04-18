<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<style type="text/css">
/*按钮样式*/
.button_botton {
	width: 200px;
	height: auto;
	font-size: 12px;
	font-weight: bold;
	text-align: center;
	padding-top: 4px;
	margin: 0 auto;
}
td{
text-align: center;
height: 40px
}
.btn-default {
	color: #FFFFFF;
	background-color: #5A5A5A;
	border-color: #cccccc;
}

.input_box {
	display: block;z-index: 99;position: absolute;
		top: 10px;left: 20px;
		/* width:800px; */
		font-size: 12px;
		border: 1px solid #858484;border-radius: 10px;
		height: 420px; 
		background:#A0A0A0;padding: 30px;
		overflow:auto;
}

.input-title-group {
	height: 40px;
	font-size: 20px;
	padding-left: 0%;
}

.input-group {
	margin-bottom: 15px;
}
.input-eqp{
text-align: center;
width: 100%;
height:100%;
}
#username, #password {
	width: 200px;
}

.ui-keyboard-preview {
	width: 92%;
}

.form-control {
	height: 20px;
}
/*密码验证结束*/
.input-group-addon {
	font-size: 14px;
}
</style>

</head>
<!-- hid_repair_div -->



<div id="hid_repair_div"style="display: none; height: 368px; width:97%; top: 40px; left: 20px; position: absolute; z-index: 90;"
	onclick="return false;">
	<div class="input_box">
		<form id="repair-box-wct-frm">
			<div  class="input-title-group" style="margin-top: -15px; margin-left: 0px;">
				<b >检修</b>
			</div><hr style="margin-top: -10px;">
			<div>
				
				<font color="#F0F0F0"><strong>备件名称：</strong></font><input id="remarkName" type="text" name="name"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<font color="#F0F0F0"><strong>备件型号：</strong></font><input id="bjType" type="text" name="type"/>
				<button onclick="showEqp()" style="width: 60px;" class="btn btn-default">查询</button>
				
			</div><br>
			<div id="sparepage">
				<table border="1"  borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 14px;font-weight: 500;" width="100%"  cellspacing="0" cellpadding="0">
				    <thead id="eqpMailPlan-tab-thead" style="background: #5a5a5a;color: #fff;">
						<tr>
							<td>备件名称</td>
							<td>备件型号</td>
							<td>剩余数量</td>
							<td>使用数量</td>
						</tr>
					</thead>
					<tbody id="wct-spare-tbody" style="border: 2px" ></tbody>
				</table>
			</div>
			<div id="eqpMailPlan-page">
			  	共<span id='T1'></span>条
				<button id="up2"  onclick="back()" style="width: 65px;" class="btn btn-default">上一页</button>
    			<span id='T2'></span>/<span id='T3'></span>
    			<button id="down2"  onclick="next()" style="width: 65px;" class="btn btn-default">下一页</button>
			</div>
			<br/>
			<div  class="input-title-group" style="margin-top: 5px;">
				<span><b>手动添加记录</b></span>
				<spn style="font-size:12px;">
				   <span style="color:red;">如果搜索不到，请单击添加按钮，手动添加！</span>
				   <button onclick="saveTrouble();" style="width: 60px;" class="btn btn-default">添加</button>
				</spn>
			</div>
			<hr style="margin-top: -5px;">
			
			<div id="trouble_div">
				<ul id="ulSel">
				    <!-- <li>
				       <font color="#F0F0F0"><strong>备品备件名称：</strong></font><select  id="trouble_name" name="trouble_name" style="width:150px;"/>
				       <font  color="#F0F0F0"><strong id="font">备品备件型号：</strong></font><input id="description" type="text" name="description"/>
				       <font  color="#F0F0F0"><strong id="font">使用数量：</strong></font><input id="useNum" type="text" name="description"/>
				    </li> -->
				</ul>
			</div>
			<br>
			<div class="button_botton">
				<button onclick="saveRepair();" style="height: 35px; width: 80px;"
					class="btn btn-default">确定</button>
				<button onclick="hiddenRepairWin();"
					style="height: 35px; width: 80px;" class="btn btn-default">
					取消</button>
			</div>
			
		</form>
	</div>
</div>
<script type="text/javascript">
	/*查询备件*/
	var pd=1;
	var tls=0;
	//备品备件个数
	var checkValue=new Array();
	//备品备件ID
	var value = new Array();
	//总数量
	var allNum=new Array();
	var spareNmae=new Array();
	var sparetype=new Array();
	var tf=0;
	function checkInput(v){
		v.value=v.value.replace(/[^\d]/g,'0');
	}
	var data_result;
	function showEqp(){
		trouble_name="";
		description="";
		useNum="";
		tf=0;
		$("#ulSel").html("");
		pd=1;
		var name=$("#remarkName").val();
		var type=$("#bjType").val();
		$("#sparepage").css("display","block");
		$("#eqpSpare-page").css("display","block");
		$.post("${pageContext.request.contextPath}/pms/spare/queryData.do",{d:Math.random(),name:name,type:type},function(reobj){
			data_result=reobj.rows;
			tls=data_result.length%3==0?parseInt(data_result.length/3):parseInt(data_result.length/3)+1;
			showTable(data_result,0,3); 
		},"JSON"); 
	} 

	function showTable(list,index,pageCount){
		$("#wct-spare-tbody").html("");
		var html="";
		for(var i=index;i<list.length&&i<pageCount;i++){
			var _num=0;
			if(checkValue[i]!=undefined){
				_num=checkValue[i];
			}
			//（页数 -1）*3是id的开始
		 	html+="<tr name='spEqp' id='tr_"+i+"'>" 
		 	+"  <input class='input-eqp' name='ids' type='hidden' value='"+list[i].id+"' />"
			+"	<td ><input class='input-eqp' name='spareNmae' type='hidden' value='"+list[i].name+"' />"+list[i].name+"</td>"
			+"  <td ><input class='input-eqp' name='sparetype' type='hidden' value='"+list[i].type+"' />"+list[i].type+"</td>"
			+"	<td><input class='input-eqp' style='background: #A0A0A0;border:0px;'  name='nums' type='text' readOnly=true  value='"+list[i].number+"'</input></td>"		
			+"  <td><input class='input-eqp' style='border:0px;' onkeyup='checkInput(this)' name='use_num'  type='text' value='"+_num+"'/></td>"
			+"</tr>";
		}  
		$("#wct-spare-tbody").html(html);
		$('#T1').html(data_result.length);
	 	$('#T2').html(pd);
	 	$('#T3').html(tls);
	}
	 
	 function next(){
	 	if(pd>=tls){
	 		jAlert("已经是最后一页","提示");
	 		return;
	 	} 
	 	if(getValue()){
	 		pd=pd+1;
			showTable(data_result,(pd-1)*3,pd*3); 
	 	}
	 }
	 function back(){
	 	if(pd<=1){
	 		jAlert("已经是第一页","提示");
	 		return;
	 	}
	 	if(getValue()){
	 		pd=pd-1;
	 		showTable(data_result,(pd-1)*3,pd*3); 
	 	}
	 }
	 
	  function getValue(){
		var all_num=getJsonData($('#repair-box-wct-frm')).nums.split(',');
		var use_n=getJsonData($('#repair-box-wct-frm')).use_num.split(',');
		var all_id=getJsonData($('#repair-box-wct-frm')).ids.split(',');
		var spname=getJsonData($('#repair-box-wct-frm')).spareNmae.split(',');
		var sparn=getJsonData($('#repair-box-wct-frm')).sparetype.split(',');
		for(var _i=0;_i<all_num.length;_i++){
			 /* if(parseInt(use_n[_i])!=0){
				flag=1;
			}
			if(parseInt(all_num[_i])<parseInt(use_n[_i])){
				jAlert("备件数量不足,操作失败！","消息提示");
				flag=0;
				return false;
			}else  */
			if(parseInt(use_n[_i])>=0){
				flag=1;
				checkValue[(pd-1)*3+_i]=parseInt(use_n[_i]);
				value[(pd-1)*3+_i]=all_id[_i]; 
				allNum[(pd-1)*3+_i]=parseInt(all_num[_i]);
				spareNmae[(pd-1)*3+_i]=spname[_i];
				sparetype[(pd-1)*3+_i]=sparn[_i];
			}
		} 
		return true;
	 } 
	 
	 //添加一览故障信息
	 
	 function saveTrouble(){
		 var html="";
		 html="<br><span color='#F0F0F0'>名称：</span>"
		 	 +"<span><input type='text' id='trouble_name"+tf+"' name='trouble_name' style='width:100px;'/></span>"
		 	 +"<span style='padding-left:10px;'>型号：</span><span><input id='description"+tf+"' type='text' name='description' style='width:100px;'/></span>"
		 	 +"<span color='#F0F0F0' style='padding-left:10px;'>使用数量：</span><span><input type='text' onkeyup='checkInput(this)' id='useNum"+tf+"' name='trouble_name' style='width:100px;'/></span>";
		 	 $("#ulSel").append(html); 
		 //追加值
		 troubleComb(tf);
		 tf++;
	 }
	 //鼠标放上事件
	 function selectMonse(val,zz){
		 if (val =='--暂无--'){
				 $('#description'+(zz-1)).val("");
			 
			 }
	 }
	 //鼠标离开事件
	 function leaveMonse(val,zz){
		 if (val ==''){
			 $('#description'+(zz-1)).val("--暂无--");
			 }
	 }
	 //故障下拉框
	 function troubleComb(tf){
		  $.post("${pageContext.request.contextPath}/wct/eqm/checkplan/queryTroubleComb.do",function(json){
			 if(json!=null){
				 var dataJson = JSON.parse(json);
				 $("#trouble_name"+tf).append("<option value='0'>--请选择--</option>");
				 $.each(dataJson,function(key,val){
					 $("#trouble_name"+tf).append("<option value='"+val+"'>"+val+"</option>");
				 });
			 }else{
				 jAlert("没有此故障，清手动输入故障","查询提示");
			 }
		 },"JSON");
	 }
	 function saveRepair(){
		//	showEqp();
		$("#showAddFixResult").html("");
			getValue();
			var flag=0;
			var all_num=allNum.join(',');
			var use_n=checkValue.join(',');
			var all_id=value.join(',');
			var chooseid = ${param.id};
			var isPass=false;
			for(var z=0;z<tf;z++){
				if($("#trouble_name"+z).val()==null||$("#trouble_name"+z).val()==""||$("#description"+z).val()==null||$("#description"+z).val()==""||$("#useNum"+z).val()==null||$("#useNum"+z).val()==""){
					isPass=false;
					trouble_name="";
					description="";
					useNum="";
				}else{
					trouble_name=trouble_name+","+$("#trouble_name"+z).val();
					description=description+","+$("#description"+z).val();
					useNum=useNum+","+$("#useNum"+z).val();
					isPass=true;
				}
			}
			 for(var _i=0;_i<value.length;_i++){
				if(parseInt(checkValue[_i])!=0){
					flag=1;
				}
			}  
			//如果跟换备件  更新备件的数据
			if(flag==1&&tf==0){
				//1、更新备件表里面的备件数量 all_num,use_n,all_id
				 $.post("${pageContext.request.contextPath}/pms/overhaul/updateSpareParts.do",{all_id:all_id,use_n:use_n,all_num:all_num}); 
				
				//2、向备件更换记录表里面添加备件的更换信息    chooseid是EQM_MAINTAIN维修表记录里面的id里面包含设备id，负责人id，完成后会有完成人id
				
				 $.post("${pageContext.request.contextPath}/wct/overhaul/insertHisSParts.do",{chooseid:chooseid,all_id:all_id,use_n:use_n,all_num:all_num});
				 showAddFixResult(tf,flag);
			}else if(tf!=0&&flag==0){
				if(isPass){
					//手动添加备品备件记录
					$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/addNewFix.do",{id:chooseid,fix_name:trouble_name,fix_code:description,useNum:useNum,type:type_})
					showAddFixResult(tf,flag);
				}else{
					jAlert("名称、型号和使用数量不准为空！","提示");
					return;
				}
			}else if(tf!=0&&flag==1){
				//1、更新备件表里面的备件数量 all_num,use_n,all_id
				 $.post("${pageContext.request.contextPath}/pms/overhaul/updateSpareParts.do",{all_id:all_id,use_n:use_n,all_num:all_num}); 
				
				//2、向备件更换记录表里面添加备件的更换信息    chooseid是EQM_MAINTAIN维修表记录里面的id里面包含设备id，负责人id，完成后会有完成人id
				
				 $.post("${pageContext.request.contextPath}/wct/overhaul/insertHisSParts.do",{chooseid:chooseid,all_id:all_id,use_n:use_n,all_num:all_num});
				 if(isPass){
						//手动添加备品备件记录
						$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/addNewFix.do",{id:chooseid,fix_name:trouble_name,fix_code:description,useNum:useNum,type:type_})
						showAddFixResult(tf,flag);
					}else{
						jAlert("名称、型号和使用数量不准为空！","提示");
						return;
					}
			}
			//刷新页面
			params=getJsonData($('#eqpMailPlan-wct-frm'));
			$("#eqpMailPlan-search").click();
			//清空数组数据
			checkValue=new Array();
			//备品备件ID
			value = new Array();
			//总数量
			allNum=new Array();
			$("#hid_repair_div").css("display","none");
			
	 }
	 function hiddenRepairWin(){
			checkValue=new Array();
			//备品备件ID
			value = new Array();
			//总数量
			allNum=new Array();
			$("#hid_repair_div").css("display","none");
	 }
</script>
</html>