<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<title>卷接包车间数据采集系统-ISP登录</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<style type="text/css">
html, body{ height: 100%;}
*{margin:0;padding:0;}
body{height:100%;behavior:url("css/hover_htc.htc");font-family:"Microsoft YaHei",宋体; color:#333;background: url("${pageContext.request.contextPath}/pms/sys/user/img/login_bg.gif") no-repeat scroll center center #fff;}/*hover*/
.login_box {height: 194px;left: 50%;margin: -150px 0 0 -230px;/*padding: 5px;*/position: absolute;top: 50%; width: 472px;z-index: 0;}
.login_win{position: relative; width: 472px; height: 194px; z-index:999;margin-top: 70px;}
.login_win .boxTitle, .login_win .boxTitle span, .login_win .boxTitle span.hover, .login_win .loadinglayer, .login_win .tipslayer, .login_win .arrowLeft, .login_win .colseBtn, .login_win .boxError em, .login_win .dialogBtn, .login_win .dialogBtn.hover { background-image: url("images/ico.png"); background-repeat: no-repeat; }
.login_win .boxTitle { position: relative; border: 1px solid #A6C9E1; border-bottom: none; background-position: 0 0; background-repeat: repeat-x; height: 30px; line-height: 30px; }
.login_win .boxTitle h3 { float: left; color: #666; font-size: 14px; font-weight: bold;margin: 0; text-indent: 10px; }
.login_win .boxTitle span { position: absolute; width: 10px; background-position: -80px -40px; text-indent: -10em; right: 10px; top: 10px; height: 16px; overflow: hidden; cursor: pointer; }
.login_win .boxTitle span.hover { background-position: -90px -40px; }
.login_win .loadinglayer { line-height: 40px; background-position: 0 -100px!important; }
.login_win .tipslayer { line-height: 20px; text-align: left; }
.login_win .arrowLeft { position: absolute; width: 8px; height: 16px; background-position: -20px -170px; text-indent: -9999em; z-index: 20591; overflow: hidden; }
.login_win .colseBtn { position: absolute; top: 5px; right: 5px; width: 8px; height: 8px; background-position: -55px -170px; text-indent: -9999em; cursor: pointer; z-index: 20591; overflow: hidden; }
.login_win .boxError { position: absolute; left: 50%; top: 50%; margin-left: -60px; margin-top: -15px; width: 120px; height: 30px; line-height: 30px; color: #f00; }
.login_win .boxError em { float: left; width:30px; height: 30px; background-position: -35px -140px; }
.login_win .dialogBtn { margin: 5px 5px 0 0; width:80px; height:35px; background-position: 0 -30px; border:none; color:#333; }
.login_win .dialogBtn.hover { background-position: 0 -65px; }
.login_win.shadow { box-shadow:2px 2px 5px #C0BBB5; -moz-box-shadow: 2px 2px 5px #C0BBB5; -webkit-box-shadow:2px 2px 5px #C0BBB5; }

.l_button,.r_button{/*background: url("./img/login_button.png") no-repeat; */width:118px; height:39px; border:none; cursor:pointer; display:block; float:left; /*text-indent:-9000px*/}
.l_button{background-position:0 -60px;}
.r_button{background-position:-138px -60px; margin-right:4px}
.l_button:hover{background-position:0 0;}
.r_button:hover{background-position:-138px 0;}
.f_reg_but{margin:10px 0 0 115px}
.reg{width:390px;; font-size:14px;line-height:25px; overflow:hidden;text-align: center;}
.reg dl{padding-left:10px; font-size:14px;}
.reg dl dt{ margin-top:15px}
.reg dl dd{padding:3px 0}
.reg .title{width:100px; display:inline-block; text-align:right; padding-right:10px}
.reg_input_pic{width:80px;}
.in_pic_s{margin-left:83px}
.reg .img{position:absolute}


.id input,.pw input,.in_id,.in_mo,.reg_input,.reg_input_pic{_behavior:url(js/Round_htc.htc);-moz-border-radius:4px;-webkit-border-radius:4px;border-radius:4px; height:25px;min-width: 220px;}
.user_button a,.pay_but{_behavior:url(js/Round_htc.htc);-moz-border-radius:100px;-webkit-border-radius:100px;border-radius:100px;}
.boxWrap{position: relative; top: 5px; margin: 0px auto; width: 462px; height: 184px; overflow: hidden; z-index: 20590; opacity: 1;}
.boxTitle{position: relative; width: 390px; border-color: rgb(166, 201, 225); cursor: move;}
.boxContent{position: relative; width: 390px; height: 150px; padding: 0px;  overflow-x: auto; overflow-y: hidden; }
.boxDialog{width: 390px; height: 0px; border-width: medium 0px 0px; border-style: none solid solid; border-color: -moz-use-text-color rgb(166, 201, 225) rgb(166, 201, 225); text-align: right;}
.boxBd{position: absolute; width: 472px; height: 194px; left: 0px; top: 0px; opacity: 1; z-index: 10715;}
.sub{font-size: 14px;padding: 2px 8px;margin-left:30px;}
.bt_l{margin-left:57px;}
.reg_box{margin-bottom:10px}
#logo{position: absolute;top: 120px;left: 300px;}
</style>
<script type="text/javascript">
    function setDivLocation(){
    	//var divStyle = document.getElementById("").style;
    	var height = document.body.clientHeight;
    	var screenHeight = window.screen.height;
    	var marginHeight = (screenHeight-height)/2;
    	//document.getElementById("login_div").style.marginTop = marginHeight +"px";
    }
    //校验表单，用户登录
	function submitForm(){
	   //if($("#myForm").form('validate')){
		   $.ajax({
				type : "POST",
				dataType:"JSON",
				url : "${pageContext.request.contextPath}/pms/sysUser/login.do",
				data : {
					"loginName" : document.getElementById("username").value,
					"pwd" : document.getElementById("password").value
				},
				success : function(r) {
					if(r.success){
						window.location = "${pageContext.request.contextPath}/isp/index.jsp";
					}else{
						 $.messager.alert("提示",r.msg);
					}
				}
			});
	 //  }
	}
	//清空表单数据
	function clearForm(){
		$("#username").val(null);
		$("#password").val(null);
	}
	//回车提交表单		
	$(function(){
		document.onkeydown = function(e){
		    var ev = document.all ? window.event : e;
		    if(ev.keyCode==13) {
		        $('#submit').click();//触发登录按钮click事件		    	
		     }
		};
		});
	//为输入框自动加载焦点
	$(document).ready(function(){
		$("#username").focus();
	}); 
</script>


</head>
<body onload="setDivLocation();">
	<div id="logo">
			<img src="${pageContext.request.contextPath}/pms/sys/user/img/banner_logo_login.png" />
			&nbsp; &nbsp;
			&nbsp; &nbsp;
			&nbsp; &nbsp;
			&nbsp; &nbsp;
			<span style="font-weight:bold;">ISP集中监控</span>
	</div>
	<!--登录开始-->
	<div class="login_box">
		<div class="login_win">
			<div class="boxWrap">
				
				<div class="boxContent">
					<div class="reg login">					
						<form method="post" id="myForm" name="myForm">
							<dl class="reg_box">
								<div style="height:17px; overflow:hidden"></div>
								<dd><span class="title">登录账号：</span><input type="text" data-options="required:true" id="username" name="username"  placeholder="请输入您的登录名或工号" class="reg_input" value=""/></dd>
								<dd><span class="title">登录密码：</span><input type="password" data-options="required:true" name="password" id="password"  placeholder="请输入您的密码" class="reg_input" value=""/></dd>
							</dl>							
							<input id="submit" class="sub bt_l" type="button" onclick="submitForm();" value="登录">
							<input id="reset" class="sub bt-r" type="button" onclick="clearForm();" value="重置">
							

						</form>                      
					</div>
				</div>
				<div class="boxDialog"></div>
			</div>
			<div class="boxBd"></div>		
		</div>
		<div style="clear:both;"></div>
	</div>
<!--登录结束-->   
</body>
</html>