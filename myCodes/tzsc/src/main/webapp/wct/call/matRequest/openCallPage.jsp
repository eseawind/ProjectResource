<%@ page language="java" import="java.util.*"  pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE HTML>
<html>
  <head>
    <title>foreach��ǩ����</title>
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
         <h2>�������������������ȴ�...</h2>
        <p onclick="goBack()">����</p>
  </body>
</html>