<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>设备-开窗取值</title>
<meta title="开发" content="leejean"/>

<script type="text/javascript">
		var eqpPickGrid=null;
		$(function() {
			eqpPickGrid = $('#eqpPickGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 20,
				remoteSort:false,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				columns : [ [{
					field : 'id',
					title : '设备id',
					width : 120,
					hidden : true
				}, {
					field : 'equipmentCode',
					title : '设备编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'equipmentName',
					title : '设备名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'equipmentDesc',
					title : '设备描述',
					align:'center',
					width : 150,
					sortable : true
				},{
					field : 'eqpTypeName',
					title : '机型',
					align:'center',
					width : 70,
					sortable : true
				},
				{
					field : 'workShopName',
					title : '车间',
					align:'center',
					width : 100,
					sortable : true
				}] ],
				toolbar:"#eqpPickToolbar",
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				}
				
			});
		});
		/**
		* 查询
		*/	
		function queryEqu() {
			eqpPickGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/equc/queryEqu.do",
						queryParams :$("#searchForm").form("getData"),
						onLoadError : function(data) {
							$.messager.show('提示', "查询用户异常", 'error');
						}
			});
		}
		function clearForm(){
			$("#searchForm input").val(null);
		}
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
	<div id="eqpPickToolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			 <table style="margin:4px 0px 0px 0px">
			 	<tr>
			 		<th>设备主数据名称：</th>
					<td><input type="text" name="equipmentName" class="easyui-validatebox "  data-options="prompt: '请输入设备名称'"/></td>
					<th>所属车间：</th>
					<td>
						<select name="workShopId" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
							<option value=""></option>
							<option value="1">卷包车间</option>
							<option value="2">成型车间</option>
						</select>
					</td>
					
				</tr>
			 	<tr>
			 		<table style="margin:4px 0px 0px 0px">
				 		<tr>
				 			<td><a onclick="queryEqu()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a></td>
							<td><a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a></td>
						</tr>
					<table>
			 	</tr>
			 </table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="eqpPickGrid"></table>
	</div>
</div>