<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备保养计划查询</title>
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
					title : '设备保养计划ID',
					width : 120,
					hidden : true
				},{
					field : 'planName',
					title : '计划名称',
					width : 100,
					align : 'center',
					sortable : true
				},{
					field : 'planNo',
					title : '计划编号',
					width : 100,
					align:'center',
					hidden:true
				}, {
					field : 'eqmName',
					title : '设备名称',
					align : 'center',
					width : 70,
					sortable : true
				},{
					field : 'scheduleDate',
					title : '计划开始日期',
					align : 'center',
					width : 130,
					sortable : true
				},{
					field : 'scheduleEndDate',
					title : '计划结束日期',
					align : 'center',
					width : 130,
					sortable : true
				},{
					field : 'wheelCoverType',
					title : '状态',
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
						}
						return type;
					}
				}] ],
				columns : [ [/* {
					field : 'wheelParts',
					title : '保养部件',
					align:'center',
					width : 100,
					sortable : true
				}, */{
					field : 'maintenanceContent',
					title : '备注',
					align:'left',
					width : 200,
					sortable : true,
					formatter : function(value, row, index) {
						return value.length>20?value.substring(0,20)+"……":value;
					}
				},{
					field : 'mdShiftName',
					title : '班次',
					align:'center',
					width : 130,
					sortable : true
				},{
					field : 'maintenanceType',
					title : '保养类别',
					align:'center',
					width : 120,
					sortable : true,
					formatter : function(value, row, index) {
						var bylx = value;
						if(value=='lb'){
							bylx="轮保";
						}
						return bylx;
					}
				}/* ,{
					field : 'period',
					title : '周期',
					align:'center',
					width : 70,
					sortable : true
				},{
					field : 'remindCycle',
					title : '提醒周期',
					align:'center',
					width : 70,
					sortable : true
				} */,{
					field : 'startTime',
					title : '保养开始时间',
					align : 'center',
					width : 130,
					sortable : true
				},{
					field : 'endTime',
					title : '保养结束时间',
					align : 'center',
					width : 130,
					sortable : true
				} ] ],
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
			param=$('#paramGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				remoteSort:false, 
				//pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 20,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				rownumbers :true,
				columns : [ [ {
					field : 'id',
					title : 'id',
					width : 120,
					hidden : true
				}, {
					field : 'planTimeStr',
					title : '计划保养日期',
					width : 150,
					align : 'center',
					sortable : true
				}, {
					field : 'userName',
					title : '保养人',
					align : 'center',
					width : 150,
					sortable : true
				}
				, {
					field : 'actualTimeStr',
					title : '实际保养时间',
					align : 'center',
					width : 150,
					sortable : true
				}, {
					field : 'enable',
					title : '是否完成',
					align : 'center',
					width : 150,
					sortable : true,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">已完成<span>':'<span style="color:red">未完成<span>';
					}
				} ] ],
				//toolbar : '#inputToolbar',
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
		
		// 查询设备保养计划
		function queryWCPlan() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/wcp/queryWCPlan.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询保养计划异常", 'error');
				}
			});
		}
		//新增设备保养计划 
		var addWCPlan;
		function goToAddWCPlan(){
			addWCPlan = parent.$.modalDialog({
				title : '设备保养计划添加',
				width : 860,
				height : 570,
				href : '${pageContext.request.contextPath}/pms/wcp/goToAddWCPlan.do',
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
								$.messager.show('提示', '请选择保养规则', 'info');
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
								del : '0',
								wheelCoverType : '0',//0 表示 新增
								metcId : rows.metcId//绑定的 项目大类(父节点，根据该节点可以查询出说有 保养项)
							};
							$.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/addWCPlan.do",pams,function(json){
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
	
	//编辑保养计划
	function goToEditWCPlan(){
		var dialog = parent.$.modalDialog({
			title : '设备保养计划编辑',
			width :  860,
			height : 570,
			href : '${pageContext.request.contextPath}/pms/equ/sbglPlan/goToEditWCPlan.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '修改',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						var rows = dialog.find('#editWcpLbgzGrid').datagrid('getSelected');
						if(rows==null||rows.metcId==null||rows.metcId==""){
							$.messager.show('提示', '请选择保养规则', 'info');
							return ;
						}
						var lunbaoGzId = f.form("getData").editWcpLbgzGridId;
						if(lunbaoGzId==null||lunbaoGzId==""){
							$.messager.show('提示', '请选择保养规则', 'info');
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
							maintenanceContent : f.form("getData").maintenanceContent,//备注
							del : '0',
							//wheelCoverType : '新建', 不修改状态
							metcId : rows.metcId//绑定的 项目大类(父节点，根据该节点可以查询出说有 保养项)
						};
						$.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/editWCPlan.do",pams,function(json){
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
	} 
	
	//审批
	function Auditing(statusId,obj){
		if(obj!=""&&dataGrid.datagrid('getSelected')!=null){
			var data={id:dataGrid.datagrid('getSelected').id,statusId:statusId,status:obj};

			var wheelCoverType =dataGrid.datagrid('getSelected').wheelCoverType;
			if(statusId==1&&(wheelCoverType==0)){//点击了审核 ，审批:新增和审核通过 的 才可以  审核
				$.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/auditing.do",data,function(json){
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
				$.post("${pageContext.request.contextPath}/pms/equ/sbglPlan/auditing.do",data,function(json){
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
			/* var isPass = false;
			if(statusId==1&&(wheelCoverType==0||wheelCoverType==1)){//审批:新增和审核通过 的 才可以  审核
				isPass = true;
			}else if(){//批准:审核通过后才可以 运行
				isPass = true;
			} */
			//$.messager.show('提示', obj+":未完成,请先审核后在批准", 'info');
			
			
		}
		
	}
	//获取维保详细
	function getParam(id){
		param.datagrid({
				url : "${pageContext.request.contextPath}/pms/wcpparam/queryParam.do?pid=" + id
		   });
	}
	
	//测试
	function ceshi(){
		$.post("${pageContext.request.contextPath}/pms/wcpparam/ceshi.do",function(){
		}); 
	}
	
	//设备管理员保养计划转交 
	function deliver(){
		var dialog = parent.$.modalDialog({
			title : '设备保养计划转交',
			width : 620,
			height : 330,
			href : '${pageContext.request.contextPath}/pms/wcp/goToCheckWCPlan.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '转交',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/wcp/examinePlanPass.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryWCPlan();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
    //删除设备保养计划
	function deleteWCPlan(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前设备保养计划？', function(b) {
			if (b) {	
				$.post('${pageContext.request.contextPath}/pms/wcp/deleteWCPlan.do', {
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
							<span class="label">保养名称：</span>
							<input type="text" name="planName" class="easyui-validatebox "  data-options="prompt: '请输入保养计划名称'"/>
						</div>
						<div >
							<span class="label">保养编号：</span>
							<input type="text" name="planNo" class="easyui-validatebox "  data-options="prompt: '请输入保养计划编号'"/>
						</div>
						<div >
							<span class="label">保养时间：</span>
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
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/queryWCPlan']}">
					<a onclick="queryWCPlan()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/addWCPlan.do']}">
					<a onclick="goToAddWCPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditing.do']}">
					<a onclick="Auditing('1','审批');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-drive-edit'">审核</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditingrun.do']}">
					<a onclick="Auditing('2','批准');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-application-go'">批准</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditingstop.do']}">
					<!-- <a onclick="Auditing('3','停止');" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-image-delete'">停止</a> -->
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/editWCPlan.do']}">
					<a onclick="goToEditWCPlan()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-report-edit'">编辑</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/deleteWCPlan.do']}">	
					<a onclick="deleteWCPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</a>
				</c:if>
				<!-- <a onclick="ceshi();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">测试</a> -->
			</div>
		</div>
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'',border:false">
		<!-- <div class="easyui-toolbar" >
			<a onclick="goToInputAddJsp()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
		</div> -->
		<table id="paramGrid"></table>
	</div>
	 <div id="menu" class="easyui-menu" style="width: 80px; display: none;">
	 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/addWCPlan.do']}">
			<div onclick="goToAddWCPlan();" href="javascript:void(0);"  data-options="iconCls:'icon-standard-plugin-add'">新增</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditing.do']}">
			<div onclick="Auditing('1','审批');" href="javascript:void(0);"  data-options="iconCls:'icon-standard-drive-edit'">审批</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditingrun.do']}">
			<div onclick="Auditing('2','批准');" href="javascript:void(0);"  data-options="iconCls:'icon-standard-application-go'">批准</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/auditingstop.do']}">
			<!-- <div onclick="Auditing('3','停止');" href="javascript:void(0);" data-options="iconCls:'icon-standard-image-delete'">停止</div> -->
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/editWCPlan.do']}">
			<div onclick="goToEditWCPlan()" href="javascript:void(0);"  data-options="iconCls:'icon-standard-report-edit'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/deleteWCPlan.do']}">
			<div onclick="deleteWCPlan();" href="javascript:void(0);"  data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>		
	</div>  
</body>
</html>