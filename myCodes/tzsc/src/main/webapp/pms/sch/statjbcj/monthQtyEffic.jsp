<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>卷包月绩效</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		var tempUrl ="${pageContext.request.contextPath}/pms/sch/statjbcj";
		var editRow = undefined;
		var passRateGrid=null;
		var bandParams = null;
		$(function() {
			var d = new Date();
			//d.setTime(d.getTime()-24*60*60*1000);
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		    $("#time").datebox("setValue",sts);	//
		    $("#time2").datebox("setValue",end);	//
			passRateGrid = $('#passRateGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				striped : true,
				remoteSort: false,	
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				frozenColumns:[[//固定表头equipmentName
						{title:'牌号', rowspan:3,field:'mat_name',width:120,align:'center'},//跨列
						{title:'工段', rowspan:3,field:'team_Name',width:80,align:'center'},//跨列
						{title:'定编机组', rowspan:3,field:'userEqpGroup',width:80,align:'center'},//跨列
						
						
				]],
				columns : [ 
				
							[ 	
								{title:'产量', rowspan:2,colspan:2,align:'center'},//跨列
								{title:'用料',colspan:16},
				               	
								{title:'单耗',colspan:16},
								
							],[
								
								
								{title:'残烟量',colspan:3},
				               	{title:'卷烟机',colspan:3},
								{title:'包装机',colspan:10},
								{title:'残烟量',colspan:3},
				               	{title:'卷烟机',colspan:3},
								{title:'包装机',colspan:10},
							],[ 
						
						{title:'卷烟',align:'center',rowspan:1,field : 'juQty'}, 
				        {title:'包装',align:'center',rowspan:1,field : 'bzQty'},
						
				        {title:'卷烟',align:'center',rowspan:1,field : 'qt_jy'}, 
				        {title:'包装',align:'center',rowspan:1,field : 'qt_bz'},
				        {title:'吹车',align:'center',rowspan:1,field : 'qt_cc'},
						
				        {title :'滤棒',align:'center',rowspan:1,field : 'sy_zj_lb',align:'center'},
						{title :'卷烟纸',align:'center',rowspan:1,field : 'sy_zj_pz',align:'center',},
						{title:'水松纸',align:'center',rowspan:1, field:'sy_zj_ssz'},
						
						{title:'商标纸',align:'center',rowspan:1, field:'sy_zb_xhz'},
						{title:'条盒',align:'center',rowspan:1, field:'sy_zb_thz'},
						{title:'铝箔纸',align:'center',rowspan:1,field:'sy_zb_lbz'},
						{title:'小透明',align:'center',rowspan:1,field:'sy_zb_xhm'},
						{title:'条透明',align:'center',rowspan:1,field:'sy_zb_thm'},
						{title:'卡纸',align:'center',rowspan:1,field:'sy_zb_kz'},
						{title:'封签',align:'center',rowspan:1,field:'sy_zb_fq'},
						{title:'拉线1',align:'center',rowspan:1,field:'sy_zb_lx1'},
						{title:'拉线2',align:'center',rowspan:1,field:'sy_zb_lx2'},
						{title:'备用',align:'center',rowspan:1,field:'xl_zb_lx2'},
						
						
						{title:'卷烟',align:'center',rowspan:1,field : 'zjDH',}, 
				        {title:'包装',align:'center',rowspan:1,field : 'zbDH',},
				        {title:'吹车',align:'center',rowspan:1,field : 'ccDH',},
				       
					    {title :'滤棒',align:'center',rowspan:1,field : 'lbDH',align:'center',sortable : true},
						{title :'卷烟纸',align:'center',rowspan:1,field : 'pzDH',align:'center',},
						{title:'水松纸',align:'center',rowspan:1, field:'sszDH'},
						
						{title:'商标纸',align:'center',rowspan:1,field:'xhzDH'},
						{title:'条盒',align:'center',rowspan:1,field:'thzDH'},
						{title:'铝箔纸',align:'center',rowspan:1,field:'lbzDH'},
						{title:'小透明',align:'center',rowspan:1,field:'xhmDH'},
						{title:'条透明',align:'center',rowspan:1,field:'thmDH'},
						{title:'卡纸',align:'center',rowspan:1,field:'kzDH'},
						{title:'封签',align:'center',rowspan:1,field:'fqDH'},
						{title:'拉线1',align:'center',rowspan:1,field:'lx1DH'},
						{title:'拉线2',align:'center',rowspan:1,field:'lx2DH'},
						{title:'备用',align:'center',rowspan:1, field:'xl_zb_lx2'},
				        
					]],
				toolbar : '#toolbar',
			});

			
		});
		
		
		
		
		
		
	   //查询
		function queryCheckInfo() {
			var date1=$("#time").datebox("getValue");
			var date2=$("#time2").datebox("getValue");
		    if(date1!=null&&date1!=""&&date1!=null&&date1!=""){
				passRateGrid.datagrid({
					url : tempUrl+"/queryMonthQtyData.do",
					queryParams :$("#passRateForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询异常", 'error');
					}
				});
			}else{
		    	$.messager.show('提示', "请选择具体时间段后进行查询！", 'info');
		    }
		}
		//重置
		function clearShapeForm(){
		   try{
			   $('#passRateForm input').val(null);
			} catch(e){
			}
	   }
		
		
	   //导出execl
	   function exportExcel(){
			var recordUrl =tempUrl+"/exportMonthQtyData.do?stim="+$("#passRateForm").form("getData").stim+"&etime="+$("#passRateForm").form("getData").etime;
			var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：   
			parent.window.open(recordUrl,"ExcelWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
	   }
	   
</script>
<body class="easyui-layout" data-options="fit : true,border : false">	
	
	<div data-options="region:'center',border:true,split:false">
		<table id="passRateGrid"></table>
	</div>
	<div id="toolbar" class="datagrid-toolbar"  style="display: none;width:100%;">
		<form id="passRateForm" style="margin:4px 0px 0px 0px;">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label" width="100px">日期：</span>
						 <input id="time" name="stim" type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" style="width:120px"/>
					</div>
					<div ><span class="label" width="100px">&nbsp;&nbsp;到</span>
						 <input id="time2" name="etime" type="text" class="easyui-datebox" readOnly=true datefmt="yyyy-MM-dd" style="width:120px"/>
					</div>		
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar" >
				<a onclick="queryCheckInfo()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="exportExcel();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">导出</a>
		</div>
	</div>
	
</body>
</html>