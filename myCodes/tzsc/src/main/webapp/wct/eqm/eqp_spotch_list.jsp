<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设备点检管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>

<style type="text/css">
   body{
     background:none;
   }
   *{
     font-family: "Microsoft YaHei","宋体",Arial;
   }
   #eqpMailPlan-title{
		font-size: 20px;
		font-weight: bold;
		text-align: center;
		padding-top: 4px;
		background: #b4b4b4;
		color: #3C3C3C;
		border-radius: 0px 4px 0px 0px;
		line-height: 35px;
		height: 40px;
		border-bottom: 2px solid #838383;
	}
	#eqpMailPlan-seach-box{
		border: 1px solid #9a9a9a;
		width: 821px;
		margin-left: 10px;
		height: 37px;
		margin: 0 auto;	
		font-size: 14px;
		font-weight: bold;
		padding-top: 4px;
		padding-bottom:4px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
	}
	#eqpMailPlan-wct-frm td{
		font-size: 14px;
		font-weight: bold;
	}
	#eqpMailPlan-tab{		
		width:824px;
		margin: 0 auto;	
		height:auto;
		margin-top:5px;
		font-size:12px;
		font-weight:bold;
		height: 462px;
		border: 1px solid #858484;
		border-radius: 4px;		
	}
	.t-header{
		text-align:center;
	}
	#eqpMailPlan-tab-thead tr td,#eqpMailPlan-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#eqpMailPlan-page{
		width:97%;
		margin-left:10px;
		height:auto;
		font-size:12px;
		font-weight:bold;
		text-align:right;
		padding-top:4px;
	}
	#details{
		border:2px solid #dddddd;
		width:96%;
		margin-left:10px;
		height:80px;
		margin-top:5px;
		padding:2px;
		text-indent:15px;
	}
	#up,#down{
		border:1px solid #9a9a9a;
		padding:3px 2px;
		width:70px;
		font-weight:bold;
		font-size:12px;
		cursor:pointer;
	}
	.btn-default {
		color: #FFFFFF;
		background-color: #5A5A5A;
		/* border-color: #cccccc; */
		
		height:28px;width:40px;
	}

	.btn {
	  display: inline-block;
	  padding:0px;
	  margin-bottom: 0;
	  font-size: 14px;
	  font-weight: normal;
	  line-height: 1.428571429;
	  text-align: center;
	  white-space: nowrap;
	  vertical-align: middle;
	  cursor: pointer;
	  background-image: none;
	  border: 1px solid transparent;
	  border-radius: 4px;
	  -webkit-user-select: none;
	  -moz-user-select: none;
	  -ms-user-select: none;
	  -o-user-select: none;
	  user-select: none;
	}

	#wkd-eqp-msg-title{
		font-size: 12px;font-weight: bold;
		text-align:left;
		background: #DEDCDA;
		border-bottom: 1px solid #838383;
		margin-bottom: 5px;
	}
</style>

<script type="text/javascript" >
	var num_;
	var status_;
	var idId_;
	var source="1";
	 $(function(){
	 $("#dateP").val(returnTday()); 
	 $("#endtime").val(returnTday());
	 queryTadRecord();
	 $("input.mh_date").manhuaDate({
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : false,//是否开启时间值默认为false
			beginY : 2010,//年份的开始默认为1949
			endY :2049//年份的结束默认为2049
	 });
	 $("#eqpMailPlan-reset").click(function(){
			$("#eqpMailPlan-wct-frm input[type!=button]").val(null);
		});
	});
	
	
	//当前日期
	function returnTday(){
		var mydate = new Date();
		var year=mydate.getFullYear();
		var month=mydate.getMonth()+1;
		var day=mydate.getDate();
		var str=year+"-"+retType(month)+"-"+retType(day);
		return str;
	}
	//判断日期是否小于10，如果小于10前面加0返回
	function retType(val){
		if(val>=10){
			return val;
		}else{
			return "0"+val;
		}
	}
	
	//查询
	function queryTadRecord(){
		//清空table内容
		$("#eqpMailPlan-tab-tbody").text("");
		//获得日期条件
		var datep=$("#dateP").val();
		var etime=$("#endtime").val();
		var url="${pageContext.request.contextPath}/wct/eqm/checkplan/queryTayRecord.do";
		$.post(url,{datep:datep,etime:etime},function(data){
			var dataJson = JSON.parse(data);
			var str="";
			$.each(dataJson,function(n,val) {
				//console.info(val.shift_id);
				var stu=val.status;
				    str+="<tr>";
				    str+="<td class='t-header' style='width:20px'>"+(n+1)+"</td>";
					str+="<td class='t-header' style='width:35px'>"+val.eqp_name+"</td>";
					str+="<td class='t-header' style='width:40px'>"+val.ch_type_name+"</td>";
					str+="<td class='t-header' style='width:110px'>&nbsp;&nbsp;"+val.check_position+"</td>";
					str+="<td class='t-header' style='width:110px'>&nbsp;&nbsp;"+val.check_standard+"</td>";
					str+="<td class='t-header' style='width:45px'>"+val.check_method+"</td>";
					str+="<td class='t-header' style='width:10px' id='butSheet"+n+"' ><br/>";
					//维修主管记录判断
					var tf=val.ch_status;
					if(tf==2){//表中为2，设置到bean中为  "有故障 "字段
						str+="<a href='${pageContext.request.contextPath}/wct/eqm/fixrec/sysStaffPage.do'><font style='color:red;height:100px;'>查看故障</font></a>";
					}else if(tf==1){
						str+="<font style='color:green;height:100px;'>通过</font>";
					}else if(tf==0){
						str+="<input type='button' value='通过'  class='btn btn-default'  onclick='butSheetInput(\""+n+"\",1,\""+val.id+"\")'/> <br/><br/>";
						if(val.roles=='8af2d43f4d73d86d014d73df6da90000'){
							str+="<a href='${pageContext.request.contextPath}/wct/eqm/fixrec/sysStaffPage.do'><input type='button' value='有故障' class='istg btn' style='color:red;' onclick='openWinWXHJ(\""+n+"\",0,\""+val.id+"\");'/></a>";	
						}else{
							str+="<a href='${pageContext.request.contextPath}/wct/eqm/eqm_troubleInfo.jsp?source=1&&type=1&&id=\""+val.id+"\"&&sbPid=0&&role=0'><input type='button'  value='有故障' class='istg btn' style='color:red;'/></a> ";	
						}
					}
					str+="<br/><br/></td></tr>";   
			});
			 $("#eqpMailPlan-tab-tbody").append(str);
		},"JSON");
	}
	
	/**
	 *功能说明： 弹出设备更换窗口
	*/
	 function showBJ(n,chStatus,id){
		//  onclick='showBJ(\""+n+"\",0,\""+val.id+"\")'
		idId=id;
		status_=chStatus;
		num_=n;
		$("#hid_repair_div").css("display","block");
		$("#bjType").val(null);
		$("#remarkName").val(null);
		$("#trouble_name").val(null);
		$("#description").val(null);
		showEqp();
		//saveRepair();
	} 
	/**
	 *功能说明： 审核操作 
     *@param ch_status:  1-通过   0-有故障   ;   
     *@param id: sys_eqp_type表 id;
	*/
	function butSheetInput(n,chStatus,id){
		var url="${pageContext.request.contextPath}/wct/eqm/checkplan/addEqmSpotchRecord.do";
		$.post(url,{ch_status:chStatus,id:id},function(data){
			if(data=='true'){
				//jAlert("成功！","审核提示");
				//queryTadRecord();
				  if(chStatus==1){
					$("#butSheet"+n).text('');
					$("#butSheet"+n).append("<font style='color:green'>通过</font>");
				}else{ 
					$("#butSheet"+n).text('');
					$("#butSheet"+n).append("<font style='color:red;height:100px;'>查看故障</font>");
				} 
			}else{
				jAlert("失败！","审核提示");
				return false;
			}
		},"JSON");
	}
	
	
	//弹出窗口
	function openWin(){
		$("#hid_div").css("display","block");
	}
	//弹出维修呼叫窗口
	function openWinWXHJ(n,chStatus,id){
		/* var url ='${pageContext.request.contextPath}/wct/eqm/repairResquestCZG.jsp'
		var url ="http://www.baidu.com"; 
	   	var iWidth=850; //弹出窗口的宽度;
		var iHeight=600; //弹出窗口的高度;
		var iTop = (window.screen.availHeight-30-iHeight)/2; //获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth-10-iWidth)/2; //获得窗口的水平位置;
		window.open(url,"","toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no,height="+iHeight+", width="+iWidth+", top="+iTop+", left="+iLeft);
		 */
		butSheetInput(n,chStatus,id);
	}
	//登录
	function initLogin(){
		var name=$("#name").val();
		var pwd=$("#pwd").val();
		var loginBean={"name":name,"passWord":pwd,"loginName":name};
		$.post("${pageContext.request.contextPath}/wct/system/loginWctEqm.do",loginBean,function(json){
			if(json.isSuccess=="true"){
				$("#sbUserName").html(json.name); //登录人姓名
				$("#sbUserRole").html(json.rolesNames); 
				$("#sbUserRoleIds").html(json.roles); 
				$("#userId").val(json.userId); //登录人Id
				$("#sbLoginStatus").html("登录成功");
				$("#hid_div").css("display","none");//隐藏弹出层
				//登录成功，查询
				queryTadRecord();
			} else {
				$("#sbUserName").html(json.name);
				$("#sbLoginStatus").html("登录失败");
				jAlert("账户或密码错误!","系统提示");
			}
		},"JSON");
	}
	//注销
	function innerLogin(){
		$.post("${pageContext.request.contextPath}/wct/system/logOutWctEqm.do",function(json){
			if(json.isSuccess=="false"){
				$("#sbUserName").html("");
				$("#sbUserRole").html("");
				$("#sbUserRoleIds").html(""); 
				$("#sbLoginStatus").html("注销成功");
				$("#hid_div").css("display","none");//隐藏弹出层
				$("#eqpMailPlan-tab-tbody").html("");
			}else{
				jAlert("注销失败", '系统提示');
			}
		},"JSON");
	}
	//取消
	function hiddenFrom(){
		$("#hid_div").css("display","none");
		var b="${loginWctEqmInfo.isSuccess}";
		if($.trim(b)=="true"){
			
		}else{
			$("#eqpMailPlan-tab-tbody").html("");
		}
	}
	//取消
/*	function hiddenRepairWin(){
		checkValue=new Array();
		//备品备件ID
		value = new Array();
		//总数量
		allNum=new Array();
		//$("#hid_repair_div").css("display","none");
	}	
	 function saveRepair(){
		//showEqp();
		getValue();
		var flag=0;
		var all_num=allNum.join(',');
		var use_n=checkValue.join(',');
		var all_id=value.join(',');
		var all_trouble;
		var all_des;
		var _pass="true";
		 for(var _i=0;_i<value.length;_i++){
			if(parseInt(checkValue[_i])!=0){
				flag=1;
			}
		}  
		 if(getValue()){
			butSheetInput(num_,status_,idId);//将状态写入数据库
		}else{
			return;
		}
		if(getJsonData($('#repair-box-wct-frm')).trouble_name!=undefined){
			 all_trouble=getJsonData($('#repair-box-wct-frm')).trouble_name.split(',');
			 all_des=getJsonData($('#repair-box-wct-frm')).description.split(',');
			 var trouble_name = all_trouble.join(',');
			 var description= all_des.join(',');
			 if(_pass=="true"){
					//3.向故障表中插入数据
					 $.post("${pageContext.request.contextPath}/wct/eqm/checkplan/addTrouble.do",{source_id:idId,allTrouble_name:trouble_name,allDescription:description,source:"1"});
					  jAlert("添加成功！","提示");
				}
			}
		//如果跟换备件  更新备件的数据
		if(flag==1){
			//1、更新备件表里面的备件数量 all_num,use_n,all_id
			 $.post("${pageContext.request.contextPath}/pms/overhaul/updateSpareParts.do",{all_id:all_id,use_n:use_n,all_num:all_num}); 
			// $.post("${pageContext.request.contextPath}/pms/overhaul/updateSpareParts.do",{all_id:getJsonData($('#repair-box-wct-frm')).ids,use_n:getJsonData($('#repair-box-wct-frm')).use_num,all_num:getJsonData($('#repair-box-wct-frm')).nums}); 
			//2、向备件更换记录表里面添加备件的更换信息    chooseid是EQM_MAINTAIN维修表记录里面的id里面包含设备id，负责人id，完成后会有完成人id
			 $.post("${pageContext.request.contextPath}/wct/eqm/checkplan/addFixRec.do",{all_id:all_id,use_n:use_n,all_num:all_num,trouble_id:idId});
			//刷新页面
			params=getJsonData($('#eqpMailPlan-wct-frm'));
			$("#eqpMailPlan-search").click();
			$("#hid_repair_div").css("display","none");
			//清空数组数据
			checkValue=new Array();
			//备品备件ID
			value = new Array();
			//总数量
			allNum=new Array();
			showEqp();
		}else{
			$("#hid_repair_div").css("display","none");
		}
	}  */
</script>
</head>
<body>
<div id="eqpMailPlan-title">设备点检</div>
<!-- 用户登录窗口 -->
<jsp:include page="eqmLogin.jsp"></jsp:include>
<%-- <jsp:include page="eqp_lb_changeEqpUnit.jsp"></jsp:include> --%>

<div id="wkd-eqp-msg-title">
	<table style="font-size: 14px;">
		<tr>
			<td><b>&nbsp;登录人：</b></td><td><div id="sbUserName">
			    <c:if test="${empty loginWctEqmInfo}">${loginInfo.name}</c:if>
			    <c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.name}</c:if>
			</div></td>
			<td width="10"></td>
			<td><b>&nbsp;登录角色：</b></td>
			<td>
				<div id="sbUserRole">
					<c:if test="${empty loginWctEqmInfo}">${loginInfo.rolesNames}</c:if>
				    <c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.rolesNames}</c:if>
				</div>
				<div id="sbUserRoleIds" style="display:none;">
					<c:if test="${empty loginWctEqmInfo}">${loginInfo.roles}</c:if>
					<c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.roles}</c:if>
				</div>
			</td>
			<td width="10"></td>
			<td><b>登录状态：</b></td><td><div id="sbLoginStatus">
			    <c:if test="${empty loginWctEqmInfo}">${loginInfo.dlStatus}</c:if>
				<c:if test="${!empty loginWctEqmInfo}">${loginWctEqmInfo.dlStatus}</c:if>
			</div></td>
			<td width="10"></td>
			<td>
				<button  onclick="openWin();" style="height:28px;width:80px;" class="btn btn-default">
					 登录/注销
				</button>
			</td>
		</tr>
	</table>
</div>

<div id="eqpMailPlan-seach-box">
	<form id="eqpMailPlan-wct-frm">
	<!-- <table width="50%">
		<tr>	
		<td>时间</td>
		<td align="left">
		<input  type="text" id="dateP" name="dateP" class="mh_date" readonly="readonly" style="width:130px;height:26px;"/>
		</td>
		<td>
		<input type="button" onclick="queryTadRecord();" value="查询" style="height:28px;width:50px;" class="btn btn-default"/>
		</td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="eqpMailPlan-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
		</tr>
	</table> -->
	<table style="margin-top:5px;" width="70%" cellspacing="0" cellpadding="0">
			<tr>	
			<td>
			       开始时间
			</td>
			<td>
			   <input  type="text" name="dateP" id="dateP" class="mh_date" readonly="readonly" value="" style="width:110px;height:27px;"/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;到&nbsp;&nbsp;&nbsp;</td>
			<td>
			   <input type="text" name="endtime" id="endtime" class="mh_date" readonly="readonly" value="" style="width:110px;height:27px;"/>
			</td>
			
			<td>
				<input  onclick="queryTadRecord();" type="button"  value="查询" style="height:28px;width:50px;" class="btn btn-default"/>
			</td>
			<td> &nbsp;&nbsp;<input type="button" id="eqpMailPlan-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			
			</tr>
		</table>
	</form>
</div>
<div id="eqpMailPlan-tab" style="height:480px;overflow:auto;">
	<table id='table' border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="808"  cellspacing="0" cellpadding="0">
	
	   <thead style="background: #5a5a5a;color: #fff;height:40px;">
			<tr style="height: 40px;">
			    <td class="t-header" style="width:20px">序列</td>
				<td class="t-header" style="width:35px">设备名称</td>
				<td class="t-header" style="width:40px">点检类型</td>
				<td class="t-header" style="width:110px">点检关键部位</td>
				<td class="t-header" style="width:110px">点检标准</td>
				<td class="t-header" style="width:45px">点检方式</td>
				<td class="t-header" style="width:10px">审核操作</td>
			</tr>
		</thead>
		<tbody id="eqpMailPlan-tab-tbody">
			
		</tbody>
			
			
	</table>
	<br/>
</div>
</body>
</html>