<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备运行效率</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
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
			$.loadComboboxData($("#eqpType"),"EQPTYPE",true);
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,//fitColumns= true就会自动适应宽度（无滚动条）
				//width:$(this).width()-252,  
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				remoteSort: false,
				pageSize : 10,
				pageList : [10, 20, 60, 80, 100],
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'area',
					title : '车间',
					width : 80,
					align:'center'
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
					field : 'date',
					title : '生产日期',
					align:'center',
					width : 120,
					sortable : true,
					hidden:true
				},{
					field : 'shift',
					title : '班次',
					align:'center',
					width : 80,
					sortable : true,
				},{
					field : 'team',
					title : '班组',
					align:'center',
					width : 80,
					sortable : true,
					hidden:true
				},{
					field : 'runTime',
					title : '运行时间',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'downTime',
					title : '停机时间',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'excludTime',
					title : '总剔除时间',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'timeUnit',
					title : '时间单位',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'effectiveRunTime',
					title : '运行效率（%）',
					align:'center',
					width : 80,
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
		* 查询设备故障
		*/
		function queryTrouble() {
			dataGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/effect/queryRunTimeEffect.do",
				queryParams :$("#searchForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
			var f = $('#searchForm');
			$.post("${pageContext.request.contextPath}/pms/effect/queryRunTimeEffectChart.do",f.form("getData"),function(obj){
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
	                text: '设备有效作业率'
	            },
	            xAxis: {
	                categories: xvalue,
					labels: {
		                    rotation: -45,
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
	                max:100,
	           		title: {
	                    text: '有效作业率(%)'
	                }
	            },
	            tooltip: {
	                pointFormat: '{point.name}<b>{point.y:.1f} %有效作业率</b>',
	            },
	            series:series
	        });
	    }
	function derive(){
		var recordUrl ="${pageContext.request.contextPath}/pms/effect/deriveEqpRunEfficExcel.do?eqpName="+$("#searchForm").form("getData").eqpName+"&eqpType="+$("#searchForm").form("getData").eqpType+"&startTime="+$("#searchForm").form("getData").startTime+"&endTime="+$("#searchForm").form("getData").endTime;
	   	var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
	   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
		//toolbar=no   是否显示工具栏，yes为显示；menubar，scrollbars   表示菜单栏和滚动栏。resizable=no   是否允许改变窗口大小，yes为允许；location=no   是否显示地址栏，yes为允许；  status=no   是否显示状态栏内的信息（通常是文件已经打开），yes为允许；   
		parent.window.open(recordUrl,"ExcelWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
	}	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">设备名称：</span>
						<input type="text" name="eqpName" class="easyui-validatebox "  data-options="prompt: '请输入设备名称'"/>
					</div>
					<div >
						<span class="label">设备型号：</span>
						<select id="eqpType" name="eqpType" class="easyui-combobox" data-options="panelHeight:200,width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">生产日期：</span>
						 <input id="date1" name="startTime" type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" style="width:130px"/>
					</div>	
					
					<div >
						<span class="label" style="width:10px">到</span>
						<input id="date2" name="endTime" type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" style="width:130px"/>					 
					</div>	
					
								
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
				<a onclick="queryTrouble()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<a onclick="derive();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">导出</a>
		</div>
	</div>
	<div data-options="region:'north',border:true,split:true" style="height: 300px;width:100%">
		<table id="dataGrid"></table>
	</div>
	<div data-options="region:'center',border:true,split:true,title:'运行效率统计'" style="height: 200px;width:100%">
		<div id="container1" style="height: 200px;width:100%"></div>
		<!-- <div id="container2" style="height: 50%;width:100%"></div> -->
	</div>
</body>
</html>