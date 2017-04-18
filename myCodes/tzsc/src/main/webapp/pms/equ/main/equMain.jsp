<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>设备管理</title>
<meta title="开发" content="刘力攻"/>
<meta title="二次开发" content="leejean"/>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">

		var dataGrid=null;
		$(function() {		
			$.loadComboboxData($("#workShopName"),"WORKSHOP",true);
			$.loadComboboxData($("#eqpTypeName"),"EQPTYPE",true);
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 10,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				remoteSort: false,
				frozenColumns : [ [ {
					field : 'id',
					title : '设备id',
					width : 120,
					checkbox : true
				}, {
					field : 'equipmentCode',
					title : '设备编号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'equipmentName',
					title : '设备名称',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'equipmentDesc',
					title : '设备描述',
					align:'center',
					width : 150,
					sortable : true
				},{
					field : 'eqpTypeName',
					title : '机型',
					align:'center',
					width : 70,
					sortable : true
				} ] ],
				columns : [ [
				{
					field : 'workShopName',
					title : '车间',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'workCenter',
					title : '工序段',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'equipmentPosition',
					title : '设备位置',
					align:'center',
					width : 130,
					sortable : true
				},{
					field : 'ratedSpeed',
					title : '额定车速',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'rateSpeedUnit',
					title : '额定车速单位',
					align:'center',
					width : 150,
					sortable : true
				},{
					field : 'yieId',
					title : '台时产量',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'yieldUnit',
					title : '台时产量单位',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'enabled',
					title : '是否启用',
					align:'center',
					width : 70,
					/* formatter : function(value, row, index) {
						return value==0?'<span style="color:red">禁用<span>':'<span style="color:green">启用<span>';
					} */
				},{
					field : 'fixedAssetNum',
					title : '固定资产编号',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'manufacturingNum',
					title : '出厂编号',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'approvalNum',
					title : '批准文号',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'navicertNum',
					title : '准运证编号',
					align:'center',
					width : 100,
					sortable : true
				},{
					field : 'fixedAssetFlag',
					title : '已入固',
					align:'center',
					width : 80,
					/* formatter : function(value, row, index) {
						return value==1?'<span style="color:red">未入固<span>':'<span style="color:green">已入固<span>';
					} */
				},{
					field : 'usingDepartment',
					title : '使用部门',
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
		function clearForm(){
			$("#searchForm input").val(null);
			$("#eqpTypeName").combobox("setValue", "");//下拉框赋值
			$("#workShopName").combobox("setValue", "");//下拉框赋值
		}
		/**
		* 查询文件
		*/	
		
		function queryEqu() {
			dataGrid.datagrid({
						url : "${pageContext.request.contextPath}/pms/equc/queryEqu.do",
						queryParams :$("#searchForm").form("getData"),
						onLoadError : function(data) {
							$.messager.show('提示', "查询用户异常", 'error');
						}
					});
		}
		
	function editEqu(){
		var dialog = parent.$.modalDialog({
			title : '设备主数据编辑',
			width : 620,
			height : 570,
			href : '${pageContext.request.contextPath}/pms/equc/goToEquEdit.do?id='+dataGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/equc/addEqu.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryEqu();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	} 
	
	function deleteEqu(){
		var row = dataGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前设备主数据？', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/equc/deleteEqu.do', {
					id : row.id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						queryEqu();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	

	

	function goToEqucMain(){
		var dialog = parent.$.modalDialog({
			title : '设备主数据添加',
			width : 620,
			height : 570,
			href : '${pageContext.request.contextPath}/pms/equc/goToEqucMain.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/equc/addEqu.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								queryEqu();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
 //导出execl---2015.9.9--张璐 
  function derive(){
			var recordUrl ="${pageContext.request.contextPath}/pms/equc/excelDeriveJuanBaoCP.do?equipmentCode="+$("#searchForm").form("getData").equipmentCode+"&equipmentName="+$("#searchForm").form("getData").equipmentName+"&eqpTypeName="+$("#searchForm").form("getData").eqpTypeName+"&workShopName="+$("#searchForm").form("getData").workShopName;
		   	var scWidth=parent.window.screen.width/2;  //屏幕分辨率的宽：
		   	var scHight=parent.window.screen.height/2;  //屏幕分辨率的高：
			//toolbar=no   是否显示工具栏，yes为显示；menubar，scrollbars   表示菜单栏和滚动栏。resizable=no   是否允许改变窗口大小，yes为允许；location=no   是否显示地址栏，yes为允许；  status=no   是否显示状态栏内的信息（通常是文件已经打开），yes为允许；   
			parent.window.open(recordUrl,"ExcelWindow","toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes, resizable=no,copyhistory=no,width="+scWidth+",height="+scHight+",left=400,top=200");
		
   }
 //excel导入
 function inputExcel(){
	 parent.$.modalDialog({
			title : '批量导入',
			width : 700,
			height : 350,
			href : '${pageContext.request.contextPath}/pms/equ/main/iframe_excel.jsp',
		});
 }
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
		<div class="topTool">
				<fieldset >
					<div >
						<span class="label">设备编号：</span>
						<input type="text" name="equipmentCode" class="easyui-validatebox "  data-options="prompt: '请输入设备编号'"/>
					</div>
					<div >
						<span class="label">设备名称：</span>
						<input type="text" name="equipmentName" class="easyui-validatebox "  data-options="prompt: '请输入设备名称'"/>
					</div>
					<div >
						<span class="label">设备类别：</span>
						<input  id="eqpTypeName" name="eqpTypeName" data-options="panelHeight:200,width:100,editable:false" />
					</div>
					<div >
						<span class="label">所属车间：</span>
						<input  id="workShopName" name="workShopName" data-options="panelHeight:'auto',width:100,editable:false" />
					</div>
			
				
					<input type="hidden" name="workshop" value="1"/><!-- 车间code -->
				</fieldset>
			</div>
		</form>
			<div class="easyui-toolbar">
				<a onclick="queryEqu()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<a onclick="goToEqucMain();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
				<a onclick="derive();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-link'">导出Excel</a>
				<a onclick="inputExcel();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">Excel导入</a>
		</div>
	</div>	
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="editEqu()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		
		<div onclick="deleteEqu();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
	</div>
</body>
</html>