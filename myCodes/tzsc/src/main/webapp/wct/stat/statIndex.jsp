<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>

<title>生产统计</title>
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
			$.post("${pageContext.request.contextPath}/wct/stat/statMenu.jsp",function(v){
				$("#prods-idx-menu").html(null).html(v);
			});
		});
</script>
</head>
<body>
	<div id="prods-idx">
		<div id="prods-idx-menu"></div>
		<div id="prods-idx-content">
		<c:if test="${loginInfo.equipmentCode>0&&loginInfo.equipmentCode<31}"> 
		<iframe id="work" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/stat/realTimeOutput_roller.jsp" style="width:100%;height:100%"></iframe>
		</c:if>
		<c:if test="${loginInfo.equipmentCode>30&&loginInfo.equipmentCode<61}">
		<iframe id="work" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/stat/realTimeOutput_packer.jsp" style="width:100%;height:100%"></iframe>
		</c:if>
		<c:if test="${loginInfo.equipmentCode>60&&loginInfo.equipmentCode<71}">
		<iframe id="work" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/stat/realTimeOutput_boxer.jsp" style="width:100%;height:100%"></iframe>
		</c:if>
		<c:if test="${loginInfo.equipmentCode>100&&loginInfo.equipmentCode<131}">
		<iframe id="work" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/stat/realTimeOutput_filter.jsp" style="width:100%;height:100%"></iframe>
		</c:if>
		
		<%--<c:if test="${groupTypeFlag==2}">
		<iframe id="work" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/prodsta/RtOut_ChengXing.jsp" style="width:100%;height:100%"></iframe>
		</c:if> --%>
		</div>
	</div>
</body>
</html>