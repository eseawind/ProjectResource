<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/module/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<title>批量生成二维码</title>
		<script language="JavaScript">
			$(function() {
				var qrCode1 = document.getElementById("qrCode1");
				qrCode1.makeCode(qrCodeText);
			})
		</script>
	</head>

	<body class="container">
		<div class="row">
			<div class="col-sm-4 col-md-4">
				<div class="thumbnail">
					<img src="..." alt="..." class="img-responsive center-block img-thumbnail">
					<div class="caption">
						
					</div>
				</div>
			</div>
		</div>
	</body>

</html>