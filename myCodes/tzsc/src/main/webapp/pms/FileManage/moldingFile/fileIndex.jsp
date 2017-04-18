<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备主数据管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'fileName',
				sortOrder : 'asc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : '文件ID',
					width : 150,
					checkbox : true
				}, {
					field : 'fileName',
					title : '文件名',
					width : 250,
					align:'center',
					sortable : true
				}, {
					field : 'fileId',
					title : '文件id',
					hidden: true
				}, {
					field : 'fileType',
					title : '文件类型',
					width : 70,
					align:'center',
					sortable : true
				},{
					field : 'securityLevel',
					title : '安全级别',
					align:'center',
					width : 70,
					sortable : true
				},{
					field : 'createTime',
					title : '上传时间',
					align:'center',
					width : 180,
					sortable : true
				},{
					field : 'createName',
					title : '上传人',
					align:'center',
					width : 80,
					sortable : true
				} ] ],
				toolbar : '#toolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#menu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			});
		});
		/**
		* 查询文件
		*/	
		function queryFile() {
			dataGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/mfc/queryFile.do",
						queryParams :$("#searchForm").form("getData"),
						onLoadError : function(data) {
							$.messager.show('提示', "查询用户异常", 'error');
						}
					});
			}
	
	function deleteFile(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前文件?', function(b) {
			if (b) {
				$.post('${pageContext.request.contextPath}/pms/mfc/delFile.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryFile();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	
	function downloadFile(){
		var row = dataGrid.datagrid('getSelected');
		window.location = "${pageContext.request.contextPath}/pms/mfc/downloadFile.do?fileId="+row.fileId+"&fileName="+row.fileName;
	}
	
	function fileConvert(){
		var row = dataGrid.datagrid('getSelected');
		window.open("${pageContext.request.contextPath}/ConvertServlet?fileId="+row.fileId);
	}
	function goTofileUploadJsp(){
		var dialog = parent.$.modalDialog({
			title : '文件上传',
			width : 620,
			height : 390,
			href : '${pageContext.request.contextPath}/pms/mfc/goTofileUploadJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/mfc/updateFile.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryFile();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			 <table style="margin:4px 0px 0px 0px">
			 	<tr>
			 		<th>文件名称</th>
					<td><input type="text" name="fileName" class="easyui-validatebox "  data-options="prompt: '请输入文件名称'"/></td>
				</tr>
			 	<tr>
			 		<td><a onclick="queryFile()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a></td>
					<td><a onclick="goTofileUploadJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">上传</a></td>
			 	</tr>
			 </table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="fileConvert()" data-options="iconCls:'icon-standard-plugin-add'">预览</div>
		<div onclick="downloadFile()" data-options="iconCls:'icon-standard-plugin-add'">下载</div>
		<div onclick="deleteFile();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>