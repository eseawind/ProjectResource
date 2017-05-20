<%@ page language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="${resRoot}/js/plug/jquery.min.js"></script>
<script type="text/javascript" src="${resRoot}/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="${resRoot}/bootstrap-3.3.7-dist/css/bootstrap.css" />
<link rel="stylesheet" href="${resRoot}/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css" />
<!-- 
	消息提示
 -->
<div id="alertInfoDIV" class="alert alert-danger alert-dismissible alertInfoDIV hidden " role="alert">
  		<strong id="infoType">消息：</strong>
  		<font id="infoContext"></font>
</div>
<!--
      	作者：hothoot@163.com
      	时间：2017-04-17
      	描述：提示框
-->
<div id="alertDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
		<div class="modal-dialog modal-sm" role="document">
			<div class="modal-content">
				<div class="modal-header alert-warning" style="padding: 8px;">
					<strong id="titleContext">消息提示</strong>
					<button type="button" class="close pull-right" data-dismiss="modal" aria-label="Close">
      					<span aria-hidden="true">&times;</span>
      				</button>
				</div>
				<div class="modal-body text-center text-info" id="alertDialogText" style="padding: 8px;min-height: 60px;">
				</div>
			</div>
		</div>
</div>
<!-- 
	定义全局变量
 -->
<script type="text/javascript" language="JavaScript">
 window.RESROOT="${resRoot}";
 window.BASEURL="${baseURL}";
 //平滑显示提示消息
 function showMsg(type,infoType,infoContext){
	 $("#infoType").html(infoType);
	 $("#infoContext").html(infoContext);
	 switch(type){
	 	case 0:
	 		$("#alertInfoDIV").addClass("alert-success");
	 		$("#alertInfoDIV").removeClass("alert-info");
	 		$("#alertInfoDIV").removeClass("alert-warning");
	 		$("#alertInfoDIV").removeClass("alert-danger");
	 		break;
	 	case 1:
	 		$("#alertInfoDIV").addClass("alert-info");
	 		$("#alertInfoDIV").removeClass("alert-success");
	 		$("#alertInfoDIV").removeClass("alert-warning");
	 		$("#alertInfoDIV").removeClass("alert-danger");
	 		break;
	 	case 2:
	 		$("#alertInfoDIV").addClass("alert-warning");
	 		$("#alertInfoDIV").removeClass("alert-success");
	 		$("#alertInfoDIV").removeClass("alert-info");
	 		$("#alertInfoDIV").removeClass("alert-danger");
	 		break;
	 	case 3:
	 		$("#alertInfoDIV").addClass("alert-danger");
	 		$("#alertInfoDIV").removeClass("alert-info");
	 		$("#alertInfoDIV").removeClass("alert-warning");
	 		$("#alertInfoDIV").removeClass("alert-success");
	 		break;
	 }
	 //设置提示框宽度
	 var width=$("#mainDiv").width()+"px";
	 $("#alertInfoDIV").css({"width":width});
	 //显示提示框
	 $("#alertInfoDIV").removeClass("hidden");
	 //3秒之后关闭提示框
	 setTimeout(function () {$("#alertInfoDIV").addClass("hidden")}, 5000);  
 }
 //弹出提示消息
 function alertMsg(titleContext,infoContext){
	 $("#alertDialogText").html(infoContext);
	 $("#titleContext").html(titleContext);
	 $("#alertDialog").modal('show')
 }
</script>