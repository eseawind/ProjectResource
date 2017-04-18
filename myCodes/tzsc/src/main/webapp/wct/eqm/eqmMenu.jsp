<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>目录</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global_menu.css"></link>
<script type="text/javascript">
	$(function(){
		var url;
		$(".prods-link").click(function(){
			var target=$(this).attr("target");
			if(target=="wct"){
				url="${pageContext.request.contextPath}"+$(this).attr("path");
			}else if(target=="tspm"){
				url=$(this).attr("path");
			}
			$("#work").attr("src",url);
		});
		$(".logout").click(function(){
			$.post("${pageContext.request.contextPath}/wct/system/logOutWctEqm.do",function(json){
				if(json.isSuccess=="false"){
					alert("用户退出成功", '系统提示');
					$("#prods-idx").hide();
					$("#hid_div").css("display","block");
				}else{
					alert("用户退出失败", '系统提示');
				}
				
			},"JSON");
		});
	});
</script>
</head>
<body>
	<div style="padding: 5px;">
		<ul class="nav nav-pills nav-stacked" id="prods-menu">
		<!--<li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/fixrec/sysStaffPage.do" ><span class="glyphicon glyphicon-wrench pull-right"></span></span>维修请求</a></li>  -->
		<li class="active"><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqpRuns.jsp" target="wct"><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备运行记录</a></li>
		<li><a href="javascript:void(0)" class="prods-link" path="http://10.114.81.244:8011/Equipment/WrokOrder/CheckWorkOrder?personid=[5858511]&dept=107550" target="tspm"><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷包点检</a></li>
		<li><a href="javascript:void(0)" class="prods-link" path="http://10.114.81.244:8011/Equipment/WrokOrder/LUBWorkOrder?personid=[5858511]&dept=107550" target="tspm"><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷包润滑</a></li>
		<li><a href="javascript:void(0)" class="prods-link" path="http://10.114.81.244:8011/Equipment/WrokOrder/MaintainWorkOrder?personid=[5858511]&dept=107550" target="tspm"><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷包保养</a></li>
		<li><a href="javascript:void(0)" class="prods-link" path="http://10.114.81.244:8011/Equipment/RepairAndCall/RepairAndCallData?PersonId=[5858511]&deptId=107547" target="tspm"><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷包维修呼叫</a></li>
		<li><a href="javascript:void(0)" class="prods-link" path="http://10.114.81.244:8011/Equipment/RoundOrder/OrderExecute?personid=11017" target="tspm"><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷包轮保工单执行</a></li>
		<li><a href="javascript:void(0)" class="prods-link" path="http://10.114.81.244:8011/Equipment/WXGDJH/wxgdjh?UserId=11017&UserName=陈来记" target="tspm"><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷包维修</a></li>
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqpMainPlan.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备轮保管理</a></li> -->
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqpLubricating.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备润滑管理</a></li> -->
		
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqpCheckCatPlan.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备点检管理</a></li> -->
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqpOverhaul.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备检修管理</a></li> -->
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqp_protect.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备日保管理</a></li> -->
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqp_spotch_list.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备点检管理</a></li> -->
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqpRunsEfficiency.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备有效作业率</a></li> -->
		
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqpRunsEqpUtilization.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备综合利用率</a></li> -->
		<!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/eqm/eqpRunsRate.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>设备运行效率</a></li> -->
		
		<%-- <li
		<c:if test="${loginInfo.equipmentCode>30}"> style="display: none;"</c:if>
		><a href="javascript:void(0)" class="prods-link" path="/wct/ctm/ctm_roller.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>卷接机成本</a></li>
		
		<li
		<c:if test="${loginInfo.equipmentCode<31||loginInfo.equipmentCode>60}"> style="display: none;"</c:if>
		><a href="javascript:void(0)" class="prods-link" path="/wct/ctm/ctm_pack.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>包装机成本</a></li>
		
		<li
		<c:if test="${loginInfo.equipmentCode<101||loginInfo.equipmentCode>130}"> style="display: none;"</c:if>
		><a href="javascript:void(0)" class="prods-link" path="/wct/ctm/ctm_filter.jsp" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>成型机成本</a></li> --%>
		
		<!-- <li><a href="javascript:void(0)" class="logout" ><span class="glyphicon glyphicon-ok-circle pull-right"></span></span>注销</a></li> -->
		</ul>
		<!-- 点击顶部菜单按钮赋予状态样式class -->
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