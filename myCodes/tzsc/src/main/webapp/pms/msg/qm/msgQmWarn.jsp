<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>文件管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			$("#time").my97("setValue",sts);
			$("#time2").my97("setValue",end);	
			$.loadComboboxData($("#workShopId"),"workshop",true);
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : true,
				border : false,
				pagination : true,
				idField : 'id',
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
				columns : [ [
				{
					field : 'id',
					title : 'ID',
					align:'center',
					width : 100,
					checkbox : true
				},{
					field : 'workShopName',
					title : '车间',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'equipName',
					title : '机台',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'uName',
					title : '质检员',
					align:'center',
					width : 150,
					sortable : true
				},{
					field : 'content',
					title : '告警内容',
					align:'center',
					width : 400,
					sortable : true
				},{
					field : 'qi',
					title : '标准值',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'val',
					title : '实际值',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'item',
					title : '科目',
					align:'center',
					width : 120,
					sortable : true
				},{
					field : 'time',
					title : '告警时间',
					align:'center',
					width : 150,
					sortable : true
				},{
					field : 'sts',
					title : '状态',
					align:'center',
					width : 80,
					sortable : true,
					formatter: function(value,row,index){
						if(value=='0')
							return "<font color=red>未读</font>";
						else if(value=='1')
							return "已读";
					}
				}] ],
				/* rowStyler:function(index,row){
					if(row.sts == '0')
						return 'background-color:#6293BB;color:#fff;';
				}, */
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
		function queryMsgQmWarn() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/msg/qm/queryMsgQmWarn.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询质量告警记录异常", 'error');
				}
			});
		}
		
	function editMsgQmWarn(){
		var dialog = parent.$.modalDialog({
			title : '质量告警记录编辑',
			width : 620,
			height : 400,
			href : '${pageContext.request.contextPath}/pms/msg/qm/gotoMsgQmWarnForm.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/msg/qm/saveOrUpdateMsgQmWarn.do?flag=0",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMsgQmWarn();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	} 
	
	function deleteMsgQmWarn(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前质量告警记录？', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/msg/qm/deleteMsgQmWarn.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryMsgQmWarn();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	

	

	function goToMsgQmWarnAdd(){
		var dialog = parent.$.modalDialog({
			title : '质量告警记录添加',
			width : 620,
			height : 400,
			href : '${pageContext.request.contextPath}/pms/msg/qm/gotoMsgQmWarnForm.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/msg/qm/saveOrUpdateMsgQmWarn.do?flag=0",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMsgQmWarn();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	function viewMsgQmWarn(){
		goToMsgQmWarnAdd();
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar"  style="display: none;width:100%;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">车间：</span>
						<input id="workShopId" style="width:120px;" name="workShopId" class="easyui-combobox easyui-validatebox"  data-options="panelHeight:'auto',width:100,editable:false,prompt: '请选择车间'"/>
						
					</div>
					<div >
						<span class="label">机台：</span>
						<input name="equipName" type="text"  style="width:100px"/>
					</div>
					
					<div >
						<span class="label">告警时间：</span>
						<input id="time"  name="time" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:130px"/>
					</div>
						<div >
						<span class="label"style="width: 15px">到</span>
						<input id="time2"  name="time2" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss"  style="width:130px"/>
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" style="width:1oo%;">
			<a onclick="queryMsgQmWarn()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			<!-- <a onclick="goToMsgQmWarnAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a> -->
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="editMsgQmWarn()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteMsgQmWarn();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>