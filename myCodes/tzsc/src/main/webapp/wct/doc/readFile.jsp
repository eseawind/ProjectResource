<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"  isELIgnored="false"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">	
  <head>
    <title>在线阅读</title>
        <style type="text/css" media="screen"> 
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }
			#flashContent { display:none; }
        </style> 
        <script type="text/javascript" src="${pageContext.request.contextPath}/jslib/flexpaper/jquery.js"></script>
  </head>
 
  <body>
     <div style="top:10px;">
		<object classid="clsid:CA8A9780-280D-11CF-A24D-444553540000" width="100%" height="100%" border="0">  
		    <param name="_Version" value="65539">  
		    <param name="_ExtentX" value="20108">  
		    <param name="_ExtentY" value="10866">  
		    <param name="_StockProps" value="0">  
		    <param name="SRC" value="${service_url }">  
		    <object data="${service_url }" type="application/pdf" width="100%" height="100%">   
		    </object>  
		</object> 
    </div> 
     
   </body>
</html>