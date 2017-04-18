<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>物料管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	var dataGrid;
	var dialog;
	$(function() {
		$.loadComboboxData($("#matType"),"MATTYPE",true);
		dataGrid = $('#dataGrid').datagrid({
			rownumbers :true,
			idField : 'id',
			fit : true,
			striped : true,
			pagination : true,
			pageSize : 20,
			pageList : [20, 30, 40, 50 ],
			sortName : 'code',
			sortOrder : 'asc',
			singleSelect :true,
			fitColumns : false,
			border : false,
			striped : true,
			remoteSort: false,
			nowrap : true,
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			columns : [ [ {
				title : '编号',
				field : 'id',
				hidden : true
			}, {
				field : 'code',
				title : '编码',
				width : 80,
				sortable:true
			} , {
				field : 'name',
				title : '物料全称',
				width : 230,
				sortable:true
			}/* , {
				field : 'simpleName',
				title : '物料简称',
				width : 140,
				sortable:true
			} */, {
				field : 'matType',
				title : '类型',
				align : "center",
				width : 70,
				sortable : true
			}, {
				field : 'unit',
				title : '计量单位',
				align : "center",
				width : 60,
				sortable : true
			}, {
				field : 'des',
				title : '物料描述',
				width : 250,
				sortable:true
			}, {
				field : 'standardVal',
				title : '标准消耗',
				width : 120,
				sortable : true
			}, {
				field : 'lastUpdateTime',
				title : '最后修改时间',
				width : 130,
				sortable : true
			}, {
				field : 'enable',
				title : '状态',
				width : 50,
				align:'center',
				sortable:true,
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
	*查询物料
	*/
	function getAllMats(){
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/mat/getAllMats.do",
			queryParams : $("#searchForm").form("getData")
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
	/**
	* 跳转到物料添加页面
	*/
	function goToMatAddJsp() {
		dialog = parent.$.modalDialog({
			title : '物料添加',
			width : 700,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/mat/goToMatAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					//var f = $("#form");
					var f = dialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/mat/addMat.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllMats();
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
	* 跳转到物料编辑页面
	*/
	function goToMatEditJsp() {
		dialog = parent.$.modalDialog({
			title : '物料编辑',
			width : 700,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/mat/goToMatEditJsp.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/mat/editMat.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllMats();
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
	* 物料删除
	*/
	function deleteMat() {
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前物料?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/mat/deleteMat.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllMats();
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
						<span class="label">物料编码：</span>
						<input type="text" name="code" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:100"/>
					</div>
					<div >
						<span class="label">物料名称：</span>
						<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:100"/>
					</div>
					<div >
						<span class="label">物料类型：</span>
						<select  id="matType" name="matType" data-options="panelHeight:200,width:100,editable:false"></select>
					</div>
				</fieldset>
				</form>
			</div>		
			<div class="easyui-toolbar">
			<a onclick="getAllMats();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			
			<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			
			<a onclick="goToMatAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">添加</a>
		
			</div>
</div>

<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
				<div onclick="goToMatEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
			
				<div onclick="deleteMat();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
			
	</div>		
</body>
</html>