<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>文件管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			$.loadComboboxData($("#searchShift"),"shift");
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : true,
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
				columns : [ [
				{
					field : 'id',
					title : 'ID',
					align:'center',
					width : 100,
					checkbox : true
				},{
					field : 'title',
					title : '标题',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'initiatorName',
					title : '发起人',
					align:'center',
					width : 50
				},{
					field : 'time',
					title : '通知时间',
					align:'center',
					width : 120,
					sortable : true
				},{
					field : 'content',
					title : '通知内容',
					align:'center',
					width : 300,
					sortable : true
				},{
					field : 'approvalName',
					title : '审核人',
					align:'center',
					width : 50			
				},{
					field : 'flag',
					title : '状态',
					align:'center',
					width : 60,
					sortable : true,
					formatter: function(value,row,index){
						if(value=='0')
							return "驳回";
						else if(value=='1')
							return "审核中";
						else if(value=='2')
							return "签发中";
						else if(value=="3")
							return "已发布";
						
					}
				},{
					field : 'approveContent',
					title : '审批内容',
					align:'center',
					width : 50					
				},{
					field : 'issuerName',
					title : '签发人',
					align:'center',
					width : 50					
				}] ],
				toolbar : '#toolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow',rowIndex);
				
					
				}
			});
			queryMsgInfo();
		});
		
	
		
		
		/**
		* 查询文件
		*/	
		function queryMsgInfo() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/msg/queryMsgInfo.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询机台通知信息异常", 'error');
				}
			});
		}

	
	

	function clearForm(){
		$("#searchForm input").val(null);
		$("#sel1").combobox("setValue", "");//下拉框赋值
	}
	function read(){
		parent.location.reload();
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar"  style="display: none;width:100%;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">标题：</span>
						<input type="text" name="title" style="width:120px;" />
					</div>					
					<div >
						<span class="label">状态：</span>
						<select id="sel1"  name="flag" style="width:120px;" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="">---选择状态---</option>
								<option value="0" >草稿</option>
								<option value="1" >审核中</option>
								<option value="2" >签发中</option>
								<option value="3" >已发布</option>
						</select>
					</div>
					<div >
						<span class="label">时间：</span>
						<input  name="time" type="text" class="easyui-my97"  datefmt="yyyy-MM-dd HH:mm:ss" style="width:130px"/>
					</div>
					<div >
						<span class="label"style="width: 15px">到</span>
						<input  name="approveTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss"  style="width:130px"/>
					</div>
					<div style="text-align: right;"><input type="button" value="返回" onclick="read()"/></div>
					
				</fieldset>
			</div>
		</form>
				<div class="easyui-toolbar" style="width:1oo%;">
			<a onclick="queryMsgInfo()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
		
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>

</body>
</html>