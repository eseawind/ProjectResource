<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>山东中烟工业公司滕州卷烟厂数据采集系统- 登陆</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<script src="${pageContext.request.contextPath}/wct/sys/js/placeholder.js" type="text/javascript" ></script>
<link  href="${pageContext.request.contextPath}/wct/sys/css/register.css" rel="stylesheet" type="text/css"/>

<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/fullScreen.js" type="text/javascript" ></script>

<meta http-equiv="refresh" content="60;url=${pageContext.request.contextPath}/wct">
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<jsp:include page='../../initlib/initKeyboard.jsp' ></jsp:include>

<script src="${pageContext.request.contextPath}/wct/js/wctTools.js" type="text/javascript" ></script>

</head>
<body>
<div class='signup_box'>
<div class='signup_container'>
    <h1 class='signup_title'>山东中烟工业有限责任公司滕州卷烟厂数据采集系统</h1>    
    <div id="signup_forms" class="signup_forms clearfix">
            <form class="signup_form_form" id="signup_form" method="post" action="#">
                    <div class="form_row first_row"><!-- value="admin" -->
                        <input type="text" name="loginName" placeholder="请输入用户名"  id="signup_name"  class="keyboard">
                    </div>
                    <div class="form_row"><!--value="lanbaoit"  -->
                        <input type="password" name="passWord" placeholder="请输入密码" id="signup_password"  class="keyboard">
                    </div>
					<div class="form_row">
                        <input type="text" name="shift" readonly="true" placeholder="班次信息" id="signup_select0" >
                        <input type="hidden" name="shiftId" readonly="true" placeholder="班次号主键" id="shiftId" >
                        <input type="hidden" name="shiftCode" readonly="true" placeholder="班次号" id="shiftCode" >
                    </div>
                    <div class="form_row">
                        <input type="text" name="team" readonly="true" placeholder="班组信息" id="signup_select" >  
                        <input type="hidden" name="teamId" readonly="true" placeholder="班组主键" id="teamId" >  
                        <input type="hidden" name="teamCode" readonly="true" placeholder="班组号" id="teamCode" >                    
                    </div>
					<div class="form_row2">
                        <input type="text" name="equipmentName" readonly="true" placeholder="机台信息" id="signup_select2" >                            
                    </div>
                    <div class="form_row">
                        <input type="text" name="equipmentType" readonly="true" placeholder="机台型号" id="signup_select" >                        
                        <input type="hidden" name="equipmentCode" readonly="true" placeholder="机台型号" id="signup_select" >
                        <input type="hidden" name="workshop" readonly="true" placeholder="车间" id="workshop" >  
                        <input type="hidden" name="workshopCode" readonly="true" placeholder="车间" id="workshopCode" >                         
                    </div>
           </form>
    </div>
    <div class="login-btn-set">
		<a href="javascript:void(0)" onclick="submitForm();" class='login-btn'id="submit"></a>
		<a href="javacript:;" onclick="CloseWindow();" class='login-btn-close'></a>
	</div>
	<div class="hr" style="border-bottom: 1px dotted #999;"></div>
    <p class='loading' style="display: none;"><img src="${pageContext.request.contextPath}/wct/sys/images/5-121204193R2-50.gif" width="16" height="16"style=" padding-right: 10px; " />正在加载通讯适配器...</p>
    <p class='copyright'>上海兰宝传感科技股份有限公司</p>
</div>
</div>

</body>
<script type="text/javascript" >
	

//回车提交	
$(function(){
	document.onkeydown = function(e){
		var ev = document.all ? window.event : e;
		if(ev.keyCode==13) {
			$('#submit').click();//
		 }
	};
	$('.keyboard').keyboard();
	//实例化登录信息（包括机台，班次班组）
	$.post("${pageContext.request.contextPath}/wct/system/initLoginInfo.do",function(json){
		$("#signup_form input[name='shift']").val(json.shift);
		$("#signup_form input[name='team']").val(json.team);
		$("#signup_form input[name='equipmentName']").val(json.equipmentName);
		$("#signup_form input[name='equipmentType']").val(json.equipmentType);
		$("#signup_form input[name='equipmentCode']").val(json.equipmentCode);
		$("#signup_form input[name='shiftId']").val(json.shiftId);
		$("#signup_form input[name='shiftCode']").val(json.shiftCode);
		$("#signup_form input[name='teamId']").val(json.teamId);
		$("#signup_form input[name='teamCode']").val(json.teamCode);
		$("#signup_form input[name='workshop']").val(json.workshop);
		$("#signup_form input[name='workshopCode']").val(json.workshopCode);
		if(json.equipmentCode<31){
			$("#signup_form input[name='loginName']").val("");
			$("#signup_form input[name='passWord']").val("");
		}else if(json.equipmentCode<61){
			$("#signup_form input[name='loginName']").val("");
			$("#signup_form input[name='passWord']").val("");
			
		}
	},"JSON");
	
});
//提交登录信息
function submitForm(){
		   $(".loading").css("display","block");
		   $.post("${pageContext.request.contextPath}/wct/system/login.do",getJsonData($("#signup_form")),function(json){
			   if(json.name){
				   window.location="${pageContext.request.contextPath}/wct/index.jsp";
			   }else{
				   $(".loading").css("display","none");
				   alert("账号或密码错误！请重新登录！");
			   }
		   },"JSON");		
}
//关闭窗口
function CloseWindow(){
	window.opener=null;
	window.open('','_self'); window.close();
}
</script>
</html>