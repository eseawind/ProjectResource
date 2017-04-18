<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>残烟考核参数设置</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var typeGrid = null;
		var partWidthGrid=null;
		$(function() {
			$.loadComboboxData($("#modelType"),"EQPTYPE",true);//设备类型
			typeGrid = $('#typeGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : true,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'modelTypeName',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : 'ROW ID',
					width : 120,
					hidden : true
				}, {
					field : 'modelTypeName',
					title : '设备机型编号',
					width : 150,
					align:'center',
					sortable : true
				}, {
					field : 'shiftName',
					title : '班次',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'storage_smoke',
					title : '储烟量',
					align:'center',
					width : 150,
					sortable : true
				}] ],
				toolbar : '#typeToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#typeMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			});
			
			partWidthGrid = $('#partWidthGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : true,
				pagination : true,
				idField : 'partName',
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
					title : 'ROW ID',
					width : 120,
					hidden : true
				}, {
					field : 'partName',
					title : '牌号',
					width : 150,
					align:'center',
					sortable : true
				}, {
					field : 'weight',
					title : '单支重量',
					width : 100,
					align:'center',
					sortable : true
				}, ] ],
				toolbar : '#partWidthToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#typeMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			});
		});
	
		//load shift and part
		$(function() {
			$.loadComboboxData($("#shift"),"SHIFT",true);
			$.loadComboboxData($("#partNumber"),"MATPROD",true);
			queryPartWeight();
			$("a[name='zoombutton']").click();
		});
	  
	   //显示机台储烟量标准
	   function queryAll(){
		   typeGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/disabled/queryDis.do",
				queryParams :$("#typeSearchForm").form("getData")
			});
	   }
	   //显示机台储烟量标准
	   function queryDis(id){
		   typeGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/disabled/queryDis.do?id="+id,
				queryParams :$("#typeSearchForm").form("getData")
			});
	   }
	   
	  
	   //跳转到储烟量标准添加页面
	   function toAddOrUpdateBean(){
		   var dialog = parent.$.modalDialog({
				title : '新增储烟量标准',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/disabled/gotoDis.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/disabled/addOrUpdate.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryAll();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   
	   //跳转到储烟量标准编辑页面
	   function toEditDis(){
		   var dialog = parent.$.modalDialog({
				title : '编辑储烟量标准',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/disabled/gotoEditDis.do?id='+typeGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/disabled/addOrUpdate.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryAll();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   
	 //查询烟支重量
	 function queryPartWeight(){
		 partWidthGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/partWeight/queryPartWeight.do",
			queryParams :$("#partWidthForm").form("getData")
		});
	 }
	 //添加烟支重量
	 function gotoPartWeight(){
		 var dialog = parent.$.modalDialog({
				title : '添加烟支重量标准',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/partWeight/gotoEditPartWeight.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/partWeight/addOrUpdatePartWeight.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryPartWeight();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	 }
	 //编辑烟支重量
	 function gotoEditWeight(){
		 var dialog = parent.$.modalDialog({
				title : '编辑烟支重量标准',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/partWeight/gotoEditPartWeight.do?id='+partWidthGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/partWeight/addOrUpdatePartWeight.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryPartWeight();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	 }
		function clearForm(){
			$("#typeSearchForm input").val(null);
		}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'west',border:true,split:false,title:'残烟考核参数设置'" style="width:510px;">
		<div id="typeToolbar" style="display: none;width:100%;">
			<form id="typeSearchForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						
							<span class="">设备型号：</span>
						 	<select id="modelType" name="modelType" data-options="panelHeight:200,width:100,editable:true"></select>
							<span class="">班          次：</span>
							<select id="shift" name="shift" data-options="panelHeight:'auto',width:100,editable:true"></select>
						
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<a onclick="queryAll()" name="zoombutton" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<a onclick="toAddOrUpdateBean()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
				<a onclick="toEditDis()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑</a>
			</div>
		</div>
		<!-- 残烟考核参数 div -->
		<table id="typeGrid"></table>
	</div>
	<div data-options="region:'center',border:true,title:'烟支重量维护'" style="width:360px;">
		<div id="partWidthToolbar"  style="display: block;">
			<form id="partWidthForm" style="margin:4px 0px 0px 0px">
				<div class="topTool" width="560px">
					<fieldset>
						<div>
							牌号：<select id="partNumber" name="partNumber" data-options="panelHeight:200,width:200,editable:true"></select>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar">
				<a onclick="queryPartWeight()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="gotoPartWeight();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
				<a onclick="gotoEditWeight()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑</a>
			</div>
		</div>
		<!-- 烟支重量参数 div -->
		<table id="partWidthGrid"></table>
	</div>
</body>
</html>