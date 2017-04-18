<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>成型机生产统计</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath}/jslib/Highcharts-3.0.1/js/highcharts.js"></script>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">

		var tempUrl ="${pageContext.request.contextPath}/pms/sch/statjbcj";

		var passRateGrid=null;
		var bandParams = null;
		$(function() {
			//初始化
			$.loadComboboxData($("#mdEquipmentSb"),"ALLEQPS",true);//加载下拉框数据
			

			
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
						{title:'设备编号', rowspan:3,field:'equipmentNameCode',width:60,align:'center'},//跨列
						{title:'设备名称', rowspan:3,field:'equipmentName',width:60,align:'center'},//跨列
						{title:'设备型号', rowspan:3,field:'equipmentType',width:60,align:'center'},//跨列
						{title:'班组', rowspan:3,field:'mdTeamName',width:30,align:'center'},//跨列
						{title:'产品', rowspan:3,field:'mdMatName',width:100,align:'center'},//跨列
						{title:'产量', rowspan:3,field:'qty',width:60,align:'center'},//跨列
						{title:'单位', rowspan:3,field:'mdUnitName',width:40,align:'center'},//跨列
						{title:'剔除', rowspan:3,field:'badQty',width:60,align:'center'},//跨列
						
				]],
				columns : [  [ 
								
								{title:'辅料消耗',colspan:9},
								{title : '生产日期',rowspan:3,field : 'date',width :$(this).width() * 0.065,align:'center',sortable : true},
								{title:'运行统计',colspan:8}
							],[ 
						
						{title:'盘纸',colspan:3},
						{title:'甘油',colspan:3},
						{title:'丝束',colspan:3},
						
						{title : '运行时间',rowspan:2,field : 'runTime',width :$(this).width() * 0.05,align:'center',sortable : true},

						{title :'停机时间',rowspan:2,field : 'stopTime',width :$(this).width() * 0.05,align:'center',sortable : true},
						{title : '单位',rowspan:2,field : 'timeUnitName',width :$(this).width() * 0.06,align:'center',sortable : true},
						{title : '停机次数',rowspan:2,field : 'stopTimes',width :$(this).width() * 0.06,align:'center',sortable : true},
					/* 	{title :'数据最后接收时间',rowspan:2,field : 'lastRecvTime',width :$(this).width() * 0.065,align:'center',sortable : true}, */
						{title : '数据最后编辑时间',rowspan:2,field : 'lastUpdateTime',width :$(this).width() * 0.065,align:'center',sortable : true},
						{title : '是否反馈',rowspan:2,field : 'isFeedback01',width :$(this).width() * 0.06,align:'center',sortable : true},
						{title : '反馈时间',rowspan:2,field : 'feedbackTime',width :$(this).width() * 0.06,align:'center',sortable : true},
						{title : '反馈人',rowspan:2,field : 'feedbackUser',width :$(this).width() * 0.06,align:'center',sortable : true},
						
						
						
					],[
						{title :'消耗',rowspan:1,field : 'qty1',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单位',rowspan:1,field : 'mdUnitName1',width :$(this).width() * 0.05,align:'center',sortable : true},
						{title : '单耗',rowspan:1,field : 'danhao1',width :$(this).width() * 0.05,align:'center',sortable : true},
						{title :'消耗',rowspan:1,field : 'qty2',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单位',rowspan:1,field : 'mdUnitName2',width :$(this).width() * 0.05,align:'center',sortable : true},
						{title : '单耗',rowspan:1,field : 'danhao2',width :$(this).width() * 0.05,align:'center',sortable : true},
						{title : '消耗',rowspan:1,field : 'qty3',width :$(this).width() * 0.07,align:'center',sortable : true},
						{title : '单位',rowspan:1,field : 'mdUnitName3',width :$(this).width() * 0.05,align:'center',sortable : true},
						{title : '单耗',rowspan:1,field : 'danhao3',width :$(this).width() * 0.05,align:'center',sortable : true},
						
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
			passRateGrid.datagrid({
				url : tempUrl+"/getAllchengxing.do",
				queryParams :$("#passRateForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
			
	//passRateGrid("loadData",$("#passRateForm").form("getData"));
			//alert();
		}
		//重置
		function clearShapeForm(){
		   try{
			   $('#passRateForm input').val(null);
		       $("#mdEquipmentSb").combobox("setValue", "");//下拉框赋值
		      
			} catch(e){
				alert(e);
			}
	   }
		//双击记载数据事件
	   function getShapeDetsByShapeId(id){
		   bandParams(id,$("#passRateForm").form("getData"));
	   }
	   
	   //导出execl
	   function derive(){
			
					var recordUrl =tempUrl+"/excelDeriveChengXingZongHe.do?mdEquipmentId="+$("#passRateForm").form("getData").mdEquipmentId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
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
						<input id="startTime" name="stim" readOnly=true type="text"
							class="easyui-datebox" datefmt="yyyy-MM-dd HH:mm:ss"
							style="width: 130px" />
					</div>
					<div>
						<span class="label" style="width: 10px;">到</span>
						<input id="endTime" name="etim" readOnly=true type="text"
						class="easyui-datebox" datefmt="yyyy-MM-dd HH:mm:ss"
						style="width: 130px" />
					</div>
				
					<div >
						<span class="label">机台(设备)：</span>
						<select id="mdEquipmentSb" name="mdEquipmentId" class="easyui-combobox"
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
	<div data-options="region:'center',border:true,split:false" style="height:450px;">
		<table id="passRateGrid"></table>
	</div>
	
	
	<!--右键按钮 <div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;"></div> -->
</body>
</html>