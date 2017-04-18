<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备检修</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
	var dataGrid=null;
	$(function() {
		var d=new Date();
		var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
		var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
	    $("#startTime").my97("setValue",sts);//
	    $("#endTime").my97("setValue",end);	//
	    
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
			striped : true,
			pageSize : 10,
			rownumbers :true,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'desc',
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			showPageList:false,
			columns : [ [
			{
				field : 'id',
				title : 'ID',
				align:'center',
				width : 1,
				hidden : true
			},{
				field : 'eqp_name',
				title : '设备名称',
				align:'center',
				width : 100,
				sortable : true
			},{
				field : 'place',
				title : '部位',
				align:'center',
				width : 150,
				sortable : true
			},{
				field : 'contents',
				title : '检修项目内容',
				align:'center',
				width : 230,
				sortable : true
			},{
				field : 'date_plans',
				title : '计划执行日期',
				align:'center',
				width : 100,
				sortable : true
			},{
				field : 'blame_usr_name',
				title : '计划责任人',
				align:'center',
				width : 70,
				sortable : true
			},{
				field : 'solution',
				title : '解决措施',
				align:'center',
				width : 160,
				sortable : true
			},{
				field : 'real_usr_name',
				title : '实际完成人',
				align:'center',
				width : 160,
				sortable : true
			},{
				field : 'real_times',
				title : '实际完成日期',
				align:'center',
				width : 100,
				sortable : true
			},{
				field : 'note',
				title : '检修注意事项',
				align:'center',
				width : 150,
				sortable : true
			},{
				field : 'review',
				title : '复查情况',
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
	function queryMaintain() {
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/overhaul/queryMaintain.do",
			queryParams :$("#searchForm").form("getData"),
			onLoadError : function(data) {
				$.messager.show('提示', "查询设备检修项目异常", 'error');
			}
		});
	}
	
	function gotoMaintainEdit(){
		var dialog = parent.$.modalDialog({
			title : '设备检修项目编辑',
			width : 620,
			height : 400,
			href : '${pageContext.request.contextPath}/pms/overhaul/gotoMaintainEdit.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '修改',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/overhaul/editMaintain.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMaintain();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	} 
	//点检完成后，编写措施
	function gotoMaintainEditSlove(){
		var dialog = parent.$.modalDialog({
			title : '设备检修项目编辑',
			width : 620,
			height : 400,
			href : '${pageContext.request.contextPath}/pms/overhaul/gotoMaintainEditSlove.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '修改',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/overhaul/editMaintain.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMaintain();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	function deleteMaintain(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前设备检修项目？', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/overhaul/deleteMaintain.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryMaintain();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	
	function gotoMaintainAdd(){
		var dialog = parent.$.modalDialog({
			title : '设备检修项目添加',
			width : 620,
			height : 400,
			href : '${pageContext.request.contextPath}/pms/overhaul/gotoMaintainAdd.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/overhaul/addMaintain.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMaintain();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
	
	
	function inputExcel(){
		///pms/overhaul/inputExeclAndReadWrite.do
		//批量导入
			dialog = parent.$.modalDialog({
				title : '批量导入',
				width : 700,
				height : 350,
				href : '${pageContext.request.contextPath}/pms/equ/overhaul/iframe_excel.jsp',
			});
	}
	
	$(function(){
		var today = new Date();
		var enddate=today.getDate();
		//var startDate=enddate-6;
		var date=today.getFullYear()+"-"+(today.getMonth()+1)+"-"+01; 
		var lastdate = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+enddate; 
	    $("#startTime").datebox("setValue",date);	//周初 
	    $("#endTime").datebox("setValue",lastdate);	//周末
	})
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar"  style="display: none;width:100%;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
				<div >
						<span class="label">设备名称：</span>
						<input type="text" name="eqp_name" class="easyui-validatebox "  data-options="prompt: '请输入设备'"/>
					</div>
						<div >
						<span class="label">计划责任人：</span>
						<input type="text" name="blame_usr_name" class="easyui-validatebox "  data-options="prompt: '请输入检修项目编号'"/>
					</div>
					
					<div >
						<span class="label">检修日期：</span>
						<span>
							<input id="startTime" name="startTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:120px" data-options="prompt:'请选择检修日期'"/>
						</span>
					</div>
					<div >
						<b>到：</b>
						<span>
							<input id="endTime" name="endTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:120px" data-options="prompt:'请选择检修日期'"/>
						</span>
					</div>
				
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/queryMaintain.do']}">
				<a onclick="queryMaintain()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
			<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/gotoMaintainAdd.do']}">
				<a onclick="gotoMaintainAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/gotoMaintainEdit.do']}">
				<a onclick="gotoMaintainEdit();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/gotoMaintainEditSlove.do']}">
				<a onclick="gotoMaintainEditSlove();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">解决措施</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/deleteMaintain.do']}">
				<a onclick="deleteMaintain();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/deleteMaintain.do']}">
				<a onclick="inputExcel()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">导入Excel</a>
			</c:if>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/gotoMaintainAdd.do']}">
			<div onclick="gotoMaintainAdd();" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/gotoMaintainEdit.do']}">
			<div onclick="gotoMaintainEdit();"data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/gotoMaintainEditSlove.do']}">
			<div onclick="gotoMaintainEditSlove();" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">解决措施</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/overhaul/deleteMaintain.do']}">
			<div onclick="deleteMaintain();" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>
</body>
</html>