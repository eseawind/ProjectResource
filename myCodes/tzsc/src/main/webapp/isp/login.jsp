<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>集中监控系统登录</title>
<meta name="author" content="leejean" />
<meta charset="UTF-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/isp/css/bootstrap.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/isp/css/bootstrap-responsive.min.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/isp/css/matrix-login.css" />
<link href="${pageContext.request.contextPath}/isp/font-awesome/css/font-awesome.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script>
<script type="text/javascript">
    //校验表单，用户登录
	function submitForm(){
	   //if($("#myForm").form('validate')){
		   $.ajax({
				type : "POST",
				dataType:"JSON",
				url : "${pageContext.request.contextPath}/isp/login.do",
				data : {
					"loginName" : $("#username").val(),
					"pwd" : $("#password").val()
				},
				success : function(r) {
					if(r.success){
						window.location = "${pageContext.request.contextPath}/isp/index.jsp";
					}else{
						$("#password").val(null).attr("placeholder",r.msg);
					}
				}
			});
	 //  }
	}
/* 	//清空表单数据
	function clearForm(){
		$("#username").val(null);
		$("#password").val(null);
	} */
	//回车提交表单		
	$(function(){
		$("#username").focus();
		document.onkeydown = function(e){
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		    	submitForm();//触发登录按钮click事件		    	
		     }
		};
	});
</script>
    </head>
    <body>
        <div id="loginbox" style="background-color: gray;">            
            <form  id="loginform" class="form-vertical" action="http://themedesigner.in/demo/matrix-admin/index.html">
				 <div class="control-group normal_text"> <h3><img src="${pageContext.request.contextPath}/isp/img/isp_logo_login.png" alt="Logo" /></h3></div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_lg"><i class="icon-user"></i></span><input type="text" id="username" placeholder="用户名" />
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <div class="main_input_box">
                            <span class="add-on bg_ly"><i class="icon-lock"></i></span><input type="password" id="password"  placeholder="密码" />
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <span class="pull-left" style="color:yellow;">初始密码为:123456</span>
                    <span class="pull-right" style="margin-right: 20px;" ><a  href="javascript:submitForm();" id="submit" class="btn btn-success" >登录</a></span>
                </div>
            </form>
        </div>
        
        <script src="${pageContext.request.contextPath}/isp/js/jquery.min.js"></script>  
        <script src="${pageContext.request.contextPath}/isp/js/matrix.login.js"></script> 
    </body>

</html>
