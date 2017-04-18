<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>备品备件管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	var dataGrid;
	var dialog;
	$(function() {
		$.loadComboboxData($("#eqpTypeId"),"EQPTYPE",true);
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
			striped : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'desc',
			singleSelect:true,
			rownumbers :true,
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
			showPageList:false,
			columns : [ [{
				field : 'id',
				title : 'id',
				width : 20,
				hidden: true
			},{
				field : 'attr1',
				title : '编码',
				width : 50,
				sortable:true
			} , {
				field : 'name',
				title : '备件名称',
				width : 120,
				sortable:true
			}, {
				field : 'type',
				title : '型号',
				width : 80,
				sortable:true
			},{
				field : 'eqpTypeName',
				title : '所属机型',
				width : 60,
				align:'left',
				sortable:true
				
			},{
				field : 'number',
				title : '数量',
				width : 50,
				align:'left',
				sortable:true
				
			},{
				field : 'unitName',
				title : '单位',
				width : 50,
				sortable:true
			}, {
				field : 'price',
				title : '单价',
				width : 50,
				align:'right',
				sortable:true
				
			}, {
				field : 'remark',
				title : '备注',
				width : 200,
				align:'left',
				sortable:true				
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
	*查询列表
	*/
	function getAllBeans(){
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/spare/queryDataPMS.do",
			queryParams : $("#searchForm").form("getData")
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
	/**
	* 跳转到残烟丝系数添加页面
	*/
	function goToBeanAddJsp() {
		dialog = parent.$.modalDialog({
			title : '备品备件添加',
			width : 540,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/equ/spare/addSpareParts.jsp',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					//var f = $("#form");
					var f = dialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/spare/addBean.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllBeans();
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
	* 跳转到编辑页面
	*/
	function goToBeanEditJsp() {
		dialog = parent.$.modalDialog({
			title : '备品备件编辑',
			width : 540,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/spare/gotoUpdateBean.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/spare/updateBean.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllBeans();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	function deleteBean(){
		var id=dataGrid.datagrid('getSelected').id;
		if(id!=""){
			$.messager.confirm('删除提醒','确认删除?',function(r){
			    if (r){
			    	$.post("${pageContext.request.contextPath}/pms/spare/deleteBean.do?id="+dataGrid.datagrid('getSelected').id,function(json){
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllBeans();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					},"JSON");
			    }
			});
		}
	}
	
	//批量导入
	function inputExcelDatas(){
		dialog = parent.$.modalDialog({
			title : '批量导入',
			width : 700,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/spare/inputExcelDatas.do',
			/* buttons : [ {
				text : '取消',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#filesForm");
					if(f.form("validate")){
						 $.post("${pageContext.request.contextPath}/pms/spare/inputExeclAndReadWrite.do?id="+Math.random(),f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllBeans();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
						
					}
				}
			} ]  */
		});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
<div id="toolbar" style="display: none;">
			<div class="topTool">
				<form id="searchForm">
				<fieldset>
						<span class="label">名称：</span>
						<span><input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:200"/></span>
						<span class="label">机型：</span>
						<span><select id="eqpTypeId" name="eqpTypeId" data-options="panelHeight:'auto',width:175,editable:false"></select></span>
				</fieldset>
				</form>
			</div>		
			<div class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/spare/queryData.do']}">
				<a onclick="getAllBeans();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
			<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/equ/spare/addSpareParts.jsp']}">
				<a onclick="goToBeanAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">添加</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/spare/deleteBean.do']}">
				<a onclick="inputExcelDatas();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-delete'">批量导入</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/spare/gotoUpdateBean.do']}">
				<a onclick="goToBeanEditJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-edit'">编辑</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/spare/deleteBean.do']}">
				<a onclick="deleteBean();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-delete'">删除</a>
			</c:if>
			</div>
</div>

<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/equ/spare/addSpareParts.jsp']}">
			<div onclick="goToBeanAddJsp();" data-options="iconCls:'icon-standard-plugin-add'">添加</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/spare/gotoUpdateBean.do']}">	
			<div onclick="goToBeanEditJsp();" data-options="iconCls:'icon-standard-plugin-edit'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/spare/deleteBean.do']}">
			<div onclick="deleteBean();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>		
</body>
</html>