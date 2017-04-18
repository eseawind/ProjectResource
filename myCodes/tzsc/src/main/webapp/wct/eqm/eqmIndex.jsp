<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>     
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设备管理首页</title>
<style type="text/css">
*{margin:0;padding:0;font-family: "Microsoft YaHei","微软雅黑","宋体",Arial;}
	#prods-idx{
		width: 1010px;
		height: 622px;
		margin: 0px auto;
		border-radius: 6px;
		border: 1px solid #979595;
	}
	#prods-idx-menu{
		float:left;
		width:170px;
		border-right: 1px solid #999;
		height:100%;		
	}
	#prods-idx-content{
		float: left;
		width: 839px;
		height:622px;
		border-radius: 0px 6px 6px 0px;
	}
</style>
<script type="text/javascript">

	$(function(){
		//登录验证
		var loginname = "${loginInfo.equipmentCode}";
		if(loginname==null || loginname=="" || loginname.length==0){
			window.location="${pageContext.request.contextPath}/wct/sys/login.jsp";
		} 
		
		$.post("${pageContext.request.contextPath}/wct/eqm/eqmMenu.jsp",function(v){
			$("#prods-idx-menu").html(v);
		});
		/* if("${loginWctEqmInfo.isSuccess}"=="true"){
			$("#hid_div").css("display","none");
		}else{
			$("#prods-idx").hide();
			$("#hid_div").css("display","block");
		} */
	});
/* 	$(".logout").click(function(){
		$.post("${pageContext.request.contextPath}/wct/system/logOutWctEqm.do",function(json){
			if(json.isSuccess=="false"){
				jAlert("用户退出成功", '系统提示');
			}else{
				jAlert("用户退出失败", '系统提示');
			}
			
		},"JSON");
	});
	$("#sub").click(function(){
		var name=$("#name").val();
		var pwd=$("#pwd").val();
		$("#name").attr("value","");
		$("#pwd").attr("value","");
		var loginBean={"name":name,"passWord":pwd,"loginName":name};
		$.post("${pageContext.request.contextPath}/wct/system/loginWctEqm.do",loginBean,function(json){
			if(json.isSuccess=="true"){
				$("#hid_div").css("display","none");
				$("#prods-idx").show(1000);
			}else{
				jAlert("用户名或密码错误", '系统提示');
			}
		},"JSON");
	});	*/
</script>
</head>
<body>
	<!-- 用户登录窗口 -->
	<%-- <jsp:include page="eqmLogin.jsp?name=设备内登录"></jsp:include> --%>
	<div id="prods-idx">
		<div id="prods-idx-menu">
			<iframe id="left" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/eqm/eqmMenu.jsp" style="width:100%;height:100%"></iframe>
		</div>
		<div id="prods-idx-content">
		 	<iframe id="work" frameborder="0" src="${pageContext.request.contextPath}/wct/eqm/eqpRuns.jsp" style="width:100%;height:100%"></iframe>
		</div>
	</div>
</body>
</html>