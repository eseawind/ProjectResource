<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备履历管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			$.loadComboboxData($("#mdEquType1"),"EQPTYPE",true);
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
				nowrap : true,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'id',
					title : '设备履历id',
					width : 120,
					checkbox : false,
					hidden:true
				}, {
					field : 'mdEquName',
					title : '设备名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'mdEquType',
					title : '设备型号',
					align:'center',
					width : 70,
					sortable : true
				},{
					field : 'manufactureDate',
					title : '设备出厂时间',
					align:'center',
					width : 130,
					sortable : true
				},{
					field : 'purchaseDate',
					title : '设备购置时间',
					align:'center',
					width : 130,
					sortable : true
				} ] ],
				columns : [ [
				{
					field : 'maintenanceDate',
					title : '保养时间',
					align:'center',
					width : 130,
					sortable : true
				},{
					field : 'maintenanceContent',
					title : '保养内容',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'maintenanceType',
					title : '保养类型',
					align:'center',
					width : 130,
					formatter : function(value, row, index) {
						if(value=='10')
							return '轮保';
						 if(value=='20')
							 return '润滑';
						 if(value=='30')
							 return '停产检修';
						 if(value=='40')
							 return '其它';
					
					}
					
				},{
					field : 'maintainDate',
					title : '维修时间',
					align:'center',
					width : 130,
					sortable : true
				},{
					field : 'maintainContent',
					title : '维修内容',
					align:'center',
					width : 150,
					sortable : true
				},{
					field : 'maintainType',
					title : '维修类型',
					align:'center',
					width : 100,
					formatter : function(value, row, index) {
						if(value=='10')
							return '电器维修';
						 if(value=='20')
							return '机械维修';
					}
				},{
					field : 'maintainPerson',
					title : '维修人',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'maintenancePerson',
					title : '保养人',
					align:'center',
					width : 70,
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
		function clearForm(){
			$("#searchForm input").val(null);
			$("#mdEquType1").combobox("setValue", "");//下拉框赋值
		}
		/**
		* 查询设备履历
		*/
		function queryResume() {
			dataGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/resume/queryResume.do",
						queryParams :$("#searchForm").form("getData"),
						onLoadError : function(data) {
							$.messager.show('提示', "查询设备履历异常", 'error');
						}
					});
		}
		
	function editEqu(){
		var dialog = parent.$.modalDialog({
			title : '设备主数据编辑',
			width : 1300,
			height :600,
			href : '${pageContext.request.contextPath}/pms/resume/goToAddResume.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/resume/addResume.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryResume();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	} 
	
	function deleteEqu(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前设备主数据？', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/resume/deleteEqu.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryResume();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}

	function goToAddResume(){
		var dialog = parent.$.modalDialog({
			title : '设备履历添加',
			width : 1300,
			height :600,
			href : '${pageContext.request.contextPath}/pms/resume/goToAddResume.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/resume/addResume.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryResume();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}


	//内部转移记录
	function gotoEqmInside(){
		var rowSelected = dataGrid.datagrid('getSelected');
		if(rowSelected==null||rowSelected ==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要修改的记录!'
			});
			return false;
		}
		var dialog = parent.$.modalDialog({
			title : '内部转移记录[双击可编辑]',
			width : 800,
			height :400,
			href : '${pageContext.request.contextPath}/pms/eqmInside/goToJsp.do?id='+rowSelected.id
		});
	}
	//原价变动记录
	function goToEqmChange(){
		var rowSelected = dataGrid.datagrid('getSelected');
		if(rowSelected==null||rowSelected ==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要修改的记录!'
			});
			return false;
		}
		var dialog = parent.$.modalDialog({
			title : '原价变动记录[双击可编辑]',
			width : 800,
			height :400,
			href : '${pageContext.request.contextPath}/pms/eqmChange/goToJsp.do?id='+rowSelected.id
		});
	}

	//附属设备
	function goToEqmAuxil(){
		var rowSelected = dataGrid.datagrid('getSelected');
		if(rowSelected==null||rowSelected ==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要修改的记录!'
			});
			return false;
		}
		var dialog = parent.$.modalDialog({
			title : '附属设备记录[双击可编辑]',
			width : 800,
			height :400,
			href : '${pageContext.request.contextPath}/pms/eqmAuxil/goToJsp.do?id='+rowSelected.id
		});
	}
	//大修理
	function goToEqmRepair(){
		var rowSelected = dataGrid.datagrid('getSelected');
		if(rowSelected==null||rowSelected ==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要修改的记录!'
			});
			return false;
		}
		var dialog = parent.$.modalDialog({
			title : '大修理记录[双击可编辑]',
			width : 800,
			height :400,
			href : '${pageContext.request.contextPath}/pms/eqmRepair/goToJsp.do?id='+rowSelected.id
		});
	}
	//停用记录
	function goToEqmStop(){
		var rowSelected = dataGrid.datagrid('getSelected');
		if(rowSelected==null||rowSelected ==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要修改的记录!'
			});
			return false;
		}
		var dialog = parent.$.modalDialog({
			title : '停用记录[双击可编辑]',
			width : 800,
			height :400,
			href : '${pageContext.request.contextPath}/pms/eqmStop/goToJsp.do?id='+rowSelected.id
		});
	}
	//报废清理
	function goToEqmScrapcel(){
		var rowSelected = dataGrid.datagrid('getSelected');
		if(rowSelected==null||rowSelected ==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要修改的记录!'
			});
			return false;
		}
		var dialog = parent.$.modalDialog({
			title : '报废清理记录[双击可编辑]',
			width : 800,
			height :400,
			href : '${pageContext.request.contextPath}/pms/eqmScrapcel/goToJsp.do?id='+rowSelected.id
		});
	}
	//调查记录
	function goToEqmSurvey(){
		var rowSelected = dataGrid.datagrid('getSelected');
		if(rowSelected==null||rowSelected ==""){
			parent.$.messager.show({
				title : '提示',
				msg : '请选择需要修改的记录!'
			});
			return false;
		}
		var dialog = parent.$.modalDialog({
			title : '调查记录[双击可编辑]',
			width : 800,
			height :400,
			href : '${pageContext.request.contextPath}/pms/eqmSurvey/goToJsp.do?id='+rowSelected.id
		});
	}
	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			 <table style="margin:4px 0px 0px 0px">
			 	<tr>
			 		<th width="80">设备名称：</th>
					<td width="100"><input type="text" name="mdEquName" class="easyui-validatebox "  data-options="prompt: '请输入设备名称'"/></td>
					<th width="80">设备型号：</th>
					<td width="100"><input  id="mdEquType1" name="mdEquType" data-options="panelHeight:200,width:100,editable:false" /></td>
					<td colspan="6"></td>
				</tr>
			 	<tr>
			 		<td colspan="10">
			 			<table>
					 		<c:if test="${not empty sessionInfo.resourcesMap['/pms/resume/queryResume.do']}">
					 			<td><a onclick="queryResume()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查 询</a></td>
					 		</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/resume/addResume.do']}">	
								<td><a onclick="goToAddResume();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加设备履历</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmInside/gotoEqmInside.do']}">	
								<td><a onclick="gotoEqmInside();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">内部转移</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmChange/goToEqmChange.do']}">	
								<td><a onclick="goToEqmChange();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">原价变动</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmAuxil/goToEqmAuxil.do']}">	
								<td><a onclick="goToEqmAuxil();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">附属设备</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmRepair/goToEqmRepair.do']}">	
								<td><a onclick="goToEqmRepair();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">大修理</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmStop/goToEqmStop.do']}">	
								<td><a onclick="goToEqmStop();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">停用记录</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmScrapcel/goToEqmScrapcel.do']}">	
								<td><a onclick="goToEqmScrapcel();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">报废清理</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/eqmSurvey/goToEqmSurvey.do']}">	
								<td><a onclick="goToEqmSurvey();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">调查记录</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/resume/restResume.do']}">	
								<td><a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a></td>
							</c:if>
						</table>
					</td>
			 	</tr>
			 </table>
		</form>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/resume/editResume.do']}">
			<div onclick="editEqu()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/resume/delResume.do']}">
			<div onclick="deleteEqu();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>
</body>
</html>