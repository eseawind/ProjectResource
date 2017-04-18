<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>维修工管理</title>
<meta charset="utf-8"/>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">	
	var dataGrid=null;	
	$(function() {
		$.loadComboboxData($("#teamId2"),"TEAM",false);
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
			/* sortName : 'name',
			sortOrder : 'asc', */
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
			},{
				field : 'shiftId',
				title : '班次id',
				align:'center',
				width : 80,
				hidden:true,
				sortable : true
			}, {
				field : 'path',
				title : '照片',
				align:'center',
				width : 80,
				height : 100,
				sortable : true,
				formatter : function(path,row){return'<img src='+path+' style="width:80px;height:100px;">'}
			}, {
				field : 'userName',
				title : '姓名',
				width : 60,
				align:'center',
				sortable : true
			} , {
				field : 'eno',
				title : '工号',
				align:'center',
				width : 80,
				sortable : true
			}, {
				field : 'teamId',
				title : '班组',
				align:'center',
				width : 60,
				sortable : true
			}, {
				field : 'status',
				title : '状态',
				align:'center',
				width : 60,
				sortable : true,
				formatter: function(value,row,index){
					if (value==0){
						return '<font color="green">在线</font>';
					} else if(value==1){
						return '<font color="red">忙碌</font>';
					} else if(value==2){
						return '<font color="red">离线</font>';
					}
				}
			},{
				field : 'typeId',
				title : '维修工类别',
				align:'center',
				width : 100,
				formatter: function(value,row,index){
					if (value==1){
						return '<font>机械维修工</font>';
					} else if(value==2){
						return '<font >电气维修工</font>';
					} else if(value==3){
						return '<font >维修主管</font>';
					}
				},
				sortable : true
			},{
				field : 'eqpType',
				title : '维修设备',
				align:'center',
				width : 100,
				sortable : true
			},{
				field : 'remark',
				title : '备注',
				align:'center',
				width : 100,
				sortable : true
			},{
				field : 'createUserName',
				title : '创建人姓名',
				align:'center',
				width : 70,
				sortable : true
			},{
				field : 'createUserTime',
				title : '创建时间',
				align:'center',
				width : 150,
				sortable : true
			},{
				field : 'updateUserName',
				title : '修改人姓名',
				align:'center',
				width : 70,
				sortable : true
			},{
				field : 'updateUserTime',
				title : '最后修改时间',
				align:'center',
				width : 130,
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
	/**
	* 查询用户
	*/	
	function getAllUsers() {
		dataGrid.datagrid({
					url : "${pageContext.request.contextPath}/pms/fixUser/queryFixUser.do?resid="+Math.random(),
					queryParams :$("#searchForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询用户异常", 'error');
					}
				});
		$('#dataGrid').datagrid('clearSelections');
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
	function goToFixUserAddJsp(){
		var name = '${sessionInfo.user.name}';
		var id = '${sessionInfo.user.id}';
		var userAddmodalDialog = parent.$.modalDialog({
			title : '添加用户',
			width : 550,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/fixUser/goToAddFixUserJsp.do',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-standard-disk',
				handler : function() {
					var f = userAddmodalDialog.find('#form');
					if(f.form("validate")){
						f.form({   
						    url:"${pageContext.request.contextPath}/pms/fixUser/addFixUser.do?id="+Math.random(),   
						    success:function(data){
						    	userAddmodalDialog.dialog("destroy");
						    	getAllUsers();
						    	data=jQuery.parseJSON(data);
						    	$.messager.show({
						    		title:'消息提示',
						    		msg:data.msg,
						    		timeout:2000,
						    		showType:'slide'
						    	});
						    }   
						}); 
						f.submit();
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
					$.post('${pageContext.request.contextPath}/pms/fixUser/deleteFixUser.do', {
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
	
	/**
	*批量删除
	*/
	function batchDeleteSysUser() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		var names = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中的人员', function(r) {
				if (r) {
					var currentUserId = '${sessionInfo.user.id}';/*当前登录用户的ID*/
					var flag = false;
					for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
						} 
					}
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/fixUser/batchDelete.do', 
					{
						ids : ids.join(','),
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllUsers();
						} else {
							$.messager.show('提示', json.msg, 'info');
						}
					},"JSON");
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
			height : 400,
			href : '${pageContext.request.contextPath}/pms/fixUser/goToUpdateFixUserJsp.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls : 'icon-standard-disk',
				handler : function() {
					var f = userEditmodalDialog.find("#form");
					if(f.form("validate")){
						f.form({   
						    url:"${pageContext.request.contextPath}/pms/fixUser/updateFixUser.do?id="+Math.random(),   
						    success:function(data){
						    	userEditmodalDialog.dialog('destroy');
						    	getAllUsers();
						    	data=jQuery.parseJSON(data);
						    	$.messager.show({
						    		title:'消息提示',
						    		msg:data.msg,
						    		timeout:2000,
						    		showType:'slide'
						    	});
						    }   
						}); 
						f.submit();
					}
					
				}
			} ]
		});
	}
	
	$(function(){
		$.loadComboboxData($("#shId"),"SHIFT",false);
	})
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
								<td><input type="text" name="userName" class="easyui-validatebox "  data-options="prompt: '姓名或工号'"/></td>
								<th>班组</th>
								<td>
						        <select id="teamId2" name="teamId" style="width:170px;" data-options="panelHeight:'auto',width:175,editable:false,prompt:'请选择班次'"/></td>
							</tr>
						</table>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
						    <c:if test="${not empty sessionInfo.resourcesMap['/pms/fixUser/queryFixUser.do']}">
							<td><a onclick="getAllUsers();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/fixUser/queryFixUser.do']}">
							<td><a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-table-refresh',plain:true">清空条件</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/fixUser/addFixUser.do']}">
							<td><a onclick="goToFixUserAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">新增维修工</a></td>
							</c:if>
							
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/fixUser/batchDelete.do']}">
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
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/fixUser/updateFixUser.do']}">
		<div onclick="goToUserEditJsp();" data-options="iconCls:'icon-standard-user-edit'">编辑</div>
	</c:if>
	<c:if test="${not empty sessionInfo.resourcesMap['/pms/fixUser/deleteFixUser.do']}">
		<div onclick="deleteSysUser();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
	</c:if>
</div>	
</body>
</html>