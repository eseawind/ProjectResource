<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>目录</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global_menu.css"></link>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/wct_icon.css">
<script type="text/javascript">
	$(function(){
	
		$(".prods-link").click(function(){
			var url="${pageContext.request.contextPath}"+$(this).attr("path");
			$("#work").html(null);
			$("#work").attr("src",url);
		});
	});
	<!-- 卷烟机:1~30    -->
	<!-- 包装机:31~60   -->
	<!-- 封箱机:61~70   -->
	<!-- 成型机:101~130 -->
</script>
</head>
<body>
	<div style="padding: 5px;">
		<ul class="nav nav-pills nav-stacked" id="prods-menu">
		  <li 
		  <c:if test="${loginInfo.equipmentCode>30}"> style="display: none;"</c:if>
		  <c:if test="${loginInfo.equipmentCode>0&&loginInfo.equipmentCode<31}"> class="active"</c:if> 
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeOutput_roller.jsp" ><span class="glyphicon glyphicon-time pull-right"></span>卷烟机实时产量</a></li>
		  <li 
		  <c:if test="${loginInfo.equipmentCode<31||loginInfo.equipmentCode>60}"> style="display: none;"</c:if>
		  <c:if test="${loginInfo.equipmentCode>30&&loginInfo.equipmentCode<61}"> class="active"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeOutput_packer.jsp" ><span class="glyphicon glyphicon-trash pull-right"></span></span>包装机实时产量</a></li>
		  <li    
		  <c:if test="${loginInfo.equipmentCode<61||loginInfo.equipmentCode>70}"> style="display: none;"</c:if>	   
		  <c:if test="${loginInfo.equipmentCode>60&&loginInfo.equipmentCode<71}"> class="active"</c:if> 
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeOutput_boxer.jsp" ><span class="glyphicon glyphicon-trash pull-right"></span></span>封箱机实时产量</a></li>
		  <li 
		  
		  <c:if test="${loginInfo.equipmentCode>100&&loginInfo.equipmentCode<131}"> class="active"</c:if>
		  <c:if test="${loginInfo.equipmentCode<101||loginInfo.equipmentCode>130}"> style="display: none;"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeOutput_filter.jsp" ><span class="glyphicon glyphicon-trash pull-right"></span></span>成型机实时产量</a></li>
		  <li
		  <c:if test="${loginInfo.equipmentCode>30}"> style="display: none;"</c:if>
		  > <a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeBadQty_roller.jsp" ><span class="glyphicon glyphicon-trash pull-right"></span></span>卷烟机实时剔除</a></li>
		  <li 
		  <c:if test="${loginInfo.equipmentCode<31||loginInfo.equipmentCode>60}"> style="display: none;"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeBadQty_packer.jsp" ><span class="glyphicon glyphicon-trash pull-right"></span></span>包装机实时剔除</a></li>
		  <li 
		  <c:if test="${loginInfo.equipmentCode<101||loginInfo.equipmentCode>130}"> style="display: none;"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeBadQty_filter.jsp" ><span class="glyphicon glyphicon-trash pull-right"></span></span>成型机实时剔除</a></li>
		  <li
		  <c:if test="${loginInfo.equipmentCode>30}"> style="display: none;"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeInput_roller.jsp" ><span class="uk-icon-tachometer pull-right"></span></span>卷烟机实时消耗</a></li>
		  <li  
		  <c:if test="${loginInfo.equipmentCode<31||loginInfo.equipmentCode>60}"> style="display: none;"</c:if>
		  >   <a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeInput_packer.jsp" ><span class="uk-icon-tachometer pull-right"></span></span>包装机实时消耗</a></li>
		  <li  
		  <c:if test="${loginInfo.equipmentCode<101||loginInfo.equipmentCode>130}"> style="display: none;"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/realTimeInput_filter.jsp" ><span class="uk-icon-tachometer pull-right"></span></span>成型机实时消耗</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/stat/hisOut.jsp" ><span class="glyphicon glyphicon-align-left pull-right"></span></span>历史产量</a></li>
		  
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/stat/hisTotal.jsp" ><span class="glyphicon glyphicon-align-left pull-right"></span></span>历史合计产量</a></li>
		  
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/stat/hisBadQty.jsp" ><span class="glyphicon glyphicon-compressed pull-right"></span></span>历史剔除</a></li>  
		  <!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/sch/stat/baozhuangjiHisInput.jsp" ><span class="uk-icon-external-link-square pull-right"></span></span>历史消耗</a></li> -->
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/stat/hisInput_packerAndRoller.jsp" ><span class="uk-icon-external-link-square pull-right"></span></span>历史消耗</a></li>
		  <li <c:if test="${loginInfo.equipmentCode<70||(loginInfo.equipmentCode<=101&&loginInfo.equipmentCode<=130)}"> style="display:block;"</c:if>>
		  	<a href="javascript:void(0)" class="prods-link" path="/wct/stat/eqperror.jsp" >
		  		<span class="uk-icon-external-link-square pull-right"></span></span>故障停机统计
		  	</a>
		  </li>
		  <li <c:if test="${loginInfo.equipmentCode<70||(loginInfo.equipmentCode<=101&&loginInfo.equipmentCode<=130)}"> style="display:block;"</c:if>>
		  	<a href="javascript:void(0)" class="prods-link" path="/wct/stat/eqpFaultList.jsp" >
		  		<span class="uk-icon-external-link-square pull-right"></span></span>故障停机历史记录
		  	</a> 
		  </li>
		  <!-- 成本管理 -->
		  <!-- 
		  <li  
		  <c:if test="${loginInfo.equipmentCode>30}"> style="display: none;"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/ctm_rollers.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷接机成本对比</a></li>
		  <li 
		  <c:if test="${loginInfo.equipmentCode<31||loginInfo.equipmentCode>60}"> style="display: none;"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/ctm_pack.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>包装机成本对比</a></li>
		  -->
		  <li  
		  <c:if test="${loginInfo.equipmentCode<101||loginInfo.equipmentCode>130}"> style="display: none;"</c:if>
		  ><a href="javascript:void(0)" class="prods-link" path="/wct/stat/ctm_filter.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>成型机成本对比</a></li>
		  <!-- 设备管理 -->
		  <!--  
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/stat/eqpRunsRate.jsp" ><span class="uk-icon-external-link-square pull-right"></span></span>设备运行效率</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/stat/eqpRunsEqpUtilization.jsp" ><span class="uk-icon-external-link-square pull-right"></span></span>设备综合利用率</a></li>
		 -->
		 </ul>
	  <script type="text/javascript">
		$('#prods-menu li').bind('click', function(){
			//alert(1);
			$('#prods-menu li').removeClass('active');
		    $(this).addClass('active');
		});
	  </script>
	</div>
</body>
</html>