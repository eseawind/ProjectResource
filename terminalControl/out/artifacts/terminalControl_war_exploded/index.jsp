<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@include file="/module/common.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
	<head>
		<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
		<link rel="stylesheet" href="${resRoot}/css/index/mainDiv.css" />
		<script type="text/javascript" src="${resRoot}/js/index/main.js"></script>
		<title>首页信息</title>
	</head>
	<body>
		<div class="col-md-2" style="margin-top: 5px;margin-left: -10px;">
			<nav class="nav nav-pills nav-stacked menuDiv">
				<c:forEach var="menu" items="${menuList}">
					<li class="mainMenu" role="presentation" data-toggle="collapse" href="#collapsePanel${menu.id}" aria-expanded="false" aria-controls="collapse">
						<a href="#">${menu.context}</a>
					</li>
					<div class="collapse" id="collapsePanel${menu.id}">
						<div class="list-group">
							<c:forEach var="cmenu" items="${menu.childMenus}">
							   <a onclick="changeHtml(this)" class="list-group-item list-group-item-success childMenu" href="#" urlVal="${cmenu.url_context }">${cmenu.context}</a>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</nav>
		</div>
		<div id="mainDiv" class="col-md-10" style="height:auto;margin-left: -10px;margin-top:5px;margin-right:10px;">
			<%@include file="/module/qrCode/areaQrCode.jsp" %>
		</div>
	</body>
</html>