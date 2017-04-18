<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- jq 核心文件 -->
<script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>

<title>系统管理首页</title>
<style type="text/css">
*{margin:0;padding:0;font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;}
	#prods-idx{
		width: 1010px;
		height: 622px;
		margin: 0px auto;
		border-radius: 6px;
		border: 1px solid #979595;
	}
	#prods-idx-menu{
		float: left;
		width: 170px;
		border-right: 1px solid #999;
		height: 100%;
		
	}
	#prods-idx-content{
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
			$.post("${pageContext.request.contextPath}/wct/sys/systemMenu.jsp",function(v){
				$("#prods-idx-menu").html(v);
			});
		});
</script>
</head>
<body>
	<div id="prods-idx">
		<div id="prods-idx-menu"></div>
		<div id="prods-idx-content"><iframe id="work" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/sys/updatePwd.jsp" style="width:100%;height:100%"></iframe></div>
	</div>
</body>
</html>