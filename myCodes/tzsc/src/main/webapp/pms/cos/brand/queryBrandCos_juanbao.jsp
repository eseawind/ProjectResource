<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">

</script>
<!DOCTYPE html>
<html>
<head>
<title>卷包车间综合成本统计</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'o.schStatOutput.schWorkorder.id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'schStatOutput.schWorkorder.id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'eqdType',
					title : '设备名称',
					width : 90,
					align : 'center',
					rowspan:2,
					sortable : false
				},{
					field : 'matName',
					title : '牌号',
					width : 160,
					rowspan:2,
					align:'center',
					sortable : false
				},{
					field : 'teamName',
					title : '班组',
					align : 'right',
					width : 50,
					rowspan:2,
					sortable : false
				},{
					field : 'sumCosts_JuanBao',
					title : '总成本',
					align : 'right',
					width : 80,
					rowspan:2,
					sortable : false
				},{
					field : 'qty',
					title : '产量',
					width : 60,
					rowspan:2,
					align:'right',
					sortable : false
				}] ],
				columns : [ [{
					title : '滤棒',
					rowspan:1,
					colspan:5
				},{
					title : '卷烟纸',
					rowspan:1,
					colspan:5
				},{
					title : '接装纸',
					rowspan:1,
					colspan:5
				},{
					title : '小盒商标纸',
					rowspan:1,
					colspan:5
				},{
					title : '条盒商标纸',
					rowspan:1,
					colspan:5
				},{
					title : '小盒烟膜',
					rowspan:1,
					colspan:5
				},{
					title : '条盒烟膜',
					rowspan:1,
					colspan:5
				},{
					title : '内衬纸',
					rowspan:1,
					colspan:5
				}],[
				{	field : 'consumQty1',
					title : '消耗量',
					align : 'right',
					width : 90,
					rowspan:1,
					sortable : false
				},{	field : 'funit1',
					title : '单位',
					align : 'right',
					width : 90,
					rowspan:1,
					sortable : false
				},{	field : 'unitPrice1',
					title : '单价',
					align : 'right',
					width : 90,
					rowspan:1,
					sortable : false
				},{	field : 'countPrice1',
					title : '总成本',
					align : 'right',
					width : 90,
					rowspan:1,
					sortable : false
				},{	field : 'singleCost1',
					title : '单箱成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'consumQty2',
					title : '消耗量',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'funit2',
					title : '单位',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'unitPrice2',
					title : '单价',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'countPrice2',
					title : '总成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'singleCost2',
					title : '单箱成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'consumQty3',
					title : '消耗量',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'funit3',
					title : '单位',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'unitPrice3',
					title : '单价',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'countPrice3',
					title : '总成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'singleCost3',
					title : '单箱成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'consumQty4',
					title : '消耗量',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'funit4',
					title : '单位',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'unitPrice4',
					title : '单价',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'countPrice4',
					title : '总成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'singleCost4',
					title : '单箱成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'consumQty5',
					title : '消耗量',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'funit5',
					title : '单位',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'unitPrice5',
					title : '单价',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'countPrice5',
					title : '总成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'singleCost5',
					title : '单箱成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'consumQty6',
					title : '消耗量',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'funit6',
					title : '单位',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'unitPrice6',
					title : '单价',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'countPrice6',
					title : '总成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'singleCost6',
					title : '单箱成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'consumQty7',
					title : '消耗量',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'funit7',
					title : '单位',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'unitPrice7',
					title : '单价',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'countPrice7',
					title : '总成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'singleCost7',
					title : '单箱成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'consumQty8',
					title : '消耗量',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'funit8',
					title : '单位',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'unitPrice8',
					title : '单价',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'countPrice8',
					title : '总成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				},{	field : 'singleCost8',
					title : '单箱成本',
					align : 'right',
					width : 90,
					rowspan:1,sortable : false
				}]],
				toolbar : '#toolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				}, onRowContextMenu : function(e, rowIndex, rowData) {
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
		
		//dataGrid.datagrid("fildColumn","attr1");//隐藏
		
		
		// 查询物料单价
		function queryBrandCos() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/barand/queryBrand_juanbao.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
		}
		$('#team').combo({
		    required:true,
		    multiple:true
		});
		
		$(function(){
			var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			$("#startTime").my97("setValue",sts);
			$("#endTime").my97("setValue",end);	
			$.loadComboboxData($("#team"),"TEAM",true);	
			$.loadComboboxData($("#eName"),"ALLROLLERS",true);
			$.loadComboboxData($("#mdMatId"),"MATPROD",true);
		})
	/**
	*清除查询条件
	*/
	function clearForm() {
		$("#searchForm input[name!='searchForm']").val(null);
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			 <table style="margin:4px 0px 0px 0px">
			 	<tr>
			 		<th>牌号</th>
			 		<td><select id="mdMatId" name="mdMatId" class="easyui-combobox easyui-validatebox"  data-options="panelHeight:200,width:160"></select></td>
			 		<th>机组</th>
					<td> 
					<select id="eName" name="eName" class="easyui-combobox easyui-validatebox"  data-options="panelHeight:'auto',width:160"></select>
					<th>班组</th>
					<td>
					<select id="team" name="team" class="easyui-combobox easyui-validatebox"  data-options="panelHeight:'auto',width:160"></select>
					</td>
					<th>日期</th>
					<td>
						 <input id="startTime" name="startTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/> -
						 <input id="endTime" name="endTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>
					</td>
				</tr>
			 	<tr>
			 		<table style="margin:4px 0px 0px 0px">
				 		<tr>
				 			<td><a onclick="queryBrandCos()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a></td>
							<td><a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">重置</a></td>
						</tr>
					</table>
			 	</tr>
			 </table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	 <div id="menu" class="easyui-menu" style="width: 80px; display: none;">
	</div>
</body>
</html>