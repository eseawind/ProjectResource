<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>班组管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
	var dataGrid;
	var dialog;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			rownumbers :true,
			idField : 'id',
			fit : true,
			singleSelect :true,
			fitColumns : false,
			border : false,
			striped : true,
			nowrap : true,
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			columns : [ [ {
				title : '编号',
				field : 'id',
				checkbox : true
			}, {
				field : 'code',
				title : '编码',
				width : 40
			} , {
				field : 'name',
				title : '班组名称',
				width : 150
			}, {
				field : 'seq',
				title : '排序',
				width : 150
			}, {
				field : 'enable',
				title : '状态',
				width : 40,
				align:'center',
				formatter : function(value, row, index) {
					return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
				}
			}  ] ],
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
	*查询班组
	*/
	function getAllTeams(){
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/team/getAllTeams.do",
			queryParams : $("#searchForm").form("getData")
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
	/**
	* 跳转到班组添加页面
	*/
	function goToTeamAddJsp() {
		dialog = parent.$.modalDialog({
			title : '班组添加',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/team/goToTeamAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					//var f = $("#form");
					var f = dialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/team/addTeam.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllTeams();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
				}
			} ]
		});
	}
	/**
	* 跳转到班组编辑页面
	*/
	function goToTeamEditJsp() {
		dialog = parent.$.modalDialog({
			title : '班组编辑',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/team/goToTeamEditJsp.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/team/editTeam.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllTeams();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
					
				}
			} ]
		});
	}
	
	/**
	* 班组删除
	*/
	function deleteTeam() {
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前班组?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/team/deleteTeam.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllTeams();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
<div id="toolbar" style="display: none;">
			<div class="topTool">
				<form id="searchForm">
				<fieldset >
					<div >
						<span class="label">班组名称：</span>
						<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:100"/>
						<th></th>
					</div>
				</fieldset>
				</form>
			</div>		
			<div class="easyui-toolbar">
			<a onclick="getAllTeams();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			
			<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			
			<a onclick="goToTeamAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">添加</a>
		
			</div>
</div>

<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
				<div onclick="goToTeamEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
			
				<div onclick="deleteTeam();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
			
	</div>		
</body>
</html>