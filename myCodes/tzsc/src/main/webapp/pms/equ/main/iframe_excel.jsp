<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>备品备件管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 储烟量标准 -->

<body class="easyui-layout" id="body" data-options="fit : true,border : false">
<style type="text/css">
img{
 max-height:720px; 
 max-width:640px; 
 width:expression(this.width > 640 && this.height < this.width ? 640: true); 
 height:expression(this.height > 720 ? 720: true);
 }
</style>
<script type="text/javascript">
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
			<fieldset>
				<br/> 
				<form action="" enctype = "multipart/form-data" class="form form-horizontal" method="post" id="filesForm" name="fileForm">
				  <span class="label">请选择文件：</span>
				   <input type="file"  class="form-control"  name="importFile" id="importFile"/>
				   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button"   id="buttonf" onclick="addFilesDataget()" value="确定" /></span>
				</form>
				<br/><br/>
				<hr/>
				<br/>
				<table class="table" style="width:97%;">
					<font style="color:red;">
					   EXCEL格式必须遵守如下格式(第一列中文说明，第二列是需要导入的数据，且第一列第二列顺序必须如下图所示)：
					</font>
					<p><img id='img' src="${pageContext.request.contextPath }/pms/equ/main/excel_Model_img/eqp.png" /></p>
					<div  style="color:red;">
					      温馨提醒：请确保导入的excel数据的行数是否小于5000行！
					</div>
				</table>
			</fieldset>
		<br/><br/>
	</div>
	
</div>
<script type="text/javascript">
//导入数据，验证
function addFilesDataget(){
	var file = $("#importFile").val();
	if (file == '' || file == null) {
		$.messager.alert('提示','请选择所要上传的文件!','error');
	}else {
		var index = file.lastIndexOf(".");
		if (index < 0) {
			$.messager.alert('提示','上传的文件格式不正确，请选择97-2007Excel文件(*.xls 或 *.xlsx)！','error');
		}else {
			var ext = file.substring(index + 1, file.length);
			if (ext == "xls" || ext=="xlsx") {
				$('#filesForm').form({   
				    url:"${pageContext.request.contextPath}/pms/equc/inputExeclAndReadWrite.do?id="+Math.random(),   
				    success:function(data){ 
				    	data=jQuery.parseJSON(data);
				    	$.messager.show({
				    		title:'消息提示',
				    		msg:data.msg,
				    		timeout:2000,
				    		showType:'slide'
				    	});
				    }   
				});  
				$("#filesForm").submit();
			}else{
				$.messager.alert('提示','上传的文件格式不正确，请选择97-2007Excel文件(*.xls 或 *.xlsx)!','error');
			}
		}
	}
}
</script>
</body>
</html>