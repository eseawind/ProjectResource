<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>卷包生产统计</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		var tempUrl ="${pageContext.request.contextPath}/pms/sch/statjbcj";
 	   
		var passRateGrid=null;
		var bandParams = null;
		$(function() {
			var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		    $("#startTime").my97("setValue",sts);//
		    $("#endTime").my97("setValue",end);	//

			//初始化
			$.loadComboboxData($("#SHIFT"),"SHIFT",true);//加载下拉框数据
			$.loadComboboxData($("#TEAM"),"TEAM",true);//加载下拉框数据
			$.loadComboboxData($("#matProd1"),"MATPROD",true);//牌号下拉数据
			//$.loadComboboxData($("#modelType"),"EQPTYPE",true);//设备类型
			/* $.loadComboboxData($("#eqpTypeName"),"EQPTYPE",true);//设备型号 */
			$.loadComboboxData($("#equipment"),"ALLEQPS",true);
			
			passRateGrid = $('#passRateGrid').datagrid({
				fit : true,
				fitColumns : false,
				//fitColumns= true,
				//width:$(this).width()-252,  
				border : false,
				//pagination : true,
				//idField : 'id',
				striped : true,
				remoteSort: false,	
				sortName : 'date',
				sortOrder : 'asc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				frozenColumns:[[//固定表头equipmentName
						{field : 'id',title : '',width : 120,hidden : true}, 
						{title:'机台', rowspan:3,field:'equipmentName',width:60,align:'center'},//跨列
						{title:'班次', rowspan:3,field:'mdShiftName',width:60,align:'center'},//跨列
						{title:'班组', rowspan:3,field:'mdTeamName',width:60,align:'center'},//跨列
						{title:'牌号', rowspan:3,field:'mdMatName',width:120,align:'center'},//跨列
						{title:'工单运行时间', rowspan:3,field:'date',width:120,align:'center'},//跨列
						
				]],
				columns : [  [ 
								
								{title:'卷烟机组',colspan:8},
								
								{title:'包装机组',colspan:12},
							],[ 
						{title :'实际产量(箱)',rowspan:2,field : 'qty',
							formatter: function(value,row,index){
								return Math.round(value*100)/100;
							},
							width :$(this).width() * 0.09,align:'center',sortable : true},
						{title :'剔除(箱)',rowspan:2,field : 'badQty',
							formatter: function(value,row,index){
								return Math.round(value*100)/100;
						},width :$(this).width() * 0.07,align:'center',sortable : true},
						{title:'滤棒',colspan:2},
						{title:'滤棒盘纸',colspan:2},
						{title:'水松纸',colspan:2},
						{title :'实际产量（箱）',rowspan:2,field : 'qtyBaoZhuang',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title :'剔除（箱）',rowspan:2,field : 'badQtyBaoZhuang',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title:'小盒',colspan:2},
						{title:'小透明纸',colspan:2},
						{title:'内衬纸',colspan:2},
						{title:'条盒 ',colspan:2},
						{title:'大透明纸',colspan:2},
						
						
						
					],[
						{title :'消耗（支）',rowspan:1,field : 'qty1',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗（支/箱）',rowspan:1,field : 'danhao1',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title :'消耗（米）',rowspan:1,field : 'qty2',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗（米/箱）',rowspan:1,field : 'danhao2',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '消耗（KG）',formatter:function(value,row,index){return Math.round(value*100)/100},rowspan:1,field : 'qty3',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗（KG/箱）',formatter: function(value,row,index){
							return Math.round(value*100)/100;
						},rowspan:1,field : 'danhao3',width :$(this).width() * 0.07,align:'center',sortable : true},
						
						
						
						{title : '消耗（张）',rowspan:1,field : 'qty4',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗（张/箱）',rowspan:1,field : 'danhao4',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '消耗（KG）',formatter: function(value,row,index){
							return Math.round(value*100)/100;
						},rowspan:1,field : 'qty5',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗（KG/箱）',formatter: function(value,row,index){
							return Math.round(value*100)/100;
						},rowspan:1,field : 'danhao5',width :$(this).width() * 0.05,align:'center',sortable : true},
						{title : '消耗（KG）',formatter: function(value,row,index){
							return Math.round(value*100)/100;
						},rowspan:1,field : 'qty6',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗（KG/箱）',formatter: function(value,row,index){
							return Math.round(value*100)/100;
						},rowspan:1,field : 'danhao6',width :$(this).width() * 0.07,align:'center',sortable : true},
						
						{title : '消耗（张）',rowspan:1,field : 'qty7',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗（张/箱）',rowspan:1,field : 'danhao7',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '消耗（KG）',formatter: function(value,row,index){
							return Math.round(value*100)/100;
						},rowspan:1,field : 'qty8',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗（KG/箱）',formatter: function(value,row,index){
							return Math.round(value*100)/100;
						},rowspan:1,field : 'danhao8',width :$(this).width() * 0.07,align:'center',sortable : true},
					]

				],
				toolbar : '#passRateToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {//鼠标右键事件
					/* e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#shiftchgMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					}); */
				},
				onClickRow:function(rowIndex,rowData){//点击行事件
					//getShapeDetsByShapeId(rowData.id);
				}
			});

			//通过机型，过滤设备 
			 $("#eqpType").combobox({
				onChange:function(n){
					if(n==1){
						$.loadComboboxData($("#equipment"),"ALLROLLERS",true);
					}else if(n==2){
						$.loadComboboxData($("#equipment"),"ALLPACKERS",true);
					}else if(n==3){
						$.loadComboboxData($("#equipment"),"ALLBOXERS",true);
					}else if(n==4){
						$.loadComboboxData($("#equipment"),"ALLFILTERS",true);
					}else{
						$.loadComboboxData($("#equipment"),"ALLEQPS",true);
					}
				}
			}); 
		});


		
		
	   //查询
		function getShapes() {
			var date1=$("#startTime").datebox("getValue");//周初 
		    var date2=$("#endTime").datebox("getValue");//周末
		    if(date1!=null&date1!=""&&date2!=null&date2!=""){
				passRateGrid.datagrid({
					url : tempUrl+"/getAllJuanBao.do",
					queryParams :$("#passRateForm").form("getData"),
					onLoadError : function(data) {
						$.messager.show('提示', "查询异常", 'error');
					}
				});
			}else{
		    	$.messager.show('提示', "请选择具体时间段后进行查询！", 'info');
		    }
	//passRateGrid("loadData",$("#passRateForm").form("getData"));
			//alert();
		}
		//重置
		function clearShapeForm(){
		   try{
			   $('#passRateForm input').val(null);
		       $("#TEAM").combobox("setValue", "");//下拉框赋值
		       $("#SHIFT").combobox("setValue", "");//下拉框赋值
			} catch(e){
				alert(e);
			}
	   }
		//双击记载数据事件
	   function getShapeDetsByShapeId(id){
		   bandParams(id,$("#passRateForm").form("getData"));
	   }
	   //导出excel
	   function derive(){
		   var mdTeamId=$("#passRateForm").form("getData").mdTeamId;
		   var mdShiftId=$("#passRateForm").form("getData").mdShiftId;
		   var stim=$("#passRateForm").form("getData").stim;
		   var etim=$("#passRateForm").form("getData").etim;
		   var equipmentType=$("#passRateForm").form("getData").equipmentType;
		   var mdEquipmentId=$("#passRateForm").form("getData").mdEquipmentId;
		   var mdMatId=$("#passRateForm").form("getData").mdMatId;
		   
			var recordUrl =tempUrl+"/excelDeriveJuanBaoCJ.do?mdTeamId="+mdTeamId+"&mdShiftId="+mdShiftId+"&stim="+stim+"&etim="+etim+"&equipmentType="+equipmentType+"&mdEquipmentId="+mdEquipmentId+"&mdMatId="+mdMatId;
			var isPass=false;
			var queryExcel =tempUrl+"/queryExcelDeriveJuanBaoCJ.do?mdTeamId="+mdTeamId+"&mdShiftId="+mdShiftId+"&stim="+stim+"&etim="+etim+"&equipmentType="+equipmentType+"&mdEquipmentId="+mdEquipmentId+"&mdMatId="+mdMatId;
			$.post(queryExcel,function(data){
				if(data=='"false"'){
					$.messager.show('提示', "数据超过一万条，请保持在一万条数据以内导出", 'error');
					return;
				}else if(data=='"true"'){
					isPass=true;
				}
				if(isPass){
					var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
				   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
					//toolbar=no   是否显示工具栏，yes为显示；menubar，scrollbars   表示菜单栏和滚动栏。resizable=no   是否允许改变窗口大小，yes为允许；location=no   是否显示地址栏，yes为允许；  status=no   是否显示状态栏内的信息（通常是文件已经打开），yes为允许；   
					parent.window.open(recordUrl,"ExcelWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
				 }
			});
	   }
	   function deriveEffic(){
		     
			var recordUrl =tempUrl+"/excelDeriveEfficJuanBaoCJ.do?mdTeamId="+$("#passRateForm").form("getData").mdTeamId+"&mdShiftId="+$("#passRateForm").form("getData").mdShiftId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
		   	var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
			//toolbar=no   是否显示工具栏，yes为显示；menubar，scrollbars   表示菜单栏和滚动栏。resizable=no   是否允许改变窗口大小，yes为允许；location=no   是否显示地址栏，yes为允许；  status=no   是否显示状态栏内的信息（通常是文件已经打开），yes为允许；   
			parent.window.open(recordUrl,"ExcelWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
		
  }	
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">	
	<div id="passRateToolbar"  style="display: none;width:100%;">
		<form id="passRateForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div>
						<span class="label">生产日期：</span>
						<input id="startTime" name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width: 130px" />
					</div>
					<div>
						<span class="label" style="width: 10px;">到</span>
						<input id="endTime" name="etim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width: 130px" />
					</div>
					<!-- 牌号 -->
				    <div >
						<span class="label">牌号：</span>
						<select id="matProd1" name="mdMatId" class="easyui-combobox"
							data-options="panelHeight:'150',width:120,editable:false"></select>
					</div>
					
					<div >
						<span class="label">班次：</span>
						<select id="SHIFT" name="mdShiftId" class="easyui-combobox"
							data-options="panelHeight:'150',width:120,editable:false"></select>
					</div>
					<div >
						<span class="label">班组：</span>
						<select id="TEAM" name="mdTeamId" class="easyui-combobox"
							data-options="panelHeight:'150',width:120,editable:false"></select>
					</div>
					<!-- 设备类型 -->
					<div>
						<span class="label">机型：</span>
						<select  id="eqpType" name="equipmentType" class="eqptype" class="easyui-combobox" data-options="panelHeight:'110',width:100,editable:false">
							<option value="">全部</option>
							<option value="1">卷烟机</option>
							<option value="2">包装机</option>
							<!-- 
							<option value="3">装箱机</option>
							<option value="4">成型机</option> 
							-->
						</select>
					</div>
					<div>
						<span class="label">机台：</span>
						<select id="equipment" name="mdEquipmentId" class="easyui-combobox" data-options="panelHeight:200,width:100,editable:false"></select>
					</div>
				</fieldset>
			</div>
		</form>
		<div class="easyui-toolbar" >
			<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/passRate/getList.do']}"> --%>
				<a onclick="getShapes()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
		<%-- 	</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/passRate/cleanQuery.do']}"> --%>
				<a onclick="derive();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-link'">导出Excel</a>
				<a onclick="clearShapeForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<!-- <a onclick="deriveEffic();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-link'">导出有效作业率Excel</a> -->
			<%-- </c:if> --%>
		</div>
	</div>
	<div data-options="region:'center',border:true,split:false">
		<table id="passRateGrid"></table>
	</div>
	<!--右键按钮 <div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;"></div> -->
</body>
</html>