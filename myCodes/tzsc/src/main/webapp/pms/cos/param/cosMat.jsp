<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>辅料奖罚金额管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var typeGrid = null;
		var categoryGrid=null;
		$(function() {
			typeGrid = $('#typeGrid').datagrid({
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
					title : '设备型号id',
					width : 120,
					hidden : true
				}, {
					field : 'code',
					title : '设备型号编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'name',
					title : '设备型号名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'des',
					title : '设备型号描述',
					align:'center',
					width : 150,
					sortable : true
				}, {
					field : 'des',
					title : '版本号',
					align:'center',
					width : 150,
					sortable : true
				}, {
					field : 'enable',
					title : '状态',
					align:'center',
					width : 70,
					sortable : true
				} ] ],
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
				}, onClickRow:function(rowIndex,rowData){
					queryCosMat2(rowData.id);
				}
			});
			
			categoryGrid = $('#categoryGrid').datagrid({
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
					title : '辅料ID',
					width : 120,
					hidden : true
				}, {
					field : 'mdEqpName',
					title : '设备型号名称',
					width : 150,
					align : 'center',
					sortable : true
				}, {
					field : 'mdMatName',
					title : '辅料名称',
					width : 100,
					align : 'center',
					sortable : true
				}, {
					field : 'award',
					title : '奖罚单价',
					align : 'center',
					width : 150,
					sortable : true
				}, {
					field : 'awardCount',
					title : '奖罚金额',
					align : 'center',
					width : 150,
					sortable : true
				} ] ],
				toolbar : '#categoryToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				}, onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#categoryMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			});
		});
	
	   function queryCosMat2(id){
		   categoryGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/param/queryCosMat.do?eqpId=" + id
		   });
	   }
	   
	   //查询辅料
	   function queryCosMat(){
		   categoryGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/param/queryCosMat.do",
				queryParams :$("#categroyForm").form("getData")/* ,
				onLoadError : function(data) {
					alert(JSON.stringify(data));
					$.messager.show('提示', "查询设备型号辅料异常", 'error');
				} */
			});
	   }
	  
	   //查询设备型号
	   function queryMdType(){
		   typeGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/param/queryMdType.do",
				queryParams :$("#typeSearchForm").form("getData")
			});
	   }
	  
	   //跳转到辅料添加页面
	   function gotoCosMat(){
		   var dialog = parent.$.modalDialog({
				title : '新增辅料',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/param/gotoCosMat.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/param/addCosMat.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryCosMat();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   
	   //跳转到辅料编辑页面
	   function gotoEditMat(){
		   var dialog = parent.$.modalDialog({
				title : '编辑辅料',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/param/gotoEditMat.do?id='+categoryGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/param/editCosMat.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryCosMat();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   
	   //删除辅料奖罚金额
	   function deleteCosMat(){
			var row = categoryGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '是否删除？', function(b) {
				if (b) {
					$.post('${pageContext.request.contextPath}/pms/param/deleteCosMat.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryCosMat();
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
	<div data-options="region:'west',border:true,split:false,title:'设备型号【可双击型号表格】'" style="width:560px;">
		<div id="typeToolbar" style="display: none;width:100%;">
			<form id="typeSearchForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div>
							<span class="">设备型号编码</span>
							<span><input type="text" name="code" class="easyui-validatebox "  data-options="prompt: '请输入设备型号编码'"/></span>
						</div>
						<div>
							<span class="">设备型号名称</span>
							<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '请输入设备型号名称'"/>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<a onclick="queryMdType()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="gotoAddMdType();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
			</div>
		</div>
		<table id="typeGrid"></table>
	</div>
	<div data-options="region:'center',border:true,title:'辅料'" style="width:560px;">
		<div id="categoryToolbar"  style="display: none;">
			<form id="categroyForm" style="margin:4px 0px 0px 0px">
				<div class="topTool" width="560px">
					<fieldset>
						<div>
							设备型号名称<input type="text" id="categoryId" name="mdEqpTypeId" class="easyui-combobox easyui-validatebox" 
							data-options="textField:'name',valueField:'id',url:'${pageContext.request.contextPath }/pms/maintaintc/queryMdEqpType.do'"/>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar">
				<a onclick="queryCosMat()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="gotoCosMat();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
			</div>
		</div>
		<table id="categoryGrid"></table>
	</div>
	
	
	<div id="typeMenu" class="easyui-menu" style="width: 80px; display: none;">
		<!-- <div onclick="gotoCosMat()" data-options="iconCls:'icon-standard-plugin-add'">新增辅料</div> -->
	</div>
	<div id="categoryMenu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="gotoEditMat()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteCosMat();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>