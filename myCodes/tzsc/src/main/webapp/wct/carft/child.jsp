<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="css/easyui.css" type="text/css" rel="stylesheet" />

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/JsUtil.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/BandSelect.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.3.1/jquery.form.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/uuid.js"></script>
<title>图片浏览窗口</title>
 <script language="javascript" type="text/javascript">
$(function(){
    var k=window.dialogArguments; 
    //获得父窗口传递来的值 
    if(k.imgs!="") 
    { 
    	alert(k.imgs);
       var button='<img src="${pageContext.request.contextPath}/carftImages/'+k.imgs+'" height="450" width="750" />'; 
	   //alert(k.imgs);
	   $("#div").append(button);
    } else{
    	  $.messager.alert("提示","没有上传图片！！");
    	  window.close; 
    }
});
</script>
</head>
<body >
	<div id="div" style="height: 450px;width: 750px" align="center"></div>
</body>
</html>