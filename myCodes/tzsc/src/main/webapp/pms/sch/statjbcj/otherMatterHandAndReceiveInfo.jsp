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
				fit : false,
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
						{title:'工段', rowspan:2,field:'team_Name',width:120,align:'center'},//跨列
						{title:'日期', rowspan:2,field:'orderDate',width:120,align:'center'},//跨列
						{title:'牌号', rowspan:2,field:'mat_name',width:120,align:'center'},//跨列
				]],
				columns : [  [ 	{title:'烟箱',colspan:3,rowspan:1,width:260,align:'center',},
				               	{title:'胶带',colspan:1,rowspan:1,width:120,align:'center',},
				               	{title:'接嘴胶',colspan:1,rowspan:1,align:'center',},
								{title:'搭扣胶',colspan:1,rowspan:1,align:'center',},
								{title:'包装胶',colspan:1,rowspan:1,align:'center',},
								{title:'烟箱',colspan:1,rowspan:1,align:'center',},
							],[ 
						{title:'虚领id',hidden:true,field:'xl_yf_id'},
						{title:'虚领',align:'center',colspan:1,width:100,field : 'xl_yf_xp',editor:{type:'numberbox'},}, 
						{title:'实领id',hidden:true,field:'sl_yf_id'},
						{title:'实领',align:'center',colspan:1,width:100,field : 'sl_yf_xp',editor:{type:'numberbox'},}, 
						{title:'虚退id',hidden:true,field:'xt_yf_id'},
						{title:'虚退',align:'center',colspan:1,width:100,field : 'xt_yf_xp',editor:{type:'numberbox'},}, 
				        {title:'实领',align:'center',colspan:1,width:90,field : 'xl_yf_jd',editor:{type:'numberbox'},},
				        {title:'实领',align:'center',colspan:1,width:90,field : 'oth_jzj',editor:{type:'numberbox'},},
				        
				        {title:'实领',align:'center',colspan:1,width:80,field:'oth_dkj',editor:{type:'numberbox'},},
						{title:'实领',align:'center',colspan:1,width:90,field:'oth_bzj',editor:{type:'numberbox'},},
						{title:'用料',align:'center',colspan:1,width:90,field:'sy_yf_xp',},
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
				//卷烟机
				//虚领
				if(!fx_xl){
					if(i.indexOf('xl_yf_xp')>-1){
						changes.xl_yf_id=data.xl_yf_id;
						fx_xl=true;
						flag=true;
					}
				}
				//实领  
				if(!zj_sl){
						if(i.indexOf('sl_yf')>-1||i.indexOf('oth')>-1||i.indexOf('_jd')>-1){
						changes.sl_yf_id=data.sl_yf_id;
						fx_sl=true;
						flag=true;
						}
				}
			//虚退
			 if(!fx_xl){
				if(i.indexOf('xt_yf_xp')>-1){
					changes.xt_yf_id=data.xt_yf_id;
					fx_xt=true;
					flag=true;
				 }
				}
			});
			if(!flag){
				return;
			}
			var url=tempUrl+"/saveOtherMatterChange.do";
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
					url : tempUrl+"/searchChangeShiftOtherMatterInfo.do",
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
			var recordUrl =tempUrl+"/otherMatterHandAndReceiveInfo.do?mdTeamId="+$("#passRateForm").form("getData").mdTeamId+"&mdShiftId="+$("#passRateForm").form("getData").mdShiftId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
			var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
			//toolbar=no   是否显示工具栏，yes为显示；menubar，scrollbars   表示菜单栏和滚动栏。resizable=no   是否允许改变窗口大小，yes为允许；location=no   是否显示地址栏，yes为允许；  status=no   是否显示状态栏内的信息（通常是文件已经打开），yes为允许；   
			parent.window.open(recordUrl,"ExcelWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
	   }
	   
	   function deriveEffic(){
			var recordUrl =tempUrl+"/excelHandAndReceiveTeam.do?mdTeamId="+$("#passRateForm").form("getData").mdTeamId+"&mdShiftId="+$("#passRateForm").form("getData").mdShiftId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
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
						<input id="startTime" name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width: 120px" />
					</div>
					<div>
						<span class="label" style="width: 10px;">到</span>
						<input id="endTime" name="etim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width: 120px" />
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
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sch/statjbcj/saveOtherMatterChange.do']}">
			<a onclick="getShapes()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
			<a onclick="derive();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-link'">导出Excel</a>
		</c:if>
			<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/sch/statjbcj/saveOtherMatterChange.do']}"> --%>
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