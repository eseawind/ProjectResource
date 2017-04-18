<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<%-- <jsp:include page='../../js/initjsp/initKeyboard.jsp' ></jsp:include> --%>
<title>成本对比</title>
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
/*密码验证开始*/	
.password_botton{width: 317px;height: auto;font-size: 12px;font-weight: bold;text-align: center;padding-top: 4px;margin: 0 auto;}
.btn-default {color: #FFFFFF;background-color: #5A5A5A;border-color: #cccccc;}
.password_box{display: block;z-index: 99;position: absolute;top: 140px;left: 430px;width: 300px;font-size: 12px;border: 1px solid #858484;border-radius: 4px;height: 120px;background: #DEDCDA;padding: 30px;}
.input-group {margin-bottom: 15px;}
#username,#password{width:200px;}
.ui-keyboard-preview{width:92%;}
.form-control{height:20px;}
/*密码验证结束*/	
</style>
<script type="text/javascript">

	

	$(function(){
		//登录验证
		var loginname = "${loginInfo.equipmentCode}";
		if(loginname==null || loginname=="" || loginname.length==0){
			window.location="${pageContext.request.contextPath}/wct/sys/login.jsp";
		} 
		
		$.post("${pageContext.request.contextPath}/wct/cost/costMenu.jsp",function(v){
			$("#prods-idx-menu").html(v);
		});
		/* $('.keyboard').keyboard(); */
		$("#hid_div").click(function(){
			$(this).css("display","none");
		});
	});
</script>
</head>
<body>
 
	<div id="prods-idx">
		<div id="prods-idx-menu"></div>
		<!-- 包装机成本对比 -->
		<c:if test="${loginInfo.equipmentCode<31||loginInfo.equipmentCode>60}"> 
		  	<div id="prods-idx-content"><iframe id="sch" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/stat/ctm_rollers.jsp" style="width:100%;height:100%"></iframe></div>
		</c:if>
		<!-- 卷接机成本对比 -->
		<c:if test="${loginInfo.equipmentCode>30}"> 
			<div id="prods-idx-content"><iframe id="sch" scrolling="no" frameborder="0" src="${pageContext.request.contextPath}/wct/stat/ctm_pack.jsp" style="width:100%;height:100%"></iframe></div>
		</c:if>
	</div>

<div id="hid_div" style="display:none;">
	<div class="password_box">
		<form action="" method="get">
		<div class="input-group">
		  <span class="input-group-addon">用户名</span>
		  <input type="text" id="username" class="form-control keyboard" placeholder="原始密码">
		</div>
		<div class="input-group">
		  <span class="input-group-addon">密&nbsp;&nbsp;&nbsp;&nbsp;码</span>
		  <input type="text" id="password" class="form-control keyboard" placeholder="确认密码">
		</div>
	<div class="password_botton">			
		<input id="sub" type="button" value="确认" class="btn btn-default">			
		<input id="reset" type="reset" value="取消" class="btn btn-default">
	</div>
</form>
</div>
</div>	
</body>
</html>