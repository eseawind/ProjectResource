<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
	$(function(){
		$(".msg-link").click(function(){
			var url="${pageContext.request.contextPath}"+$(this).attr("path");
			$.post(url,function(v){
				$("#msg-idx-content").html(v);
			});
		});
	});
</script>
<title>目录</title>

<style type="text/css">
		#msg-menu{
			text-decoration:none;
			font-size:20px;
			font-weight:300;
			width:170px;
			margin-left:5px;
		}
		#msg-menu li{
			margin-top:5px;			
			text-align:center;
		}
		#msg-menu li a{
			display:block;
			height:30px;
			width:100%;
			line-height:30px;
		}
		#msg-menu li a:hover{
			background:#bebebe;
		}
</style>
</head>
<body>
	<div>
		<ul id="msg-menu">
			<li><a href="javascript:void(0)" class="msg-link" path="/wct/message/msg_myNotice.jsp">我的通知</a></li>
			<li><a href="javascript:void(0)" class="msg-link" path="/wct/message/msg_qualityWarn.jsp">质量告警</a></li>
			<li><a href="javascript:void(0)" class="msg-link" path="/wct/message/msg_unitConWarn.jsp">单耗告警</a></li>
		</ul>
	</div>
</body>
</html>