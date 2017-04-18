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
						{title:'工段', rowspan:3,field:'team_Name',width:50,align:'center'},//跨列
						{title:'日期', rowspan:3,field:'orderDate',width:80,align:'center'},//跨列
						{title:'定编机组', rowspan:3,field:'handover_user_name',width:160,align:'center'},//跨列
						{title:'实开机组', rowspan:3,field:'eqpName',width:80,align:'center'},//跨列
						{title:'牌号', rowspan:3,field:'mat_name',width:80,align:'center'},//跨列
				]],
				columns : [  [ 	{title:'产量',colspan:2},
				               	{title:'残烟',colspan:3},
				               	{title:'卷烟纸规格',colspan:1,rowspan:3,field : 'pa_gg',align:'center',editor:{type:'numberbox'}},
								{title:'虚领',colspan:12},
								{title:'实领',colspan:12},
								{title:'虚退',colspan:12},
								{title:'用料',colspan:12}
							],[ 
						{title:'卷烟',align:'center',rowspan:2,field : 'juQty',}, 
						{title:'包装',align:'center',rowspan:2,field : 'bzQty',}, 
				        {title:'卷烟',align:'center',rowspan:2,field : 'qt_jy',editor:{type:'numberbox',options:{precision:2}},}, 
				        {title:'包装',align:'center',rowspan:2,field : 'qt_bz',editor:{type:'numberbox',options:{precision:2}},},
				        {title:'吹车',align:'center',rowspan:2,field : 'qt_cc',editor:{type:'numberbox',options:{precision:2}},},
				        
				        {title:'卷烟机用料',align:'center',rowspan:1,colspan:3,editor:{type:'numberbox',options:{precision:2}},},
						{title:'包装机用料',align:'center',rowspan:1,colspan:9,editor:{type:'numberbox',options:{precision:2}},},
						{title:'卷烟机用料',align:'center',rowspan:1,colspan:3,editor:{type:'numberbox',options:{precision:2}},},
						{title:'包装机用料',align:'center',rowspan:1,colspan:9,editor:{type:'numberbox',options:{precision:2}},},
						{title:'卷烟机用料',align:'center',rowspan:1,colspan:3,editor:{type:'numberbox',options:{precision:2}},},
						{title:'包装机用料',align:'center',rowspan:1,colspan:9,editor:{type:'numberbox',options:{precision:2}},},
						{title:'卷烟机用料',align:'center',rowspan:1,colspan:3,editor:{type:'numberbox',options:{precision:2}},},
						{title:'包装机用料',align:'center',rowspan:1,colspan:9,editor:{type:'numberbox',options:{precision:2}},},
					],[
					//虚领
					{field : 'lx_jy_id',title : '',width : 120,hidden : true}, 
					{title :'滤棒',align:'center',rowspan:1,field : 'xl_zj_lb',editor:{type:'numberbox'},align:'center',sortable : true},
					{title :'卷烟纸',align:'center',rowspan:1,field : 'xl_zj_pz',editor:{type:'numberbox',options:{precision:2}},align:'center',sortable : true},
					{title:'水松纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zj_ssz'},
					{field : 'xl_bz_id',title : '',width : 120,hidden : true}, 
					{title:'商标纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_xhz'},
					{title:'条盒',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_thz'},
					{title:'铝箔纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_lbz'},
					{title:'小透明',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_xhm'},
					{title:'条透明',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_thm'},
					{title:'卡纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_kz'},
					{title:'封签',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_fq'},
					{title:'拉线1',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_lx1'},
					{title:'拉线2',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xl_zb_lx2'},
					//实领
					{field : 'sl_jy_id',title : '',width : 120,hidden : true}, 
					{title :'滤棒',align:'center',rowspan:1,field : 'sl_zj_lb',editor:{type:'numberbox'},align:'center',sortable : true},
					{title :'卷烟纸',align:'center',rowspan:1,field : 'sl_zj_pz',editor:{type:'numberbox',options:{precision:2}},align:'center',sortable : true},
					{title:'水松纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zj_ssz'},
					{field : 'sl_bz_id',title : '',width : 120,hidden : true}, 
					{title:'商标纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_xhz'},
					{title:'条盒',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_thz'},
					{title:'铝箔纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_lbz'},
					{title:'小透明',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_xhm'},
					{title:'条透明',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_thm'},
					{title:'卡纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_kz'},
					{title:'封签',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_fq'},
					{title:'拉线1',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_lx1'},
					{title:'拉线2',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'sl_zb_lx2'},
					
					
					//虚退
					{field : 'xt_jy_id',title : '',width : 120,hidden : true}, 
					{title :'滤棒',align:'center',rowspan:1,field : 'xt_zj_lb',editor:{type:'numberbox'},align:'center',sortable : true},
					{title :'卷烟纸',align:'center',rowspan:1,field : 'xt_zj_pz',editor:{type:'numberbox'},align:'center',sortable : true},
					{title:'水松纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zj_ssz'},
					{field : 'xt_bz_id',title : '',width : 120,hidden : true}, 
					{title:'商标纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zb_xhz'},
					{title:'条盒',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zb_thz'},
					{title:'铝箔纸)',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zb_lbz'},
					{title:'小透明',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zb_xhm'},
					{title:'条透明',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zb_thm'},
					{title:'卡纸',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zb_kz'},
					{title:'封签',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zb_fq'},
					{title:'拉线1',align:'center',rowspan:1, editor:{type:'numberbox',options:{precision:2}},field:'xt_zb_lx1'},
					{title:'拉线2',align:'center',rowspan:1, editor:{type:'numberbox'},field:'xt_zb_lx2'},
					
					
					
					//实用
					{field : 'sy_jy_id',title : '',width : 120,hidden : true},  
					{title :'滤棒',align:'center',rowspan:1,field : 'sy_zj_lb',align:'center',sortable : true},
					{title :'卷烟纸',align:'center',rowspan:1,field : 'sy_zj_pz',align:'center',sortable : true},
					{title:'水松纸',align:'center',rowspan:1,field:'sy_zj_ssz'},
					{field : 'sy_bz_id',title : '',width : 120,hidden : true}, 
					{title:'商标纸',align:'center',rowspan:1,field:'sy_zb_xhz'},
					{title:'条盒',align:'center',rowspan:1,field:'sy_zb_thz'},
					{title:'铝箔纸',align:'center',rowspan:1,field:'sy_zb_lbz'},
					{title:'小透明',align:'center',rowspan:1,field:'sy_zb_xhm'},
					{title:'条透明',align:'center',rowspan:1,field:'sy_zb_thm'},
					{title:'卡纸',align:'center',rowspan:1,field:'sy_zb_kz'},
					{title:'封签',align:'center',rowspan:1,field:'sy_zb_fq'},
					{title:'拉线1',align:'center',rowspan:1,field:'sy_zb_lx1'},
					{title:'拉线2',align:'center',rowspan:1,field:'sy_zb_lx2'}, 
					   
					   
					   ]],
				toolbar : '#passRateToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				 onDblClickRow:function(rowIndex, rowData){//鼠标双击事件
					$(this).treegrid('unselectAll');
					$(this).treegrid('select', rowIndex);
					editRow=rowIndex;
					passRateGrid.datagrid("beginEdit", editRow);
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
			var zj_xl=false;
			var zj_sl=false;
			var zj_xt=false;
			
			var bz_xl=false;
			var bz_sl=false;
			var bz_xt=false;
			
			//添加虚领/虚退/实领 id
			$.each(changes,function(i,n){
				//卷烟机
				//虚领
				if(!zj_xl){
					if(i.indexOf('xl_zj')>-1){
						changes.lx_jy_id=data.lx_jy_id;
						zj_xl=true;
						flag=true;
					}
				}
				//实领  当实领辅料数据修改 或 产量/残烟量/吹车/盘纸 修改时
				if(!zj_sl){
					if(i.indexOf('sl_zj')>-1||i.indexOf('juQty')>-1||i.indexOf('qt_jy')>-1||i.indexOf('qt_cc')>-1||i.indexOf('pa_gg')>-1){
						changes.sl_jy_id=data.sl_jy_id;
						zj_sl=true;
						flag=true;
					}
				}
				//虚退
				if(!zj_xt){
					if(i.indexOf('xt_zj')>-1){
						changes.xt_jy_id=data.xt_jy_id;
						zj_xt=true;
						flag=true;
					}
				}
				//包装机
				//虚领
				if(!bz_xl){
					if(i.indexOf('xl_zb')>-1){
						changes.xl_bz_id=data.xl_bz_id;
						bz_xl=true;
						flag=true;
					}
				}
				
				//实领  当实领辅料数据修改 或 产量/残烟量/吹车修改时
				if(!bz_sl){
					if(i.indexOf('sl_zb')>-1||i.indexOf('bzQty')>-1||i.indexOf('qt_bz')>-1||i.indexOf('qt_cc')>-1||i.indexOf('pa_gg')>-1){
						changes.sl_bz_id=data.sl_bz_id;
						bz_sl=true;
						flag=true;
					}
				}
				
				//虚退
				if(!bz_xt){
					if(i.indexOf('xt_zb')>-1){
						changes.xt_bz_id=data.xt_bz_id;
						bz_xt=true;
						flag=true;
					}
				}
			})
			if(!flag){
				return;
			}
			var url=tempUrl+"/saveMatterChange.do";
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
		    if(date1!=null&&date1!=""&&date1!=null&&date1!=""){
				passRateGrid.datagrid({
					url : tempUrl+"/searchChangeShiftMatterInfo.do",
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
			var recordUrl =tempUrl+"/excelHandAndReceiveTeam.do?mdTeamId="+$("#passRateForm").form("getData").mdTeamId+"&mdShiftId="+$("#passRateForm").form("getData").mdShiftId+"&stim="+$("#passRateForm").form("getData").stim+"&etim="+$("#passRateForm").form("getData").etim;
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
						<input id="startTime" name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width: 120px" />
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
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sch/statjbcj/saveMatterChange.do']}">
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