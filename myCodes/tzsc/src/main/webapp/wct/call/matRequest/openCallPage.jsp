<%@ page language="java" import="java.util.*"  pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML>
<html>
  <head>
    <title>foreach标签测试</title>
    <script src="${pageContext.request.contextPath}/wct/js/jquery.min.js" type="text/javascript" ></script>
    <script type="text/javascript">
	    var objShell;
	    objShell=new ActiveXObject("WScript.Shell");
	    var iReturnCode=objShell.Run("C:\\Windows\\notepad.exe",0,true);
    
       function goBack(){
    	   window.location.href="${pageContext.request.contextPath}/wct/call/matRequest/matRequest.jsp";
       }
    </script>
  </head>


  <body>
         <h2>正在启动呼叫软件，请等待...</h2>
        <p onclick="goBack()">返回</p>
  </body>
</html>