<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>接口日志管理</title>
<meta charset="utf-8"/>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
 
<script type="text/javascript">	
	var dataGrid=null;
	var id;
	$(function() {
		var today = new Date();
		var month=today.getMonth()+1;
		if(month<10){month=("0"+month);}
		var day=today.getDate();
		if(day<10){day=("0"+day);}
		var date=today.getFullYear()+"-"+month+"-"+day; 
		$("#date").my97("setValue",date);
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
			striped : true,
			remoteSort: false,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'date_',
			sortOrder : 'desc',
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			showPageList:false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				checkbox : true
			}, {
				field : 'des',
				title : '接口类型',
				width : 100,
				align:'center',
				sortable : true
			}, {
				field : 'msgType',
				title : '收发类型',
				width : 80,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					var type="";
					switch(value){
						case 1:
							type = "发送";
							break;
						case 0:
							type = "接收";
							break;
					}
					return type;
				}
			},{
				field : 'flag',
				title : '完成结果',
				width : 80,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					var type="";
					switch(value){
						case 1:
							type = "<font color='green'>成功</font>";
							break;
						case 0:
							type = "<font color='red'>失败</font>";
							break;
					}
					return type;
				}
			}, {
				field : 'sysSend',
				title : '发送系统',
				width : 100,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					var type="";
					switch(value){
						case 1:
							type = "数采系统";
							break;
						case 2:
							type = "MES系统";
							break;
					}
					return type;
				}
			}, {
				field : 'sysReceive',
				title : '接收系统',
				width : 80,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					var type="";
					switch(value){
					case 1:
						type = "数采系统";
						break;
					case 2:
						type = "MES系统";
						break;
					}
					return type;
				}
			}, {
				field : 'date_',
				title : '收发时间',
				width : 140,
				align:'center',
				sortable : true
			}, {
				field : 'content',
				title : '消息内容',
				width : 490,
				sortable : true,
				hidden : true
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
				id=rowData.id;
			}
		});
	});
	/**
	* 查询日志
	*/	
	function getAllMessageQueues() {
		dataGrid.datagrid({
					type:"post",
					dataType :"text",
					url : "${pageContext.request.contextPath}/pms/msgqueue/getAllMessageQueues.do",
					queryParams :$("#searchForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询日志异常", 'error');
					}
				});
		
	}
	/**
	*清除查询条件
	*/
	function clearForm() {
		$("#searchForm input").val(null);
	}
	/**
	*删除接口日志
	*/
	function deleteMessageQueue() {		
		var id=dataGrid.datagrid("getSelected").id;
		parent.$.messager.confirm('您是否要删除当前接口日志?', function(b) {
			if (b) {
				$.post('${pageContext.request.contextPath}/pms/msgqueue/deleteLog.do', {
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
	function batchDeleteMessageQueues() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中的接口日志', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
					}
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/msgqueue/batchDeleteMessageQueues.do', 
					{
						ids : ids.join(',')
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getAllMessageQueues();
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
	function showInfo(){
		var content = dataGrid.datagrid('getSelected').content;
		$("#content").val(content);
		$('#showInfo').window('open');
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
<div id="toolbar" style="display: none;">
<form id="searchForm" style="margin:4px 0px 0px 0px" method="POST" action="#">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">收发日期：</span>
						<input id="date" name="date_" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>
					</div>
					
					<div >
						<span class="label">收发状态：</span>
						<select name="msgType"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<option value="">全部</option>
							    <option value="0">接收</option>
								<option value="1">发送</option>
						</select>
					</div>
					<div >
						<span class="label">完成状态：</span>
						<select name="flag"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<option value="">全部</option>
							    <option value="0">失败</option>
								<option value="1">成功</option>
						</select>
					</div>
					<div >
						<span class="label">发送方：</span>
						<select name="sysSend"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<option value="">全部</option>
							    <option value="1">数采系统</option>
								<option value="2">MES系统</option>
								<option value="3">其他系统</option>								
						</select>
					</div>
					<div >
						<span class="label">接收方：</span>
						<select name="sysReceive"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								<option value="">全部</option>
							    <option value="1">数采系统</option>
								<option value="2">MES系统</option>
								<option value="3">其他系统</option>							
						</select>
					</div>
					<div>
						<span class="label">关键字:</span>
						<input type="text" name="content" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:100" />
					</div>
				</fieldset>
			</div>
		</form>

		<div class="easyui-toolbar">
				<a onclick="getAllMessageQueues();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">重置</a>
				<a onclick="batchDeleteMessageQueues();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">批量删除</a>
		</div>
</div>
<div data-options="region:'center',border:false">
	<table id="dataGrid"></table>
</div>
<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
	<div onclick="showInfo();" data-options="iconCls:'icon-standard-user-edit'">查看消息内容</div>
	<div onclick="deleteMessageQueue();" data-options="iconCls:'icon-standard-user-edit'">删除</div>	
</div>
<div id="showInfo" class="easyui-window" title="消息详情"
		data-options="modal:true,closed:true,resizable:false,collapsible:false,minimizable:false,maximizable:false,iconCls:'icon-save'"
		style="width:600px; height:450px; padding: 15px;">
					<table>					
						<tr style="height:30px">
							<th>消息内容</th>
						</tr>
						<tr>
							<td>
							<textarea id="content" rows="22" cols="75"></textarea>
							</td>
						</tr>
					</table>
</div>			
</body>
</html>