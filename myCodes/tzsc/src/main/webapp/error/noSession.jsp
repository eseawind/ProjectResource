<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>登录超时</title>
<meta name="author" content="leejean">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
<%-- <meta http-equiv="refresh" content="3;url=${pageContext.request.contextPath}/pms/sys/user/login.jsp"> --%>
<script type="text/javascript">
	function reLogin(){
		window.top.location="${pageContext.request.contextPath}/pms/sys/user/login.jsp";
	}
</script>
</head>

<body style="text-align:center;padding:10px;">
	<h2>哎呀,登录超时啦!</h2>
	<img alt="登录超时" src="${pageContext.request.contextPath}/img/blue_face/bluefaces_26.png">
	<br>
	<a href="javascript:reLogin()">立即登录</a>	
</body>
</html>