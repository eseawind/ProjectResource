<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备点检计划查询</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		var param=null;
		$(function() {
			 $.loadComboboxData($("#mdEqpCategory"),"EQPCATEGORY",false);
			//初始化时间
		    var today = new Date();
			var enddate=today.getDate();
			//var startDate=enddate-6;
			var date=today.getFullYear()+"-"+(today.getMonth()+1)+"-"+01; 
			var lastdate = today.getFullYear()+"-"+(today.getMonth()+1)+"-"+enddate; 
		    $("#date1").datebox("setValue",date);	//月初 
		    $("#date2").datebox("setValue",lastdate);	//月末 
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : true,
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
				checkOnSelect : true,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'id',
					title : '设备保养计划ID',
					width : 140,
					checkbox : true
				}, {
					field : 'mdEqpCategory',
					title : '设备类型',
					align : 'center',
					width : 140,
					sortable : true
				},{
					field : 'shift',
					title : '班次',
					align:'center',
					width : 140,
					sortable : true
				},{
					field : 'team',
					title : '班组',
					align:'center',
					width : 140,
					sortable : true
				},{
					field : 'dateP',
					title : '计划日期',
					align : 'center',
					width : 140,
					sortable : true
				},{
					field : 'status',
					title : '状态',
					align:'center',
					width : 140,
					sortable : true,
					formatter : function(value, row, index) {
						var type = value;
						if(type=='0'){
							type="下发";
						}else if(type=='1'){//写死
							type="完成";
						}else if(type=='2'){
							type="未完成";
						}
						return type;
					}
				} ] ],
				toolbar : '#toolbar',
				onClickRow:function(rowIndex,rowData){
					getParam(rowData.id);
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
				sortName : 'eqpName',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'eqpName',
					title : '设备名',
					width : 150,
					align : 'center',
					sortable : true
				}, {
					field : 'checkType',
					title : '点检类型',
					width : 150,
					align : 'center',
					formatter : function(value, row, index) {//写死 
						if(value=='402899894db72650014db78d4035004f'){
							return '<span >机械维修工点检<span>'
						}else if(value=='8af2d49050d2002d0150da342dfb05b3'){
							return '<span>电气维修工点检<span>'
						}else{
							return '<span>操作工点检<span>'
						}
						//return value=='402899894db72650014db78d4035004f'?'<span >维修工点检<span>':'<span>操作工点检<span>';
					}
				}, {
					field : 'checkPosition',
					title : '点检关键部位',
					width : 150,
					align : 'center',
				}, {
					field : 'checkStandard',
					title : '点检标准',
					align : 'center',
					width : 150,
				}
				, {
					field : 'checkMethod',
					title : '点检方式',
					align : 'center',
					width : 150,
				}/* , 
				{
					field : 'status',
					title : '是否完成',
					align : 'center',
					width : 150,
					formatter : function(value, row, index) {//写死 
						return value==1?'<span style="color:green">已完成<span>':value==2?'<span style="color:yellow">有故障<span>':'<span style="color:red">未完成<span>';
					}
				} */ ] ],
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
			}); 
		});
		// 查询设备点检计划
		function queryEqmCheckPlan() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/eqmcheck/queryEqmCheckPlan.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询点检计划异常", 'error');
				}
			});
			$('#dataGrid').datagrid('clearSelections');
		} 
	//获取点检详细(下方的详细)  现在没有修改  链接是错误的
	function getParam(id){
		param.datagrid({
				url : "${pageContext.request.contextPath}/pms/eqmcheck/queryEqmCheckPlanParam.do?pid=" + id
		   });
	}
	
	//批量删除
	function batchDeleteEqmCheckPlan() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中得数据', function(r) {
				if (r) {
					var flag = false;
					for ( var i = 0; i < rows.length; i++) {
							ids.push(rows[i].id);
					}
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/eqmcheck/batchDeleteEqmCheckPlan.do', 
					{
						ids : ids.join(','),
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryEqmCheckPlan();
							getParam("")
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
    //清空查询条件
	function clearForm(){
		$("#searchForm input").val(null);
	}
	//批量添加点检计划
	function batchAddPlan(){
		var addPlan = parent.$.modalDialog({
			title : '点检计划添加',
			width : 660,
			height : 270,
			href : '${pageContext.request.contextPath}/pms/equ/eqp_check/add_wcplan.jsp',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = addPlan.find("#form");
					if(f.form("validate")){
						parent.$.messager.progress({
							title:'',
							msg:'正在生成点检计划...',
							interval:'1000'
						}); 
						$.post("${pageContext.request.contextPath}/pms/eqmcheck/batchEqmCheckPlan.do",{
							mdEqpCategory : f.form("getData").mdEqpCategory,//设备类型
							dicID : f.form("getData").dicID,//设备点检规则
							date_plan1 : f.form("getData").date_plan1,//开始时间
							date_plan2 : f.form("getData").date_plan2, //结束时间 
							shift : f.form("getData").shift, //班次 
							team : f.form("getData").team,//班次 
							workshop : f.form("getData").workshop
					},function(json){
							if (json.success) {
								parent.$.messager.progress('close');
								$.messager.show('提示', '操作成功', 'info');
								addPlan.dialog('destroy');
								queryEqmCheckPlan();
							}else{
								$.messager.show('提示', "保存失败", 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
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
						<span class="label">设备类型：</span>
						<input id="mdEqpCategory" name="mdEqpCategory" class="easyui-combobox" data-options="panelHeight:'auto',width:120,editable:false,prompt:'请选择设备类型'"/>
					</div>
					<div >
						<span class="label">计划时间：</span>
						<input id="date1" name="date_plan1"  type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" data-options="width:130"/>
					</div>
					<div >
						<span class="label" style="width:10px">到</span>
						<input id="date2" name="date_plan2"  type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" data-options="width:130"/>
					</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/queryWCPlan']}">
					<a onclick="queryEqmCheckPlan()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<a onclick="batchAddPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">批量新增</a> 
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/wcp/deleteWCPlan.do']}">	
					<a onclick="batchDeleteEqmCheckPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</a>
				</c:if>
			</div>
		</div>
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'',border:false">
		<table id="paramGrid"></table>
	</div>
	<!-- 右键菜单 -->
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="batchDeleteEqmCheckPlan();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>