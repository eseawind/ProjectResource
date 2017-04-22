<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/module/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>二维码首页</title>
	</head>

	<body>
		<div class="col-md-2" style="margin-top: 5px;margin-left: -10px;">
			<nav class="nav nav-pills nav-stacked">
				<li role="presentation" class="active">
					<a href="#">二维码</a>
				</li>
			</nav>
		</div>
		<div id="mainDiv" class="col-md-10" style="height:auto;margin-left: -10px;margin-top:5px;margin-right:10px;">
			<%@include file="/module/qrCode/areaQrCode.jsp" %>
		</div>
		
	</body>

</html>