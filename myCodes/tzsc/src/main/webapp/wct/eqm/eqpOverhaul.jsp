<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
<html>
<head>
<title>设备检修</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>
<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initHighcharts.jsp' ></jsp:include>
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
		height: 448px;
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
	var chooseid; 
	var idId;
	var source="5";
	//place,contents,date_plans,blame_usr_name,note,eqp_id,blame_usr_id
	var placeJ;
	var contentsJ;
	var date_plansJ;
	var blame_usr_nameJ;
	var noteJ;
	var eqp_idJ;
	var blame_usr_idJ;
	var real_usr_nameJ;
	
	
	var groupTypeFlag="${groupTypeFlag}";
	var group=null;
	var pageIndex=1;
	var allPages=0;
	var params={};
	var zl_=1;
	$(function(){
		$("input.mh_date").manhuaDate({					       
			Event : "click",//可选				       
			Left : 0,//弹出时间停靠的左边位置
			Top : -16,//弹出时间停靠的顶部边位置
			fuhao : "-",//日期连接符默认为-
			isTime : false,//是否开启时间值默认为false
			beginY : 2010,//年份的开始默认为1949
			endY :2049//年份的结束默认为2049
		});
		var bandParams=function(pageIndex,params){
			params.eqp_id="${loginInfo.equipmentId}";//设备Id
			$.post("${pageContext.request.contextPath}/wct/overhaul/queryOverhaul.do?pageIndex="+pageIndex,params,function(v){				
				var reobj=eval("("+v+")");
				var list=reobj.rows;
				allPages=reobj.total%10==0?parseInt(reobj.total/10):parseInt(reobj.total/10)+1;
				$("#pageIndex").html(pageIndex);
				$("#allPages").html(allPages);
				$("#count").html(reobj.total);
				clearParams();
				 $("#eqpMailPlan-tab-tbody tr").each(function(idx){
						if(list.length>idx){
							var tr=$(this);
							var revalue=list[idx];
							//console.info(revalue);
							tr.find("td:eq(1)").html(revalue.eqp_name);
							tr.find("td:eq(2)").html(revalue.place);
							tr.find("td:eq(3)").html(revalue.contents);
							tr.find("td:eq(4)").html(revalue.date_plans);
							tr.find("td:eq(5)").html(revalue.blame_usr_name);
							tr.find("td:eq(6)").html(revalue.note);
							tr.find("td:eq(7)").html(revalue.real_usr_name);
							if(revalue.real_usr_name==null||revalue.real_usr_name==""){
								tr.find("td:eq(8)").html("<a href='${pageContext.request.contextPath}/wct/eqm/updateJX.jsp?type=5&&id=\""+revalue.id+"\"'><input type='button' id='eqpFault-time-order' value='检修'   style='height:28px;width:60px;' class='btn btn-default'/></a>");
								//tr.find("td:eq(8)").html("<input type='button' id='eqpFault-time-order' value='完成 '  onclick=completes('"+revalue.id+"','"+revalue.place+"','"+revalue.contents+"','"+revalue.date_plans+"','"+revalue.blame_usr_name+"','"+revalue.note+"','"+revalue.eqp_id+"','"+revalue.blame_usr_id+"','"+revalue.real_usr_name+"') style='height:28px;width:60px;' class='btn btn-default'/>");
							
							}else{
								tr.find("td:eq(8)").html("已完成");
							}
						}
						zl_++;
				}); 
			});
		};
		
		$("#up").click(function(){
			if(pageIndex<=1){
				return;
			}
			pageIndex=pageIndex-10;
			bandParams(pageIndex,params);
		});
		
		$("#down").click(function(){
			if(pageIndex>=allPages){
				return;
			}
			pageIndex=pageIndex+10;
			bandParams(pageIndex,params);
		});
		$("#eqpMailPlan-search").click(function(){
			params=getJsonData($('#eqpMailPlan-wct-frm'));
			bandParams(1,params);
		});
		$("#eqpMailPlan-reset").click(function(){
			params={};
			$("#eqpMailPlan-wct-frm input[type!=button]").val(null);
			$("#equipmentCode").val("全部"); 
		});
		
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
					tr.find("td:eq(8)").html(null);
					tr.find("td:eq(9)").html(null);
			});
		};
		//设置默认日期
		var today = new Date();
		var month=today.getMonth()+1;
		if(month<10){month=("0"+month);}
		var day=today.getDate();
		if(day<10){day=("0"+day);}
		var defaultlDate=today.getFullYear()+"-"+month+"-"+day;
		//前7天
		var dateOld = new Date(today.getTime() - 7 * 24 * 3600 * 1000);
		var yearOld = dateOld.getFullYear();
		var monthOld = dateOld.getMonth() + 1;
		if(monthOld<10){monthOld=("0"+monthOld);}
		var dayOld = dateOld.getDate();
		if(dayOld<10){dayOld=("0"+dayOld);}
		/* var hour = date.getHours();
		var minute = date.getMinutes();
		var second = date.getSeconds(); */
		var defaultlDateOld=yearOld + '-' + monthOld + '-' + dayOld;
		$("#date_plans").val(defaultlDateOld);	//时间用这个
		$("#date_planst").val(defaultlDate);	//时间用这个
		params=getJsonData($('#eqpMailPlan-wct-frm'));
		bandParams(1,params);
		
		
		
		
	});
	//这里弹出弹出框
	function completes(id,place,contents,date_plans,blame_usr_name,note,eqp_id,blame_usr_id,real_usr_name){
		$("#hid_repair_div").css("display","block");
		$("#bjType").val(null);
		$("#remarkName").val(null);
		$("#trouble_name").val(null);
		$("#description").val(null);
		showEqp();
		chooseid=id;
		idId=id;
		placeJ=place;
		contentsJ=contents;
		date_plansJ=date_plans;
		blame_usr_nameJ=blame_usr_name;
		noteJ=note;
		eqp_idJ=eqp_id;
		blame_usr_idJ=blame_usr_id;
		real_usr_nameJ=real_usr_name;
	}
	//取消
	function hiddenRepairWin(){
		$("#hid_repair_div").css("display","none");
		//刷新页面
		params=getJsonData($('#eqpMailPlan-wct-frm'));
		$("#eqpMailPlan-search").click();
		//清空数组数据
		checkValue=new Array();
		//备品备件ID
		value = new Array();
		//总数量
		allNum=new Array();
	}
	function updateStatus(id,i){
		/* alert($("#area3"+i).val()+"--"+i);
		alert($("#area6"+i).val());
		return; */
		var area3=$("#area3"+i).val();
		var area6=$("#area6"+i).val();
		$.post("${pageContext.request.contextPath}/wct/overhaul/updateStatus.do",{id:id,area3:area3,area6:area6},function(returnData){
					var arr=eval("["+returnData+"]");
					if(!arr[0].success){
						jAlert(arr[0].msg,"提示");
					}else{
						//将文本域变为只读
						
					}
		});  
		$("#area3"+i).attr("readonly","readonly");
		$("#area6"+i).attr("readonly","readonly");
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
	
	
	/* //查询检修信息，用作修改
	function queryJX(id_){
		alert(id_);
		$.post("${pageContext.request.contextPath}/wct/overhaul",{},function{
			
		});
	} */
</script>
</head>
<body>
<div id="eqpMailPlan-title">设备检修</div>
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
<div id="eqpMailPlan-seach-box" >
	<form id="eqpMailPlan-wct-frm">
		<table width="70%" cellspacing="0" cellpadding="0">
			<tr>
			<td>创建日期</td>
			<td>
			<input type="text" id="date_plans" name="date_plans" class="mh_date" readonly="readonly" style="width:110px;height:27px;"/>
			</td>
			<td>到&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
			<input type="text" id="date_planst" name="attr1" class="mh_date" readonly="readonly" style="width:110px;height:27px;"/>
			</td>
			<td><input type="button" id="eqpMailPlan-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/></td>
			<td>&nbsp;&nbsp;&nbsp;<input type="button" id="eqpMailPlan-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>
</div>
<div id="eqpMailPlan-tab" style="height:448px;overflow:auto;">
	<table border="1" borderColor="#9a9a9a" style="BORDER-COLLAPSE:collapse;font-size: 12px;font-weight: 500;" width="824" height="448" cellspacing="0" cellpadding="0">
		<thead id="eqpMailPlan-tab-thead" style="background: #5a5a5a;color: #fff;">
			<tr>
				<td class="t-header" style="width:30px">序号</td>
				<td class="t-header" style="width:80px">设备名称</td>
				<td class="t-header" style="width:40px">部位</td>
				<td class="t-header" style="width:180px">检修项目</td>
				<td class="t-header" style="width:60px">检修日期</td>
				<td class="t-header" style="width:60px">责任人</td>
				<td class="t-header" style="width:180px">注意事项</td>
				<td class="t-header" style="width:60px">检修人员</td>
				<td class="t-header" style="width:80px">是否完成</td>
			</tr>
		</thead>
		<tbody id="eqpMailPlan-tab-tbody">
			<tr>
				<td>1</td>
				<td></td>
				<td></td>
				<td></td>
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
				<td></td>
				<td></td>
				<td></td>
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

</body>
</html>