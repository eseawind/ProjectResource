<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>修改密码</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>

<link rel="stylesheet" href="${pageContext.request.contextPath}/wct/sys/css/updatePwd.css"   type="text/css" />
<!-- wctAlert -->
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include> 
<jsp:include page='../../initlib/initKeyboard.jsp' ></jsp:include>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/css/wct_global.css"></link>

<script type="text/javascript">
	$(function(){
		//实例化
		$('.keyboard').keyboard();
		$("#sub").click(function(){
			jAlert('...', '系统提示');
			var oldpwd=$("#oldpwd").val();
			var newpwd=$("#newpwd").val();
			var renewpwd=$("#renewpwd").val();
			if(newpwd==renewpwd){
				$.post("${pageContext.request.contextPath}/wctLogin/updatePwd.action",{"basisUsr.isgn":oldpwd,"basisUsr.pwd":renewpwd},function(r){
					if(r=="1"){
						jAlert('密码修改成功.', '系统提示');
					}else if(r=="0"){
						jAlert('密码修改失败,请检查原密码输入是否正确.', '系统提示');
					}else if(r=="-1"){
						jAlert('登录超时,请重新登录.', '系统提示');
					}
				});
			}else{
				jAlert('新密码两次输入不一致.', '系统提示');
			}
		});		

		var exitWin =function(){
			var url="${pageContext.request.contextPath}"+$(this).attr("path");
			jConfirm('确认退出系统?', '系统提示',function(r){
				if(r){
					//退出 
					$.post("${pageContext.request.contextPath}/wct/system/logOutWct.do",function(bean){
						console.info(bean.isSuccess);
						if(bean.isSuccess=="true"){
							top.location = "${pageContext.request.contextPath}/wct/sys/login.jsp";	
						}
					},"Json");	
				}
			});
		};
		var type = window.location.search;
		if(type!="?type=1"){
			exitWin();
		}
	});
</script>
<style type="text/css">
	#renewpwd,#newpwd,#oldpwd{
		width:200px;
	}
	.ui-keyboard-preview{width:92%;}
	.form-control{height:20px;}
</style>
</head>
<body style="background:#DDDDDD">
<div id="wkd-qua-title">修改密码</div>
	<div class="password_box " >
		<form action="" method="get">
		
		<div class="input-group">
		  <span class="input-group-addon">原始密码</span>
		  <input type="text" id="oldpwd" class="form-control keyboard" placeholder="原始密码">
		</div>
		<div class="input-group">
		  <span class="input-group-addon">新设密码</span>
		  <input type="text" id="newpwd" class="form-control keyboard" placeholder="新设密码">
		</div>
		<div class="input-group">
		  <span class="input-group-addon">确认密码</span>
		  <input type="text" id="renewpwd" class="form-control keyboard" placeholder="确认密码">
		</div>
	<div class="password_botton">			
		<input id="sub" type="button" value="确认" class="btn btn-default">			
		<input id="reset" type="reset" value="取消" class="btn btn-default">
	</div>
</form>
</div>
</body>
</html>