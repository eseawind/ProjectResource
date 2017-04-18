<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page='../../initlib/initMyAlert.jsp' ></jsp:include>
<title>目录</title>
<style type="text/css">
		#prods-menu{
			text-decoration:none;
			font-weight:300;
			width:160px;
		}
		#prods-menu li a{
			display: block;
			height: 17px;
			line-height: 17px;
			font-size: 14px;
		}
		#prods-menu li a:hover{
			background:#5A5A5A;
		}
		.nav-pills > li.active > a, .nav-pills > li.active > a:hover, .nav-pills > li.active > a:focus {
			color: #FFFFFF;
			background-color: #5A5A5A;
			font-weight: bold;
		}
		.nav > li > a:hover,
		.nav > li > a:focus {
		    text-decoration: none;
			color: #FFFFFF;
			background-color: #5A5A5A;
		}
		a {
			color: #414141;
			text-decoration: none;
		}
</style>

<script type="text/javascript">
	$(function(){
		$(".prods-link").click(function(){
			var url="${pageContext.request.contextPath}"+$(this).attr("path");
			$("#work").attr("src",url);
		});
		$(".exit").click(function(){
			jConfirm('确认退出系统?', '系统提示',function(r){
				if(r){
					//退出 
					$.post("${pageContext.request.contextPath}/wct/system/logOutWct.do",function(bean){
						console.info(bean.isSuccess);
						if(bean.isSuccess=="true"){
							location = "${pageContext.request.contextPath}/wct/sys/login.jsp";	
						}
					},"Json");
				}
			});
			
		});
	});

	function exitWin(){
		var url="${pageContext.request.contextPath}"+$(this).attr("path");
		$("#work").attr("src",url);
		jConfirm('确认退出系统?', '系统提示',function(r){
			if(r){
				//退出 
				$.post("${pageContext.request.contextPath}/wct/system/logOutWct.do",function(bean){
					console.info(bean.isSuccess);
					if(bean.isSuccess=="true"){
						location = "${pageContext.request.contextPath}/wct/sys/login.jsp";	
					}
				},"Json");	
			}
		});

	}
	
</script>
</head>
<body>
	<div style="padding: 5px;">
		<ul class="nav nav-pills nav-stacked" id="prods-menu">
		 	<li class="active">
		  		<a href="javascript:void(0);" class="exit"><span class="glyphicon glyphicon-log-out pull-right"></span></span>注销登录</a>
		  	</li>
			<li>
			  	<a href="javascript:void(0);" class="prods-link" path="/wct/sys/updatePwd.jsp?type=1" >
			  		<span class="glyphicon glyphicon-lock pull-right"></span>修改密码
			  	</a>
			</li>
		 
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