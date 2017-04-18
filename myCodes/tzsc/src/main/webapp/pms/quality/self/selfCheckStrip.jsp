<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>外观质量检验记录</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var tempUrl ="${pageContext.request.contextPath}/pms/selfCheckStrip";
		var selfCheckStripGrid=null;
		$(function() {
			//初始化
			$.loadComboboxData($("#mdShiftBc"),"SHIFT",true);//加载下拉框数据
			$.loadComboboxData($("#mdEquipmentSb"),"ALLEQPS",true);//加载下拉框数据
			$.loadComboboxData($("#mdWorkshopCj"),"WORKSHOP",true);//加载下拉框数据
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
			selfCheckStripGrid = $('#selfCheckStripGrid').datagrid({
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
					field : 'checkFlag',
					title : '检验程序',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'time',
					title : '自检时间',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value!=null&&value.length>16){//hh:mm:ss
							value = value.substr(value.length-8, 5);//从11位开始 截取后5位
						}
						return value;
					}
				}, {
					field : 'mdMatName',
					title : '牌号',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdTeamName',
					title : '班组',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdShiftName',
					title : '班次',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdWorkshopName',
					title : '车间',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true
				}, {
					field : 'mdEquipmentName',
					title : '设备',
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
					title : '错包',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = "√";
						}else{
							value = "×";
						}
						return value;
					}
				}, {
					field : 'sb',
					title : '少包',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = "√";
						}else{
							value = "×";
						}
						return value;
					}
				}, {
					field : 'ps',
					title : '破损',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = "√";
						}else{
							value = "×";
						}
						return value;
					}
				}, {
					field : 'zt',
					title : '粘贴',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = "√";
						}else{
							value = "×";
						}
						return value;
					}
				}
				, {
					field : 'wz',
					title : '污渍',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = "√";
						}else{
							value = "×";
						}
						return value;
					}
				}, {
					field : 'dz',
					title : '倒装',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = "√";
						}else{
							value = "×";
						}
						return value;
					}
				}, {
					field : 'tmz',
					title : '透明纸',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = "√";
						}else{
							value = "×";
						}
						return value;
					}
				}, {
					field : 'lx',
					title : '拉线',
					width :$(this).width() * 0.08,//表示百分之8
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						if(value=='0'){
							value = "√";
						}else{
							value = "×";
						}
						return value;
					}
				}] ],
				toolbar : '#selfCheckStripToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');//提示信息
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					/* $('#selfCheckStripMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					}); */
				}
			});

		});
		

	   /** 查询所有*/	
		function getList() {
			selfCheckStripGrid.datagrid({
				url : tempUrl+"/getList.do",
				queryParams :$("#searchForm").form("getData")
			});
		}
	   /**清空*/
	   function cleanQuery(){
		   try{
			   $('#searchForm input').val(null);
			   $("#mdShiftBc").combobox("setValue", "");//下拉框赋值
		       $("#mdEquipmentSb").combobox("setValue", "");//下拉框赋值
		       $("#mdWorkshopCj").combobox("setValue", "");//下拉框赋值
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
		  
			//document.getElementById("mdShiftBc").value=1;
		 	
	   }
		
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	
	<div id="selfCheckStripToolbar" style="display: none;">
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
						<span class="label">车间：</span>
						<select id="mdWorkshopCj" name="mdWorkshopId" class="easyui-combobox"
							data-options="panelHeight:'auto',width:120,editable:false"></select>
					</div>
					<div >
						<span class="label">机台(设备)：</span>
						<select id="mdEquipmentSb" name="mdEquipmentId" class="easyui-combobox"
							data-options="panelHeight:'150',width:120,editable:false"></select>
					</div>
	 			</fieldset>
	 		</div>
		</form>
		<div class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/selfCheckStrip/getList.do']}">
				<a onclick="getList();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a>
			</c:if>
			 <c:if test="${not empty sessionInfo.resourcesMap['/pms/selfCheckStrip/cleanQuery.do']}">
				<a onclick="cleanQuery();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-refresh',plain:true">清空</a>
			</c:if>
		</div>
	</div>
	<div data-options="region:'center',border:false,split:false,title:''">
		<table id="selfCheckStripGrid"></table>		
	</div>
	
	
	<!-- 右键功能 -->
	<div id="selfCheckStripMenu" class="easyui-menu" style="width: 80px; display: none;">
	</div>
	
	
</body>
</html>