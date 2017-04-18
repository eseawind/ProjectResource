<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设备轮保</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>
<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
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
		height: 36px;
		margin: 0 auto;		
		margin-top: 15px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
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
border-color: #cccccc;
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
}
</style>
<script type="text/javascript" >
	//工作内容
	var contextValue = {};
	var groupTypeFlag="${groupTypeFlag}";
	var isCzg = "false";
	var isLbg = "false";
	var jsType = "";//这里牵扯到 到哪个 session取值
	var bandParams;
	$(function(){
		//页面进来，给时间空间赋值当天日期
		var mydate = new Date();
		var year=mydate.getFullYear();
		var month=mydate.getMonth()+1;
		var day=mydate.getDate();
		var str=year+"-"+retType(month)+"-"+retType(day);
		//将日期赋值给时间控件
		$("input[name='queryStartTime']").attr("value",str);
		$("input[name='queryEndTime']").attr("value",str);
		//判断日期是否小于10，如果小于10前面加0返回
		function retType(val){
			if(val>=10){
				return val;
			}else{
				return "0"+val;
			}
		}
		$("input.mh_date").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : false,//是否开启时间值默认为false
			beginY : 2010,//年份的开始默认为1949
			endY :2049//年份的结束默认为2049
		});
		/*查询*/
		var pageIndex=1;
		var allPages=0;
		var params={};
		bandParams=function(pageIndex,params){
			params=getJsonData($('#eqpMailPlan-wct-frm'));
			params.eqpCode="${loginInfo.equipmentCode}";//设备编号	
			$.post("${pageContext.request.contextPath}/wct/eqm/covelplan/queryPlanList.do?pageIndex="+pageIndex,params,function(reobj){
				var list=reobj.rows;
				allPages=reobj.total%10==0?parseInt(reobj.total/10):parseInt(reobj.total/10)+1;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.total);
				clearParams();
				var i=1;
				$("#eqpMailPlan-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];
							//console.info(revalue);
							//tr.find("td:eq(1)").html(revalue.planNo);//计划编号
							tr.find("td:eq(1)").html(revalue.planName);//计划名称
							tr.find("td:eq(2)").html(revalue.scheduleDate);//计划时间
							tr.find("td:eq(3)").html(revalue.mdShiftName);//计划班次
							// wheelParts 绑定的 维护项目 ID
							tr.find("td:eq(4)").html(revalue.modifyDate);//操作时间
							var wcType = "";
							if(revalue.wheelCoverType=='2'){wcType ="运行";}
							else if(revalue.wheelCoverType=='3'){wcType ="停止运行";}
							else if(revalue.wheelCoverType=='4'){wcType ="操作工操作完毕";}
							else if(revalue.wheelCoverType=='5'){wcType ="轮保工操作完毕";}
							else if(revalue.wheelCoverType=='6'){wcType ="操作工、维修工、轮保工操作完毕";}
							else if(revalue.wheelCoverType=='7'){wcType ="审核完毕";}
							else if(revalue.wheelCoverType=='8'){wcType ="完成";}
							else if(revalue.wheelCoverType=='9'){
								wcType ="维修工操作完毕";
							}
							tr.find("td:eq(5)").html(wcType);//操作状态
							// 0 :新增;1:审核通过;2:运行;3:停止运行; 4:操作工操作完毕;5 轮保工操作完毕
							// 6:操作工、轮保工操作完毕;7:审核完毕;8:计划完成;
							tr.find("td:eq(6)").html("<input type='button' value='确定' onclick=jsCompletes('"+revalue.id+"','"+revalue.mdEquipmentId+"','"+revalue.wheelParts+"','"+revalue.wheelCoverType+"','"+revalue.isCzgFinsh+"','"+revalue.isLbgFinsh+"','"+revalue.mdShiftId+"') style='height:28px;width:60px;' class='btn btn-default'/>");//操作工操作
							
							if(revalue.wheelCoverType=='6'){
								tr.find("td:eq(7)").html("<input type='button' value='完成' onclick=jsFinish('"+revalue.id+"','"+revalue.wheelCoverType+"','"+revalue.isCzgFinsh+"','"+revalue.isLbgFinsh+"') style='height:28px;width:60px;' class='btn btn-default'/>");//操作工操作
							}else{
								tr.find("td:eq(8)").html("");
							}
							if(revalue.wheelCoverType=='8'){
								tr.find("td:eq(8)").html("完成");
								tr.find("td:eq(6)").html("已完成");
								//tr.find("td:eq(8)").html("完成");
							}
						}
				});
			},"JSON");
			//console.info(contextValue);
		};
		
		var clearParams=function(){
			$("#eqpMailPlan-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(1)").html(null);
					tr.find("td:eq(2)").html(null);
					tr.find("td:eq(3)").html(null);
					tr.find("td:eq(4)").html(null);
					tr.find("td:eq(5)").html(null);
					tr.find("td:eq(6)").html(null);
					tr.find("td:eq(7)").html(null);
					//tr.find("td:eq(8)").html(null);
			});
		};
		
		$("#up").click(function(){
			if(pageIndex<=1){
				return;
			}
			pageIndex=pageIndex-1;
			bandParams(pageIndex,params);
		});
		
		$("#down").click(function(){
			if(pageIndex>=allPages){
				return;
			}
			pageIndex=pageIndex+1;
			bandParams(pageIndex,params);
		});

		bandParams(1,params);

		//查询按钮
		$("#eqpMailPlan-search").click(function(){
			params=getJsonData($('#eqpMailPlan-wct-frm'));
			bandParams(1,params);
		});
		
		$("#eqpMailPlan-reset").click(function(){
			$("#eqpMailPlan-wct-frm input[type!=button]").val(null);
		});
		clearParams();
	});
	/*planId：计划ID,sbLbxType:设备维修项大类,wheelParts 设备大类ID, shiftId 班次*/
	function jsCompletes(planId,sbLbxType,wheelParts,status,isCzgFinsh,isLbgFinsh,shiftId){
		var roleIds = $("#sbUserRoleIds").html(); ;//当前登陆人
		var isCzgIndex = roleIds.indexOf("8af2d43f4d73d86d014d73df6da90000");//操作工角色ID
		var isLbgIndex = roleIds.indexOf("8af2d43f4d73d86d014d73e1615a0001");//机械轮保工角色ID
		var isDqLbgIndex = roleIds.indexOf("8af2d49050d2002d0150da33910005b2");//电气轮保 工角色ID
		var isShgIndex = roleIds.indexOf("8af2d43f4d887f33014d889c1a3c0000");//审核工角色ID
		//2:运行 3:停止运行 4:操作工操作完毕 5:轮保工操作完毕 6:操作工、轮保工操作完毕 7:审核完毕 8:计划完成
		//alert(isShgIndex+":"+status);
		if(status=='8'){
			jAlert("完成 的 计划不可以进行后续操作","提示");
			return ;
		}
		//三种种角色
		else if((isCzgIndex>=0&&isLbgIndex>=0&&isDqLbgIndex>=0)&&(status=='2'||status=='4'||status=='5'||status=='6')){
			insertJsp(planId,sbLbxType,"all","loginWctEqmInfo",wheelParts,shiftId);
		}
		//两种角色  3种
		//操作工+机械轮保工
		else if((isCzgIndex>=0&&isLbgIndex>=0&&isDqLbgIndex<0)&&(status=='2'||status=='4'||status=='5'||status=='6')){
			insertJsp(planId,sbLbxType,"c_jl","loginWctEqmInfo",wheelParts,shiftId);
		}
		//操作工+电气轮保工
		else if((isCzgIndex>=0&&isLbgIndex<0&&isDqLbgIndex>=0)&&(status=='2'||status=='4'||status=='5'||status=='6')){
			insertJsp(planId,sbLbxType,"c_dl","loginWctEqmInfo",wheelParts,shiftId);
		}
		//机械轮保工+电气轮保工
		else if((isCzgIndex<0&&isLbgIndex>=0&&isDqLbgIndex>=0)&&(status=='2'||status=='4'||status=='5'||status=='6')){
			insertJsp(planId,sbLbxType,"jl_dl","loginWctEqmInfo",wheelParts,shiftId);
		}
		//单角色 3种
		else if(isCzgIndex>=0&&(status=='2'||status=='4')){//运行 操作工操作完毕
			insertJsp(planId,sbLbxType,"czg","loginWctEqmInfo",wheelParts,shiftId);
		}else if(isLbgIndex>=0&&(status=='2'||status=='4'||status=='5'||status=='6')){
			insertJsp(planId,sbLbxType,"lbg","loginWctEqmInfo",wheelParts,shiftId);
		}else if(isDqLbgIndex>=0&&(status=='2'||status=='4'||status=='5'||status=='6')){
			insertJsp(planId,sbLbxType,"dqLbg","loginWctEqmInfo",wheelParts,shiftId);
		}
		/* else if(isShgIndex>=0&&(status=='6'||status=='7'||isCzgFinsh=='Y'||isLbgFinsh=='Y')){
			insertJsp(planId,sbLbxType,"shg","loginWctEqmInfo",wheelParts,shiftId);
		} */else if(isShgIndex>=0){
			insertJsp(planId,sbLbxType,"shg","loginWctEqmInfo",wheelParts,shiftId);
		}else{
			jAlert("请使用 相应用户登录,内部登录系统","提示");
		}
	}
	
 	//打开页面
	function insertJsp(planId,sbLbxType,role,fromSession,wheelParts,shiftId){
		$("#wkd-eqp-msg-title").css("display","none");//登录信息页面隐藏
		$("#eqpMailPlan-seach-box").css("display","none");//查询条件隐藏
		$("#eqpMailPlan-tab").css("display","none");//表格隐藏
		$("#eqpMailPlan-page").css("display","none");//分页隐藏
		$("#mat_system").css("display","block");
		var url="eqp_lb_jsp.jsp?";
		url+="planId="+planId;
		url+="&sbLbxType="+sbLbxType;
		url+="&role="+role;
		url+="&fromSession="+fromSession;
		url+="&wheelParts="+wheelParts;
		url+="&shiftId="+shiftId;
		$("#insertJsp").attr("src",url);
	}
	//关闭按钮
	function closePage(){
		$("#wkd-eqp-msg-title").css("display","block");//登录信息页面显示
		$("#eqpMailPlan-seach-box").css("display","block");//查询条件显示
		$("#eqpMailPlan-tab").css("display","block");//表格显示
		$("#eqpMailPlan-page").css("display","block");//分页显示
		$("#mat_system").css("display","none");
		//重新查询下
		var params=getJsonData($('#eqpMailPlan-wct-frm'));
		bandParams(1,params);
	}
	function jsFinish(id,wheelCoverType,isCzgFinsh,isLbgFinsh){
		//7:审核完毕
		if(wheelCoverType=='8'){
			jAlert("完成 的 计划不可以进行后续操作","提示");
			return false;
		}else if(isCzgFinsh!="Y"){
			jAlert("请先使用操作工角色登录并操作","提示");
			return false;
		}else if(isLbgFinsh!="Y"){
			jAlert("请先使用轮保工角色登录并操作","提示");
			return false;
		}else if(wheelCoverType=='6'){
			jAlert("请先使用审核工角色登录并操作","提示");
			return false;
		}
		//alert(wheelCoverType);
		//return;
		var roleIds = $("#sbUserRoleIds").html(); ;//当前登陆人
		var isCzgIndex = roleIds.indexOf("8af2d43f4d73d86d014d73df6da90000");//操作工审核确认角色ID
		if(isCzgIndex<0){
			jAlert("请先使用操作工角色登录并操作","提示");
			return false;
		}
		jConfirm('确认完成?', '系统提示',function(r){
			if(r==true){
				$.post("${pageContext.request.contextPath}/wct/eqm/covelplan/updatePlanStatus.do?id="+id,function(val){
					if(val){
						params=getJsonData($('#eqpMailPlan-wct-frm'));
						$("#eqpMailPlan-search").click();	
					}else{
						JAlert("操作失败","系统提示");
					}
					
				});
			}
		});
	}
</script>
<script>
	//弹出窗口
	function openWin(){
		$("#hid_div").css("display","block");
	}
	//登录
	function initLogin(){
		var name=$("#name").val();
		var pwd=$("#pwd").val();
		var loginBean={"name":name,"passWord":pwd,"loginName":name};
		$.post("${pageContext.request.contextPath}/wct/system/loginWctEqm.do",loginBean,function(json){
			if(json.isSuccess=="true"){
				$("#sbUserName").html(json.name);
				$("#sbUserRole").html(json.rolesNames); 
				$("#sbUserRoleIds").html(json.roles); 
				$("#sbLoginStatus").html("登录成功");
				$("#hid_div").css("display","none");//隐藏弹出层
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
			}else{
				jAlert("注销失败", '系统提示');
			}
		},"JSON");
	}
	//取消
	function hiddenFrom(){
		$("#hid_div").css("display","none");
	}
</script>
</head>
<body>
<div id="eqpMailPlan-title">设备轮保</div>
<!-- 用户登录窗口 -->
<jsp:include page="eqmLogin.jsp"></jsp:include>

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
<div id="eqpMailPlan-seach-box" >
	<form id="eqpMailPlan-wct-frm">
		<table width="70%" cellspacing="0" cellpadding="0">
			<tr>	
			<td>开始时间</td>
			<td>
			<input  type="text" name="queryStartTime" class="mh_date" readonly="readonly" style="width:110px;height:26px;"/>
			</td>
			<td>到&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
			<input type="text" name="queryEndTime" class="mh_date" readonly="readonly" style="width:110px;height:26px;"/>
			</td>
			<td>
				<input type="button" id="eqpMailPlan-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" id="eqpMailPlan-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="eqpMailPlan-tab" style="height:448px;overflow:auto;">
	<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="824" height="450" cellspacing="0" cellpadding="0">
		<thead id="eqpMailPlan-tab-thead" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header" style="width:30px">序号</td>
				<!-- <td class="t-header" style="width:70px">计划编号</td> -->
				<td class="t-header" style="width:110px">计划名称</td>
				<td class="t-header" style="width:70px">计划时间</td>
				<td class="t-header" style="width:50px">计划班次</td>
				<td class="t-header" style="width:150px">操作时间</td>
				<td class="t-header" style="width:80px">操作状态</td>
				<td class="t-header" style="width:60px">操作</td>
				<!-- <td class="t-header" style="width:60px">完成</td> -->
			</tr>
		</thead>
		<tbody id="eqpMailPlan-tab-tbody">
			<tr>
				<td>1</td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<!-- <td></td> -->
			</tr>
			<tr>
				<td>2</td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<!-- <td></td> -->
			</tr>
			<tr>
				<td>3</td>
				<td></td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<!-- <td></td> -->
			</tr>
			<tr>
				<td>4</td>
			<!-- 	<td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<!-- <td></td> -->
			</tr>
			<tr>
				<td>5</td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<!-- <td></td> -->
			</tr>
			<tr>
				<td>6</td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			<!-- 	<td></td> -->
			</tr>
			<tr>
				<td>7</td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			<!-- 	<td></td> -->
			</tr>
			<tr>
				<td>8</td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<!-- <td></td> -->
			</tr>
			<tr>
				<td>9</td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<!-- <td></td> -->
			</tr>
			<tr>
				<td>10</td>
				<!-- <td></td> -->
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			<!-- 	<td></td> -->
			</tr>
		</tbody>
	</table>
</div>
<div id="eqpMailPlan-page">
共<span id="count">0</span>条数据
	<input id="up" type="button"  value="上一页" class="btn btn-default"/>
	<span id="pageIndex">0</span>/<span id="allPages">0</span>
    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
</div>

<div id="mat_system"  style="display:none;">
	<div >
		<iframe id="insertJsp" frameborder="0"  style="height: 522px;width: 828px;padding-top: -20px;"></iframe>				
	</div>
	<div>
		<input style="margin-left:384px;height:40px;width:100px;" id="closeJsp" onclick="closePage();" type="button"  value="关闭" class="btn btn-default" />
	</div>
 </div>


</body>
</html>