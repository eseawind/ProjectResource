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
				rownumbers :true,
				striped : true,
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
					title : '润滑剂ID',
					width : 120,
					hidden : true
				}, {
					field : 'lubricantCode',
					title : '卡片编号',
					width : 100,
					align:'center',
					sortable : true
				}] ],
				columns : [ [
					{
						field : 'lubricantName',
						title : '油品名称',
						width : 200,
						align:'left',
						sortable : true
					}, {
						field : 'standard',
						title : '规格',
						align:'left',
						width : 250,
						sortable : true
					}, {
						field : 'createName',
						title : '创建人',
						align:'center',
						width : 70,
						sortable : true
					},{
						field : 'createDate',
						title : '创建时间',
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
		function queryEqu() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/equ/lubricant/queryLubricant.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询用户异常", 'error');
				}
			});
		}
		
	function editLubercant(){
		var dialog = parent.$.modalDialog({
			title : '润滑剂规格编辑',
			width : 620,
			height : 240,
			href : '${pageContext.request.contextPath}/pms/equ/lubricant/goToLubricantEdit.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/equ/lubricant/addLubricant.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryEqu();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	} 
	
	function deleteLubricant(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前润滑剂规格？', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/equ/lubricant/deleteLubricant.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryEqu();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	

	

	function goToLubriAdd(){
		var dialog = parent.$.modalDialog({
			title : '润滑剂规格添加',
			width : 620,
			height : 240,
			href : '${pageContext.request.contextPath}/pms/equ/lubricant/gotoLubricantAdd.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/equ/lubricant/addLubricant.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryEqu();
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
		goToLubriAdd();
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar"  style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">卡片编号：</span>
						<span><input type="text" name="lubricantCode" class="easyui-validatebox "  data-options="prompt: '请输入卡片编号'"/></span>
					</div>
					<div >
						<span class="label">油品编号：</span>
						<input type="text" name="lubricantName" class="easyui-validatebox "  data-options="prompt: '请输入油品编号'"/>
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" style="width:1oo%;">
			<a onclick="queryEqu()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			<a onclick="goToLubriAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
			<!-- <a onclick="viewLubricate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">详情</a> -->
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="goToLubriAdd();"  data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</div>
		<div onclick="editLubercant()" data-options="iconCls:'icon-standard-plugin-edit'">编辑</div>
		<div onclick="deleteLubricant();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		<!-- <div onclick="viewLubricate();"  data-options="iconCls:'icon-standard-plugin-add'">详情</div> -->
	</div>
</body>
</html>