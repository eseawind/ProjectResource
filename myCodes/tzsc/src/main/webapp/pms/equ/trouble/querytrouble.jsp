<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备停机统计</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			$.loadComboboxData($("#equType"),"EQPTYPE",true);
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
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'id',
					title : '设备故障id',
					width : 120,
					checkbox : true
				}, {
					field : 'equName',
					title : '设备名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'equType',
					title : '设备型号',
					align:'center',
					width : 70,
					sortable : true	
				},{
					field : 'stopTime',
					title : '停机时间',
					align:'center',
					width : 120,
					sortable : true
				},{
					field : 'stopTimes',
					title : '停机次数',
					align:'center',
					width : 120,
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
		/*
		* 查询设备故障
		*/
		function queryTrouble() {
			dataGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/trouble/queryTrouble.do",
						queryParams :$("#searchForm").form("getData"),
						onLoadError : function(data) {
							$.messager.show('提示', "查询设备故障异常", 'error');
						}
					});
			}
		function clearForm(){
			$("#searchForm input").val(null);
			$("#equType").combobox("setValue", "");//下拉框赋值
		}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">设备名称：</span>
						<input type="text" name="equName" class="easyui-validatebox "  data-options="prompt: '请输入设备名称'"/>
					</div>
					<div >
						<span class="label">设备型号：</span>
						<select id="equType" name="equType" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">查询日期：</span>
						 <input name="runDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:130px"/>
					</div>	
					
					<div >
						<span class="label" style="width:10px">到</span>
						<input name="runDate2" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:130px"/>					 
					</div>	
					
								
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
				<a onclick="queryTrouble()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<!-- <div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="editEqu()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteEqu();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div> -->
</body>
</html>