<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>卷接包车间数据采集系统-登录</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/pms/sys/user/css/zice.style.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/pms/sys/user/css/tipsy.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/pms/sys/user/css/icon.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/pms/sys/user/css/buttons.css">
	<script type="text/javascript" src="${pageContext.request.contextPath}/pms/sys/user/js/jquery-1.8.0.min.js" ></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery.cookie.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/pms/sys/user/js/iphone.check.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/pms/sys/user/js/jquery-jrumble.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/pms/sys/user/js/jquery.tipsy.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/pms/sys/user/js/login.js"></script>
	<script type="text/javascript">
		if(top!=self){
			if(top.location != self.location)
			 top.location=self.location; 
		}
	</script>
	 <style type="text/css">
html {
	background-image: none;
}

label.iPhoneCheckLabelOn span {
	padding-left: 0px
}

#versionBar {
	background-color: #212121;
	position: fixed;
	width: 100%;
	height: 35px;
	bottom: 0;
	left: 0;
	text-align: center;
	line-height: 35px;
	z-index: 11;
	-webkit-box-shadow: black 0px 10px 10px -10px inset;
	-moz-box-shadow: black 0px 10px 10px -10px inset;
	box-shadow: black 0px 10px 10px -10px inset;
}

.copyright {
	text-align: center;
	font-size: 10px;
	color: #CCC;
}

.copyright a {
	color: #A31F1A;
	text-decoration: none
}

/*update-begin--Author:tanghong  Date:20130419 for：【是否】按钮错位*/
.on_off_checkbox{
	width:0px;
}
/*update-end--Author:tanghong  Date:20130419 for：【是否】按钮错位*/
#login .logo {
	width: 500px;
	height: 51px;
}
#cap{
margin-left: 88px;
}
.tip{margin-top:15px;}
</style>
  </head>
  <body>
	<div id="alertMessage"></div>
	<div id="successLogin"></div>
	<div class="text_success">
		<img src="${pageContext.request.contextPath}/pms/sys/user/img/loader_green.gif" alt="Please wait" /> <span>登陆成功!请稍后....</span>
	</div>
	<div id="login">
		 <%-- <div class="ribbon" style="background-image:url(${pageContext.request.contextPath}/pms/sys/user/img/typelogin.png)"></div> --%>
		<div class="inner">
			<div class="logo">
				<img src="${pageContext.request.contextPath}/pms/sys/user/img/banner_logo_login.png" />
			</div>
			<div class="formLogin">
				<form name="formLogin" action="${pageContext.request.contextPath}/pms/sysUser/login.do" id="formLogin" method="post">
					<input name="userKey" type="hidden" id="userKey" value="D1B5CC2FE46C4CC983C073BCA897935608D926CD32992B5900" />
					<div class="tip">
						<input class="userName" name="loginName" type="text" id="userName" title="用户名" iscookie="true" value="" nullmsg="请输入用户名!" />
					</div>
					<div class="tip">
						<input class="password" name="pwd" type="password" id="password" title="密码" value="" nullmsg="请输入密码!" />
					</div>
					<div class="loginButton">
						<div style="float: left; margin-left: -9px;">
							<input type="checkbox" id="on_off" name="remember" checked="ture" class="on_off_checkbox" value="0" /> <span class="f_help">是否记住用户名?</span>
						</div>
						<div style="float: right; padding: 3px 0; margin-right: -12px;">
							<div>
								<ul class="uibutton-group">
									<li><a class="uibutton normal" href="javascript:void(0);" id="but_login">登录</a>
									</li>
									<li><a class="uibutton normal" href="javascript:void(0);" id="forgetpass">重置</a>
									</li>
								</ul>
							</div>
							<div style="float: left; margin-left: 30px;">
								<a href="javascript:void(0);"><span class="f_help">首次登录默认密码为:123456</span></a>
							</div>
						</div>
						<div class="clear"></div>
					</div>
				</form>
			</div>
		</div>
		<div class="shadow"></div>
	</div>
	<!--Login div-->
	<div class="clear"></div>
	<div id="versionBar">
		<div class="copyright">&copy; 版权所有 <span class="tip"><a href="http://www.shlanbao.cn" title="http://www.shlanbao.cn">上海兰宝传感科技股份有限公司</a>
				(推荐使用IE9+,谷歌浏览器可以获得更快,更安全的页面响应速度)</span>
		</div>
	</div>
</body>
</html>


