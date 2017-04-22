<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/module/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>二维码首页</title>
		<script language="JavaScript">
			$(function() {
				$("#createQrCodeBtn").bind("click", function() {
					var qrcodes = $("#qrCodetextarea").val();
					if($.trim(qrcodes) == "") {
						$('#alertDialog').modal('show');
						$("#alertDialogText").html("二维码内容不能为空！");
						return;
					}
					var params=encodeURI(qrcodes);
					window.location.href ="/qrCodeIMG.jsp?params="+params;
				});
				$("#clearTextBtn").bind("click", function() {
				});
			});
		</script>
	</head>

	<body>
		<div class="col-md-2" style="margin-top: 5px;margin-left: -10px;">
			<nav class="nav nav-pills nav-stacked">
				<li role="presentation" class="active">
					<a href="#">二维码</a>
				</li>
			</nav>
		</div>
		<div class="col-md-10" style="height:auto;margin-left: -10px;margin-top:5px;margin-right:10px;">
			<div class="panel panel-info">
				<div class="panel-heading">批量生成二维码</div>
				<div class="panel-body bg-warning">
					<textarea id="qrCodetextarea" class="form-control" rows="30" placeholder="多个数据以,分割"></textarea>
				</div>
				<div class="panel-footer">
					<button id="createQrCodeBtn" type="button" class="btn btn-success">确&nbsp;&nbsp;&nbsp;&nbsp;认</button>
					<button id="clearTextBtn" type="button" class="btn btn-warning">取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
				</div>
			</div>
		</div>
		
		<!--
        	作者：hothoot@163.com
        	时间：2017-04-17
        	描述：提示框
        -->
		<div id="alertDialog" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
			<div class="modal-dialog modal-sm" role="document">
				<div class="modal-content">
					<div class="modal-header">
        				<h4 class="modal-title" id="exampleModalLabel">消息提示</h4>
      				</div>
      				<div class="modal-body text-center text-info" id="alertDialogText">
      				</div>
      				<div class="modal-footer">
				        <button type="button" class="btn btn-warning" data-dismiss="modal">关闭</button>
      				</div>
				</div>
			</div>
		</div>
	</body>

</html>