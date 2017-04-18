<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>文件管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
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
				rownumbers :true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'id',
					title : '设备id',
					width : 120,
					hidden : true
				},{
					field : 'equId',
					title : '润滑ID',
					width : 120,
					hidden : true
				},{
					field : 'equipmentCode',
					title : '设备编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'equipmentName',
					title : '设备名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'equipmentType',
					title : '设备类别',
					align:'center',
					width : 70,
					sortable : true
				},{
					field : 'oilCode',
					title : '润滑剂编码',
					align:'center',
					width : 70,
					sortable : true
				},{
					field : 'oilName',
					title : '润滑剂名称',
					align:'center',
					width : 70,
					sortable : true
				},{
					field : 'lubricantPart',
					title : '润滑部位',
					align:'center',
					width : 70,
					sortable : true
				} ] ],
				columns : [ [
				{
					field : 'lubricanteDate',
					title : '润滑日期',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'lubricanteName',
					title : '完成人',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'examinePeopleName',
					title : '检查人',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'examineDate',
					title : '检查日期',
					align:'center',
					width : 130,
					sortable : true
				},{
					field : 'createName',
					title : '创建人',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'createDate',
					title : '创建日期',
					align:'center',
					width : 150,
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
		function queryLubri() {
			dataGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/equc/lubri/queryLubri.do",
						queryParams :$("#searchForm").form("getData"),
						onLoadError : function(data) {
							$.messager.show('提示', "查询用户异常", 'error');
						}
					});
		}
		
	function editLubri(){
		var dialog = parent.$.modalDialog({
			title : '设备润滑编辑',
			width : 620,
			height : 570,		
			href : '${pageContext.request.contextPath}/pms/equc/lubri/goToLubriEdit.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '修改',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/equc/lubri/addLubri.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryLubri();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	} 
	
	function deleteLubri(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前设备润滑？', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/equc/lubri/deleteLubri.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryLubri();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	

	

	function goToLubriAdd(){
		var dialog = parent.$.modalDialog({
			title : '设备润滑添加',
			width : 620,
			height : 570,
			href : '${pageContext.request.contextPath}/pms/equc/lubri/gotoLubriAdd.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					alert(f.form("validate"));
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/equc/lubri/addLubri.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryLubri();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	function viewLubricate(){
		editLubri();
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar"  style="display: none;width:100%;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">设备名称：</span>
						<input type="text" name="mdEquipment.equipmentName" class="easyui-validatebox "  data-options="prompt: '请输入设备'"/>
					</div>
					<div >
						<span class="label">润滑部位：</span>
						<input type="text" name="lubricantPart" class="easyui-validatebox "  data-options="prompt: '请输入润滑部位'"/>
					</div>
					<div >
						<span class="label">润滑剂名称：</span>
						<input type="text" name="eqmLubricantMaintain.id" style="width:120px;" class="easyui-combobox easyui-validatebox" data-options="textField:'lubricantName',valueField:'id',
								url:'${pageContext.request.contextPath }/pms/equ/lubricant/queryAllLubricant.do',prompt: '请选择润滑剂'
							"/>
					</div>
					
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" style="width:1oo%;">
			<a onclick="queryLubri()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			<a onclick="goToLubriAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
			<!-- <a onclick="viewLubricate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">详情</a> -->
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="editLubri()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteLubri();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		<div onclick="viewLubricate();"  data-options="iconCls:'icon-standard-plugin-add'">详情</div>
	</div>
</body>
</html>