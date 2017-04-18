<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>角色管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
	<script type="text/javascript">
		$.canEdit = true;
	</script>
	<script type="text/javascript">
		$.canDelete = true;
	</script>
	<script type="text/javascript">
		$.canGrant = true;
	</script>

<script type="text/javascript">
	var dataGrid;
	var dialog;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			rownumbers :true,
			idField : 'id',
			fit : true,
			singleSelect :true,
			fitColumns : false,
			border : false,
			striped : true,
			nowrap : true,
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			columns : [ [ {
				title : '编号',
				field : 'id',
				checkbox : true
			}, {
				field : 'name',
				title : '角色名称',
				width : 150,
				formatter : function(value, row, index) {
					var style="display: inline-block;width: 16px;height: 16px;margin-top:-1px";
					var icon =$.formatString("<span class='{0}' style='{1}'>&nbsp;</span>&nbsp;",row.iconCls,style);
					return icon+value; 
				}
			} , {
				field : 'seq',
				title : '排序',
				width : 40
			}, {
				field : 'remark',
				title : '备注',
				width : 200
			}, {
				field : 'enable',
				title : '状态',
				width : 40,
				align:'center',
				formatter : function(value, row, index) {
					return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
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
	*查询角色
	*/
	function getAllRoles(){
		dataGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/sysRole/getAllRoles.do",
			queryParams : $("#searchForm").form("getData")
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
	/**
	* 跳转到角色添加页面
	*/
	function goToRoleAddJsp() {
		dialog = parent.$.modalDialog({
			title : '角色添加',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/sysRole/goToRoleAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					//var f = $("#form");
					var f = dialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/sysRole/addRole.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllRoles();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
				}
			} ]
		});
	}
	/**
	* 跳转到角色编辑页面
	*/
	function goToRoleEditJsp() {
		dialog = parent.$.modalDialog({
			title : '角色编辑',
			width : 500,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/sysRole/goToRoleEditJsp.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/sysRole/editRole.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllRoles();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
					
				}
			} ]
		});
	}
	
	/**
	* 角色删除
	*/
	function deleteRole() {
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前角色?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/sysRole/deleteRole.do', {
					id : row.id,
					name : row.name
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllRoles();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	/**
	* 角色批量删除
	*/
	function batchDeleteRoles(){
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		var names = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中的角色?', function(r) {
				if (r) {
					var flag = false;
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
						names.push(rows[i].name);
					}
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/sysRole/batchDeleteRoles.do', 
					{
						ids : ids.join(','),
						names : names.join(',')
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllRoles();
						} else {
							$.messager.show('提示', json.msg, 'info');
						}
					},"JSON");
				}
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要删除的角色!'
			});
		}
	}
	/**
	* 跳转到给角色分配权限页面
	*/
	function goToAssignResourceJsp(){
		var id=dataGrid.datagrid('getSelected').id;//角色id
		dialog = parent.$.modalDialog({
			title : '权限分配',
			width : 700,
			height : 400,
			href : '${pageContext.request.contextPath}/pms/sysRole/goToAssignResourceJsp.do?id='+id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var ids=[];//资源ids(菜单,功能)
					var menus=dialog.find('input[class="menu"]:checked');
					var funcs=dialog.find('input[class="func"]:checked');
					menus.each(function(i){
						ids.push($(this).attr("id"));
					});
					funcs.each(function(i){
						ids.push($(this).attr("id"));
					});
					$.post("${pageContext.request.contextPath}/pms/sysRole/assignResourceToRole.do"
							,{id:id,ids:ids.join(",")},function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
					},"JSON");
					
				}
			} ]
		});
	}
	/**
	 * 跳转到给角色用户分配界面
	 */
	function goToAssignUserJsp(){
		var id=dataGrid.datagrid('getSelected').id;//角色id
		dialog = parent.$.modalDialog({
			title : '分配用户',
			width : 700,
			height : 450,
			href : '${pageContext.request.contextPath}/pms/sysRole/goToAssignUserJsp.do?id='+id,//并加载该机构原有用户
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var rows=dialog.find("#assignedGrid").datagrid("getRows");
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
					//alert(id+","+ids.join(','));
					$.post("${pageContext.request.contextPath}/pms/sysRole/assignUsersToRole.do",{id:id,ids:ids.join(',')},function(json){
						if (json.success) {
							parent.$.messager.show('提示', json.msg, 'info');
						}else{
							parent.$.messager.show('提示', json.msg, 'error');
						}
						dialog.dialog('destroy');
					},"JSON");
				}
			} ]
		});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
<div id="toolbar" style="display: none;">		
	<table>
			<tr>
				<td>
					<form id="searchForm" style="margin:4px 0px 0px 0px">
						<table>
							<tr>
								<th>角色名称</th>
								<td><input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:100"/></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
						<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/getAllRoles.do']}">
							<td><a onclick="getAllRoles();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a></td>
						</c:if>
						<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/getAllRoles.do']}">
							<td><a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">清空查询</a></td>
						</c:if>		
						<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/addRole.do']}">
							<td><a onclick="goToRoleAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">添加</a></td>
						</c:if>	
						<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/batchDeleteRoles.do']}">
							<td><a onclick="batchDeleteRoles();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-group-delete'">批量删除</a></td>
						</c:if>	
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>

<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/editRole.do']}">
				<div onclick="goToRoleEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
			</c:if>
			
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/deleteRole.do']}">
				<div onclick="deleteRole();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
			</c:if>
			
				<div onclick="goToAssignResourceJsp();" data-options="iconCls:'icon-standard-group-key'">分配权限</div>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/assignResourceToRole.do']}">
			</c:if>
			
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/assignUsersToRole.do']}">
				<div onclick="goToAssignUserJsp();" data-options="iconCls:'icon-standard-group'">分配用户</div>
			</c:if>
	</div>		
</body>
</html>