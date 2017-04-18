<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备运行统计</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			//初始化时间
		    var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		    $("#date1").my97("setValue",sts);//
		    $("#date2").my97("setValue",end);//
			$.loadComboboxData($("#equType"),"EQPTYPE",true);
			$.loadComboboxData($("#shiftName"),"SHIFT",true);
			$.loadComboboxData($("#teamName"),"TEAM",true);
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				rownumbers :true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				frozenColumns : [ [ {
					field : 'id',
					title : '设备运行id',
					width : 120,
					hidden : true
				}, {
					field : 'time',
					title : '设备运行日期',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'eqpName',
					title : '设备名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'eqpType',
					title : '设备型号',
					align:'center',
					width : 70,
					sortable : true	
				},{
					field : 'shiftName',
					title : '运行班次',
					align:'center',
					width : 120,
					sortable : true
				},{
					field : 'teamName',
					title : '运行班组',
					align:'center',
					width : 120,
					sortable : true
				},{
					field : 'runTime',
					title : '运行时间(分)',
					align:'center',
					width : 120,
					sortable : true
				} ] ],
				toolbar : '#toolbar',
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
				}
			});
		});
		/*
		* 查询设备运行统计
		*/
		function queryTrouble() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/eqpRun/queryEqpRunTime.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', " 查询设备运行统计异常", 'error');
				}
			});
			$.post("${pageContext.request.contextPath}/pms/eqpRun/queryEqpRunTimeChart.do",$("#searchForm").form("getData"),function(obj){
				var xvalue=obj.xvalue;
				var yvalue=obj.yvalue;
				var yvalueType=obj.yvalueType;
				var series=new Array();;
				for(var i=0;i<yvalueType.length;i++){
					//动态创建柱形图个数
					var seriess={name:yvalueType[i],data:yvalue[i]};
					series[i]=seriess;
				}
				loadView(xvalue,series);
			},"JSON"); 
		}
		function clearForm(){
			$("#searchForm input").val(null);
			$("#equType").combobox("setValue", "");//下拉框赋值
		}
		
		var loadView=function (xvalue,series) {
	        $('#container1').highcharts({
				credits: {
	            	 text: 'lanbaoit',
	            	 href: 'http://www.shlanbao.cn'
	        		 },
	        		 exporting: {
	     	            enabled: false
	     	        },
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: '设备运行统计'
	            },
	            xAxis: {
	                categories: xvalue,
					labels: {
		                    rotation: -40,
		                    align: 'right',
		                    color: 'black',
		                    style: {
		                        fontSize: '12px',
		                        fontFamily: 'Verdana, sans-serif'
		                    }
		                }
	            },
	            credits: {
	                enabled: false
	            },
				yAxis: {
	                min: 0,
	                max:500,
	           		title: {
	                    text: '运行时间(分)'
	                }
	            },
	            tooltip: {
	                pointFormat: '{point.name}<b>{point.y:.1f} 设备运行时间</b>',
	            },
	            series:series
	        });
	    }
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div>
						<span class="label" style="width:65px">设备名称：</span>
						<input id="eqpName" name="eqpName" type="text" class="easyui-validatebox "  data-options="prompt: '请输入设备名称'"/>
					</div>
					<div style="width:200px">
						<span class="label" style="width:65px">设备型号：</span>
						<select id="equType" name="equType" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div style="width:140px">
						<span class="label" style="width:40px">班次：</span>
						<select id="shiftName" name="shiftName" class="easyui-combobox" data-options="panelHeight:'auto',width:70,editable:false"></select>
					</div>
					<div style="width:140px">
						<span Style="display:inline" style="width:40px">班组：</span>
						<select id="teamName" name="teamName" class="easyui-combobox" data-options="panelHeight:'auto',width:70,editable:false"></select>
					</div>
					<div>
						<span Style="display:inline" style="width:65px">运行时间：</span>
						 <input id="date1" name="runDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>
					</div>	
					<div>
						<span Style="display:inline" style="width:10px">到</span>
						<input id="date2" name="runDate2" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>					 
					</div>	
					
								
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
				<a onclick="queryTrouble()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
		</div>
	</div>
	<div data-options="region:'north',border:true,split:true" style="height: 300px;width:100%">
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'center',border:true,split:true,title:'设备运行统计'" style="height: 200px;width:100%">
		<div id="container1" style="height: 250px;width:100%"></div>
		<!-- <div id="container2" style="height: 50%;width:100%"></div> -->
	</div>
	<!-- <div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="editEqu()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		<div onclick="deleteEqu();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div> -->
</body>
</html>