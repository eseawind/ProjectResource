<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备类型与型号管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
		
		var categoryGrid=null;
		$(function() {		
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
					title : '设备类型名称',
					width : 120,
					hidden : true
				}, {
					field : 'code',
					title : '设备类型编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'name',
					title : '设备类型名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'des',
					title : '设备类型描述',
					align:'center',
					width : 150,
					sortable : true
				}, {
					field : 'enable',
					title : '是否启用',
					align:'center',
					width : 70,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
					}
				} ] ],
				toolbar : '#categoryToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#categoryMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				},
				onClickRow:function(rowIndex,rowData){
					queryMdTypeByCategory(rowData.id);
				}
			});
		});
   /**
		* 查询设备
		*/	
		function queryMdCategory() {
			categoryGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/md/eqpcategory/queryMdCategory.do",
				queryParams :$("#categroyForm").form("getData")//,
				//onLoadError : function(data) {
				//	alert(JSON.stringify(data));
				//	$.messager.show('提示', "查询设备型号异常", 'error');
				//}
			});
		}
		
		function deleteCategory(){
			var row = categoryGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前设备主数据？', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/md/eqpcategory/deleteMdCategory.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryMdCategory();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
		}

		function gotoCategoryAdd(){
			var dialog = parent.$.modalDialog({
				title : '设备类型添加',
				width : 620,
				height : 290,
				href : '${pageContext.request.contextPath}/pms/md/eqpcategory/addMdCategoryJSP.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#formcategory");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/md/eqpcategory/addMdCategory.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryMdCategory();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
		function gotoCategoryEdit(){
			var dialog = parent.$.modalDialog({
				title : '设备型号编辑',
				width : 620,
				height : 290,
				href : '${pageContext.request.contextPath}/pms/md/eqpcategory/gotoMdCategory.do?id='+categoryGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '修改',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/md/eqpcategory/updatecategory.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryMdCategory();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}

</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'west',border:true,split:false,title:'设备类型【可双击类型表格】'" style="width:100%px;">
		<div id="categoryToolbar"  style="display: none;width:100%;">
			<form id="categroyForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div >
							<span class="label">类型编号：</span>
							<span><input type="text" name="code" class="easyui-validatebox "  data-options="prompt: '请输入设备类型编码'"/></span>
						</div>
						<div >
							<span class="label">类型名称：</span>
							<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '请输入设备类型名称'"/>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" style="width:1oo%;">
				<a onclick="queryMdCategory()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="gotoCategoryAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
				<!-- <a onclick="viewLubricate();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">详情</a> -->
			</div>
		</div>
		<table id="categoryGrid"></table>
	</div>
	<div id="categoryMenu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="gotoCategoryEdit()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteCategory();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>