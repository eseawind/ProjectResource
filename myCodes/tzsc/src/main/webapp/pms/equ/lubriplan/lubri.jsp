<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备润滑周期管理</title>
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
				columns : [ [ {
					field : 'id',
					title : '设备润滑计划ID',
					width : 120
					,hidden : true
				},{
					field : 'name',
					title : '计划名称',
					width : 100,
					align : 'center',
					sortable : true
				}, {
					field : 'eqp_typeName',
					title : '设备类型',
					align : 'center',
					width : 70,
					sortable : true
				}, {
					field : 'ruleName',
					title : '保养名称',
					align : 'center',
					width : 200,
					sortable : true
				},{
					field : 'cycle',
					title : '周期',
					align : 'center',
					width : 80,
					sortable : true
				},{
					field : 'create_uName',
					title : '创建人',
					align : 'center',
					width : 80,
					sortable : true
				},{
					field : 'create_time',
					title : '创建时间',
					align:'center',
					width : 180,
					sortable : true
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
					getParam(rowData.ruleId);
				}
			});
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
				sortName : 'code',
				sortOrder : 'asc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : '二级ID',
					width : 120,
					hidden : true
				}, {
					field : 'code',
					title : '编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'name',
					title : '名称',
					width : 240,
					align:'center',
					
				}, {
					field : 'oilName',
					title : '润滑油',
					width : 150,
					align:'center'
				}, {
					field : 'fashionName',
					title : '润滑方式',
					width : 100,
					align:'center'
				}, {
					field : 'fillUnitName',
					title : '单位',
					width : 100,
					align:'center',
					hidden:true
				}, {
					field : 'fillQuantity',
					title : '加注点、加注量',
					width : 100,
					align:'center',
					hidden:true
				}, {
					field : 'des',
					title : '数据描述',
					align:'center',
					width : 250,
					
				}, {
					field : 'enable',
					title : '是否启用',
					align:'center',
					width : 70,
					sortable : false,
					formatter : function(value, row, index) {
						return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
					}
				}] ],
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
		
		// 查询设备润滑计划
		function queryLubrCycle() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/lubrCycle/queryLubrCycle.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询润滑计划异常", 'error');
				}
			});
		}
		//新增设备润滑计划 
		function goToAddLubiCycle(){
			var addPlan = parent.$.modalDialog({
				title : '润滑计划添加',
				width : 660,
				height : 270,
				href : '${pageContext.request.contextPath}/pms/lubrCycle/goToAddLubiCycle.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = addPlan.find("#form");
						if(f.form("validate")){
							var pams = {
									name : f.form("getData").name,//计划名称
									eqp_category : f.form("getData").eqp_category,//设备类型
									eqp_typeId:f.form("getData").eqp_typeId,//设备机型
									eqp_typeName:f.form("getData").eqp_typeName,
									ruleId : f.form("getData").ruleId,//设备保养规则
									ruleName : f.form("getData").ruleName,//设备保养名称
									cycle : f.form("getData").cycle	//保养周期
							};
							$.post("${pageContext.request.contextPath}/pms/lubrCycle/addLubrCycle.do",pams,function(json){
								if (json.success) {
									$.messager.show('提示', '操作成功', 'info');
									addPlan.dialog('destroy');
									queryLubrCycle();
								}else{
									$.messager.show('提示', "保存失败", 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
	
	//编辑润滑计划
	function goToEditLubiCycle(){
		var row = dataGrid.datagrid('getSelected');
		if(row!=null){
			var dialog = parent.$.modalDialog({
				title : '设备润滑计划编辑',
				width : 660,
				height : 270,
				href : '${pageContext.request.contextPath}/pms/lubrCycle/goToEditLubiCycle.do?id='+row.id,
				buttons : [ {
					text : '修改',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							var pams = {
									id : f.form("getData").id,//计划名称
									name : f.form("getData").name,//计划名称
									eqp_category : f.form("getData").eqp_type_ids,//设备类型
									eqp_typeId:f.form("getData").eqp_typeId,//设备机型
									eqp_typeName:f.form("getData").eqp_typeName,
									ruleId : f.form("getData").ruleId,//设备保养规则
									ruleName : f.form("getData").ruleName,//设备保养名称
									cycle : f.form("getData").cycle	//保养周期
							};
							$.post("${pageContext.request.contextPath}/pms/lubrCycle/editLubrCycle.do",pams,function(json){
								if (json.success) {
									$.messager.show('提示', '操作成功', 'info');
									dialog.dialog('destroy');
									queryLubrCycle();
								}else{
									$.messager.show('提示', "保存失败", 'error');
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
	
	
	//获取维保详细
	function getParam(id){
		param.datagrid({
				url : "${pageContext.request.contextPath}/pms/sys/eqptype/queryMdType.do?categoryId=" + id
		   });
	}
	
    //删除设备润滑计划
	function deleteCycle(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前设备润滑计划？', function(b) {
			if (b) {	
				$.post('${pageContext.request.contextPath}/pms/equ/rhPlan/deletePlan.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryLubiPlan();
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
							<span class="label">润滑名称：</span>
							<input type="text" name="planName" class="easyui-validatebox "  data-options="prompt: '请输入润滑计划名称'"/>
						</div>
						<div >
							<span class="label">润滑编号：</span>
							<input type="text" name="planNo" class="easyui-validatebox "  data-options="prompt: '请输入润滑计划编号'"/>
						</div>
						<div >
							<span class="label">润滑时间：</span>
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
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrCycle/queryLubrCycle.do']}">
					<a onclick="queryLubrCycle()" href="javascript:void(0);"  class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrCycle/resetLubri.do']}">
					<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrCycle/goToAddLubiCycle.do']}">
					<a onclick="goToAddLubiCycle();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrCycle/goToEditLubiCycle.do']}">
			 		<a onclick="goToEditLubiCycle()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-report-edit'">编辑</a>
			 	</c:if>
			 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrCycle/deleteCycle.do']}">
					<a onclick="deleteCycle();" href="javascript:void(0);" class="easyui-linkbutton"  data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</a>
				</c:if>
			</div>
		</div>
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'',border:false">
		<table id="paramGrid"></table>
	</div>
	 <div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrCycle/goToAddLubiCycle.do']}">
			<div onclick="goToAddLubiCycle();" href="javascript:void(0);"  data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrCycle/goToEditLubiCycle.do']}">
	 		<div onclick="goToEditLubiCycle()" href="javascript:void(0);" data-options="plain:true,iconCls:'icon-standard-report-edit'">编辑</div>
	 	</c:if>
	 	
	</div>  
</body>
</html>