<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<title>现场协同目录</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global_menu.css"></link>

<script type="text/javascript">
	$(function(){
		$(".prods-link").click(function(){
			var url="${pageContext.request.contextPath}"+$(this).attr("path");
			$("#sch").attr("src",url);
		});
	});
</script>
	<div style="padding: 5px;">
		<ul class="nav nav-pills nav-stacked" id="prods-menu">
		  <li class="active"><a href="javascript:void(0)" class="prods-link" path="/wct/call/matRequest/matRequest.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>呼叫物料</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/call/maintain/maintain.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>维修呼叫</a></li>
		</ul>
<!-- 			点击顶部菜单按钮赋予状态样式class -->
	  <script type="text/javascript">
		$('#prods-menu li').bind('click', function(){
			//alert(1);
			$('#prods-menu li').removeClass('active');
		    $(this).addClass('active');
		});
	  </script>

	</div>