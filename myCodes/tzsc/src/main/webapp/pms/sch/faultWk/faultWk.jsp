<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>机台故障历史记录查询</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			$("#startDate").my97("setValue",sts);//
			$("#endDate").my97("setValue",end);	//
			
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
					field : 'eqp_name',
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
					field : 'date',
					title : '故障日期',
					align:'center',
					width : 160,
					sortable : true
				},{
					field : 'fault_name',
					title : '故障名称',
					align: 'left',
					width : 180,
					sortable : true
				},{
					field : 'time',
					title : '停机时间(分)',
					align:'center',
					width : 120,
					sortable : true
				},{
					field : 'times',
					title : '停机次数(次)',
					align:'center',
					width : 120,
					sortable : true
				},{
					field : 'wk_code',
					title : '工单号',
					align:'center',
					
					width : 120,
					sortable : true
				} ,{
					field : 'runtime',
					title : '运行时长(分)',
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
		function queryFaultWk() {
			var tempUrl ="${pageContext.request.contextPath}/pms/faultWk";
			dataGrid.datagrid({
				url : tempUrl+"/getFaultWkGrid.do",
				queryParams :$("#searchForm").form("getData")
			});
		}
		
		function exportFaultWk(){
			var tempUrl ="${pageContext.request.contextPath}/pms/faultWk/faultWkExcel.do";
			$("#searchForm").action=tempUrl;
			$("#searchForm").method="post";
			$("#searchForm").submit();
		}
		
		function clearForm(){
			$("#searchForm input").val(null);
			$("#equType").combobox("setValue", "");//下拉框赋值
			$("#alleqps").combobox("setValue", "");//下拉框赋值
			$("#workshop").combobox("setValue", "");//下拉框赋值
			$("#eqpcategory").combobox("setValue", "");//下拉框赋值
			$("#shift").combobox("setValue", "");//下拉框赋值
		}
		
		//反馈TSPM接口
		function amsSendTOTSPM(){
			$.post('${pageContext.request.contextPath}/pms/workorder/amsSendTOTSPM.do',null, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllWorkOrder();
						} else {
							$.messager.show('提示', json.msg, 'info');
						}
			},"JSON");
		}
		
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" name="searchForm" style="margin:4px 0px 0px 0px" action="${pageContext.request.contextPath}/pms/faultWk/faultWkExcel.do" method="post">
			<div class="topTool">
				<fieldset >
				<div >
						<span class="label">车间：</span>
						
							<select id="workshop" name="workShop_id" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">设备类型：</span>
						<select id="eqpcategory" name="eqp_type_cate_id" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">设备型号：</span>
						<select id="equType" name="eqp_type_id" class="easyui-combobox" data-options="width:100,editable:false"></select>
					</div>
					
					
					<div >
						<span class="label">开始时间：</span>
						 <input id="startDate" name="startDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>
					</div>	
					
					<div >
						<span class="label" style="width:10px;">到</span>
						<input id="endDate" name="endDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>					 
					</div>	
					
					<div >
						<span class="label">设备名称：</span>
					
					<select id="alleqps" name="eqp_id" class="easyui-combobox" data-options="width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">班次：</span>
		
					<select id="shift" name="shift_id" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
								
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/faultWk/getFaultWkGrid.do']}">
				<a onclick="queryFaultWk()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/faultWk/faultWkExcel.do']}">
				<a onclick="exportFaultWk()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">导出Excel</a>
			</c:if>
				<a onclick="clearForm();"  href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
		        <a onclick="amsSendTOTSPM();"  href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">反馈TSPM</a>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
</body>
</html>