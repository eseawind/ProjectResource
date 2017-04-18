<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>目录</title>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global_menu.css"></link>
</head>
<script type="text/javascript">
	$(function(){
		$(".prods-link").click(function(){
			var url="${pageContext.request.contextPath}"+$(this).attr("path");
			$("#work").attr("src",url);
		});
	});
</script>
<body>
	<div style="padding: 5px;">
		<ul class="nav nav-pills nav-stacked" id="prods-menu">
		  <li  class="active"><a href="javascript:void(0)" class="prods-link" path="/wct/qm/qm_prod.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>技术标准</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/qm_proc.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>管理文件</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/qm_checkRecords.jsp" ><span class="glyphicon glyphicon-calendar pull-right"></span></span>质检记录</a></li>
		  <!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/qm_self.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>外观检验</a></li> -->
		  <!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/qmMassCheck.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质量自检记录</a></li> -->
		  
		  <c:if test="${loginInfo.equipmentCode<=30}">
		      <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/public/checkRecords.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质检纪录查询</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/rollerCheck/qmMassFirst_czg.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>操作工首检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/rollerCheck/qmMassFirst_zjy.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质检员复检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/rollerCheck/qmMassFirst_gdz.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>工段长复检</a></li>
			  <!-- 卷烟机过程自检页面 -->
			  <li><a href="javascript:void(0)" id='zj' class="prods-link" path="/wct/qm/rollerCheck/qmMassFirst_gczj.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>过程自检记录</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/rollerCheck/qmMassFirst_zhcs.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>物理指标自检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/rollerCheck/qmMassFirst_flzj.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>辅料自检自控确认</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/rollerCheck/qmMassFirst_shg.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>丝含梗</a></li>
		  </c:if>
		  <c:if test="${loginInfo.equipmentCode>30 && loginInfo.equipmentCode<=60}">
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/public/checkRecords.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质检纪录查询</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/packageCheck/qmMassFirst_czg.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>操作工首检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/packageCheck/qmMassFirst_zjy.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质检员复检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/packageCheck/qmMassFirst_gdz.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>工段长复检</a></li>
		 	  <li><a href="javascript:void(0)"  class="prods-link" path="/wct/qm/packageCheck/qmMassFirstD_PackerGczj.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>挡车工过程自检</a></li>
			  <li ><a href="javascript:void(0)"  class="prods-link" path="/wct/qm/packageCheck/qmMassFirstC_PackerGczj.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>操作工过程自检</a></li>
			  <li><a href="javascript:void(0 )" class="prods-link" path="/wct/qm/packageCheck/qmMassFirst_flzjB.jsp"><span class="glyphicon glyphicon-list-alt pull-right"></span>辅料自检自控确认</a></li>
		  </c:if>
		  <c:if test="${loginInfo.equipmentCode>60 && loginInfo.equipmentCode<=70}">
		  	  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/public/checkRecords.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质检纪录查询</a></li>
		  	  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/sealerCheck/qmMassFirst_czg.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>操作工首检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/sealerCheck/qmMassFirst_zjy.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质检员复检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/sealerCheck/qmMassFirst_gdz.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>工段长复检</a></li>
			  <li><a href="javascript:void(0)" id='zj' class="prods-link" path="/wct/qm/sealerCheck/qmMassFirstF_cartonGczj.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>过程自检记录</a></li>
			  <li><a href="javascript:void(0 )" class="prods-link" path="/wct/qm/sealerCheck/qmMassFirst_flzjF.jsp"><span class="glyphicon glyphicon-list-alt pull-right"></span>辅料自检自控确认</a></li>
		  </c:if>
		  <c:if test="${loginInfo.equipmentCode>100 && loginInfo.equipmentCode<=130}">
		  	  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/public/checkRecords.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质检纪录查询</a></li>
		  	  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/filterCheck/qmMassFirst_czg.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>操作工首检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/filterCheck/qmMassFirst_zjy.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质检员复检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/filterCheck/qmMassFirst_gdz.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>工段长复检</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/filterCheck/qmMassFirst_gczj.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>过程自检记录</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/filterCheck/qmMassFirstFabrics_Gczj.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>丝束过程检查</a></li>
			  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/filterCheck/qmMassFirstGlycerine_Gczj.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>甘油酯施加量</a></li>
			  <li><a href="javascript:void(0 )" class="prods-link" path="/wct/qm/filterCheck/qmMassFirst_flzjC.jsp"><span class="glyphicon glyphicon-list-alt pull-right"></span>辅料自检自控确认</a></li>
		  </c:if>
		  <!-- <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/realTimeInput_packer.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>DEMO</a></li> -->
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/qm_warn.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>质量警告</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/con_warn.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>单耗警告</a></li>
		  <li><a href="javascript:void(0)" class="prods-link" path="/wct/qm/info_warn.jsp" ><span class="glyphicon glyphicon-list-alt pull-right"></span></span>系统通知</a></li>
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