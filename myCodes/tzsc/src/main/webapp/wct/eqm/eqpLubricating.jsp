<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>设备润滑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>
<style type="text/css">
body{background:none;}
*{font-family: "Microsoft YaHei","宋体",Arial;}
	#eqpLubriPlan-title{
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
	#eqpLubriPlan-seach-box{
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
	#eqpLubriPlan-wct-frm td{
		font-size: 14px;
		font-weight: bold;
	}
	#eqpLubriPlan-tab{		
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
	#eqpLubriPlan-tab-thead tr td,#eqpLubriPlan-tab-tbody tr td{
		/* padding:7px; */
		height:40px;
		text-align:center;
	}
	#eqpLubriPlan-page{
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
	/*查询*/
	var pageIndex=1;
	var allPages=0;
	var params={};
	$(function(){
		setTodayTime();
		$("input.mh_date").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : false,//是否开启时间值默认为false
			beginY : 2010,//年份的开始默认为1949
			endY :2049//年份的结束默认为2049
		});
		
		//下拉框
		$.loadSelectData($("#eqpId"),"ALLROLLERS",false);
		$("#eqpId").append("<option value=''>全部</option>");
		params=getJsonData($('#eqpLubriPlan-wct-frm'));
		bandParams=function(pageIndex,params){
			params=getJsonData($('#eqpLubriPlan-wct-frm'));
			params.eqpCode="${loginInfo.equipmentCode}";//设备编号
			params.eqp_id="${loginInfo.equipmentId}";
			var p={page:pageIndex,rows:10,date_plan:params.date_plan,eqp_id:params.eqp_id,attr1:params.attr1};
			$.post("${pageContext.request.contextPath}/wct/lubrplan/queryLubrplan.do",p,function(reobj){
				var list=reobj.rows;
				allPages=reobj.total%10==0?parseInt(reobj.total/10):parseInt(reobj.total/10)+1;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.total);
				clearParams();
				$("#eqpLubriPlan-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];
							tr.find("td:eq(1)").html(revalue.eqp_name);//设备名称
							tr.find("td:eq(2)").html(revalue.lub_id_name);//润滑类型
							tr.find("td:eq(3)").html(revalue.shiftName);//班次
							tr.find("td:eq(4)").html(revalue.date_plan);//计划时间
							if(revalue.lubricating_name==null){
								tr.find("td:eq(5)").html("<input type='button' value='润滑' onclick=rh('"+revalue.id+"','rh') style='height:28px;width:100px;' class='btn btn-default'/>");//润滑 操作工确认
							}else{
								tr.find("td:eq(5)").html(revalue.lubricating_name);//润滑,润滑工确认
							}
						}
				});
			},"JSON");
		};
		var clearParams=function(){
			$("#eqpLubriPlan-tab-tbody tr").each(function(idx){
					var tr=$(this);
					tr.find("td:eq(1)").html(null);
					tr.find("td:eq(2)").html(null);
					tr.find("td:eq(3)").html(null);
					tr.find("td:eq(4)").html(null);
					tr.find("td:eq(5)").html(null); 
					tr.find("td:eq(6)").html(null);
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
		$("#eqpLubriPlan-search").click(function(){
			params=getJsonData($('#eqpLubriPlan-wct-frm'));
			bandParams(1,params);
		});
		
		$("#eqpLubriPlan-reset").click(function(){
			$("#eqpLubriPlan-wct-frm input[type!=button]").val(null);
		});
		
	});
	function setTodayTime(){
		//设置默认日期
		var today = new Date();
		var month=today.getMonth()+1;
		if(month<10){month=("0"+month);}
		var day=today.getDate();
		if(day<10){day=("0"+day);}
		var defaultlDate=today.getFullYear()+"-"+month+"-"+day;
		$("#date_plan").val(defaultlDate);	//时间用这个
		$("#date_plant").val(defaultlDate);	
	}
	//润滑工确认计划完成
	function rh(id,type){
		var roleIds = $("#sbUserRoleIds").html();//当前登陆人
		if(roleIds!=null&&roleIds.trim()!=""){
			if(type=="rh"){
				var isRhGIndex = roleIds.indexOf(parent.getRH_Role());//润滑工角色ID
				if(isRhGIndex>-1){
					insertJsp(id,'rh');
				}else{
					jAlert("当前登录用户无此操作权限，请使用润滑工角色重新登录,内部登录系统","提示");
				}
			}/* else if(type="czg"){
				var isCZGIndex = roleIds.indexOf(parent.getCZG_Role());//操作工角色ID
				if(isCZGIndex>-1){
					insertJsp(id,'czg');
				}else{
					jAlert("当前登录用户无此操作权限，请重新登录,内部登录系统","提示");
				}
			} */else{
				jAlert("当前登录用户无此操作权限，请重新登录,内部登录系统","提示");
			}
		}else{
			jAlert("请使用 相应用户,内部登录系统","提示");
		}
	}
	function qr(id){
		var roleIds = $("#sbUserRoleIds").html(); ;//当前登陆人
		if(roleIds!=null&&roleIds.trim()!=""){
			var isRhGIndex = roleIds.indexOf(parent.getCZG_Role());//润滑工角色ID
			if(isRhGIndex>-1){
				jConfirm('确认润滑任务已完成?', '系统提示',function(r){
					if(r){
						$.post("${pageContext.request.contextPath}/wct/lubrplan/editLubrOperplanById.do?plan_id="+id,function(returnData){
							if(returnData.success){
								jAlert("保存成功","提示");
								bandParams(pageIndex,params);
							}else{
								jAlert("保存失败!","提示");
							}
						},"JSON");
					}
				});
			}else{
				jAlert("当前登录用户无此操作权限，请重新登录,内部登录系统","提示");
			}
		}else{
			jAlert("请使用 相应用户,内部登录系统","提示");
		}
		
	}
 	//打开页面
	function insertJsp(id,role){
		$("#this_plan_id").val(id);//计划ID
		$("#this_plan_role").val(role);//角色
		
		$("#wkd-eqp-msg-title").css("display","none");//登录信息页面隐藏
		$("#eqpLubriPlan-seach-box").css("display","none");//查询条件隐藏
		$("#eqpLubriPlan-tab").css("display","none");//表格隐藏
		$("#eqpLubriPlan-page").css("display","none");//分页隐藏
		$("#mat_system").css("display","block");
		var url="eqp_rh_jsp.jsp?";
		url+="id="+id;
		url+="&role="+role;
		$("#insertJsp").attr("src",url);
	}
	//关闭按钮
	function closePage(){
		$("#wkd-eqp-msg-title").css("display","block");//登录信息页面显示
		$("#eqpLubriPlan-seach-box").css("display","block");//查询条件显示
		$("#eqpLubriPlan-tab").css("display","block");//表格显示
		$("#eqpLubriPlan-page").css("display","block");//分页显示
		$("#mat_system").css("display","none");
		//重新查询下
		var params=getJsonData($('#eqpLubriPlan-wct-frm'));
		bandParams(1,params);
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
				jAlert("账户或密码错误","提示");
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
				jAlert("注销失败","提示");
			}
		},"JSON");
	}
	//取消
	function hiddenFrom(){
		$("#hid_div").css("display","none");
	}
	
	
	//底下查询全部按钮
	function goToRh(){
		var id = $("#this_plan_id").val(); 
		var role = $("#this_plan_role").val();
		rh(id,role);
		
	}
	
	//调用子页面保存方法。
	function saveChange(){
		$("#insertJsp")[0].contentWindow.save(); 
	}

	

</script>
</head>
<body>
<div id="eqpLubriPlan-title">设备润滑</div>
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
<div id="eqpLubriPlan-seach-box" >
	<form id="eqpLubriPlan-wct-frm">
		<table width="70%" cellspacing="0" cellpadding="0">
			<tr>	
			<td>计划日期</td>
			<td>
			<input type="text" id="date_plan" name="date_plan" class="mh_date" readonly="readonly" style="width:110px;height:27px;"/>
			</td>
			<td>到&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
			<input type="text" id="date_plant" name="attr1" class="mh_date" readonly="readonly" style="width:110px;height:27px;"/>
			</td>
			<td>
				<input type="button" id="eqpLubriPlan-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/>
			</td>
			<td>
				&nbsp;&nbsp;&nbsp;<input type="button" id="eqpLubriPlan-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="eqpLubriPlan-tab" style="height:440px;overflow:auto;">
	<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="820" height="460" cellspacing="0" cellpadding="0">
		<thead id="eqpLubriPlan-tab-thead" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header" style="width:30px">序号</td>
				<td class="t-header" style="width:110px">设备名称</td>
				<td class="t-header" style="width:100px">润滑类型</td>
				<td class="t-header" style="width:100px">班次</td>
				<td class="t-header" style="width:70px">计划时间</td>
				<!-- <td class="t-header" style="width:150px">保存</td> -->
				<td class="t-header" style="width:150px">操作</td>
			</tr>
		</thead>
		<tbody id="eqpLubriPlan-tab-tbody">
			<tr>
				<td>1</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>3</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>4</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>5</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>6</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>7</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>8</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>9</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>10</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>
<div id="eqpLubriPlan-page">
共<span id="count">0</span>条数据
	<input id="up" type="button"  value="上一页" class="btn btn-default"/>
	<span id="pageIndex">0</span>/<span id="allPages">0</span>
    <input id="down" type="button"  value="下一页" class="btn btn-default"/>
</div>

<div id="mat_system"  style="display:none;">
	<div >
		<iframe id="insertJsp" name="insertJsp" frameborder="0"  style="height: 522px;width: 818px;padding-top: -20px;"></iframe>				
	</div>
	<div>
		<input type="hidden"  id='this_plan_id' value=""/ >
		<input type="hidden"   id='this_plan_role' value=""/ >
		<input style="margin-left:220px;height:40px;width:100px;" id="closeJsp" onclick="goToRh()" type="button"  value="查询全部" class="btn btn-default" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input style="height:40px;width:100px;" type="button" id="button_id"  onClick="saveChange()" value="完成" class="btn btn-default"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input style="height:40px;width:100px;" id="closeJsp" onclick="closePage();" type="button"  value="关闭" class="btn btn-default" />
	</div>
 </div>
</body>
</html>