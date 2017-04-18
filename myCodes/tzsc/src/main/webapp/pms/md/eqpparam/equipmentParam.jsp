<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>	
<title>滚轴系数管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	var dataGrid;
	var dialog;
	$(function() {
		$.loadComboboxData($("#workshop"),"WORKSHOP",true);
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
				field : 'equipment',
				title : '设备',
				width : 120
			} , {
				field : 'axlePz',
				title : '卷烟纸/滤棒盘纸滚轴参数',
				align : 'right',
				width : 140
			}, {
				field : 'axleSsz',
				title : '水松纸滚轴参数',
				align : 'right',
				width : 120
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
	*查询滚轴系数
	*/
	function getAllEquipmentParams(){
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/eqpparam/getAllEquipmentParams.do",
			queryParams : $("#searchForm").form("getData")
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
	/**
	* 跳转到滚轴系数添加页面
	*/
	function goToEquipmentParamAddJsp() {
		dialog = parent.$.modalDialog({
			title : '滚轴系数添加',
			width : 600,
			height : 320,
			href : '${pageContext.request.contextPath}/pms/eqpparam/goToEquipmentParamAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find('#form');
					//if(f.form("validate")&&f.find("#mat").attr("value")){
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/eqpparam/addEquipmentParam.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllEquipmentParams();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}else{
						//f.find("input[name='matName']").focus();;
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
	* 跳转到滚轴系数编辑页面
	*/
	function goToEquipmentParamEditJsp() {
		dialog = parent.$.modalDialog({
			title : '滚轴系数编辑',
			width : 600,
			height : 320,
			href : '${pageContext.request.contextPath}/pms/eqpparam/goToEquipmentParamEditJsp.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					//if(f.form("validate")&&f.find("#mat").attr("value")){
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/eqpparam/editEquipmentParam.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllEquipmentParams();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
					
				}
			} ],
			onLoad:function(){
				/* var f = dialog.find('#form'), ret = $.fn.dialog.defaults.onLoad();
				f.find("input[name='matName']").focus();
				return ret; */
			}
		});
	}
	
	/**
	* 滚轴系数删除
	*/
	function deleteEquipmentParam() {
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前滚轴系数?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/eqpparam/deleteEquipmentParam.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllEquipmentParams();
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
						<span class="label">车间</span>
						<select id="workshop" name="workshop" data-options="panelHeight:'auto',width:130,editable:false"></select>
					</div>
				</fieldset>
				</form>
			</div>		
			<div class="easyui-toolbar">
			<a onclick="getAllEquipmentParams();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			
			<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			
			<a onclick="goToEquipmentParamAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">添加</a>
			<span class="label">[由于采集程序采集的原始数据为传感器脉冲数，所以需要对卷烟机的【卷烟纸滚轴】【水松纸滚轴】，成型机的【滤棒盘纸滚轴】进行系数设置]</span>
			</div>
</div>

<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
				<div onclick="goToEquipmentParamEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
			
				<div onclick="deleteEquipmentParam();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
			
	</div>		
</body>
</html>