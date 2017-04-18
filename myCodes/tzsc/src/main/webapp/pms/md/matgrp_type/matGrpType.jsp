<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>物料组与物料类型</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
		var typeGrid = null;
		var grpGrid=null;
		$(function() {
			typeGrid = $('#typeGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				//pagination : true,
				idField : 'id',
				striped : true,
				remoteSort: false,
				/* pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc', */
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : 'id',
					width : 120,
					hidden : true
				}, {
					field : 'code',
					title : '类型编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'name',
					title : '类型名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'matGrp',
					title : '物料组',
					align:'center',
					width : 60,
					sortable : true
				}
				, {
					field : 'des',
					title : '类型描述',
					align:'left',
					width : 150,
					sortable : true
				}, {
					field : 'enable',
					title : '是否启用',
					align:'center',
					width : 70,
					sortable : true,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
					}
				} ] ],
				toolbar : '#typeToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#typeMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			});
			
			grpGrid = $('#grpGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				//pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				remoteSort: false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : '物料组名称',
					width : 120,
					hidden : true
				}, {
					field : 'code',
					title : '物料组编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'name',
					title : '物料组名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'des',
					title : '物料组描述',
					align:'left',
					width : 150,
					sortable : true
				}, {
					field : 'enable',
					title : '是否启用',
					align:'center',
					width : 70,
					sortable : true,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
					}
				} ] ],
				toolbar : '#grpToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#grpMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				},
				onClickRow:function(rowIndex,rowData){
					getAllTypesByGrp(rowData.id);
				}
			});
		});
/**-----------------------------------------物料组处理----------------------------------------*/
	   /**
		* 查询
		*/	
		function getAllMatGrps() {
			grpGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/matgrp/getAllMatGrps.do",
				queryParams :$("#grpForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
		}
		function clearGrpForm(){
			$("#grpForm input").val(null);
	   }
		function deleteMatGrp(){
			var row = grpGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前物料组?', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/matgrp/deleteMatGrp.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllMatGrps();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
		}

		function goToMatGrpAddJsp(){
			var dialog = parent.$.modalDialog({
				title : '物料组添加',
				width : 620,
				height : 290,
				href : '${pageContext.request.contextPath}/pms/matgrp/goToMatGrpAddJsp.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/matgrp/addMatGrp.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getAllMatGrps();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
		function goToMatGrpEditJsp(){
			var dialog = parent.$.modalDialog({
				title : '类型编辑',
				width : 620,
				height : 290,
				href : '${pageContext.request.contextPath}/pms/matgrp/goToMatGrpEditJsp.do?id='+grpGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/matgrp/editMatGrp.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getAllMatGrps();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
	   function getAllTypesByGrp(id){
		   typeGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/mattype/getAllTypesByGrp.do?gid=" + id
		   });
	   }
	   function clearTypeForm(){
			$("#typeForm input").val(null);
	   }
	   //查询物料组
	   function getAllMatTypes(){
		   typeGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/mattype/getAllMatTypes.do",
				queryParams :$("#typeForm").form("getData")
			});
	   }
	   function deleteMatType(){
		   var row = typeGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前物料类型？', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/mattype/deleteMatType.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllMatTypes();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
	   }
	   //跳转到类型添加页面
	   function goToMatTypeAddJsp(){
		   var dialog = parent.$.modalDialog({
				title : '物料类型添加',
				width : 620,
				height : 290,
				href : '${pageContext.request.contextPath}/pms/mattype/goToMatTypeAddJsp.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/mattype/addMatType.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getAllMatTypes();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   
	   //跳转到类型编辑页面
	   function goToMatTypeEditJsp(){
		   var dialog = parent.$.modalDialog({
				title : '物料类型编辑',
				width : 620,
				height : 290,
				href : '${pageContext.request.contextPath}/pms/mattype/goToMatTypeEditJsp.do?id='+typeGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/mattype/editMatType.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getAllMatTypes();
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
	<div data-options="region:'west',border:true,split:false,title:'物料组【可双击类型表格】'" style="width:560px;">
		<div id="grpToolbar"  style="display: none;width:100%;">
			<form id="grpForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div >
							<span class="label">类型名称：</span>
							<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '请输入物料组名称'"/>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<a onclick="getAllMatGrps()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearGrpForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<a onclick="goToMatGrpAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
			</div>
		</div>
		<table id="grpGrid"></table>
	</div>
	
	<div data-options="region:'center',border:true,title:'物料类型'">
		<div id="typeToolbar" style="display: none;">
			<form id="typeForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div >
							<span class="label">型号名称：</span>
							<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '请输入类型名称'"/>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<a onclick="getAllMatTypes()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearTypeForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<a onclick="goToMatTypeAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加类型</a>
			</div>
		</div>
		<table id="typeGrid"></table>
	</div>
	<div id="typeMenu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="goToMatTypeEditJsp()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteMatType();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
	<div id="grpMenu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="goToMatGrpEditJsp()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteMatGrp();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>