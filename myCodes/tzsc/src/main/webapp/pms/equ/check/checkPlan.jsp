<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备点检计划管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		var param=null;
		$(function() {
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				rownumbers :true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'id',
					title : '设备ID',
					width : 120
				},{
					field : 'planName',
					title : '工单时间',
					width : 100,
					align : 'center',
					sortable : true
				},{
					field : 'planNo',
					title : '班次',
					width : 100,
					align:'center',
				}, {
					field : 'eqmName',
					title : '点检时间',
					align : 'center',
					width : 70,
					sortable : true
				},{
					field : 'scheduleDate',
					title : '完成状态',
					align : 'center',
					width : 130,
					sortable : true
				},{
					field : 'scheduleEndDate',
					title : '操作工',
					align : 'center',
					width : 130,
					sortable : true
				},
				{
					field : 'scheduleEndDate',
					title : '维修工',
					align : 'center',
					width : 130,
					sortable : true
				},{
					field : 'wheelCoverType',
					title : '主管主管',
					align:'center',
					width : 60,
					sortable : true,
					formatter : function(value, row, index) {
						var type = value;
						if(type=='0'){
							type="新增";
						}else if(type=='1'){
							type="审核通过";
						}else if(type=='2'){
							type="运行";
						}else if(type=='3'){
							type="停止运行";
						}else if(type=='4'){
							type="操作工操作完毕";
						}else if(type=='5'){
							type="点检工操作完毕";
						}else if(type=='6'){
							type="点检工操作完毕";
						}else if(type=='7'){
							type="审核完毕";
						}else if(type=='8'){
							type="计划完成";
						}
						return type;
					}
				}] ],
				toolbar : '#toolbar',
				onLoadSuccess : function() {
					//$(this).datagrid('tooltip');
				}, onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#menu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					}); 
				},onClickRow:function(rowIndex,rowData){
					getParam(rowData.id);
				}
			});
			
			//点击之后下方显示的该生产单相关设备点修信息
			param=$('#paramGrid').datagrid({
				rownumbers :true,
				fit : true,
				fitColumns : false,//fitColumns= true就会自动适应宽度（无滚动条）
				//width:$(this).width()-252,  
				border : false,
				pagination : false,
				idField : 'id',
				striped : true,
				remoteSort: false,
				pageSize : 100,
				pageList : [80, 100],
				sortName : 'sbCode',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : '设备ID',
					width : 120,
				}, {
					field : 'sbCode',
					title : '工单时间',
					width : 150,
					align : 'center',
					sortable : true
				}, {
					field : 'sbName',
					title : '班次',
					width : 150,
					align : 'center',
					sortable : true
				}, {
					field : 'planTimeStr',
					title : '点检时间',
					width : 150,
					align : 'center',
					sortable : true
				}, 
				{
					field : 'enable',
					title : '完成状态',
					align : 'center',
					width : 150,
					sortable : true,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">已完成<span>':'<span style="color:red">未完成<span>';
					}
				} ,
				{
					field : 'actualStrTime',
					title : '操作工',
					align : 'center',
					width : 150,
					sortable : true
				}, {
					field : 'actualStrTime',
					title : '维修工',
					align : 'center',
					width : 150,
					sortable : true
				}, {
					field : 'actualStrTime',
					title : '维修主管',
					align : 'center',
					width : 150,
					sortable : true
				}, ] ],
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#inputMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			}); 
		});
		
		// 查询设备点检计划
		function queryWCPlan() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/equc/check/queryPlan.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询点检计划异常", 'error');
				},
				onLoadSuccess:function(data){
					param.datagrid('loadData', { total: 0, rows: [] });//清空下方DateGrid
				}
			});
		}
		//新增设备点检计划 
		var addWCPlan;
		function goToAddWCPlan(){
			addWCPlan = parent.$.modalDialog({
				title : '设备点检计划添加',
				width : 860,
				height : 570,
				href : '${pageContext.request.contextPath}/pms/equc/check/goToAddWCPlan.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = addWCPlan.find("#form");
						if(f.form("validate")){
							//var type  =f.form("getData").planNo;//车间
							//alert(type);
							var rows = addWCPlan.find('#addWcpLbgzGrid').datagrid('getSelected');
							if(rows==null||rows.metcId==null||rows.metcId==""){
								$.messager.show('提示', '请选择点检规则', 'info');
								return ;
							}
							var pams = {
								planCode : f.form("getData").planCode,//计划编号
								planName : f.form("getData").planName,//计划名称
								equipmentLxId : f.form("getData").equipmentLxId,//设备类型
								equipmentLxName : f.form("getData").equipmentLxName,
								equipmentXhId : f.form("getData").equipmentXhId,//设备型号
								equipmentXhName : f.form("getData").equipmentXhName,
								equipmentId : f.form("getData").equipmentId,//设备ID
								maintenanceType : f.form("getData").maintenanceType,//轮保类别
								equipmentMinute : f.form("getData").equipmentMinute,//轮保时长
								scheduleDate : f.form("getData").scheduleDate,//轮保日期
								matId : f.form("getData").matId,//牌号
								mdShiftId : f.form("getData").mdShiftId,//班次
								maintenanceContent : f.form("getData").maintenanceContent,//备注
								isZgCheck : f.form("getData").isZgCheck,
								del : '0',
								wheelCoverType : '0',//0 表示 新增
								metcId : rows.metcId//绑定的 项目大类(父节点，根据该节点可以查询出说有 点检项)
							};
							$.post("${pageContext.request.contextPath}/pms/equc/check/addWCPlan.do",pams,function(json){
								if (json.success) {
									$.messager.show('提示', '操作成功', 'info');
									addWCPlan.dialog('destroy');
									queryWCPlan();
								}else{
									$.messager.show('提示', "保存失败", 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
	
	//编辑点检计划
	function goToEditWCPlan(){
		var wheelCoverType = dataGrid.datagrid('getSelected').wheelCoverType;
		if(wheelCoverType=='0'||wheelCoverType=='1'){
			var dialog = parent.$.modalDialog({
				title : '设备点检计划编辑',
				width :  860,
				height : 570,
				href : '${pageContext.request.contextPath}/pms/equc/check/goToEditWCPlan.do?id='+dataGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '修改',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							var rows = dialog.find('#editWcpLbgzGrid').datagrid('getSelected');
							if(rows==null||rows.metcId==null||rows.metcId==""){
								$.messager.show('提示', '请选择点检规则', 'info');
								return ;
							}
							var lunbaoGzId = f.form("getData").editWcpLbgzGridId;
							if(lunbaoGzId==null||lunbaoGzId==""){
								$.messager.show('提示', '请选择点检规则', 'info');
								return ;
							}
							//alert(rows.metcId+":"+f.form("getData").editWcpLbgzGridId);
							//return ;
							var pams = {
								planId : f.form("getData").planId,//主键
								planCode : f.form("getData").planCode,//计划编号
								planName : f.form("getData").planName,//计划名称
								equipmentLxId : f.form("getData").equipmentLxId,//设备类型
								equipmentLxName : f.form("getData").equipmentLxName,
								equipmentXhId : f.form("getData").equipmentXhId,//设备型号
								equipmentXhName : f.form("getData").equipmentXhName,
								equipmentId : f.form("getData").equipmentId,//设备ID
								maintenanceType : f.form("getData").maintenanceType,//轮保类别
								equipmentMinute : f.form("getData").equipmentMinute,//轮保时长
								scheduleDate : f.form("getData").scheduleDate,//轮保日期
								matId : f.form("getData").matId,//牌号
								mdShiftId : f.form("getData").mdShiftId,//班次
								isZgCheck : f.form("getData").isZgCheck,
								maintenanceContent : f.form("getData").maintenanceContent,//备注
								del : '0',
								//wheelCoverType : '新建', 不修改状态
								metcId : rows.metcId//绑定的 项目大类(父节点，根据该节点可以查询出说有 点检项)
							};
							$.post("${pageContext.request.contextPath}/pms/equc/check/editWCPlan.do",pams,function(json){
								if (json.success) {
									$.messager.show('提示', '修改成功', 'info');
									dialog.dialog('destroy');
									queryWCPlan();
								}else{
									$.messager.show('提示', "修改失败", 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}else{
			$.messager.show('提示', '该状态下的计划不可以修改', 'info');
			return ;
		}
	} 
	
	//审批
	function Auditing(statusId,obj){
		//alert(obj);
		//return false;
		if(obj!=""&&dataGrid.datagrid('getSelected')!=null){
			var data={id:dataGrid.datagrid('getSelected').id,statusId:statusId,status:obj};

			var wheelCoverType =dataGrid.datagrid('getSelected').wheelCoverType;
			if(statusId==1&&(wheelCoverType==0)){//点击了审核 ，审批:新增和审核通过 的 才可以  审核
				$.post("${pageContext.request.contextPath}/pms/equc/check/auditing.do",data,function(json){
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryWCPlan();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				},"JSON"); 
			}else if(statusId==1&&(wheelCoverType==1)){//审核,运行状态
				$.messager.show('提示',"该单已经审核了,无需审核", 'info');
			}else if(statusId==1&&(wheelCoverType==2)){//审核,运行状态
				$.messager.show('提示',"该单已经批准运行中,无需审核", 'info');

			}else if(statusId==2&&(wheelCoverType==0)){//审批,运行状态
				$.messager.show('提示',"该单需先审核", 'info');
			}else if(statusId==2&&(wheelCoverType==1)){
				$.post("${pageContext.request.contextPath}/pms/equc/check/auditing.do",data,function(json){
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryWCPlan();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				},"JSON"); 
			}else if(statusId==2&&(wheelCoverType==2)){//审批,运行状态
				$.messager.show('提示',"该单已经审批运行中,无需批准", 'info');
			}else{
				$.messager.show('提示',obj+":操作失败", 'info');
			}
		}
	}
	//获取维保详细
	function getParam(id){
		param.datagrid({
				url : "${pageContext.request.contextPath}/pms/equc/check/queryParam.do?pid=" + id
		   });
	}
	
    //删除设备点检计划
	function deleteWCPlan(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前设备点检计划？', function(b) {
			if (b) {	
				$.post('${pageContext.request.contextPath}/pms/equc/check/deletePlan.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryWCPlan();
					} else {
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	function clearForm(){
		$("#searchForm input").val(null);
	}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false,split:true,title:'产出数据'" style="height:320px;">
		<div id="toolbar"  style="display: none;width:100%;">
			<form id="searchForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div >
							<span class="label">点检名称：</span>
							<input type="text" name="planName" class="easyui-validatebox "  data-options="prompt: '请输入点检计划名称'"/>
						</div>
						<div >
							<span class="label">点检编号：</span>
							<input type="text" name="planNo" class="easyui-validatebox "  data-options="prompt: '请输入点检计划编号'"/>
						</div>
						<div >
							<span class="label">点检时间：</span>
							 <input name="date1" type="text" class="easyui-my97"  style="width:130px"/>
						</div>	
						<div >
							<span class="label" style="width:10px">到</span>
							<input name="date2" type="text" class="easyui-my97" style="width:130px"/>
						</div>		
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/queryCheck.do']}">
					<a onclick="queryWCPlan()" href="javascript:void(0);"  class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<!--  
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/resetCheck.do']}">
					<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/addCheck.do']}">
					<a onclick="goToAddWCPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/editCheck.do']}">
			 		<a onclick="goToEditWCPlan()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-report-edit'">编辑</a>
			 	</c:if>
			 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/deleteCheck.do']}">
					<a onclick="deleteWCPlan();" href="javascript:void(0);" class="easyui-linkbutton"  data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/passCheck.do']}">
					<a onclick="Auditing('1','审批');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-drive-edit'">审核</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/runCheck.do']}">
					<a onclick="Auditing('2','批准');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-application-go'">批准</a>
				</c:if>
				-->
			</div>
		</div>
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'',border:false">
		<table id="paramGrid"></table>
	</div>
	 <div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/addCheck.do']}">
			<div onclick="goToAddWCPlan();" href="javascript:void(0);"  data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/editCheck.do']}">
	 		<div onclick="goToEditWCPlan()" href="javascript:void(0);" data-options="plain:true,iconCls:'icon-standard-report-edit'">编辑</div>
	 	</c:if>
	 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/deleteCheck.do']}">
			<div onclick="deleteWCPlan();" href="javascript:void(0);"  data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/passCheck.do']}">
			<div onclick="Auditing('1','审批');" href="javascript:void(0);" data-options="plain:true,iconCls:'icon-standard-drive-edit'">审核</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/equc/check/runCheck.do']}">
			<div onclick="Auditing('2','批准');" href="javascript:void(0);" data-options="plain:true,iconCls:'icon-standard-application-go'">批准</div>
		</c:if>
	</div> 
</body>
</html>