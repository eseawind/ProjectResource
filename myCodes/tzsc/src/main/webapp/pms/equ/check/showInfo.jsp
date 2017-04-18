<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	var dataGrid2 = null;
	$(function() {
		dataGrid2 = $('#dataGrid2').datagrid({
			fit : true,
			fitColumns : false,
			border : false,
			nowrap : true,
			columns : [ [ {
				field : 'eqmId',
				title : '设备id',
				hidden:true
			}, {
				field : 'eqpName',
				title : '设备名称',
				width:120,
				align:'center'
			}, {
				field : 'shiftName',
				title : '班次',
				width:60,
				align:'center'
			}, {
				field:'teamName',
				title:'班组',
				width:60,
				align:'center'
			},{
				field:'chLocation',
				title:'检查部位',
				align:'center',
				width:100
			},{
				field : 'chStatus',
				title : '点检状态',
				width:100,
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
			} ,{
				field : 'chDate',
				title : '点检日期',
				align:'center',
				width:100
			}, {
				field : 'createUserName',
				title : '点检人',
				align:'center',
				width:70
			}, {
				field : 'chDate',
				title : '点检完成时间',
				align : 'center',
				width:100

			},{
				field:'remark',
				title:'备注',
				align:'center',
				width:120
			}] ],
		});
	});
	// 查询设备点检计划
	function queryWCPlan() {
		var id="${param.id}";
		dataGrid2.datagrid({
			url : '${pageContext.request.contextPath}/pms/equc/check/queryInfoById.do?id='+id,
			onLoadError : function(data) {
				$.messager.show('提示', "查询点检计划异常", 'error');
			}
		});
	}
	queryWCPlan();
	function clearForm() {
		$("#searchForm input").val(null);
	}
</script>
	<div data-options="region:'center',border:false,split:true,title:'产出数据'" style="height:100%;">
		<table id="dataGrid2"></table>
	</div>