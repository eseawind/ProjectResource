<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>成型班组生产统计</title>
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
				sortName : 'id',
				sortOrder : 'asc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				frozenColumns:[[//固定表头
						{field : 'id',title : '',width : 120,hidden : true}, 
						{title:'班组', rowspan:3,field:'mdTeamName',width:60,align:'center'},//跨列
						{title :'实际产量',rowspan:3,field : 'qty',width :$(this).width() * 0.09,align:'center',sortable : true},
						{title :'产量单位',rowspan:3,field : 'mdUnitName',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title :'剔除',rowspan:3,field : 'badQty',width :$(this).width() * 0.09,align:'center',sortable : true},
						
						
				]],
				columns : [  [ 
								
								{title:'成型机组',colspan:9},
								
							
							],[ 
						
						{title:'盘纸',colspan:3},
						{title:'甘油',colspan:3},
						{title:'丝束',colspan:3},
					
						
						
						
					],[
						{title :'消耗',rowspan:1,field : 'qty1',width :$(this).width() * 0.09,align:'center',sortable : true},
						{title : '单位',rowspan:1,field : 'mdUnitName1',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗',rowspan:1,field : 'danhao1',width :$(this).width() * 0.09,align:'center',sortable : true},
						{title :'消耗',rowspan:1,field : 'qty2',width :$(this).width() * 0.09,align:'center',sortable : true},
						{title : '单位',rowspan:1,field : 'mdUnitName2',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗',rowspan:1,field : 'danhao2',width :$(this).width() * 0.09,align:'center',sortable : true},
						{title : '消耗',rowspan:1,field : 'qty3',width :$(this).width() * 0.09,align:'center',sortable : true},
						{title : '单位',rowspan:1,field : 'mdUnitName3',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单耗',rowspan:1,field : 'danhao3',width :$(this).width() * 0.09,align:'center',sortable : true},
					
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

			

		
			
		});


		
		
	   //查询
		function getShapes() {
			var date1=$("#startTime").datebox("getValue");//周初 
		    var date2=$("#endTime").datebox("getValue");//周末
		    if(date1!=null&date1!=""&&date2!=null&date2!=""){
		    	passRateGrid.datagrid({
					url : tempUrl+"/getAllChengXingBanZu.do",
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
	   function derive(){
		   var recordUrl =tempUrl+"/excelDeriveChengXingBanZu.do?mdTeamId="+$("#passRateForm").form("getData").mdTeamId+"&mdShiftId="+$("#passRateForm").form("getData").mdShiftId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
		   var isPass=false;
		   var queryExcel=tempUrl+"/queryExcelDeriveChengXingBanZu.do?mdTeamId="+$("#passRateForm").form("getData").mdTeamId+"&mdShiftId="+$("#passRateForm").form("getData").mdShiftId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
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
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">	
	<div id="passRateToolbar"  style="display: none;width:100%;">
		<form id="passRateForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div>
						<span class="label">生产日期：</span>
						<input id="startTime" name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width: 130px" />
					</div>
					<div>
						<span class="label" style="width: 10px;">到</span>
						<input id="endTime" name="etim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width: 130px" />
					</div>
				
					<div >
						<span class="label">班组：</span>
						<select id="TEAM" name="mdTeamId" class="easyui-combobox"
							data-options="panelHeight:'150',width:120,editable:false"></select>
					</div>
					<div >
						<span class="label">班次：</span>
						<select id="SHIFT" name="mdShiftId" class="easyui-combobox"
							data-options="panelHeight:'150',width:120,editable:false"></select>
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
			<%-- </c:if> --%>
		</div>
	</div>
	<div data-options="region:'center',border:true,split:false">
		<table id="passRateGrid"></table>
	</div>
	<!--右键按钮 <div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;"></div> -->
</body>
</html>