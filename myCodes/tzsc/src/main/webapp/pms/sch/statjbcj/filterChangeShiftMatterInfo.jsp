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
		var editRow = undefined;
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
				border : false,
				striped : true,
				remoteSort: false,	
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				frozenColumns:[[//固定表头equipmentName
						{title:'工段', rowspan:2,field:'team_Name',width:80,align:'center'},//跨列
						{title:'工单日期', rowspan:2,field:'orderDate',width:80,align:'center'},//跨列
						{title:'机台', rowspan:2,field:'eqpName',width:80,align:'center'},//跨列
						{title:'牌号', rowspan:2,field:'mat_name',width:120,align:'center'},//跨列
						{title:'产量', rowspan:2,field:'filterQty',width:80,align:'center',editor:{type:'numberbox',options:{precision:2}},},//跨列
				]],
				columns : [  [ 	{title:'盘纸',colspan:4,rowspan:1,width:240,align:'center',},
				               	{title:'丝束',colspan:4,rowspan:1,width:240,align:'center',},
				               	{title:'甘油',colspan:1,rowspan:1,align:'center',},
								{title:'搭口胶',colspan:1,rowspan:1,align:'center',},
								{title:'热熔胶',colspan:1,rowspan:1,align:'center',},
							],[ 
						{title:'虚领id',hidden:true,field:'xl_zl_id'},
						{title:'虚领',align:'center',colspan:1,width:60,field : 'xl_zl_pz',editor:{type:'numberbox',options:{precision:2}},}, 
						{title:'实领id',hidden:true,field:'sl_zl_id'},
						{title:'实领',align:'center',colspan:1,width:60,field : 'sl_zl_pz',editor:{type:'numberbox',options:{precision:2}},}, 
						{title:'虚退id',hidden:true,field:'xt_zl_id'},
						{title:'虚退',align:'center',colspan:1,width:60,field : 'xt_zl_pz',editor:{type:'numberbox',options:{precision:2}},},
						{title:'用料',align:'center',colspan:1,width:60,field : 'sy_zl_pz',},
						{title:'虚领',align:'center',colspan:1,width:60,field : 'xl_zl_ss',editor:{type:'numberbox',options:{precision:2}},}, 
						{title:'实领',align:'center',colspan:1,width:60,field : 'sl_zl_ss',editor:{type:'numberbox',options:{precision:2}},}, 
						{title:'虚退',align:'center',colspan:1,width:60,field : 'xt_zl_ss',editor:{type:'numberbox',options:{precision:2}},},
						{title:'用料',align:'center',colspan:1,width:60,field : 'sy_zl_ss',},
				        {title:'实领',align:'center',colspan:1,width:60,field : 'sl_zl_gy',editor:{type:'numberbox',options:{precision:2}},},
				        {title:'实领',align:'center',colspan:1,width:60,field : 'sl_zl_dkj',editor:{type:'numberbox',options:{precision:2}},},
				        {title:'实领',align:'center',colspan:1,width:60,field:'sl_zl_rrj',editor:{type:'numberbox',options:{precision:2}},},
					]
					   
					   ],
				toolbar : '#passRateToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {//鼠标右键事件
					e.preventDefault();
					$(this).treegrid('unselectAll');
					$(this).treegrid('select', rowIndex);
					editRow=rowIndex;
					$('#menu').menu('show', {
						left : e.pageX,
						top : e.pageY
					}); 
				},
				onAfterEdit:function(rowIndex, rowData,changes){
						passRateGrid.datagrid('endEdit', rowIndex);
						editRow = undefined;
						save(rowData,changes);
				},
				onCancelEdit:function(rowIndex, rowData){
					passRateGrid.datagrid("endEdit", editRow);
					editRow=undefined;
				}
			});

			
		});
		
		
		//保存修改
		function save(data,changes){
			var flag=false;
			var fx_xl=false;
			var fx_sl=false;
			var fx_xt=false;
			
			//添加虚领/虚退/实领 id
			$.each(changes,function(i,n){
				//虚退
				if(!fx_xt){
					if(i.indexOf('xt_zl')>-1){
						changes.xt_zl_id=data.xt_zl_id;
						fx_xt=true;
						flag=true;
					}
				}
				//实领  
				if(!fx_sl){
						if(i.indexOf('sl_zl')>-1||i.indexOf('filterQty')>-1){
						changes.sl_zl_id=data.sl_zl_id;
						fx_sl=true;
						flag=true;
						}
				}
			//虚领
			 if(!fx_xl){
				if(i.indexOf('xl_zl')>-1){
					changes.xl_zl_id=data.xl_zl_id;
					fx_xl=true;
					flag=true;
				 }
				}
			});
			if(!flag){
				return;
			}
			var url=tempUrl+"/saveFilterMatterChange.do";
			$.post(url,changes,function(json){
				if(json.success){
					//保存成功
					$.messager.show('提示', json.msg, 'info');
				}else{
					//保存失败
					$.messager.show('提示', json.msg, 'error');
				}
			},"JSON");
			getShapes();//刷新数据
		}
		
		
		//右键开启编辑
		function edit(){
			passRateGrid.datagrid("beginEdit", editRow);
		}
		
		
	   //查询
		function getShapes() {
			var date1=$("#startTime").datebox("getValue"); 
			var date2=$("#endTime").datebox("getValue"); 
		    if(date1!=null&&date1!=""&&date2!=null&&date2!=""){
				passRateGrid.datagrid({
					url : tempUrl+"/searchFilterMatterInfo.do",
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
		       $("#TEAM").combobox("setValue", "");//下拉框赋值
		       $("#SHIFT").combobox("setValue", "");//下拉框赋值
			} catch(e){
			}
	   }
		
		
	   //导出execl
	   function derive(){
			var recordUrl =tempUrl+"/filterMatterInfo.do?mdTeamId="+$("#passRateForm").form("getData").mdTeamId+"&mdShiftId="+$("#passRateForm").form("getData").mdShiftId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
			var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
			//toolbar=no   是否显示工具栏，yes为显示；menubar，scrollbars   表示菜单栏和滚动栏。resizable=no   是否允许改变窗口大小，yes为允许；location=no   是否显示地址栏，yes为允许；  status=no   是否显示状态栏内的信息（通常是文件已经打开），yes为允许；   
			parent.window.open(recordUrl,"ExcelWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
	   }
	   
	   function deriveEffic(){
			var recordUrl =tempUrl+"/filterMatterInfo.do?mdTeamId="+$("#passRateForm").form("getData").mdTeamId+"&mdShiftId="+$("#passRateForm").form("getData").mdShiftId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
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
						<input id="startTime" name="stim" type="text"
							class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss"
							style="width: 120px" />
					</div>
					<div>
						<span class="label" style="width: 10px;">到</span>
						<input id="endTime" name="etim" type="text"
						class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss"
						style="width: 130px" />
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
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sch/statjbcj/saveFilterMatterChange.do']}">
			<a onclick="getShapes()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			<a onclick="derive();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-link'">导出Excel</a>
		</c:if>
			<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/passRate/getList.do']}"> --%>
				<!-- <a onclick="getShapes()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a> -->
		<%-- 	</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/passRate/cleanQuery.do']}"> --%>
				<!-- <a onclick="derive();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-link'">导出Excel</a> -->
				<a onclick="clearShapeForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
			<%-- </c:if> --%>
		</div>
	</div>
	<div data-options="region:'center',border:true,split:false">
		<table id="passRateGrid"></table>
	</div>
	<!--右键按钮 <div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;"></div> -->
	
	
	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
				<div onclick="edit();" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
				<div onclick="quit();" data-options="iconCls:'icon-standard-plugin-edit'">取消</div>
	</div>
	
	
</body>
</html>