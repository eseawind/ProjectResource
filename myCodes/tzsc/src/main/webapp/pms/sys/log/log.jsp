<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>系统日志管理</title>
<meta charset="utf-8"/>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">	
	var dataGrid=null;	
	$(function() {
		var d=new Date();
		var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
		var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		$("#date").my97("setValue",sts);
		$("#date2").my97("setValue",end);	
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
			striped : true,
			remoteSort: false,
			pageSize : 20,
			pageList : [ 20, 40, 60, 80, 100],
			sortName : 'date',
			sortOrder : 'desc',
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : true,
			showPageList:false,
			frozenColumns : [ [
			                   {
				field : 'id',
				title : 'id',
				checkbox : true
			},{
				field : 'name',
				title : '操作用户',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'sys',
				title : '系统模块',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'optname',
				title : '操作名称',
				width : 100,
				align:'center',
				sortable : true
			}, {
				field : 'ip',
				title : '用户IP',
				width : 160,
				align:'center',
				sortable : true
			}, {
				field : 'date',
				title : '操作时间',
				width : 140,
				align:'center',
				sortable : true
			}/* , {
				field : 'execTime',
				title : '执行耗时(ms)',
				width : 70,
				align:'center',
				sortable : true
			} */, {
				field : 'success',
				title : '操作结果',
				width : 60,
				align:'center',
				sortable : true
			} ]],
			columns : [ [{
				field : 'params',
				title : '日志参数',
				width : 750,
				sortable : true,
				align:'left', editor: "text"
			}]],
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
			},
            enableHeaderClickMenu: false,
            enableHeaderContextMenu: false,
            enableRowContextMenu: false,
            autoFocusField: "url",
            autoEditing: true,          //该属性启用双击行时自定开启该行的编辑状态
            extEditing: true,           //该属性启用行编辑状态的 ExtEditing 风格效果，该属性默认为 true。
            singleEditing: true,         //该属性启用datagrid的只允许单行编辑效果，该属性默认为 true。
            onAfterEdit: function(data) {
                $.fn.datagrid.extensions.onAfterEdit.apply(this, arguments);  //这句一定要加上
            }
		});
	});
	/**
	* 查询系统日志
	*/	
	function getAllLogs() {
		dataGrid.datagrid({
					url : "${pageContext.request.contextPath}/pms/log/getAllLogs.do",
					queryParams : $("#searchForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询系统日志异常", 'error');
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
	*删除系统日志
	*/
	function deleteLog() {		
		var id=dataGrid.datagrid("getSelected").id;
		parent.$.messager.confirm('您是否要删除当前系统日志?', function(b) {
			if (b) {
				$.post('${pageContext.request.contextPath}/pms/log/deleteLog.do', {
					id : id
				}, function(json) {
				    parent.$.messager.progress('close');
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllLogs();
					}else{
						$.messager.show('提示', json.msg, 'info');
					}
				}, 'JSON');
			}
		});
	}
	/**
	*批量删除
	*/
	function batchDeleteLogs() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中的系统日志', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
					}
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/log/batchDeleteLogs.do', 
					{
						ids : ids.join(',')
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllLogs();
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
								<th>姓名</th>
								<td><input name="name" style="width: 80px;height:18px" /></td>
								<th>&nbsp;操作名称</th>
								<td><input name="optname" style="width: 80px;height:18px" /></td>
								<th>&nbsp;系统模块</th>
								<td>
								<select name="sys" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								        <option value="">全部</option>
										<option value="PMS">PMS</option>
										<option value="WCT">WCT</option>
								</select>
								</td>
								<th>&nbsp;操作时间</th>								
								<td>
								 <input id="date" name="date" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px"/> 到
								 <input id="date2" name="date2" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px"/>
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
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/getAllRoles.do']}">
							<td><a onclick="getAllLogs();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/getAllRoles.do']}">
							<td><a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-table-refresh',plain:true">清空条件</a></td>
							</c:if>
							<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/getAllRoles.do']}">
							<td><a onclick="batchDeleteLogs();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-group-delete'">批量删除</a></td>
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
<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRole/getAllRoles.do']}">		
<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="deleteLog();" data-options="iconCls:'icon-standard-user-delete'">删除</div>
</div>
</c:if>	
</body>
</html>