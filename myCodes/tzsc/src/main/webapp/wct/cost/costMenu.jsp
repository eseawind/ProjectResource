<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<title>成本考核目录</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global_menu.css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/wct_icon.css">

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
	
	
		  <li <c:if test="${loginInfo.equipmentCode>30}"> style="display: none;" </c:if> class="active" >
		  	<a href="javascript:void(0)" class="prods-link" path="/wct/stat/ctm_rollers.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷接机成本对比</a>
		  </li>
		  <li <c:if test="${loginInfo.equipmentCode<31||loginInfo.equipmentCode>60}"> style="display: none;" </c:if> class="active" >
		  	<a href="javascript:void(0)" class="prods-link" path="/wct/stat/ctm_pack.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>包装机成本对比</a>
		  </li>
		  <!-- 
		  <li>
		  	<a href="javascript:void(0)" class="prods-link" path="/wct/cost/eqpDayEffic.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>机台工段日报</a>
		  </li>
		   -->
		 
	</ul>
<!-- 			点击顶部菜单按钮赋予状态样式class -->
 <script type="text/javascript">
		$('#prods-menu li').bind('click', function(){
			//console.info(this);
			$('#prods-menu li').removeClass('active');
		    $(this).addClass('active');
		   
		});
 </script>
	</div>