<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<title>使用百度API定位测试</title>
<meta name="author" content="leejean">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 
<script src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/wct/js/comboboxUtil.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	/*  $.ajax({
         type: "POST",
         url: "http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=202.198.18.3&coor=bd09ll",
         dataType: "jsonp",
         success: function(data){
               $("#myDiv").html(JSON.stringify(data));    
         }
     }); */
	 
	 /*根据客户提供接口，获取手机验证码*/
		function showData(){
		  var mobile="15921578824";
		  var account="1";
		  var url="https://qigehulu.banksteel.com/register!sendPhoneRendCode?mobile=15921578824&account=123456&responseJson=1";
		/*   $.post(url,function(data){
			 
		  },"JSON"); */
		  
		  $.ajax({
		        url: url,
		        type: 'POST',
		       // contentType:"application/x-www-form-urlencoded;charset=UTF-8",
		       // contentType:"text/json;charset=UTF-8",
		        dataType: 'JSONP',//here
		        success: function (data) {
		        	 console.info(data);
		        }
		    });
		}
		//showData();
	  $.loadSelectData($("#select"),"SHIFT",true)
	});
</script>
</head>
<body>
	   <div id="myDiv"></div>  
	   <select id="select"></select>  
</body>
</html>
