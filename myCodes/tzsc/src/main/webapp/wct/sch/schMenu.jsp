<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<title>目录</title>
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
		  <li
		  <c:if test="${loginInfo.equipmentCode>60}"> style="display: none;"</c:if>
		  
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/sch/calendar/calendar_juanbao.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>卷包排班</a></li>
		  <li
		  <c:if test="${loginInfo.equipmentCode<101||loginInfo.equipmentCode>132}"> style="display: none;"</c:if>
		  <c:if test="${loginInfo.equipmentCode>100&&loginInfo.equipmentCode<131}"> class="active"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/sch/calendar/calendar_chengxing.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>成型排班</a></li>
		  <li class="active"><a href="javascript:void(0)" class="prods-link" path="/wct/sch/workorder/workorder.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>生产工单</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/sch/stat/stat.jsp" ><span class="glyphicon glyphicon-sort-by-attributes-alt pull-right"></span></span>工单实绩</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/call/matRequest/matRequest.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>呼叫物料</a></li>
		 <!-- 卷烟机:1~30    -->
		 <!-- 包装机:31~60   -->
		 <!-- 封箱机:61~70   -->
		 <!-- 成型机:101~130 -->
		   <!--
		   <c:if test="${loginInfo.equipmentCode>=1 && loginInfo.equipmentCode<=30}"> 
		       <li><a href="javascript:void(0)" class="prods-link" path="/wct/call/matRequest/shiftDatas_roller.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>交接班维护</a></li>
		   </c:if>
		   -->  
		   <!-- 
		   <c:if test="${loginInfo.equipmentCode>=31  && loginInfo.equipmentCode<=60}">  
		       <li><a href="javascript:void(0)" class="prods-link" path="/wct/call/matRequest/shiftDatas_packer.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>交接班维护</a></li>
		   </c:if>
		    --> 
		   <!-- 
		   <c:if test="${loginInfo.equipmentCode>=61  && loginInfo.equipmentCode<=70}">  
		       <li><a href="javascript:void(0)" class="prods-link" path="/wct/call/matRequest/shiftDatas_box.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>交接班维护</a></li>
		   </c:if>
		   -->
		    <!-- 
		    <c:if test="${loginInfo.equipmentCode>=101 && loginInfo.equipmentCode<=130}">  
		       <li><a href="javascript:void(0)" class="prods-link" path="/wct/call/matRequest/shiftDatas_filter.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>交接班维护</a></li>
		   	</c:if>
		     -->
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