<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>换牌记录</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		var inputGrid = null;
		var exchgGrid=null;
		
		$(function() {
			
			$.loadComboboxData($("#hoShift"),"SHIFT",true);
			$.loadComboboxData($("#hoTeam"),"TEAM",true);
			$.loadComboboxData($("#hoMat"),"MATPROD",true);
			$.loadComboboxData($("#toShift"),"SHIFT",true);
			$.loadComboboxData($("#toTeam"),"TEAM",true);
			$.loadComboboxData($("#toMat"),"MATPROD",true);
			var d=new Date();
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
			$("#scrq_date").my97("setValue",end);
			
			exchgGrid = $('#exchgGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				remoteSort:false, 
				pagination : true,
				idField : 'id',
				striped : true,
				pageSize : 20,
				pageList : [ 10, 20, 30, 40, 50 ],
				sortName : 'id',
				sortOrder : 'desc',
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				rownumbers :true,
				nowrap : true,
				showPageList:false,
				columns : [ [ {
					field : 'id',
					title : '',
					width : 120,
					hidden : true
				}, {
					field : 'date',
					title : '日期',
					width : 80,
					align : 'center'
					,sortable:true
				}, {
					field : 'equipment',
					title : '机台',
					width : 120,
					align:'center'
						,sortable:true
				}, {
					field : 'hoOrderId',
					title : '交班工单',
					width : 120,
					align:'center'
					,sortable:true,
					hidden : true
				}, {
					field : 'hoShift',
					title : '班次',
					width : 60,
					align:'center',
					styler: function(value,row,index){
						return 'background-color:#E5EFFF';
						}
				}, {
					field : 'hoTeam',
					title : '班组',
					align : 'center',
					width : 60,
					styler: function(value,row,index){
						return 'background-color:#E5EFFF';
						}
				}, {
					field : 'hoMat',
					title : '前牌号',
					align : 'center',
					width : 120,
					sortable:true,
					styler: function(value,row,index){
					return 'background-color:#E5EFFF';
					}
				}, {
					field : 'hoOrderId',
					title : '后牌号工单',
					width : 120,
					align:'center',
					sortable:true,
					hidden : true
				}, {
					field : 'toMat',
					title : '后牌号',
					align : 'center',
					width : 120,
					styler: function(value,row,index){
						return 'background-color:#FCF3F4';
				    }
				}, {
					field : 'hoUser',
					title : '换牌用户',
					align : 'center',
					width : 100,
					sortable:true,
					styler: function(value,row,index){
						return 'background-color:#E5EFFF';
						}
				}, {
					field : 'hoTime',
					title : '换牌时间',
					align : 'center',
					width : 120,
					styler: function(value,row,index){
						return 'background-color:#E5EFFF';
						}
				} ] ],
				toolbar : '#exchgToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#shiftchgMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				},
				onClickRow:function(rowIndex,rowData){
					getExchgDetsByExchgId(rowData.id);
				}
			});
			inputGrid = $('#inputGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				//pagination : true,
				idField : 'id',
				striped : true,
				remoteSort:false, 
				pageSize : 20,
				pageList : [ 10, 20, 30, 40, 50 ],
				singleSelect:true,
				checkOnSelect : false,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				rownumbers :true,
				columns : [ [ {
					field : 'id',
					title : 'id',
					width : 120,
					hidden : true
				}, {
					field : 'mat',
					title : '物料名称',
					width : 200,
					sortable:true,
					align : 'center'
				}, {
					field : 'qty',
					title : '消耗量',
					align : 'right',
					sortable:true,
					width : 80
				}
				, {
					field : 'unit',
					title : '单位',
					align : 'left',
					width : 50,
					sortable : true
				} ] ],
				//toolbar : '#inputToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#inputMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					});
				}
			});
		});
	   /**
		* 查询
		*/	
		function getExchgs() {
			exchgGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/shiftexchg/getExchgs.do",
				queryParams :$("#exchgForm").form("getData"),
				onLoadError : function(data) {
					$.messager.show('提示', "查询异常", 'error');
				}
			});
			inputGrid.datagrid("loadData",[]);
		}
		function clearExchgForm(){
			$("#exchgForm input[type!=hidden]").val(null);
	   }
		function deleteExchg(){
			var row = exchgGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前生产数据?', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/shiftexchg/deleteExchg.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							getExchgs();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
		}

		function goToExchgAddJsp(){
			var dialog = parent.$.modalDialog({
				title : '换牌记录新增',
				width : 640,
				height : 440,
				href : '${pageContext.request.contextPath}/pms/shiftexchg/goToExchgAddJsp.do?workshop=1&type=2',//workshop 1卷包车间 2成型车间   //type     1换牌  2 换牌
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/shiftexchg/addExchg.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getExchgs();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}else{
							f.find("#hoOrder").focus();
						}
					}/* ,
					onLoad:function(){
						var f = dialog.find('#form'), ret = $.fn.dialog.defaults.onLoad();
						f.find("#hoOrder").focus();
						return ret;
					} */
				} ]
			});
		}
		function goToExchgEditJsp(){
			var dialog = parent.$.modalDialog({
				title : '换牌记录编辑',
				width : 640,
				height : 440,
				href : '${pageContext.request.contextPath}/pms/shiftexchg/goToExchgEditJsp.do?type=2&id='+exchgGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/shiftexchg/editExchg.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getExchgs();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
		}
	   function getExchgDetsByExchgId(id){
		   inputGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/shiftexchg/getExchgDetsByExchgId.do?id=" + id
		   });
	   }
	   function deleteExchgDet(){
		   var row = inputGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '您是否要删除当前消耗数据？', function(b) {
				if (b) {					
					$.post('${pageContext.request.contextPath}/pms/shiftexchg/deleteExchgDet.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							var index = inputGrid.datagrid("getRowIndex",row);
							inputGrid.datagrid("deleteRow",index);
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
	   }
	   //跳转到类型添加页面
	   function goToDetAddJsp(){
		   var row = exchgGrid.datagrid('getSelected');
		   if(row){
			   var dialog = parent.$.modalDialog({
					title : '物料结存添加',
					width : 260,
					height : 190,
					href : '${pageContext.request.contextPath}/pms/shiftexchg/goToDetAddJsp.do?id='+row.id+"&hoOrder="+row.hoOrderId,
					buttons : [ {
						text : '保存',
						iconCls:'icon-standard-disk',
						handler : function() {
							var f = dialog.find("#form");
							if(f.form("validate")){
								$.post("${pageContext.request.contextPath}/pms/shiftexchg/addExchgDet.do",f.form("getData"),function(json){
									if (json.success) {
										$.messager.show('提示', json.msg, 'info');
										dialog.dialog('destroy');
										getExchgDetsByExchgId(row.id);
									}else{
										$.messager.show('提示', json.msg, 'error');
									}
								},"JSON");
							}
						}
					} ]
				});
		   }else{
			   $.messager.show('提示', "请选择一条换牌记录!", 'info');
		   }
	   }
	   
	   //跳转到类型编辑页面
	   function goToDetEditJsp(){
		   var row = exchgGrid.datagrid('getSelected');
		   var dialog = parent.$.modalDialog({
				title : '物料结存编辑',
				width : 260,
				height : 190,
				href : "${pageContext.request.contextPath}/pms/shiftexchg/goToDetEditJsp.do?id="+inputGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/shiftexchg/editExchgDet.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									getExchgDetsByExchgId(row.id);
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'north',border:false,split:true,title:'换牌记录'" style="height:320px;">
		<div id="exchgToolbar"  style="display: none;width:100%;">
			<form id="exchgForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
					<div >
						<input type="hidden" name="type" value="2"/><!-- 1换牌 2换牌 -->
						<input type="hidden" name="workshop" value="1"/><!-- 1卷包 2成型 -->
						<span class="label">生产日期：</span>
						<input id="scrq_date" name="date" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>
					</div>
					<div >
						<span class="label">班次：</span>
						<select id="hoShift" name="hoShift" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">班组：</span>
						<select id="hoTeam" name="hoTeam" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">前牌号：</span>
						<select id="hoMat" name="hoMat" class="easyui-combobox" data-options="panelHeight:'200',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">后牌号：</span>
						<select id="toMat" name="toMat" class="easyui-combobox" data-options="panelHeight:'200',width:100,editable:false"></select>
					</div>
				
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/shiftexchg/getExchgs.do']}">
					<a onclick="getExchgs()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/shiftexchg/getExchgs.do']}">
					<a onclick="clearExchgForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/shiftexchg/goToExchgAddJsp.do']}">
					<a onclick="goToExchgAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
				</c:if>
			</div>
		</div>
		<table id="exchgGrid"></table>
	</div>
	
	<div data-options="region:'center',border:false,split:true,title:'物料结存'">
		<div class="easyui-toolbar" >
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/shiftexchg/goToDetAddJsp.do']}">
				<a onclick="goToDetAddJsp()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">新增</a>
			</c:if>
		</div>
		<table id="inputGrid"></table>
	</div>
	<div id="inputMenu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/shiftexchg/goToDetEditJsp.do']}">
			<div onclick="goToDetEditJsp()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/shiftexchg/deleteExchgDet.do']}">
			<div onclick="deleteExchgDet();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>
	<div id="shiftchgMenu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/shiftexchg/goToExchgEditJsp.do']}">
			<div onclick="goToExchgEditJsp()" data-options="iconCls:'icon-standard-plugin-add'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/shiftexchg/deleteExchg.do']}">
			<div onclick="deleteExchg();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
		</div>
</body>
</html>