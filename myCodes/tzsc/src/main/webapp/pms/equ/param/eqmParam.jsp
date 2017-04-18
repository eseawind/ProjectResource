<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备管理模块参数维护界面</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
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
			columns : [ [{
				field : 'id',
				title : 'ID',
				width : 40,
				hidden: true
			},{
				field : 'code',
				title : '编码',
				width : 80
			} , {
				field : 'name',
				title : '名称',
				width : 150
			}, {
				field : 'modulu',
				title : '系数',
				width : 150
			}, {
				field : 'remark',
				title : '备注',
				width : 300,
				align:'left',
				
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
	*查询残烟丝系数
	*/
	function getAllBeans(){
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/eqmparam/queryParam.do",
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
			title : '设备模块参数添加',
			width : 560,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/eqmparam/gotoBean.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					//var f = $("#form");
					var f = dialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/eqmparam/addOrUpdateParam.do",f.form("getData"),function(json){
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
	* 跳转到残烟丝系数编辑页面
	*/
	function goToBeanEditJsp() {
		dialog = parent.$.modalDialog({
			title : '设备模块参数编辑',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/eqmparam/gotoEditBean.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/eqmparam/addOrUpdateParam.do",f.form("getData"),function(json){
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
	
	
	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
<div id="toolbar" style="display: none;">
			<div class="topTool">
				<form id="searchForm">
				<fieldset>
						<span class="label">名称：</span>
						<span><input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:200"/></span>
				</fieldset>
				</form>
			</div>		
			<div class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmparam/queryParam.do']}">
				<a onclick="getAllBeans();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
			<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmparam/addOrUpdateParam.do']}">
				<a onclick="goToBeanAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">添加</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmparam/gotoEditBean.do']}">
				<a onclick="goToBeanEditJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-edit'">编辑</a>
			</c:if>
			</div>
</div>

<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmparam/addOrUpdateParam.do']}">
			<div onclick="goToBeanAddJsp();"  data-options="iconCls:'icon-standard-user-add'">添加</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmparam/gotoEditBean.do']}">
			<div onclick="goToBeanEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
		</c:if>
	</div>		
</body>
</html>