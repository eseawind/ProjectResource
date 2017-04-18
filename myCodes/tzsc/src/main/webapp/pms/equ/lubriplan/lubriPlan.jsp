<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备润滑计划管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		var param=null;
		$(function() {
			//初始化时间
		    var today = new Date();
			var month=today.getMonth()+1;
			if(month<10){month=("0"+month);}
		    var date=today.getFullYear()+"-"+month+"-01"; 
		    var day = new Date(today.getFullYear(),today.getMonth()+1,0);   
	        var lastdate = today.getFullYear() + '-' + month + '-' + day.getDate();//获取当月最后一天日期
		    $("#date1").datebox("setValue",date);	//月初 
		    $("#date2").datebox("setValue",lastdate);	//月末
			$.loadComboboxData($("#eqp_id"),"ALLEQPS",true);
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
				checkOnSelect : true,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : 'id',
					width : 120,
					checkbox : true
				},{
					field : 'code',
					title : '润滑编号',
					width : 100,
					align : 'center',
					sortable : true
				}, {
					field : 'eqp_name',
					title : '设备名称',
					align : 'center',
					width : 70,
					sortable : true
				},{
					field : 'shift_name',
					title : '班次',
					align : 'center',
					width : 70,
					sortable : true
				},{
					field : 'lub_id',
					title : '润滑方式',
					align : 'center',
					width : 70,
					sortable : true
				}, {
					field : 'date_plan',
					title : '计划执行日期',
					align : 'center',
					width : 140,
					sortable : true
				},{
					field : 'operatives_name',
					title : '确认人',
					align : 'center',
					width : 180,
					sortable : true
				},{
					field : 'lubricating_name',
					title : '操作人',
					align : 'center',
					width : 180,
					sortable : true
				},{
					field : 'etims',
					title : '结束时间',
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
					getParam(rowData.id);
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
					
				} ,{
					field : 'des',
					title : '数据描述',
					align:'center',
					width : 250,
					
				}, {
					field : 'oiltd',
					title : '润滑油',
					width : 150,
					align:'center'
				}, {
					field : 'methods',
					title : '润滑方式',
					width : 100,
					align:'center'
				}, {
					field : 'fill_quantity',
					title : '加注点、加注量',
					width : 100,
					align:'center'
				}, {
					field : 'fill_unit',
					title : '单位',
					width : 50,
					align:'center'
				}, {
					field : 'end_times',
					title : '完成时间',
					width : 160,
					align:'center'
				}, {
					field : 'end_user_name',
					title : '润滑人',
					width : 100,
					align:'center'
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
		function queryLubiPlan() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/lubrplan/queryLubrplan.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询润滑计划异常", 'error');
				}
			});
			$('#dataGrid').datagrid('clearSelections');
		}
		//新增设备润滑计划 
		function goToAddLubiPlan(){
			var addPlan = parent.$.modalDialog({
				title : '润滑计划添加',
				width : 660,
				height : 270,
				href : '${pageContext.request.contextPath}/pms/lubrplan/goToAddLubiPlan.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						parent.$.messager.progress({
							title:'',
							msg:'正在生成计划...',
							interval:'1000'
						});
						var f = addPlan.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/lubrplan/addEqmLubricatPlan.do",{
								eqp_category : f.form("getData").eqp_category,//设备类型
								eqp_typeId:f.form("getData").eqp_typeId,//设备机型
								eqp_typeName:f.form("getData").eqp_typeName,
								ruleId : f.form("getData").ruleId,//设备保养规则
								ruleName : f.form("getData").ruleName,//设备保养名称
								date_plan1 : f.form("getData").date_plan1,//开始时间
								date_plan2 : f.form("getData").date_plan2, //结束时间 
								lub_id:f.form("getData").lub_id, //润滑类型ID  1-日润滑  2-周期润滑   3-月润滑
								ruleIdf : f.form("getData").ruleIdf,//设备保养规则周期性
								ruleNamef : f.form("getData").ruleNamef,//设备保养名称周期性
								shift_id:f.form("getData").shift_id
						},function(json){
							parent.$.messager.progress('close');
								if (json.success) {
									$.messager.show('提示', '操作成功', 'info');
									addPlan.dialog('destroy');
									queryLubiPlan();
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
	function goToEditLubiPlan(){
		var row = dataGrid.datagrid('getSelected');
		if(row!=null){
			var dialog = parent.$.modalDialog({
				title : '设备润滑计划编辑',
				width : 660,
				height : 200,
				href : '${pageContext.request.contextPath}/pms/lubrplan/goToEditLubiPlan.do?id='+row.id,
				buttons : [ {
					text : '修改',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							var pams = {
									id : f.form("getData").id,//计划名称
									date_plan : f.form("getData").date_p,//计划名称
							};
							$.post("${pageContext.request.contextPath}/pms/lubrplan/editLubrplan.do",pams,function(json){
								if (json.success) {
									$.messager.show('提示', '操作成功', 'info');
									dialog.dialog('destroy');
									queryLubiPlan();
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
				url : "${pageContext.request.contextPath}/pms/lubrplanParam/queryDataGridByPlanId.do?plan_id="+id
		});
	}
	/**
	*批量删除
	*/
	function deleteCycle() {
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
					$.post('${pageContext.request.contextPath}/pms/lubrplan/deleteCycle.do', 
					{
						ids : ids.join(','),
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryLubiPlan();
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
	
	//重置
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
							<span class="label">设备名称：</span>
							<input type="text" id="eqp_id" name="eqp_id" class="easyui-validatebox" style="width:130px"  data-options="panelHeight:150,prompt: '请输入润滑计划名称'"/>
						</div>
						<div >
							<span class="label">润滑编号：</span>
							<input type="text" name="code" class="easyui-validatebox "  data-options="prompt: '请输入润滑计划编号'"/>
						</div>
						<div >
							<span class="label">润滑时间：</span>
							 <input id="date1" name="date_plan1" type="text" class="easyui-datebox" readOnly=true style="width:130px"/>
						</div>	
						<div >
							<span class="label" style="width:10px">到</span>
							<input id="date2" name="date_plan2" type="text" class="easyui-datebox" readOnly=true style="width:130px"/>
						</div>		
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrplan/queryLubrplan.do']}">
					<a onclick="queryLubiPlan()" href="javascript:void(0);"  class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				 <c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrplan/goToAddLubiPlan.do']}">
					<a onclick="goToAddLubiPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
				</c:if>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrplan/goToAddLubiPlan.do']}">
					<a onclick="goToAddLubiPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">（周）新增</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrplan/goToAddLubiPlan.do']}">
					<a onclick="goToAddLubiPlan();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">（月）新增</a>
				</c:if> --%>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrplan/goToEditLubiPlan.do']}">
			 		<a onclick="goToEditLubiPlan()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-report-edit'">编辑</a>
			 	</c:if>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrCycle/deleteCycle.do']}"> --%>
					<a onclick="deleteCycle();" href="javascript:void(0);" class="easyui-linkbutton"  data-options="plain:true,iconCls:'icon-standard-plugin-delete'">批量删除</a>
				<%-- </c:if> --%>
			</div> 
		</div>
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'',border:false">
		<table id="paramGrid"></table>
	</div>
	 <div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrplan/goToAddLubiPlan.do']}">
			<div onclick="goToAddLubiPlan();" href="javascript:void(0);"  data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/lubrplan/goToEditLubiPlan.do']}">
	 		<div onclick="goToEditLubiPlan()" href="javascript:void(0);" data-options="plain:true,iconCls:'icon-standard-report-edit'">编辑</div>
	 	</c:if>
	 	
	</div>  
</body>
</html>