<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>文件夹显示页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/jslib/bootstrap-2.3.1/css/bootstrap.css"></link>
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/wct/stat/css/wct_prodsta.css"></link>
<script type="text/javascript" src="${pageContext.request.contextPath}/wct/js/jquery.min.js"></script>
<jsp:include page="../../initlib/initMyAlert.jsp"></jsp:include><style>
.progress {margin-bottom: 0px;width: 330px;float: left;height:12px;}
</style>
<script type="text/javascript">
var fileSeach=function(){
	$("#table1 tr").eq(0).nextAll().remove();
	$.get("${pageContext.request.contextPath}//wct/file/docfile/getList.do",function(json){
		for(var i=0;i<json.length;i++){
			var str="";
			str+="<tr>";
			str+="<td><img src=""/>"+json[i].filename+"</td>";
			if(json[i].fileType==null){
				str+="<td style='text-align: center;'>&nbsp</td>";
			}else{
			str+="<td style='text-align: center;'>"+json[i].fileType+"</td>";
			}
			if(json[i].type==1){
				str+="<td style='text-align: center;'>文件</td>";
			}else{
				str+="<td style='text-align: center;'>文件夹</td>";
			}
			if(json[i].createTime==null){
				str+="<td style='text-align: center;'>&nbsp</td>";
			}else{
				str+="<td style='text-align: center;'>"+json[i].createTime+"</td>";
			}
			str+="<td style='text-align: center;'>"+json[i].createUserName+"</td>";
			str+="</tr>";
			$("#table1").append(str);	
		}
		
	},"JSON");
	
};
$(function(){
	fileSeach();
}); 

</script>
</head>
<body>


<div id="prods-idx-content">
	<div id="wkd-qua-title">所有文件夹</div>
	<div class="info_machine"  style="height: 100%;width:800px;">	
			<table id="table1" border="1" style="width:100%;">
				<tr style="text-align: center;">
					<td>文件名称</td>
					<td>文件后缀名</td>
					<td>文件类型</td>
					<td>上传时间</td>
					<td>上传用户</td>
				</tr>
			</table>
			<input type="button" value="测试" onclick="fileSeach()">
	</div>
	
</div>
</body>
</html>