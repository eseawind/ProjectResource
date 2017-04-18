<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript">

</script>
<!DOCTYPE html>
<html>
<head>
<title>物料单价成本维护</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			$("#date1").my97("setValue",sts);
			$("#date2").my97("setValue",end);	
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
				columns : [ [ {
					field : 'id',
					title : '成本维护ID',
					width : 120,
					checkbox : true
				},{
					field : 'matName',
					title : '物料名称',
					width : 150,
					align : 'center',
					sortable : true 
				},{
					field : 'matPrice',
					title : '物料单价',
					width : 75,
					align:'right',
					sortable : true
				},{
					field : 'unitName',
					title : '单位',
					align : 'left',
					width : 45,
					sortable : true
				},{
					field : 'takeeffectDate',
					title : '生效时间',
					align : 'center',
					width : 200,
					sortable : true
				},{
					field : 'shiftName',
					title : '班次',
					align : 'center',
					width : 100,
					sortable : true
				},{
					field : 'attr1',
					title : '备注',
					align : 'center',
					width : 240,
					sortable : true
				}] ],
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
		
		// 查询物料单价
		function queryCosMaintain() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/maintain/queryCosMaintain.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
		}
		
		//新增物料单价
		function goToAddCosMaintain(){
			var dialog = parent.$.modalDialog({
				title : '物料单价添加',
				width : 620,
				height : 340,
				href : '${pageContext.request.contextPath}/pms/maintain/gotoAddCosMaintain.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/maintain/addCosMaintain.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryCosMaintain();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
	
	//编辑物料单价
	function goToEditCosMaintain(){
		var dialog = parent.$.modalDialog({
			title : '物料单价编辑',
			width : 620,
			height : 340,
			href : '${pageContext.request.contextPath}/pms/maintain/gotoEditCosMaintain.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/maintain/editCosMaintain.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryCosMaintain();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	} 
	
    //删除设备轮保计划
	function deleteCosMaintain(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '是否删除？', function(b) {
			if (b) {	
				$.post('${pageContext.request.contextPath}/pms/maintain/delCosMaintain.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryCosMaintain();
					} else {
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
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			 <table style="margin:4px 0px 0px 0px">
			 	<tr>
			 		<th>辅料名称</th>
					<td><input type="text" name="matName" class="easyui-validatebox "  data-options="prompt: '请输入辅料名称'"/></td>
					<th>生效时间</th>
					<td>
						 <input id="date1" name="date1" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px"/> -
						 <input id="date2" name="date2" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px"/>
					</td>
				</tr>
			 	<tr>
			 		<table style="margin:4px 0px 0px 0px">
				 		<tr>
				 			<td><a onclick="queryCosMaintain()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a></td>
				 			<td><a onclick="goToAddCosMaintain();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加辅料单价</a></td>
				 			<td><a onclick="goToEditCosMaintain();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑辅料单价</a></td>
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
	 	<div onclick="queryCosMaintain();" id="delPlan" data-options="iconCls:'icon-standard-zoom'">查询</div>
		<div onclick="goToAddCosMaintain()" id="deliver" data-options="iconCls:'icon-standard-plugin-add'">添加</div>
		<div onclick="goToEditCosMaintain()" id="editPlan" data-options="iconCls:'icon-standard-plugin-edit'">编辑</div>
		
	</div>  
</body>
</html>