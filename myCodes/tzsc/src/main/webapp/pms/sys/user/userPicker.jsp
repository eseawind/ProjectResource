<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>用户选择页面</title>
<script type="text/javascript">	
	var userPickGrid=null;	
	$(function() {
		userPickGrid = $('#userPickGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
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
			columns : [ [
				{
					field : 'id',
					title : '编号',
					width : 150,
					checkbox : true
				}, {
					field : 'name',
					title : '姓名',
					width : 100,
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
				},{
					field : 'gender',
					title : '性别',
					width : 40,
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						return value==0?'女':'男';
					}
				}
			 ] ],
			toolbar : '#userPickToolbar',
			onLoadSuccess : function() {								
				$(this).datagrid('tooltip');
			}
		});
	});
	/**
	* 查询用户
	*/	
	function getAllUsers() {
		userPickGrid.datagrid({
					url : "${pageContext.request.contextPath}/pms/sysUser/getAllUser.do",
					queryParams :$("#pickUserForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询用户异常", 'error');
					}
				});
		
	}
	/**
	*清除查询条件
	*/
	function clearForm() {
		$('#pickUserForm input').val(null);		
	}
	
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
<div id="userPickToolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="pickUserForm" style="margin:4px 0px 0px 0px">
						<table>
							<tr>
								<th>过滤条件</th>
								<td><input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '姓名,登录账户或工号'" style="width:120px"/></td>
								<th>&nbsp;&nbsp;性别</th>
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
							<td><a onclick="getAllUsers();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a></td>
							<td><a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-table-refresh',plain:true">清空条件</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>
<div data-options="region:'center',border:false">
	<table id="userPickGrid"></table>
</div>		
</div>