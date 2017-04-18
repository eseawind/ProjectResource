<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登陆页</title>
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
<link href="${pageContext.request.contextPath}/wct/sys/css/login.css"  rel="stylesheet" >
<script type="text/javascript">
//光标默认选中文本框
$(function(){
	 
	//实例化登录信息（包括机台，班次班组）
		$.post("${pageContext.request.contextPath}/wct/system/initLoginInfo.do",function(json){
			$("#signup_form input[name='equipmentCode']").val(json.equipmentCode);
			$("#signup_form input[name='equipmentName']").val(json.equipmentName);
			$("#signup_form input[name='equipmentType']").val(json.equipmentType);
			$("#signup_form input[name='shift']").val(json.shift);
			$("#signup_form input[name='shiftId']").val(json.shiftId);
			$("#signup_form input[name='shiftCode']").val(json.shiftCode);
			$("#signup_form input[name='team']").val(json.team);
			$("#signup_form input[name='teamId']").val(json.teamId);
			$("#signup_form input[name='teamCode']").val(json.teamCode);
			$("#signup_form input[name='workshop']").val(json.workshop);
			$("#signup_form input[name='workshopCode']").val(json.workshopCode);
		},"JSON");
		$("#cardNum").focus(); 
	 //自动回车
	 document.onkeydown=function(event){
	      var e = event || window.event || arguments.callee.caller.arguments[0];    
	       if(e && e.keyCode==13){ // enter 键
	          var carn= $("#cardNum").val(); //获取文本框值
	          if(carn=""){
	        	  $("#error_").text("");
    			  $("#error_").text("刷卡失败，请再次刷卡!");
    			  $("#cardNum").val("");
    			  $("#cardNum").focus();
	          }else{
	        	  /* $.post("${pageContext.request.contextPath}/wct/system/login.do",getJsonData($("#signup_form")),function(json){
		   			   if(json.name){
		   				   window.location="${pageContext.request.contextPath}/wct/index.jsp";
		   			   }else{
		   				   $(".loading").css("display","none");
		   				   alert("账号或密码错误！请重新登录！");
		   			   }
	   		      },"JSON"); */
	        	  $.ajax({ url: "${pageContext.request.contextPath}/wct/system/login.do", 
	        		  data:{
	        			    "equipmentCode":$("#signup_form input[name='equipmentCode']").val(),
	        				"equipmentName":$("#signup_form input[name='equipmentName']").val(),
	        				"equipmentType":$("#signup_form input[name='equipmentType']").val(),
	        				"shift":$("#signup_form input[name='shift']").val(),
	        				"shiftId":$("#signup_form input[name='shiftId']").val(),
	        				"shiftCode":$("#signup_form input[name='shiftCode']").val(),
	        				"team":$("#signup_form input[name='team']").val(),
	        				"teamId":$("#signup_form input[name='teamId']").val(),
	        				"teamCode":$("#signup_form input[name='teamCode']").val(),
	        				"workshop":$("#signup_form input[name='workshop']").val(),
	        				"workshopCode":$("#signup_form input[name='workshopCode']").val(),
	        				"eno":$("#cardNum").val()
	        		  },
	        		 /*  contentType: "application/json; charset=utf-8",
	                  dataType: "json", */
	                  type: "POST",
	        		  success: function(data){
	        			  var json=eval('(' + data + ')'); 
	        			  if(json.name){
	        				  window.location="${pageContext.request.contextPath}/wct/index.jsp";
	        			  }else{
	        				  $("#error_").text("");
		        			  $("#error_").text("刷卡失败，请再次刷卡!");
		        			  $("#cardNum").val("");
		        			  $("#cardNum").focus();
	        			  } 
	        	      },error:function(){
	        	    	  $("#error_").text("");
	        			  $("#error_").text("刷卡失败，请再次刷卡!");
	        			  $("#cardNum").val("");
	        			  $("#cardNum").focus();
	        	      }
	        	  });
	          }
	      }
	 };
});
 

function gotoLoginSd(){
	window.location.href="${pageContext.request.contextPath}/wct/sys/login_sd.jsp";
	//$("#cardNum").focus();
}
</script>
</head>

<body id="wrap_login">
<input type="text" id="cardNum" name="cardNum" value=""  style="width:1px;height: 1px;margin-top:-10px;"/>
<div >
  <div class="login_middle">
	<div class="login_logo" onclick="gotoLoginSd()"><p class="loginlogo_top"><img src="${pageContext.request.contextPath}/wct/sys/images/login2_icon.png" width="185" height="189" /></p>
    <br/>
    
    <p class="loginlogo_writ">欢迎使用刷卡登录~~</p>
    <br/>
    <font>提醒：如登录失败,请联系管理员录入卡信息，谢谢！</font>
    <br/>
    <p id="error_" style="color:red;"> </p>
    </div>
    <div class="clearfix"></div>
  </div>
</div>
<div id="signup_forms" class="signup_forms clearfix">
 <form class="signup_form_form" id="signup_form" method="post" action="#">
      <input type="hidden" name="equipmentCode" readonly="true" placeholder="机台型号" id="signup_select" >
      <input type="hidden" name="equipmentName" readonly="true" placeholder="机台信息" id="signup_select2" >                            
      <input type="hidden" name="equipmentType" readonly="true" placeholder="机台型号" id="signup_select" >                                 
      <input type="hidden" name="shift" readonly="true" placeholder="班次信息" id="signup_select0" >
      <input type="hidden" name="shiftId" readonly="true" placeholder="班次号主键" id="shiftId" >
      <input type="hidden" name="shiftCode" readonly="true" placeholder="班次号" id="shiftCode" >
      <input type="hidden" name="team" readonly="true" placeholder="班组信息" id="signup_select" >  
      <input type="hidden" name="teamId" readonly="true" placeholder="班组主键" id="teamId" >  
      <input type="hidden" name="teamCode" readonly="true" placeholder="班组号" id="teamCode" >                    
      <input type="hidden" name="workshop" readonly="true" placeholder="车间" id="workshop" >  
      <input type="hidden" name="workshopCode" readonly="true" placeholder="车间" id="workshopCode" >                           
</form>
</div>
</body>
</html>
