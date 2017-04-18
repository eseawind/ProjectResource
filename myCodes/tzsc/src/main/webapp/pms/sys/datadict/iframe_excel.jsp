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
function changeIMG(val){
	//1点检2、轮保3、润滑4、日保
	if(val==1){
		$('#img').attr('src','${pageContext.request.contextPath }/pms/sys/datadict/imagesHotspot/img/dianjian.png');
	}else if(val==2){
		$('#img').attr('src','${pageContext.request.contextPath }/pms/sys/datadict/imagesHotspot/img/lunbao.png');
	}else if(val==3){
		$('#img').attr('src','${pageContext.request.contextPath }/pms/sys/datadict/imagesHotspot/img/runhua.png');
	}else if(val==4){
		$('#img').attr('src','${pageContext.request.contextPath }/pms/sys/datadict/imagesHotspot/img/ribao.png');
	}
}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
			<fieldset>
				<br/> 
				<form action="" enctype = "multipart/form-data" class="form form-horizontal" method="post" id="filesForm" name="fileForm">
				  <span class="label">请选择文件：</span>
				   <input type="file"  class="form-control"  name="importFile" id="importFile"/>
				   <span class="label">一级名称：</span>
				  	<span>
							<select  id="paul_category" name="id" class="easyui-combobox" data-options="valueField:'id',required:true,panelHeight:'auto',width:200,prompt:'请选择日保养规则'"></select>
					</span>
					<br><br>
					<span class="label">导入类型：</span>&nbsp;
							<select onchange="changeIMG($(this).children('option:selected').val())" id='fileType' name="type" >
								<option value="4">日保</option>
								<option value="1">点检</option>
								<option value="2">轮保</option>
								<option value="3">润滑</option>
							</select>
				   <span class="label">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" class='btn' id="buttonf" onclick="addFilesDataget()" value="确定" /></span>
				</form>
				<br/><br/>
				<hr/>
				<br/>
				<table class="table" style="width:97%;">
					<font style="color:red;">
					   EXCEL格式必须遵守如下格式(第一列中文说明，第二列是需要导入的数据，且第一列第二列顺序必须如下图所示)：
					</font>
					<p><img id='img' src="${pageContext.request.contextPath }/pms/sys/datadict/imagesHotspot/img/ribao.png" /></p>
					<div  style="color:red;">
					      温馨提醒：请确保导入的excel数据的行数是否小于5000行！
					</div>
				</table>
			</fieldset>
		<br/><br/>
	</div>
	
</div>
<script type="text/javascript">
$(function(){
	$("#paul_category").combobox({  
	    url:"${pageContext.request.contextPath}/pms/sys/eqpcategory/queryMdEqpCategory.do",
	    valueField:'id',  
	    textField:'name',
	    panelHeight:'250'
	}); 
});
//导入数据，验证
function addFilesDataget(){
	var file = $("#importFile").val();
	if (file == '' || file == null) {
		$.messager.alert('提示','请选择所要上传的文件！','error');
	}else {
		var index = file.lastIndexOf(".");
		if (index < 0) {
			$.messager.alert('提示','上传的文件格式不正确，请选择97-2007Excel文件(*.xls 或 *.xlsx)！','error');
		}else {
			var ext = file.substring(index + 1, file.length);
			if (ext == "xls" || ext=="xlsx") {
				$.messager.progress({
					title:'',
					msg:'正在导入...',
					interval:'1000'
				}); 
				$('#filesForm').form({   
				    url:"${pageContext.request.contextPath}/pms/sys/eqptype/inputExeclAndReadWrite.do?id="+Math.random(),   
				    success:function(data){ 
				    	data=jQuery.parseJSON(data);
				    	$.messager.progress('close');
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
				$.messager.alert('提示','上传的文件格式不正确，请选择97-2007Excel文件(*.xls 或 *.xlsx)！','error');
			}
			
		}
	}
}

</script>
</body>
</html>