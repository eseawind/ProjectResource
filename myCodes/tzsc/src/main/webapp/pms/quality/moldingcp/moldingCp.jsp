<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>成型车间成品物理检验记录</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var tempUrl ="${pageContext.request.contextPath}/pms/moldingCp";
		var moldingCpGrid=null;
		$(function() {
			//初始化
			$.loadComboboxData($("#mdShiftBc"),"SHIFT",true);//加载下拉框数据
			$.loadComboboxData($("#mdEquipmentSb"),"ALLEQPS",true);//加载下拉框数据
			$.loadComboboxData($("#mdWorkshopCj"),"TEAM",true);
			//初始化时间
		    var today = new Date();
			var month=today.getMonth()+1;
			if(month<10){month=("0"+month);}
			var day=today.getDate();
			if(day<10){day=("0"+day);}
		    var date=today.getFullYear()+"-"+month+"-"+day;
		    $("#startTime").datebox("setValue",date);	//时间用这个
			$("#endTime").datebox("setValue",date);	//时间用这个
			//end
			moldingCpGrid = $('#moldingCpGrid').datagrid({
				fit : true,
				fitColumns : true,//fitColumns= true就会自动适应宽度（无滚动条）
				//width:$(this).width()-252,  
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				remoteSort: false,
				pageSize : 10,
				pageList : [10, 20, 60, 80, 100],
				sortName : 'time',
				sortOrder : 'asc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : true,
				showPageList:false,
				columns : [ [{
					field : 'id',
					title : '主键ID',
					width :$(this).width() * 0.08,//表示百分之8
					hidden : true
				}, {
					field : 'time',
					title : '新增时间',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdMatName',
					title : '牌号',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				},{
					field : 'mdShiftName',
					title : '班次',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdTeamName',
					title : '班组',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				},  {
					field : 'mdEquipmentName',
					title : '机台号',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'uName',
					title : '操作工',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'cb',
					title : '平均值',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'sb',
					title : '最大值',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'ps',
					title : '最小值',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = '<span style="color:red">102<span>';
						}else{
							value = "×";
						}
						return value;
					}
				}] ],
				toolbar : '#moldingCpToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');//提示信息
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					/* $('#moldingCpMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					}); */
				}
			});

		});
		

	   /** 查询所有*/	
		function getList() {
			moldingCpGrid.datagrid({
				url : tempUrl+"/getList.do",
				queryParams :$("#searchForm").form("getData")
			});
		}
	   /**清空*/
	   function cleanQuery(){
		   try{
			   $('#searchForm input').val(null);
			   $("#mdShiftBc").combobox("setValue", "");//下拉框赋值 班次
		       $("#mdEquipmentSb").combobox("setValue", "");//下拉框赋值 机台号,设备
		       $("#mdWorkshopCj").combobox("setValue", "");//下拉框赋值  班组
				//初始化时间
			    var today = new Date();
				var month=today.getMonth()+1;
				if(month<10){month=("0"+month);}
				var day=today.getDate();
				if(day<10){day=("0"+day);}
			    var date=today.getFullYear()+"-"+month+"-"+day;
			   $("#startTime").datebox("setValue",date);	//时间用这个
			   $("#endTime").datebox("setValue",date);	//时间用这个
			} catch(e){
				alert(e);
			}
		 	
	   }
		
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	
	<div id="moldingCpToolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
	 			<fieldset >
	 				<div>
						<span class="label">开始日期：</span>
						<input id="startTime" name="time" readOnly=true type="text"
						class="easyui-datebox" datefmt="yyyy-MM-dd HH:mm:ss"
						style="width: 130px" />
					</div>
					<div>
						<span class="label">结束日期：</span>
						<input id="endTime" name="endTime" readOnly=true type="text"
						class="easyui-datebox" datefmt="yyyy-MM-dd HH:mm:ss"
						style="width: 130px" />
					</div>
	 				<div >
						<span class="label">班次：</span> 
						<select id="mdShiftBc" name="mdShiftId" class="easyui-combobox"
							data-options="panelHeight:'auto',width:120,editable:false"></select>
					</div>
					<div >
						<span class="label">班组：</span>
						<select id="mdWorkshopCj" name="mdTeamId" class="easyui-combobox"
							data-options="panelHeight:'auto',width:120,editable:false"></select>
					</div>
					<div >
						<span class="label">机台号：</span>
						<select id="mdEquipmentSb" name="mdEquipmentId" class="easyui-combobox"
							data-options="panelHeight:'150',width:120,editable:false"></select>
					</div>
	 			</fieldset>
	 		</div>
		</form>
		<div class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/moldingCp/getList.do']}">
				<a onclick="getList();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a>
			</c:if>
			 <c:if test="${not empty sessionInfo.resourcesMap['/pms/moldingCp/cleanQuery.do']}">
				<a onclick="cleanQuery();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-refresh',plain:true">清空</a>
			</c:if>
		</div>
	</div>
	<div data-options="region:'center',border:false,split:false,title:''">
		<table id="moldingCpGrid"></table>		
	</div>
	
	
	<!-- 右键功能 -->
	<div id="moldingCpMenu" class="easyui-menu" style="width: 80px; display: none;">
	</div>
	
	
</body>
</html>