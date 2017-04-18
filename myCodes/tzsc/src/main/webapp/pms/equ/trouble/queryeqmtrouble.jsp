<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备故障统计_维护时记录</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			//初始化时间
		    var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		    $("#date1").my97("setValue",sts);//
		    $("#date2").my97("setValue",end);	//
		   
			$.loadComboboxData($("#equType"),"EQPTYPE",true);
			$.loadComboboxData($("#shift"),"SHIFT",true);
			$.loadComboboxData($("#workshop"),"WORKSHOP",true);
			$.loadComboboxData($("#eqpcategory"),"EQPCATEGORY",true);
			$.loadComboboxData($("#alleqps"),"ALLEQPS",true);
			
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'equ_name',
					title : '设备名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'shift_name',
					title : '班次',
					align:'center',
					width : 70,
					sortable : true	
				},{
					field : 'create_date',
					title : '故障发生日期',
					align:'center',
					width : 120,
					sortable : true
				},{
					field : 'sourceString',
					title : '维护类型',
					align:'center',
					width : 80,
					sortable : true
				}/* ,{
					field : 'source_name',
					title : '维护内容',
					align:'center',
					width : 220,
					sortable : true
				} */,{
					field : 'name',
					title : '故障名称',
					align:'center',
					width : 180,
					sortable : true
				},{
					field : 'des',
					title : '故障描述',
					align:'center',
					width : 280,
					sortable : true
				},{
					field : 'create_user_name',
					title : '创建人',
					align:'center',
					width : 100,
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
		function queryEqmTrouble() {
			var tempUrl ="${pageContext.request.contextPath}/pms/trouble";
			dataGrid.datagrid({
				url : tempUrl+"/queryTrouble.do",
				queryParams :$("#searchForm").form("getData")
			});
		}
		function clearForm(){
			$("#searchForm input").val(null);
			$("#equType").combobox("setValue", "");//下拉框赋值
			$("#alleqps").combobox("setValue", "");//下拉框赋值
			$("#workshop").combobox("setValue", "");//下拉框赋值
			$("#eqpcategory").combobox("setValue", "");//下拉框赋值
			$("#shift").combobox("setValue", "");//下拉框赋值
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
					
					<select id="alleqps" name="equ_id" class="easyui-combobox" data-options="width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">班次：</span>
		
					<select id="shift" name="shift_id" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">故障时间：</span>
						 <input id="date1" name="startDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>
					</div>	
					
					<div >
						<span class="label">到</span>
						<input id="date2" name="endDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>					 
					</div>	
					
								
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
				<a onclick="queryEqmTrouble()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
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