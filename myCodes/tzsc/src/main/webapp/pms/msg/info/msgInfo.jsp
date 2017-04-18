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
			var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			$("#time").my97("setValue",sts);
			$("#approveTime").my97("setValue",end);	
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
				}/* ,{
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
				} */] ],
				toolbar : '#toolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow',rowIndex);
					var items = getMenus(rowData.flag,rowData.initiator,rowData.initiatorName,
							rowData.approval,rowData.approvalName,
							rowData.issuer,rowData.issuerName);
	               	$.easyui.showMenu({
	                  	left:e.pageX, top:e.pageY,
	                  	items:eval("("+items+")")
	              	});  
					//$(this).datagrid('unselectAll').datagrid('uncheckAll');
					/* $(this).datagrid('selectRow', rowIndex);
					$('#menu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});  */
				}
			});
		});
		
		function getMenus(state,senderId,sponsor,approval,assessor,issuer,sender){
			//状态~发起者ID~发起者~审核ID~审核者~签发ID~签发者
			var menus = "[";
			if("${msgOperator.operId}"==senderId){//发起者登陆右键		
					 /* if(state==1 ||state==0){//审核中 */ 
						menus += '{ text: "编辑",iconCls:"icon-standard-plugin-edit", handler: function (e, item) { editMsgInfo(); } }';
						menus +=",";
						menus += '{ text: "删除",iconCls:"icon-standard-plugin-delete", handler: function (e, item) { deleteMsgInfo(); } }';	
				/* }else if("${msgOperator.operId}"==approval){//审核人
					if(state==1)
						menus += '{ text: "审核",iconCls:"icon-standard-plugin-add", handler: function (e, item) { approveMsgInfo(); } }';
						
				}else if("${msgOperator.operId}"==issuer){//签发人
					if(state==2){
						menus += '{ text: "签发",iconCls:"icon-standard-plugin-add", handler: function (e, item) { issueMsgInfo(); } }';
						
					} */
				}		
				
				menus +="]";
				return menus;
			}
			
		
		
		
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
	//机台通知信息编辑
	function editMsgInfo(){
		var id=dataGrid.datagrid('getSelected').id;
		if(id!=null){
			var dialog = parent.$.modalDialog({
				title : '机台通知信息编辑',
				width : 620,
				height : 300,	
				href : '${pageContext.request.contextPath}/pms/msg/gotoMsgInfoForm.do?id='+id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/msg/saveOrUpdateMsgInfo.do?flag=0",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryMsgInfo();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}else{
			alert("请选中修改项");
		}
	} 
	//删除当前机台通知信息
	function deleteMsgInfo(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前机台通知信息？', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/msg/deleteMsgInfo.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryMsgInfo();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	

	
//机台通知信息添加
	function goToMsgInfoAdd(){
		var dialog = parent.$.modalDialog({
			title : '机台通知信息添加',
			width : 620,
			height : 300,
			href : '${pageContext.request.contextPath}/pms/msg/gotoMsgInfoForm.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/msg/saveOrUpdateMsgInfo.do?flag=0",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMsgInfo();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	function viewMsgInfo(){
		goToMsgInfoAdd();
	}
	
	//审核
	function approveMsgInfo(){
		var dialog = parent.$.modalDialog({
			title : '机台通知信息-审核',
			width : 620,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/msg/gotoMsgInfoApprove.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '驳回',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/msg/saveMsgInfo.do?flag=0",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMsgInfo();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			},{
				text : '审核通过',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/msg/saveMsgInfo.do?flag=2",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMsgInfo();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	//签发
	function issueMsgInfo(){
		var dialog = parent.$.modalDialog({
			title : '机台通知信息-签发',
			width : 620,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/msg/gotoMsgInfoIssuer.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '驳回',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/msg/saveMsgInfo.do?flag=1",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMsgInfo();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			},{
				text : '签发',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/msg/saveMsgInfo.do?flag=3",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryMsgInfo();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			}]
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
		$("#sel1").combobox("setValue", "");//下拉框赋值
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
						<input id="time"  name="time" type="text" class="easyui-my97"  datefmt="yyyy-MM-dd HH:mm:ss" style="width:130px"/>
					</div>
					<div >
						<span class="label"style="width: 15px">到</span>
						<input id="approveTime"  name="approveTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss"  style="width:130px"/>
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" style="width:1oo%;">
			<a onclick="queryMsgInfo()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			<a onclick="goToMsgInfoAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${msgOperator.name == 'APPROVE'}">
			<div onclick="approveMsgInfo();" data-options="iconCls:'icon-standard-plugin-add'">审核</div>
		</c:if>
		<c:if test="${msgOperator.name == 'ISSUER' }">
			<div onclick="issueMsgInfo();" data-options="iconCls:'icon-standard-plugin-add'">签发</div>
		</c:if>
		<div onclick="editMsgInfo()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteMsgInfo();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>