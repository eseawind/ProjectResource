<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备日保养</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			$.loadComboboxData($("#shiftId"),"SHIFT",true);
			$.loadComboboxData($("#teamId"),"TEAM",true);
			$.loadComboboxData($("#equ_id"),"ALLEQPS",true);
			
			//初始化时间
			var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			$("#stime").my97("setValue",sts);	//月初 
			$("#etime").my97("setValue",end);	//月末
			
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				rownumbers :true,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect:true,
				checkOnSelect : true,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				scrollbarSize:180,
				columns : [ [ {
					field : 'equ_name',
					title : '设备名称',
					width : 100,
					align:'center',
					sortable : true
				},{
					field : 'ep_shift',
					title : '班次',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'eqpTypeName',
					title : '保养项',
					align:'center',
					width : 160,
					sortable : true
				},{
					field : 'eqpTypeDES',
					title : '备注说明',
					align:'center',
					width : 160,
					sortable : true
				}, {
					field : 'ep_stime',
					title : '计划开始时间',
					align:'center',
					width : 160,
					sortable : true
				},{
					field : 'ep_etime',
					title : '计划完成时间',
					align:'center',
					width : 180,
					sortable : true
				},{
					field : 'epr_time',
					title : '实际保养时间',
					align:'center',
					width : 180,
					sortable : true
				},{
					field : 'epr_username',
					title : '保养人',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'status',
					title : '保养状态',
					align:'center',
					width : 80,
					sortable : true,
					formatter : function(value, row, index) {
						var type = value;
						if(type==0){
							type="未保养";
						}else{
							type="已保养";
						}
						return type;
					}
				}
				] ],
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
			
			//页面进来，m
		});
		/*
		* 查询设备故障
		*/
		function queryBean() {
			var tempUrl ="${pageContext.request.contextPath}/pms/paul";
			dataGrid.datagrid({
				url : tempUrl+"/queryProtectRecordByList.do",
				queryParams :$("#searchForm").form("getData")
			});
		}
		function clearForm(){
			$("#searchForm input").val(null);
		}
		
		
		

		
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">设备类型：</span>
						<input id="equ_id" name="equ_id" class="easyui-combobox" data-options="panelHeight:'auto',width:100,panelHeight:150,editable:false"/>
					</div>
					<div >
						<span class="label">班次：</span>
						<select id="shiftId" name="ep_shift" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					
					<div >
						<span class="label" style="width:120px">计划开始时间：</span>
						<input id="stime" name="stime" type="text" value="" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:90px"/>
					</div>	
					
					<div >
						<span class="label" style="width:10px">到</span>
						<input id="etime" name="etime" type="text" value="" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:90px"/>				 
					</div>	
								
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
				<a onclick="queryBean()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	
</body>
</html>