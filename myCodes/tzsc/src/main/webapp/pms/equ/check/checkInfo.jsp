<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备点修计划管理</title>
<meta charset="utf-8" />
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
$(function(){
	$.loadComboboxData($("#shiftId"),"SHIFT",true);
	$.loadComboboxData($("#eqmId"),"ALLEQPS",true);
});
	var dataGrid = null;
	$(function() {
		//初始化时间
	    var d=new Date();
		var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
		var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		$("#date1").my97("setValue",sts);//
		$("#date2").my97("setValue",end);	//
		dataGrid = $('#dataGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			rownumbers :true,
			pagination : true,
			idField : 'id',
			striped : true,
			pageSize : 15,
			pageList : [ 15, 30, 45, 60, 75,90 ],
			singleSelect:true,
			checkOnSelect : true,
			selectOnCheck : false,
			nowrap : true,
			showPageList:false,
			columns : [ [/*  {
				field : 'czgId',
				title : '操作工点修记录编号',
				hidden:true
			}, {
				field : 'wxgId',
				title : '维修工点修记录id',
			   	hidden:true
			}, {
				field : 'wxzugId',
				title : '维修主管记录ID',
				hidden:true
			}, */{
				field : 'id',
				title : '主键',
				hidden:true
			}, {
				field:'eqpName',
				title:'设备名称',
				align : 'center',
				width:90,
				sortable:true
			} ,{
				field : 'orderDate',
				title : '计划执行日期',
				align : 'center',
				width:100,
				sortable:true
			}, {
				field : 'shiftName',
				title : '班次',
				align : 'center',
				width:80,
				sortable:true,
				/* formatter : function(value, row, index) {
					if (value == 1) {
						return '<span>早班<span>'
					}
					if (value == 2) {
						return '<span>中班<span>'
					}
					if (value == 3) {
						return '<span>晚班<span>'
					}
				} */
			},{
				field : 'eqpTypeDes',
				title : '点检关键部位',
				align : 'center',
				width:160,
			},{
				field : 'eqpTypeName',
				title : '点检标准',
				align : 'center',
				width:250,
			}, {
				field : 'chDate',
				title : '点检时间',
				align : 'center',
				width:150,
				sortable:true

			}, {
				field : 'chTypeName',
				title : '点检类型',
				align : 'center',
				width:80
			},{
				field : 'createUserName',
				title : '点检人',
				align : 'center',
				width:100
			},{
				field : 'status',
				title : '点检状态',
				width:80,
				align:'center',
				formatter: function(value,row,index){
					if (value==1){
						return '<font color="green">通过</font>';
					} else if(value==0){
						return '<font color="red">未通过</font>';
					} else if(value==2){
						return '<font color="red">有故障</font>';
					}
				}
			} /*  {
				field : 'czgName',
				title : '操作工',
				align : 'center',
				width:150
			}, {
				field : 'wxgName',
				title : '维修工',
				align : 'center',
				width:150
			}, {
				field : 'wxzugName',
				title : '维修主管',
				align : 'center',
				width:150
			}  */] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$(this).datagrid('tooltip');
			},
			//左键点击弹出该点检的详细信息String id,String orderState,String orderDate
			 /* onClickRow : function(rowIndex, rowData) {
				showInfo(rowData.id);
			} */
			/* onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				showInfo(rowData.czgId, rowData.wxgId, rowData.wxzugId);
			} */
		});
	});
	// 查询设备点检计划
	function queryWCPlan() {
		dataGrid.datagrid({
					url : "${pageContext.request.contextPath}/pms/equc/check/queryPlan.do",
					queryParams : $("#searchForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询点检计划异常", 'error');
					}
				});
	}
	
	function showInfo(id) {
		console.info(id);
		var addWCPlan = parent.$.modalDialog({
			title : '设备点检计划添加',
			width : 860,
			height : 370,
			href :  '${pageContext.request.contextPath}/pms/equ/check/showInfo.jsp?id='+ id,
			buttons : [ {
				text : '关  闭',
				iconCls: 'icon-standard-cross',
				handler : function() {
					addWCPlan.dialog('destroy');
				}
			} ]
		});
	}
	function clearForm() {
		$("#searchForm input").val(null);
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="allInfo" class="easyui-dialog" title="设备点修详细信息" 
		data-options="resizable:true,modal:true,closed:true,fit:true">
		<table id="dg"></table>
	</div>

	<div id="toolbar" style="display: none;">
		<table>
			<tr>
				<td>
					<form id="searchForm" style="margin: 4px 0px 0px 0px">
						<div class="topTool">
							<fieldset>
								<div>
									<span class="label">执行时间：</span> 
									<input id="date1" name="chDate" type="text" class="easyui-my97" style="width: 110px" />
								</div>
								<div>
									<span class="label" style="width: 10px">到</span>
									<input id="date2" name="endChDate" type="text" class="easyui-my97" style="width: 110px" />
								</div>
								<div><span class="lable">班次</span>
								<select id="shiftId" name="shiftId" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
								</div>
								<div><span class="lable">设备名称</span>
									<input id="eqmId" name="eqmId" class="easyui-combobox" data-options="panelHeight:'auto',width:100,panelHeight:150,editable:false"/>
								</div>
							</fieldset>
						</div>
					</form>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<c:if
								test="${not empty sessionInfo.resourcesMap['/pms/sysUser/getAllUser.do']}">
								<td><a onclick="queryWCPlan();" href="javascript:void(0);"
									class="easyui-linkbutton"
									data-options="iconCls:'icon-standard-zoom',plain:true">查询</a></td>
							</c:if>

							<td><a onclick="clearForm();" href="javascript:void(0);"
								class="easyui-linkbutton"
								data-options="iconCls:'icon-standard-table-refresh',plain:true">清空条件</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
</body>
</html>