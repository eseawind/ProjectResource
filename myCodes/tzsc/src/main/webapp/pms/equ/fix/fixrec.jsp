<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备维修记录</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		//初始化时间
		
		var dataGrid=null;
		$(function() {
			$.loadComboboxData($("#eqp_id"),"ALLEQPS",true);
			$.loadComboboxData($("#searchShift"),"shift");
			
			var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		    $("#datef1").my97("setValue",sts);//
		    $("#datef2").my97("setValue",end);	//
			
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'bjNum',
				sortOrder : 'asc',
				rownumbers :true,
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [
				{
					field : 'id',
					title : 'ID',
					align:'center',
					width : 100,
					hidden : true
				},{
					field : 'equipName',
					title : '设备名称',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'shiftName',
					title : '班次',
					align:'center',
					width : 60,
					sortable : true
				},{
					field : 'bjName',
					title : '备件名',
					align:'center',
					width : 250,
					sortable : true
				},{
					field : 'bjType',
					title : '备件型号',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'bjNum',
					title : '使用数量',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'fixtim',
					title : '更换时间',
					align:'center',
					width : 150,
					sortable : true
				},{
					field : 'ghName',
					title : '更换人员',
					align:'center',
					width : 150,
					sortable : true
				},{
					field : 'fixtype',
					title : '维修类型',
					align:'center',
					width : 100,
					sortable : true,
					formatter: function(value,row,index){
						 /* 1-点检   2-维修呼叫   3-轮保   4-备品备件 5、检修 */
						if(value=='1')
							return "点检计划";
						else if(value=='2')
							return "维修呼叫";
						else if(value=='3')
							return "轮保计划"
						else if(value=='4')
							return "备品备件"
						else if(value=='5')
							return "检修计划"
					}
				},{
					field : 'isR',
					title : '是否反馈',
					align:'center',
					width : 80,
					sortable : true,
					formatter: function(value,row,index){
						if(value=='0')
							return "未反馈";
						else if(value=='1')
							return "已反馈";
					}
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
		* 查询
		*/	
		function queryFixRec() {
			dataGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/fixrec/queryFixRec.do",
						queryParams :$("#searchForm").form("getData"),		
						onLoadError : function(data) {
							$.messager.show('提示', "查询设备检修项目异常", 'error');
						}
					});
		}
		
	function editFixRec(){
		var dialog = parent.$.modalDialog({
			title : '设备检修项目编辑',
			width : 620,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/fixrec/gotoFixRecEdit.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '修改',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/fixrec/addFixRec.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryFixRec();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	} 
	
	function deleteFixRec(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前设备检修项目？', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/fixrec/deleteFixRec.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryFixRec();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	

	

	function goToFixRecAdd(){
		var dialog = parent.$.modalDialog({
			title : '设备检修项目添加',
			width : 620,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/fixrec/gotoFixRecAdd.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/fixrec/addFixRec.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryFixRec();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	function viewFixRec(){
		goToFixRecAdd();
	}
	function clearForm(){
		$("#searchForm input").val(null);
		$("#fixtype").combobox("setValue", "");//下拉框赋值
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar"  style="display: none;width:100%;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">班次：</span>
						<input id="searchShift" style="width:120px;" name="shiftId"  data-options="panelHeight:'auto',width:100,editable:false,prompt: '请选择班次'"/>
					</div>
					<div >
							<span class="label">设备名称：</span>
							<input type="text" id="eqp_id" name="equipId" class="easyui-validatebox" style="width:130px"/>
					</div>
					<div >
						<span class="label">维修类型：</span>
						<select id="fixtype" name="fixtype" style="width:120px;" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="">-选择类型-</option>
								<option value="0" >电器</option>
								<option value="1" >机械</option>
						</select>
					</div>
					
					<div>
						<span class="label">换件时间：</span> 
						<input id="datef1" name="reqtim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width: 110px" />
					</div>
					<div>
						<span class="label" style="width: 10px">到</span>
						<input id="datef2" name="restim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd"  style="width: 110px" />
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" style="width:1oo%;">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/fixrec/queryFixRec.do']}">
				<a onclick="queryFixRec()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			</c:if>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			
			<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/fixrec/gotoFixRecEdit.do']}">
				<a onclick="goToFixRecAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
			</c:if> --%>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/fixrec/gotoFixRecEdit.do']}">
			<div onclick="editFixRec()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/fixrec/deleteFixRec.do']}">
			<div onclick="deleteFixRec();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>
</body>
</html>