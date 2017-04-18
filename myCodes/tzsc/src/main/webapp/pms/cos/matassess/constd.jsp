<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	var constdGrid;
	$(function() {
		constdsGrid = $('#constdsGrid').datagrid({
			rownumbers :true,
			idField : 'id',
			fit : true,
			striped : true,
			//pagination : true,//不需要分页
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
				field : 'matProd',
				title : '产品',
				width : 140,
				sortable : true
			} , {
				field : 'matName',
				title : '辅料',
				width : 160,
				sortable : true
			}, {
				field : 'val',
				title : '标准值',
				align : 'right',
				width : 60,
				sortable : true
			}, {
				field : 'uval',
				title : '超标上限',
				align : 'right',
				width : 60,
				sortable : true
			}, {
				field : 'lval',
				title : '超标下限',
				align : 'right',
				width : 60,
				sortable : true
			}, {
				field : 'euval',
				title : '报警上限',
				align : 'right',
				width : 60,
				sortable : true
			}, {
				field : 'elval',
				title : '报警下限',
				align : 'right',
				width : 60,
				sortable : true
			}, {
				field : 'des',
				title : '说明',
				width : 150,
				sortable : true
			}  ] ],
			toolbar : '#constdToolbar',
			onLoadSuccess : function() {
				//$(this).datagrid('tooltip');
			}
		});
	});
	/**
	*查询标准单耗
	*/
	function getAllConStds(){
		constdsGrid.datagrid({
			url : "${pageContext.request.contextPath}/pms/constd/getAllConStds.do?matProd=${param.prods}"
			//queryParams :$("#constdSeachForm").form("getData")
		});
	}
	getAllConStds();
</script>

<div class="easyui-layout" data-options="fit : true,border : false">


<div data-options="region:'center',border:false">
	<table id="constdsGrid"></table>
</div>
</div>
	