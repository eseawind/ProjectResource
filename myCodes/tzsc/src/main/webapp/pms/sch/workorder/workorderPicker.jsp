 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">	
	var workorderPickGrid=null;
	var id;
	$(function() {
		
		$.loadComboboxData($("#workorderPick-shift"),"SHIFT",true);
		$.loadComboboxData($("#workorderPick-team"),"TEAM",true);
		
		workorderPickGrid = $('#workorderPickGrid').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			pagination : true,
			idField : 'id',
			striped : true,
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'asc',
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			showPageList:false,
			columns : [ [{
				field : 'id',
				title : '编号',
				hidden : true
			}, {
				field : 'code',
				title : '工单号',
				width : 100,
				align:'center',
				sortable : true
			}, {
				field : 'type',
				title : '类型',
				width : 80,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					var type="";
					switch(value){
						case 1:
							type = "卷烟机工单";
							break;
						case 2:
							type = "包装机工单";
							break;
						case 3:
							type = "封箱机工单";
							break;
						case 4:
							type = "成型机工单";
							break;
					}
					return type;
				}
			}, {
				field : 'mat',
				title : '牌号',
				width : 90,
				align:'center',
				sortable : true
			}, {
				field : 'equipment',
				title : '设备',
				width : 90,
				align:'center',
				sortable : true
			}, {
				field : 'equipmentId',
				title : '设备',
				width : 90,
				align:'center',
				hidden : true
			}, {
				field : 'date',
				title : '生产日期',
				width : 80,
				align:'center',
				sortable : true
			}
			, {
				field : 'shift',
				title : '班次',
				width : 40,
				align:'center',
				sortable : true
			}, {
				field : 'team',
				title : '班组',
				width : 40,
				align:'center',
				sortable : true
			}, {
				field : 'shiftId',
				title : '班次',
				width : 40,
				align:'center',
				hidden : true
			}, {
				field : 'teamId',
				title : '班组',
				width : 40,
				align:'center',
				hidden : true
			},{
				field : 'qty',
				title : '计划产量',
				width : 60
			},{
				field : 'unit',
				title : '单位',
				width : 40,
			}, {
				field : 'unitId',
				title : '产量单位ID',
				hidden : true
			}, {
				field : 'stim',
				title : '计划开始时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'etim',
				title : '计划结束时间',
				width : 130,
				align:'center',
				sortable : true
			}, {
				field : 'sts',
				title : '状态',
				width : 60,
				align:'center',
				sortable : true,
				formatter:function(value,row,index){
					var type="";
					switch(value){
						case 1:
							type = "下发";
							break;
						case 2:
							type = "运行";
							break;
						case 3:
							type = "暂停";
							break;
						case 4:
							type = "中止";
							break;
						case 5:
							type = "完成";
							break;
						case 6:
							type = "结束";
							break;
					}
					return type;
				}
			}
			 ] ],
			toolbar : '#workorderPickToolbar',
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
	* 查询工单
	*/	
	function getAllWorkOrder() {
		workorderPickGrid.datagrid({
					url : "${pageContext.request.contextPath}/pms/workorder/getAllWorkOrders.do",
					queryParams :$("#workorderPickForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询工单异常", 'error');
					}
				});
		
	}
	/**
	*清除查询条件
	*/
	function clearWorkorderPickForm() {
		$("#workorderPickForm input[name!='workshop']").val(null);
	}
</script>
</head>
<div class="easyui-layout" data-options="fit : true,border : false">
<div id="workorderPickToolbar" style="display: none;">
<form id="workorderPickForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<!-- <div >
						<span class="label">工单号：</span>
						<input name="code" class="easyui-validatebox " data-options="prompt: '支持模糊查询'"/>
					</div> -->
					<div >
						<span class="label">生产日期：</span>
						<input name="date" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>
					</div>
					<div >
						<span class="label">班次：</span>
						<select id="workorderPick-shift" name="shift" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">班组：</span>
						<select id="workorderPick-team" name="team" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
				
					<!-- <div >
						<span class="label">运行状态：</span>
						<select name="sts"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:80">
								1,下发2,运行3,暂停4,完成,5,终止,6结束
								<option value="">全部</option>
							    <option value="1">下发</option>
								<option value="2">运行</option>
								<option value="3">暂停</option>
								<option value="4">完成</option>							
								<option value="5">终止</option>							
								<option value="6">结束</option>							
						</select>
					</div> -->
					<input type="hidden" name="workshop" value="${workshop}"/><!-- 车间code -->
				</fieldset>
			</div>
		</form>

		<div class="easyui-toolbar">
				<a onclick="getAllWorkOrder();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a>
				<a onclick="clearWorkorderPickForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">重置</a>
		</div>
</div>
<div data-options="region:'center',border:false">
	<table id="workorderPickGrid"></table>
</div>		
</div>