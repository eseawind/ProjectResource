<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>	
<title>辅料系数管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	var dataGrid;
	var dialog;
	$(function() {
		$.loadComboboxData($("#matType"),"MATTYPE",true);
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
			} , {
				field : 'mat',
				title : '辅料名称',
				width : 160
			}, {
				field : 'length',
				title : '长度(m)',
				align : 'right',
				width : 60
			}, {
				field : 'width',
				title : '宽度(m)',
				align : 'right',
				width : 60
			}, {
				field : 'density',
				title : '密度(kg/㎡)',
				align : 'right',
				width : 60
			}, {
				field : 'val',
				title : '计算系数',
				align : 'right',
				width : 60
			}, {
				field : 'des',
				title : '说明',
				width : 150
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
	*查询辅料系数
	*/
	function getAllMatParams(){
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/matparam/getAllMatParams.do",
			queryParams : $("#searchForm").form("getData")
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
	/**
	* 跳转到辅料系数添加页面
	*/
	function goToMatParamAddJsp() {
		dialog = parent.$.modalDialog({
			title : '辅料系数添加',
			width : 620,
			height : 320,
			href : '${pageContext.request.contextPath}/pms/matparam/goToMatParamAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find('#form');
					if(f.form("validate")&&f.find("#mat").attr("value")){
						$.post("${pageContext.request.contextPath}/pms/matparam/addMatParam.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllMatParams();
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
	* 跳转到辅料系数编辑页面
	*/
	function goToMatParamEditJsp() {
		dialog = parent.$.modalDialog({
			title : '辅料系数编辑',
			width : 620,
			height : 320,
			href : '${pageContext.request.contextPath}/pms/matparam/goToMatParamEditJsp.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")&&f.find("#mat").attr("value")){
						$.post("${pageContext.request.contextPath}/pms/matparam/editMatParam.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllMatParams();
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
	* 辅料系数删除
	*/
	function deleteMatParam() {
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前辅料系数?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/matparam/deleteMatParam.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllMatParams();
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
						<span class="label">辅料类型</span>
						<select id="matType" name="matType" data-options="panelHeight:200,width:130,editable:false"></select>
					</div>
				</fieldset>
				</form>
			</div>		
			<div class="easyui-toolbar">
			<a onclick="getAllMatParams();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			
			<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			
			<a onclick="goToMatParamAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">添加</a>
			<span class="label">[由于采集程序采集的原始数据为传感器脉冲数，所以需要对【水松纸】【小盒烟膜】【条盒烟膜】进行计算系数设置]</span>
			</div>
</div>

<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
				<div onclick="goToMatParamEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
			
				<div onclick="deleteMatParam();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
			
	</div>		
</body>
</html>