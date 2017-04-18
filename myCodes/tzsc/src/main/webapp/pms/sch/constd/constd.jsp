<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>	
<title>标准单耗管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	var dataGrid;
	var dialog;
	var id;
	$(function() {
		$.loadComboboxData($("#matProd"),"MATPROD",true);
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			remoteSort: false,
			idField : 'id',
			striped : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'asc',
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			showPageList:false,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				checkbox : true
			}, {
				field : 'matProd',
				title : '产品',
				width : 160,
				sortable : true
			} , {
				field : 'matName',
				title : '辅料',
				align : 'left',
				width : 250,
				sortable : true
			}, {
				field : 'val',
				title : '标准值',
				align : 'left',
				width : 100,
				sortable : true
			}, {
				field : 'uval',
				title : '超标上限',
				align : 'left',
				width : 100,
				sortable : true
			}, {
				field : 'lval',
				title : '超标下限',
				align : 'left',
				width : 100,
				sortable : true
			}, {
				field : 'euval',
				title : '报警上限',
				align : 'left',
				width : 100,
				sortable : true
			}, {
				field : 'elval',
				title : '报警下限',
				align : 'left',
				width : 100,
				sortable : true
			}, {
				field : 'des',
				title : '说明',
				width : 150,
				sortable : true
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
				id=rowData.id;
			}
		});
	});
	/**
	*查询标准单耗
	*/
	function getAllConStds(){
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/constd/getAllConStds.do",
			queryParams : $("#searchForm").form("getData")
		});
		dataGrid.datagrid('uncheckAll');
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
	/**
	* 跳转到标准单耗添加页面
	*/
	function goToConStdAddJsp() {
		dialog = parent.$.modalDialog({
			title : '标准单耗添加',
			width : 600,
			height : 320,
			href : '${pageContext.request.contextPath}/pms/constd/goToConStdAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find('#form');
					if(f.form("validate")&&f.find("#mat").attr("value")){
						$.post("${pageContext.request.contextPath}/pms/constd/addConStd.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllConStds();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}else{
						f.find("input[name='matName']").focus();;
					}
					
				}
			} ],
			onLoad:function(){
				var f = dialog.find('#form'), ret = $.fn.dialog.defaults.onLoad();
				f.find("input[name='matName']").focus();
				return ret;
			}
		});
	}
	/**
	* 跳转到标准单耗编辑页面
	*/
	function goToConStdEditJsp() {
		dialog = parent.$.modalDialog({
			title : '标准单耗编辑',
			width : 600,
			height : 320,
			href : '${pageContext.request.contextPath}/pms/constd/goToConStdEditJsp.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")&&f.find("#mat").attr("value")){
						$.post("${pageContext.request.contextPath}/pms/constd/editConStd.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllConStds();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
					
				}
			} ],
			onLoad:function(){
				var f = dialog.find('#form'), ret = $.fn.dialog.defaults.onLoad();
				f.find("input[name='matName']").focus();
				return ret;
			}
		});
	}
	
	/**
	* 标准单耗删除
	*/
	function deleteConStd() {
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前标准单耗?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/constd/deleteConStd.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllConStds();
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
						<span class="label">产品</span>
						<select id="matProd" name="matProd" data-options="panelHeight:200,width:130,editable:false"></select>
					</div>
				</fieldset>
				</form>
			</div>		
			<div class="easyui-toolbar">
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/constd/getAllConStds.do']}">
					<a onclick="getAllConStds();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/constd/getAllConStds.do']}">
					<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/constd/goToConStdAddJsp.do']}">
					<a onclick="goToConStdAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-add'">添加</a>
			    </c:if>
			</div>
</div>

<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['goToConStdEditJsp.do']}">
			<div onclick="goToConStdEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/constd/deleteConStd.do']}">
			<div onclick="deleteConStd();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
		</c:if>
	</div>		
</body>
</html>