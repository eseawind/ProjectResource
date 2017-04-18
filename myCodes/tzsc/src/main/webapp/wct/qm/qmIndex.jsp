<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<title>质量自检首页</title>
<style type="text/css">
*{margin:0;padding:0;font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;}
/* 	#qm-idx{
		width:1015px;
		height:622px;
		background:#CCCCCC;
		padding-left:4px;
		padding-top:2px;
		margin:0px auto;
	} */
	#qm-idx {
		width: 1010px;
		height: 622px;
		margin: 0px auto;
		border-radius: 6px;
		border: 1px solid #979595;
	}
	#qm-idx-menu{
		float: left;
		width: 170px;
		border-right: 1px solid #999;
		height: 100%;
		
	}
	#qm-idx-content{
		float: left;
		width: 839px;
		height: 622px;
		border-radius: 0px 6px 6px 0px;
	}
</style>
<script type="text/javascript">

	$(function(){
		//登录验证
		var loginname = "${loginInfo.equipmentCode}";
		if(loginname==null || loginname=="" || loginname.length==0){
			window.location="${pageContext.request.contextPath}/wct/sys/login.jsp";
		} 
		$.post("${pageContext.request.contextPath}/wct/qm/qmMenu.jsp",function(v){//调用默认网页
			$("#qm-idx-menu").html(v);
		});
	});
</script>
</head>
<body>
	<div id="qm-idx">
		<div id="qm-idx-menu"></div>
		<div id="qm-idx-content">
			<iframe id="work" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/qm/qm_prod.jsp" style="width:100%;height:100%"></iframe>
		</div>
	</div>
</body>
</html>