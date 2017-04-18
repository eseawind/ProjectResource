<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
<meta charset="utf-8"/>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">	
	var dataGrid=null;	
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
			remoteSort: false,
			striped : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			showPageList:false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'name',
				title : '姓名',
				width : 60,
				align:'center',
				sortable : true
			} , {
				field : 'loginName',
				title : '登录账户',
				align:'center',
				width : 80,
				sortable : true
			}, {
				field : 'eno',
				title : '工号',
				align:'center',
				width : 80,
				sortable : true
			}, {
				field : 'pwd',
				title : '密码(hidden)',
				align:'center',
				width : 80,
				hidden:true,
				sortable : true
			},{
				field : 'gender',
				title : '性别',
				width : 30,
				align:'center',
				sortable : true,
				formatter : function(value, row, index) {
					return value==0?'女':'男';
				}
			} ] ],
			columns : [ [
			{
				field : 'phone',
				title : '手机号码',
				align:'center',
				width : 100,
				sortable : true
			},{
				field : 'tel',
				title : '固定电话',
				width : 130,
				align:'center',
				sortable : true
			},{
				field : 'fax',
				title : '传真',
				width : 100,
				align:'center',
				sortable : true
			},{
				field : 'email',
				title : '邮箱',
				width : 120,
				align:'center',
				sortable : true
			}, {
				field : 'createDatetime',
				title : '创建时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'modifyDatetime',
				title : '最后修改时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'securityLevel',
				title : '安全级别',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'enable',
				title : '状态',
				width : 40,
				align:'center',
				sortable : true,
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
	* 查询用户
	*/	
	function getAllUsers() {
		dataGrid.datagrid({
					url : "${pageContext.request.contextPath}/pms/sysUser/getAllUser.do",
					queryParams :$("#searchForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询用户异常", 'error');
					}
				});
		
	}
	/**
	*清除查询条件
	*/
	function clearForm() {
		$('#searchForm input').val(null);		
	}
	/**
	* 定向到用户添加页面
	*/
	function goToUserAddJsp(){
		var userAddmodalDialog = parent.$.modalDialog({
			title : '添加用户',
			width : 550,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/sysUser/goToUserAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-standard-disk',
				handler : function() {
					var f = userAddmodalDialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/sysUser/addSysUser.do",f.form("getData"),function(json){
							if (json.success) {
								userAddmodalDialog.dialog('destroy');
								$.messager.show('提示', json.msg, 'info');
								getAllUsers();	
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
	*删除用户
	*/
	function deleteSysUser() {		
		var row=dataGrid.datagrid("getSelected");
		parent.$.messager.confirm('您是否要删除当前用户?', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.user.id}';/*当前登录用户的ID*/
				if (currentUserId != row.id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/sysUser/deleteSysUser.do', {
						name : row.name,
						id : row.id
					}, function(json) {
					    parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllUsers();
						}else{
							$.messager.show('提示', json.msg, 'info');
						}
					}, 'JSON');
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '不可以删除当前登录用户,请重新选择!'
					});
				}
			}
		});
	}
	function enableOrUnenableSysUser(){
		var id=dataGrid.datagrid("getSelected").id;
		var enable=dataGrid.datagrid("getSelected").enable;
		var msg="";
		if(enable==1){
			enable=0;msg="禁用";
		}else if(enable==0){
			enable=1;msg="启用";
		}
		parent.$.messager.confirm('您是否要'+msg+'当前用户?', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.user.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/sysUser/enableOrUnenableSysUser.do', {
						id : id,
						enable:enable
					}, function(json) {
					    parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllUsers();
						}else{
							$.messager.show('提示', json.msg, 'info');
						}
					}, 'JSON');
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '不可以'+msg+'当前登录用户,请重新选择!'
					});
				}
			}
		});
	}
	/**
	*批量删除
	*/
	function batchDeleteSysUser() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		var names = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中的用户', function(r) {
				if (r) {
					var currentUserId = '${sessionInfo.user.id}';/*当前登录用户的ID*/
					var flag = false;
					for ( var i = 0; i < rows.length; i++) {
						if (currentUserId != rows[i].id) {
							ids.push(rows[i].id);
							names.push(rows[i].name);
						} else {
							flag = true;
						}
					}
					if (flag) {
						parent.$.messager.show({
							title : '提示',
							msg : '不可以删除当前登录用户,请重新选择!'
						});
						return;
					}
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/sysUser/batchDeleteSysUser.do', 
					{
						ids : ids.join(','),
						names : names.join(',')
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllUsers();
						} else {
							$.messager.show('提示', json.msg, 'info');
						}
					},"JSON");
				}
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录!'
			});
		}
	}
	/**
	* 定向到用户编辑页面
	*/
	function goToUserEditJsp(){
		
		var userEditmodalDialog = parent.$.modalDialog({
			title : '编辑用户',
			width : 550,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/sysUser/goToUserEditJsp.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-standard-disk',
				handler : function() {
					var f = userEditmodalDialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/sysUser/editSysUser.do",f.form("getData"),function(json){
							if (json.success) {
								userEditmodalDialog.dialog('destroy');
								$.messager.show('提示', json.msg, 'info');
								getAllUsers();	
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
				}
			} ]
		});
	}
	function resetPwd(){
		$.post("${pageContext.request.contextPath}/pms/sysUser/resetPwd.do",{id:dataGrid.datagrid("getSelected").id},function(json){
			if (json.success) {
				$.messager.show('提示', json.msg, 'info');
			} else {
				$.messager.show('提示', json.msg, 'info');
			}
		},"JSON");
	}
	/**
	* 分配角色
	*/
	function assignRole(){
		var assignRoleDialog = parent.$.modalDialog({
			title : '角色分配',
			width : 340,
			height : 360,
			href : '${pageContext.request.contextPath}/pms/sysUser/goToUserAssignRoleJsp.do?id=' + dataGrid.datagrid("getSelected").id+'&name='+dataGrid.datagrid('getSelected').name,
			buttons : [{
				text : '保存',
				iconCls : 'icon-standard-disk',
				handler : function() {
					var id = dataGrid.datagrid('getSelected').id;
					var rows=assignRoleDialog.find("#roleDataGrid").datagrid('getChecked');
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
					$.post("${pageContext.request.contextPath}/pms/sysUser/assignRole.do",{id:id,ids:ids.join(',')},function(json){
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
						assignRoleDialog.dialog('destroy');
					},"JSON");
				}
			} ]
		});
	}
	/**
	* 批量分配角色
	*/
	function batchAssignRole(){
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		var names = [];
		
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
				names.push(rows[i].name);
			}
			var batchAssignRoleDialog=parent.$.modalDialog({
				title : '批量角色分配',
				width : 340,
				height : 360,
				href : '${pageContext.request.contextPath}/pms/sysUser/goToUserBatchAssignRoleJsp.do?names='+names.join(','),
				buttons : [ {
					text : '保存',
					iconCls : 'icon-standard-disk',
					handler : function() {
						var rows = dataGrid.datagrid('getChecked');
						var uids = [];//用户ids
						for(var i=0;i<rows.length;i++){
							uids.push(rows[i].id);
						}
						var rids = [];//角色ids
						rows = batchAssignRoleDialog.find("#roleDataGrid").datagrid('getChecked');
						for(var i=0;i<rows.length;i++){
							rids.push(rows[i].id);
						}
						$.post("${pageContext.request.contextPath}/pms/sysUser/batchAssignRole.do",{uids:uids.join(','),rids:rids.join(',')},function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
							batchAssignRoleDialog.dialog('destroy');
						},"JSON");
						
					}
				} ]
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要操作的用户!'
			});
		}
	}
	/**
	* 分配机构
	*/
	function assignOrg(){
		var assignOrgDialog = parent.$.modalDialog({
			title : '用户组织机构分配',
			width : 485,
			height : 360,
			href : '${pageContext.request.contextPath}/pms/sysUser/goToUserAssignOrgJsp.do?id=' + dataGrid.datagrid("getSelected").id+'&name='+dataGrid.datagrid('getSelected').name,
			buttons : [/* {
				text : '全选',
				iconCls:'basis_search',
				handler : function() {
				}
			},{
				text : '反选',
				iconCls:'basis_search',
				handler : function() {
				}
			}, */ {
				text : '保存',
				iconCls : 'icon-standard-disk',
				handler : function() {
					var id = dataGrid.datagrid('getSelected').id;
					var rows=assignOrgDialog.find("#orgTree").tree('getChecked');
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
					$.post("${pageContext.request.contextPath}/pms/sysUser/assignOrg.do",{id:id,ids:ids.join(',')},function(json){
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
						assignOrgDialog.dialog('destroy');
					},"JSON");
				}
			} ]
		});
	}
	/**
	* 批量分配机构
	*/
	function batchAssignOrg(){
		//goToUserBatchAssignOrgJsp
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		var names = [];
		
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
				names.push(rows[i].name);
			}
			var batchAssignOrgDialog=parent.$.modalDialog({
				title : '批量分配机构',
				width : 340,
				height : 360,
				href : '${pageContext.request.contextPath}/pms/sysUser/goToUserBatchAssignOrgJsp.do?names='+names.join(','),
				buttons : [ {
					text : '保存',
					iconCls : 'icon-standard-disk',
					handler : function() {
						 var rows = dataGrid.datagrid('getChecked');
						var uids = [];//用户ids
						for(var i=0;i<rows.length;i++){
							uids.push(rows[i].id);
						}
						var oids = [];//机构ids
						rows = batchAssignOrgDialog.find("#orgTree").tree('getChecked');
						for(var i=0;i<rows.length;i++){
							oids.push(rows[i].id);
						}
						$.post("${pageContext.request.contextPath}/pms/sysUser/batchAssignOrg.do",{uids:uids.join(','),oids:oids.join(',')},function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
							batchAssignOrgDialog.dialog('destroy');
						},"JSON"); 
						
					}
				} ]
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要操作的用户!'
			});
		}
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
								<th>查询条件</th>
								<td><input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '姓名,登录账户或工号'"/></td>
								<th>性别</th>
								<td>
								<select name="gender" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								        <option value="">全部</option>
										<option value="1">男</option>
										<option value="0">女</option>
								</select>
								</td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
						    <c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/getAllUser.do']}">
							<td><a onclick="getAllUsers();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/getAllUser.do']}">
							<td><a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-table-refresh',plain:true">清空条件</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/addSysUser.do']}">
							<td><a onclick="goToUserAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">新增用户</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/batchAssignRole.do']}">
							<td><a onclick="batchAssignRole();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-group-go'">批量分配角色</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/batchAssignOrg.do']}">
							<td><a onclick="batchAssignOrg();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-group-link'">批量分配机构</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/batchDeleteSysUser.do']}">
							<td><a onclick="batchDeleteSysUser();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-group-delete'">批量删除</a></td>
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
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/editSysUser.do']}">
		<div onclick="goToUserEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/deleteSysUser.do']}">
		<div onclick="deleteSysUser();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/enableOrUnenableSysUser.do']}">
		<div onclick="enableOrUnenableSysUser();" data-options="iconCls:'icon-standard-group-key'">禁/启用</div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/assignRole.do']}">
		<div onclick="assignRole();" data-options="iconCls:'icon-standard-group-go'">分配角色</div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/assignOrg.do']}">
		<div onclick="assignOrg();" data-options="iconCls:'icon-standard-group-link'">分配机构</div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysUser/resetPwd.do']}">
		<div onclick="resetPwd();" data-options="iconCls:'icon-standard-group-gear'">重置密码</div>
	</c:if>
</div>	
</body>
</html>