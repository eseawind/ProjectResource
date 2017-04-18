<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>维修呼叫</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css" rel="stylesheet" media="screen">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>

<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initManhuaDate.jsp' ></jsp:include>


<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" charset="utf-8"></script>
<style type="text/css">

body{background:none;}

.gz_f{margin-left:16px;margin-right:16px;background-color: #F0F0F0;}
.bj_f{margin-left:16px;margin-right:16px;background-color: #F0F0F0;}
.spanstl{
  color:green;
  margin-right: 40px;
  line-height: 30px;
}
.divulli{
  color:green;
  margin-right:20px;
  line-height: 60px;
}
.menu_body{
  width: 100%;
  background-color: #EBEBEB;
}
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
*{margin:0;padding:0;list-style-type:none;}  
body{margin:10px auto;font:75%/120% Verdana,Arial, Helvetica, sans-serif;}  
.demo{width:400px;margin:0 auto;}  
.demo h2{font-size:14px;height:24px;line-height:24px;margin:30px 0 10px 0;padding:0 10px;}  
.menu_head{
border: 1px solid #9a9a9a;
		width: 810px;
		margin-left: 9px;
		height: 36px;
		margin: 0 auto;		
		margin-top: 5px;
		font-size: 14px;
		font-weight: bold;
		padding-top: 8px;
		padding-left: 5px;
		border-radius: 4px;
		background: #dedcda;
/* padding:5px 10px;cursor:pointer;
position:relative;margin:1px;
font-weight:bold;
background:#CCCCCC url(images/left.png) center right no-repeat; */
}  

/* .menu_list .current{background:#CCCCCC url(images/down.png) center right no-repeat;} */  
.menu_body{display:none;}  
.menu_body a{display:block;color:#006699;background-color:#EFEFEF;padding-left:10px;font-weight:bold;text-decoration:none;height:24px;line-height:24px;}  
.menu_body a:hover{color:#000000;text-decoration:underline;}  


</style>
<script type="text/javascript" >
var a = 0;
//打开维修呼叫页面
function openWxHjJsp(obj){
	var roleIds = $("#sbUserRoleIds").html();//当前登陆人
	var isCzgIndex = roleIds.indexOf("8af2d43f4d73d86d014d73df6da90000");//操作工角色ID
	var isWxgIndex = roleIds.indexOf("402899894db72650014db78d4035004f");//维修工角色ID
	if(obj!=3&&isCzgIndex<0){
		jAlert("请先使用操作工角色登录并操作","提示");
		return false;
	}
	if(obj==3&&isWxgIndex<0){
		jAlert("请先使用维修工角色登录并操作","提示");
		return false;
	}
	$("#planIdFromParam").attr("value","");
	$("#planParamId").attr("value","");
	$("#equipId").attr("value","");
	$("#hid_repair_div").css("display","block");
	queryStaffAll(obj);
}
		

//关闭维修呼叫页面
function hiddenWxHjJsp(){
	$("#project").text(""); //清空维修工图像
	$("#remark").attr("value","");//备注
	$("#hid_repair_div").css("display","none");
}

//确定
function showTimeSave(id,status,designated_person_id,count,designated_person_name,roletype){
	var roleIds = $("#sbUserRoleIds").html();//当前登陆人
	var isCzgIndex = roleIds.indexOf("402899894db72650014db78d4035004f");//维修工角色ID
	var isWXZGIndex = roleIds.indexOf("402899894db72650014db78daf010050");//维修主管角色ID
	var acceptUser = $("#sbUserName").html();
	var mydate = new Date();
	var year=mydate.getFullYear();
	var month=mydate.getMonth()+1;
	var day=mydate.getDate();
	var hours=mydate.getHours();
	var minutes=mydate.getMinutes();
	var seconds=mydate.getSeconds();
	var str=year+"-"+retType(month)+"-"+retType(day)+" "+retType(hours)+":"+retType(minutes)+":"+retType(seconds);
	//页面进来，给时间空间赋值当天日期
	//呼叫维修工
	$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/querySlServiceInfoById.do?id="+id,function(o){
		if(o.status=="0"&&isCzgIndex>0&&roletype!=3){
			$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/updateSlServiceInfo.do",{id:id,status:status});
			$("#span"+count).html("<font style='color:red;'>&nbsp维修中</font>");
			$("#accept"+count).html("<font>"+acceptUser+"</font>");
			$("#guZhang"+count).html("<a  href='${pageContext.request.contextPath}/wct/eqm/eqm_troubleInfo.jsp?source=2&&type=2&&id=\""+id+"\"&&sbPid=0&&role=0' style='margin-top:-28px; margin-left: 190px;'><input type='button'  value='故障' style='height:28px;width:60px;' class='btn btn-default'/></a>");
			$("#createUserTime"+count).html("<font style='color:green;'>"+str+"</font>");
			$("#zhuguan"+count).html("<input type='button' onclick='openWxHjJsp(3);'  value='请求主管' style='height:28px;width:100px;margin-top:-24px; margin-left: 70%;' class='btn btn-default'/>");
			$("#wanCheng"+count).html("<input type='button' onclick=showFinish('"+id+"','"+status+"','"+designated_person_id+"','"+count+"','"+designated_person_name+"','"+roletype+"');  value='完成' style='height:28px;width:60px;' class='btn btn-default'/>");
		//到场时间页面修改
		//修改sys_service_info表维修工到场时间及状态
		//修改人员状态
		//请求主管
		}else if(o.status=="0"&&isWXZGIndex>0&&roletype==3){
			$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/updateSlServiceInfo.do",{id:id,status:status});
			$("#span"+count).html("<font style='color:red;'>&nbsp维修中</font>");//
			$("#accept"+count).html("<font>"+acceptUser+"</font>");
			$("#guZhang"+count).html("<a  href='${pageContext.request.contextPath}/wct/eqm/eqm_troubleInfo.jsp?source=2&&type=2&&id=\""+id+"\"&&sbPid=0&&role=0' style='margin-top:-28px; margin-left: 190px;'><input type='button'  value='故障' style='height:28px;width:60px;' class='btn btn-default'/></a>");
			$("#createUserTime"+count).html("<font style='color:green;'>"+str+"</font>");
			$("#zhuguan"+count).html("<input type='button' onclick='openWxHjJsp(3);'  value='请求主管' style='height:28px;width:100px;margin-top:-24px; margin-left: 70%;' class='btn btn-default'/>");
			$("#wanCheng"+count).html("<input type='button' onclick=showFinish('"+id+"','"+status+"','"+designated_person_id+"','"+count+"','"+designated_person_name+"','"+roletype+"');  value='完成' style='height:28px;width:60px;' class='btn btn-default'/>");
		}else if(isCzgIndex<0){
			jAlert("请先使用维修工角色登录并操作","提示");
			return ;
		}else if(isCzgIndex>0&&roletype==3){
			jAlert("请先使用维修主管角色登录并操作","提示");
			return ;
		}else if(isCzgIndex<0&&roletype==3){
			jAlert("请先使用维修主管角色登录并操作","提示");
			return ;
		}
	},"JSON");
}

//完成
function showFinish(id,status,designated_person_id,count,designated_person_name,roletype){
	var html="";
	var html1="";
	var html2="";
	var html3="";
	$("#gzbp1"+count).html("");
	var roleIds = $("#sbUserRoleIds").html();//当前登陆人
	var isCzgIndex = roleIds.indexOf("402899894db72650014db78d4035004f");//维修工角色ID
	var isWXZGIndex = roleIds.indexOf("402899894db72650014db78daf010050");//维修主管角色ID
	if(roletype!=3&&isCzgIndex<0){
		jAlert("请先使用维修工角色登录并操作","提示");
		return false;
	}
	if(roletype==3&&isWXZGIndex<0){
		jAlert("请先使用维修主管角色登录并操作","提示");
		return false;
	}
	$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/querySlServiceInfoById.do?id="+id,function(o){
		if(o.status=="1"){
			$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/updateSlServiceInfo.do",{id:id,status:o.status,designated_person_name:designated_person_name,designated_person_id:designated_person_id});
			$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/updateStaff.do",{designated_person_id:designated_person_id,status:o.status});
			$("#span"+count).html("<font style='color:green;'>&nbsp已完成</font>");//
			$("#wanCheng"+count).html("<input type='hidden'  value='完成' style='height:28px;width:60px;' class='btn btn-default'/>");
			$("#zhuguan"+count).html("<input type='hidden'   value='请求主管' style='height:28px;width:100px;margin-top:-24px; margin-left: 70%;' class='btn btn-default'/>");
			$("#guZhang"+count).html("<input type='hidden'  value='故障' style='height:28px;width:60px;' class='btn btn-default'/>");
			//$("#sl").html("<input type='button' value='受理' style='height:28px;width:60px;'  class='btn btn-default'/>");
			//$("#sl").style("display","hidden");
			$("#sl"+count).css("display","none");
			$("#wc"+count).css("display","none");
			$("#agz"+count).css("display","none");
			$("#gz"+count).css("display","none");
			$("#hjzg"+count).css("display","none");
			//隐藏按钮
		}
	},"JSON");
	$.post("${pageContext.request.contextPath}/wct/eqm/fixrec/querySpareTrouble.do",{id:id},function(jsonn){
		//console.info(descri[0]);
		var descri=jsonn[0].description.split("；");
		var troubleName=jsonn[0].trouble_name.split("；");
		for(var j=0;j<jsonn.length;j++){
			var sparePartsNum=jsonn[j].spare_parts_num;
			var sparePartsName=jsonn[j].spare_prats_name;
			html2+="<tr><td width=10%>"+sparePartsName+"</td><td width=10%>"+sparePartsNum+"</td></tr>"
			}
		  for(var i=0;i<descri.length;i++){
			html1+="<tr><td width=10%>"+troubleName[i]+"</td><td width=10%>"+descri[i]+"</td></tr>"
		  }
		  html="<table style='margin-left: 3%;font-size:12px; width:50%'>"
            +" "+html1+" "
            +"</table><hr style='height:1px;border:none;border-top:1px;solid #ADADAD;margin-left:3%;margin-right:11%'>";
          html3="<table style='margin-left: 3%;font-size:12px;width:54%'>"
	        +" "+html2+" "
	        +"</table>"; 
          $("#gzbp1"+count).append(html);
    	  $("#gzbp1"+count).append(html3);
	},"Json");
}
function retType(val){
	if(val>=10){
		return val;
	}else{
		return "0"+val;
	}
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
				$("#sbUserId").attr("value",json.userId);//用户ID
				$("#sbUserName").html(json.name);
				$("#sbUserRole").html(json.rolesNames); 
				$("#sbUserRoleIds").html(json.roles); 
				$("#sbLoginStatus").html("登录成功");
				$("#hid_div").css("display","none");//隐藏弹出层
				//queryStaffAll();
				location.reload();
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
				$("#sbUserId").attr("value","");//用户ID
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
	 //工作内容
	var contextValue = {};
	var groupTypeFlag="${groupTypeFlag}";
	var isCzg = "false";
	var isLbg = "false";
	var jsType = "";//这里牵扯到 到哪个 session取值
	var bandParams;
	var pageIndex=1;
	//收缩事件
	$(document).ready(function(){
		var stime="${info.stime}";
		var etime="${info.etime}";
		if(stime!='' || etime!=''){
			$("input[name='stime']").attr("value",stime);
			$("input[name='etime']").attr("value",etime);
		}else{
			//页面进来，给时间空间赋值当天日期
			var mydate = new Date();
			var year=mydate.getFullYear();
			var month=mydate.getMonth()+1;
			var day=mydate.getDate();
			var str=year+"-"+retType(month)+"-"+retType(day);
			//将日期赋值给时间控件
			$("input[name='stime']").attr("value",str);
			$("input[name='etime']").attr("value",str);
		}
		   
			//判断日期是否小于10，如果小于10前面加0返回
			function retType(val){
				if(val>=10){
					return val;
				}else{
					return "0"+val;
				}
			}
			
			//重置
			$("#eqpMailPlan-reset").click(function(){
				$("#eqpMailPlan-wct-frm input[type!=button]").val(null);
			});
			
			$("input.mh_date").manhuaDate({					       
				Event : "click",//可选				       
				Left : 0,//弹出时间停靠的左边位置
				Top : -16,//弹出时间停靠的顶部边位置
				fuhao : "-",//日期连接符默认为-
				isTime : false,//是否开启时间值默认为false
				beginY : 2010,//年份的开始默认为1949
				endY :2049//年份的结束默认为2049
			});
			
		    //定义方法  
		    //获得显出方法  accept${st.count}
		    $("#firstpane p.menu_head").click(function(){
		    	var html3="";
		    	var html2="";
		    	var html1="";
		    	var html="";
			    //定义点击事件
			    $(this).addClass("current").next("div.menu_body").slideToggle(300).siblings("div.menu_body").slideUp("slow"); 
			    $(this).siblings().removeClass("current");
		    	var num=this.id;
		    	var jingID=$("#sa"+num).val();
		    	$("#gzbp2"+num).html("");
			    //查看故障，备品备件信息
			    $.post("${pageContext.request.contextPath}/wct/eqm/fixrec/querySpareTrouble.do",{id:jingID},function(jsonn){
		    	//console.info(jsonn[0]);
		    	$(".gz_bjf").html("");
		    	var html_bj="";
		    	var html_gz="";
		    	for(var j=0;j<jsonn.length;j++){
	    			var sparePartsNum=jsonn[j].spare_parts_num;
	    			var sparePartsName=jsonn[j].spare_prats_name;
	    			var stime=jsonn[j].stime;
	    			var type=jsonn[j].type_name;
	    			if(type=='1'){ //备件
	    				html_bj=html_bj+"<p class='bj_f'>【备件】：更换<font style='color:green;margin-right:10px;'>"+sparePartsName+"</font>"+sparePartsNum+"件&nbsp;&nbsp;&nbsp;&nbsp;["+stime+"]</p>";
	    			}else if(type=='2'){//故障
	    				html_gz=html_gz+"<p class='gz_f'>【故障】："+sparePartsName+"["+stime+"]</p>";
	    			}
		    	}
		    	$(".gz_bjf").html("<br/>"+html_bj+html_gz);
		    	
		    	},"Json");
		        //当前的互动额 查找所有类名 删除类名为current  
		    });
		    $("#secondpane .menu_body:eq(0)").show();  
		    //获得显出方法  
		    $("#secondpane p.menu_head").mouseover(function(){  
		    //定义鼠标触及方法  
		        $(this).addClass("current").next("div.menu_body").slideDown(500).siblings("div.menu_body").slideUp("slow");  
		        //当鼠标触及到当前添加一个current类 查找每个段落的下一个同胞元素名为div.menu_body滑动效果带有隐藏和显示功能 查找所有类名为div.menu_body的元素获得滑动方式隐藏  
		        $(this).siblings().removeClass("current");  
		        //当前的互动额 查找所有类名 删除类名为current  
		    });  
		    
		    //查询
		    $("#hisBadQty-search").click(function(){
		    	 $("#eqpMailPlan-wct-frm").submit();
		    });
		   
		      
	});  
	
	
</script>
</head>
<body>
<div id="eqpMailPlan-title">维修呼叫</div>
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
	<form id="eqpMailPlan-wct-frm" method="get" action="${pageContext.request.contextPath}/wct/eqm/fixrec/sysStaffPage.do">
		<table width="90%" cellspacing="0" cellpadding="0">
			<tr>	
				<td>
					<input type="button" onclick="openWxHjJsp('2');"  value="电气工" style="height:28px;width:100px;" class="btn btn-default"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				   <input type="button" onclick="openWxHjJsp('1');"  value="机械工" style="height:28px;width:100px;" class="btn btn-default"/>
				</td>
				<td>时间</td>
				<td><input  type="text" name="stime" class="mh_date" readonly="readonly" value="" style="width:110px;height:27px;"/></td>
				<td>到&nbsp;&nbsp;&nbsp;</td>
				<td><input type="text" name="etime" class="mh_date" readonly="readonly" value="" style="width:110px;height:27px;"/></td>
				<td style="width:76px;text-align:center;"><input type="button" id="hisBadQty-search" value="查询" style="height:28px;width:50px;" class="btn btn-default"/></td>
			    <td style="width:86px;text-align:center;"><input type="button" id="eqpMailPlan-reset" value="重置" style="height:28px;width:50px;" class="btn btn-default"/></td>
			</tr>
		</table>
	</form>
</div>

<div style="height:476px;margin-top:13px;width:824px;margin-left:7px;border:0px;padding:3px; PADDING:0px; LINE-HEIGHT: 20px; OVERFLOW: auto;">
     <div id="firstpane" class="menu_list">
       <c:if test="${!empty listInfo}">
        <c:forEach  items="${listInfo}" var="lif" varStatus="st"> 
         <p class="menu_head" id="${st.count}">
           <input id="sa${st.count}" type="hidden" value="${lif.id}"/>
           <span>请求时间：<font class="spanstl"><fmt:formatDate value="${lif.create_user_time}" pattern="yyyy-MM-dd HH:mm:ss"/></font></span>
           <span>类型：<font class="spanstl">${lif.type_name}</font></span>
           <span>受理人姓名：
               <font class="spanstl" id="accept${st.count}">
	              <c:if test="${lif.status!=0}"><font>${lif.update_user_name}</font></c:if>   
			   </font>
           </span>
           <span>状态：
	           <font class="spanstl" id="span${st.count}">
		           <c:if test="${lif.status==0}"><font style="color:red;">等待中</font></c:if>
		           <c:if test="${lif.status==1}"><font style="color:red;">维修中</font></c:if>
		           <c:if test="${lif.status==2}">已完成</c:if>
	           </font>
           </span>
         </p>  
         <div class="menu_body">
            <ul>  
              <li>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			             请求人：    <font class="divulli">${lif.create_user_name}</font>  &nbsp;&nbsp;&nbsp; 
			             请求时间：<font class="divulli"><fmt:formatDate value="${lif.create_user_time}" pattern="yyyy-MM-dd HH:mm:ss"/></font> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			            维修工姓名：    <font class="divulli">${lif.designated_person_name}</font>  &nbsp;&nbsp;&nbsp; 
			             到场时间：
		             <font class="divulli" id="createUserTime${st.count}" >
			             <c:if test="${lif.designated_person_time!=''}">
			                <fmt:formatDate  value="${lif.designated_person_time}" pattern="yyyy-MM-dd HH:mm:ss"/>
			              </c:if>
		             </font>
              </li> 
               <li>
                  <font class="gz_bjf"> 
                     
                  </font>
               </li>
              
               <li><br/>
                <c:if test="${lif.status!=2}">
	                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        <input type="button" id="sl${st.count}" onclick="showTimeSave('${lif.id}','${lif.status}','${lif.designated_person_id}','${st.count}','${lif.designated_person_name}','${lif.roletype}');"  value="受理" style="height:28px;width:60px;" class="btn btn-default"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		        </c:if>
		        <font id="wanCheng${st.count}"></font>
		        
		        <c:if test="${lif.status==1}">
			       <input type="button" id="wc${st.count}" onclick="showFinish('${lif.id}','${lif.status}','${lif.designated_person_id}','${st.count}','${lif.designated_person_name}','${lif.roletype}');"  value="完成" style="height:28px;width:60px;" class="btn btn-default"/>
			    </c:if>
			    <font id="guZhang${st.count}"></font>
			    
			    <c:if test="${lif.status==1}">
			        <a id="agz${st.count}"  href='${pageContext.request.contextPath}/wct/eqm/eqm_troubleInfo.jsp?source=2&&type=2&&id="${lif.id}"&&sbPid=0&&role=0' style="margin-top:-28px; margin-left: 190px;width:60px"><input type="button"  id="gz" value="故障" style="height:28px;width:60px;" class="btn btn-default"/></a>
			    </c:if>
			    <font id="zhuguan${st.count}"></font>
			    
			    <c:if test="${lif.status==1}">
			        <input id="hjzg${st.count}" type="button" onclick="openWxHjJsp(3);"  value="请求主管" style="height:28px;width:80px;margin-top:-24px; margin-left: 70%;" class="btn btn-default"/>
			    </c:if>
			    <font id="gzbp1${st.count}"></font>
			    
			    <c:if test="${lif.status==2}">
			        <font id="gzbp2${st.count}"></font>
			    </c:if>
              </li>
              <br/>
            </ul>
            
         </div> 
       </c:forEach>
       </c:if>
    </div><!--firstpane end-->
</div>



<!--轮保新增维修页面维修 -->
<jsp:include page="eqp_wxhj_jsp.jsp"></jsp:include>
<!--轮保新增维修页面维修处理 -->
<jsp:include page="eqp_wxcl_jsp.jsp"></jsp:include>
</body>
</html>