<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <script type="text/javascript">
	var matPickGrid;
	$(function() {
		$.loadComboboxData($("#matType"),"MATTYPE",true);
		matPickGrid = $('#matPickGrid').datagrid({
			rownumbers :true,
			idField : 'id',
			fit : true,
			striped : true,
			pagination : true,
			pageSize : 10,
			pageList : [10, 20, 30, 40, 50 ],
			sortName : 'code',
			sortOrder : 'asc',
			singleSelect :true,
			fitColumns : false,
			border : false,
			striped : true,
			nowrap : true,
			singleSelect:true,
			checkOnSelect : false,
			selectOnCheck : false,
			columns : [ [ {
				title : '编号',
				field : 'id',
				hidden : true
			}, {
				field : 'code',
				title : '编码',
				width : 80,
				sortable:true
			} , {
				field : 'name',
				title : '物料全称',
				width : 230,
				sortable:true
			}, {
				field : 'unit',
				title : '单位',
				align : "center",
				width : 70
			}, {
				field : 'unitId',
				title : '单位ID',
				align : "center",
				hidden: true
			}, {
				field : 'matType',
				title : '类型',
				align : "center",
				width : 70
			}  ] ],
			toolbar : '#matPickToolbar',
			onLoadSuccess : function() {
				$(this).datagrid('tooltip');
			},
		});
	});
	/**
	*查询物料
	*/
	function getAllMatsForPick(){
		matPickGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/mat/getAllMats.do",
			queryParams : $("#pickMatForm").form("getData")
		});
	}
	function clearPickMatForm(){
		$("#pickMatForm input[name!='enable']").val(null);
	}
	
	
</script>
</head>
<div class="easyui-layout" data-options="fit : true,border : false">
<div id="matPickToolbar" style="display: none;">
			<div class="topTool">
				<form id="pickMatForm">
				<fieldset >
					<div >
						<!-- del -->
						<input type="hidden" name="enable" value="1"/>
						<span class="label">物料编码：</span>
						<input type="text" name="code" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:100"/>
					</div>
					<div >
						<span class="label">物料名称：</span>
						<input type="text" name="name" class="easyui-validatebox "  data-options="prompt: '支持模糊查询',width:100"/>
					</div>
					<div >
						<span class="label">物料类型：</span>
						<select  id="matType" name="matType" data-options="panelHeight:200,width:100,editable:false"></select>
					</div>
				</fieldset>
				</form>
			</div>		
			<div class="easyui-toolbar">
				<a onclick="getAllMatsForPick();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearPickMatForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			</div>
</div>

<div data-options="region:'center',border:false">
	<table id="matPickGrid"></table>
</div>
</div>