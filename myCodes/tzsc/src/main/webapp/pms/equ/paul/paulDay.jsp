<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备日保养</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			$.loadComboboxData($("#shiftId"),"SHIFT",true);
			$.loadComboboxData($("#teamId"),"TEAM",true);
			$.loadComboboxData($("#eqp_type_id"),"EQPCATEGORY",true);
			//初始化时间
		   var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		    $("#runtime").my97("setValue",sts);//
		    $("#endtime").my97("setValue",end);//
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				rownumbers :true,
				pageSize : 100,
				pageList : [ 10, 20, 30, 40, 50,100 ],
				singleSelect:true,
				checkOnSelect : true,
				selectOnCheck : false,
				nowrap : false,
				showPageList:false,
				scrollbarSize:180,
				columns : [ [ {
					field : 'id',
					title : 'Id',
					width : 100,
					align:'center',
					checkbox : true
				},{
					field : 'name',
					title : '名称',
					width : 100,
					align:'center'
				},{
					field : 'eqp_type_name',
					title : '设备类型',
					align:'center',
					width : 120
				}, {
					field : 'shiftName',
					title : '班次',
					align:'center',
					width : 70
				},{
					field : 'teamName',
					title : '班组',
					align:'center',
					width : 70
				},{
					field : 'date_p',
					title : '执行日期',
					align:'center',
					width : 180
				},{
					field : 'stim',
					title : '开始日期',
					align:'center',
					width : 180
				},{
					field : 'etim',
					title : '结束日期',
					align:'center',
					width : 180
				},{
					field : 'status',
					title : '状态',
					align:'center',
					width : 60,
					formatter : function(value, row, index) {
						var type = value;
						if(type=='0'){
							type="新增";
						}else if(type=='1'){
							type="审核通过";
						}else if(type=='2'){
							type="运行";
						}else if(type=='3'){
							type="停止运行";
						}
						return type;
					}
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
		/*
		* 查询设备故障
		*/
		function queryBean() {
			var tempUrl ="${pageContext.request.contextPath}/pms/paul";
			dataGrid.datagrid({
				url : tempUrl+"/queryDataGrid.do",
				queryParams :$("#searchForm").form("getData")
			});
			$('#dataGrid').datagrid('clearSelections');
		}
		function clearForm(){
			$("#searchForm input").val(null);
		    $("#eqp_type_id").combobox("setValue", "");//下拉框赋值 
			$("#shiftId").combobox("setValue", "");//下拉框赋值
			$("#teamId").combobox("setValue", "");//下拉框赋值
			$("#status").combobox("setValue", "-2");//下拉框赋值
		}
		//批量添加日保养
		function batchAdd(){
			var dialog=parent.$.modalDialog({
				title : '批量新增设备日保养',
				width : 780,
				height : 380,
				href : '${pageContext.request.contextPath}/pms/equ/paul/bacthPaul.jsp',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");	
						if(f.form("validate")){
							parent.$.messager.progress({
								title:'',
								msg:'正在生成计划...',
								interval:'1000'
							}); 
							   var pams = {
									eqp_type_id : f.form("getData").eqp_type_id,
									zao : f.form("getData").zao,
									zhong : f.form("getData").zhong,
									wan : f.form("getData").wan,
									zaoT : f.form("getData").zaoT,
									zhongT : f.form("getData").zhongT,
									paul_category : f.form("getData").paul_category,
									date : f.form("getData").date,
									del : 0,
									date2:f.form("getData").date2,
							        zaoTD:f.form("getData").zaoTD,
							        zhongTD:f.form("getData").zhongTD,
							        paul_category2:f.form("getData").paul_category2,
							     	paul_category3:f.form("getData").paul_category3
								}; 
							 $.post("${pageContext.request.contextPath}/pms/paul/batchAdd.do",pams,function(json){
								 parent.$.messager.progress('close');
								 if (json.success) {
									$.messager.show('提示', '添加成功', 'info');
									dialog.dialog('destroy');
									queryBean();
								}else{
									$.messager.show('提示', "添加失败", 'error');
								}
							},"JSON"); 
						}
					}
				} ]
			});
		}
		//日保养日历
		function paulCal(){
			var dialogs=parent.$.modalDialog({
				title : '设备轮保日历',
				width : 740,
				height : 590,
				href : '${pageContext.request.contextPath}/pms/equ/paul/paulCat.jsp',
				buttons : [ {
					text : '关闭',
					iconCls:'icon-standard-disk',
					handler : function() {
						dialogs.dialog('destroy');
					}
				} ]
			});
		}
		//增加日保养
		function addPaul(){
			var dialog=parent.$.modalDialog({
				title : '新增设备日保养计划',
				width : 750,
				height : 320,
				href : '${pageContext.request.contextPath}/pms/equ/paul/addPaul.jsp',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							 $.post("${pageContext.request.contextPath}/pms/paul/addPaul.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', '添加成功', 'info');
									dialog.dialog('destroy');
									queryBean();
								}else{
									$.messager.show('提示', "添加失败", 'error');
								}
							},"JSON"); 
						}
					}
				} ]
			});
		}
		//修改日保养
		function editPaul(){
			var row = dataGrid.datagrid('getSelected');
			var dialog=parent.$.modalDialog({
				title : '修改设备日保养计划',
				width : 750,
				height : 320,
				href : '${pageContext.request.contextPath}/pms/paul/gotoEdit.do?id='+row.id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							 $.post("${pageContext.request.contextPath}/pms/paul/editPaul.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', '修改成功', 'info');
									dialog.dialog('destroy');
									queryBean();
								}else{
									$.messager.show('提示', "修改失败", 'error');
								}
							},"JSON"); 
						}
					}
				} ]
			});
		}
		
		/**
		*批量审核工单
		*/
		function checkWork() {
			var rows = dataGrid.datagrid('getChecked');
			var ids = [];
			if (rows.length > 0) {
				parent.$.messager.confirm('确认', '您是否确认审核选中行?', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
						}
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post('${pageContext.request.contextPath}/pms/paul/checkWork.do', 
						{
							ids : ids.join(','),
							status:status
						}, function(json) {
							parent.$.messager.progress('close');
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								queryBean();
							} else {
								$.messager.show('提示', json.msg, 'info');
							}
						},"JSON");
					}
				});
			} else {
				parent.$.messager.show({
					title : '提示',
					msg : '请勾选要需要审核的数据!'
				});
			}
		}
		
		/**
		*批量运行计划
		*/
		function runWork() {
			var rows = dataGrid.datagrid('getChecked');
			var ids = [];
			if (rows.length > 0) {
				parent.$.messager.confirm('确认', '您是否确认运行选中行?', function(r) {
					if (r) {
						for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
						}
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post('${pageContext.request.contextPath}/pms/paul/runWork.do', 
						{
							ids : ids.join(','),
							status:status
						}, function(json) {
							parent.$.messager.progress('close');
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								queryBean();
							} else {
								$.messager.show('提示', json.msg, 'info');
							}
						},"JSON");
					}
				});
			} else {
				parent.$.messager.show({
					title : '提示',
					msg : '请勾选要需要审核的数据!'
				});
			}
		}
		
		/**
		*审核计划
		*/
		function checkPaul() {
			var rows = dataGrid.datagrid('getSelected');
			if(rows.status!="0"){
				$.messager.show('审核失败', '只有新增的机会可以审核', 'info');
				return;
			}
			parent.$.messager.confirm('确认', '您是否确认审核该计划?', function(r) {
				if (r) {
					var id=rows.id;
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/paul/checkPaul.do', 
					{
						id : id
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryBean();
						} else {
							$.messager.show('提示', json.msg, 'info');
						}
					},"JSON");
				}
			});
		}
		/**
		*运行计划
		*/
		function runPaul() {
			var rows = dataGrid.datagrid('getSelected');
			if(rows.status!="1"){
				$.messager.show('运行失败', '只有审核通过的计划可以运行', 'info');
				return;
			}
			parent.$.messager.confirm('确认', '您是否确认运行该计划?', function(r) {
				if (r) {
					var id=rows.id;
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/paul/runPaul.do', 
					{
						id : id
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryBean();
						} else {
							$.messager.show('提示', json.msg, 'info');
						}
					},"JSON");
				}
			});
		}
		/**
		*删除计划
		*/
		function delPaul() {
			var rows = dataGrid.datagrid('getSelected');
			parent.$.messager.confirm('确认', '您是否确认删除该计划?', function(r) {
				if (r) {
					var id=rows.id;
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/paul/delPaul.do', 
					{
						id : id
					}, function(json) {
						parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryBean();
						} else {
							$.messager.show('提示', json.msg, 'info');
						}
					},"JSON");
				}
			});
		}
		/**
		*批量删除
		*/
		function deletePaul() {
			var rows = dataGrid.datagrid('getChecked');
			var ids = [];
			if (rows.length > 0) {
				parent.$.messager.confirm('确认', '您是否要删除当前选中得数据', function(r) {
					if (r) {
						var flag = false;
						for ( var i = 0; i < rows.length; i++) {
								ids.push(rows[i].id);
						}
						parent.$.messager.progress({
							title : '提示',
							text : '数据处理中，请稍后....'
						});
						$.post('${pageContext.request.contextPath}/pms/paul/deletePaul.do', 
						{
							ids : ids.join(','),
						}, function(json) {
							parent.$.messager.progress('close');
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								queryBean();
							} else {
								$.messager.show('提示', json.msg, 'info');
							}
						},"JSON");
					}
				});
			} else {
				parent.$.messager.show({
					title : '提示',
					msg : '请勾选要删除的记录!'
				});
			}
		}
		function appendTables() {
		// add a unselected tab panel
		$('#bd').tabs("add",{title:'ceshi',content:'Tab Body',
		    closable:true});
		
		}
</script>
</head>
<body id='bd' class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">设备类型：</span>
						<select id="eqp_type_id" name="eqp_type_id" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">班次：</span>
						<select id="shiftId" name="shiftId" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label">班组：</span>
						<select id="teamId" name="teamId" class="easyui-combobox" data-options="width:100,editable:false"></select>
					</div>
					
					<div >
						<span class="label" style="width:120px">计划开始时间：</span>
						<input id="runtime" name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:90px"/>
					</div>	
					
					<div >
						<span class="label" style="width:10px">到</span>
						<input id="endtime" name="etim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>					 
					</div>	
					<div >
						<span class="label" >状态</span>
						<select id="status" name="status" class="easyui-combobox" name="dept" style="width:100px;">  
							<option value="-2">全部</option>  
						    <option value="0">新增</option>   
						    <option value="1">审核通过</option> 
						    <option value="2">运行</option> 
						    <option value="3">停止运行</option> 
						    <option value="4">操作完成</option> 
						    <option value="5">审核完毕</option> 
						</select>
					</div>			
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
				<a onclick="queryBean()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/addPaul.do']}">	
					<a onclick="addPaul();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">增加</a>
				</c:if> --%>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/batchAdd.do']}">	
					<a onclick="batchAdd();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">批量新增</a>
				</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/editPaul.do']}">	
					<a onclick="editPaul();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">修改</a>
				</c:if>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/gotoEdits.do']}">	
					<a onclick="editPaul(false);" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">超级修改</a>
				</c:if> --%>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/checkWork.do']}">
					<a onclick="checkWork();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-switch',plain:true">批量审核</a>
		    	</c:if> --%>
		    	<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/runWork.do']}">
					<a onclick="runWork();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-arrow-switch',plain:true">批量运行</a>
		    	</c:if>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/queryPaulCalendar.do']}">
					<a onclick="paulCal();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-application-go'">日保日历</a>
				</c:if>
					
				<!-- star:这里没有加权限 -->
					<a onclick="deletePaul();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">批量删除</a>
				<!-- end -->
		</div>
	</div>
	<div id="appendTables" data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/addPaul.do']}">
			<div onclick="addPaul()" data-options="iconCls:'icon-standard-plugin-add'">添加</div>
		</c:if> --%>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/editPaul.do']}">
			<div onclick="editPaul()" data-options="iconCls:'icon-standard-plugin-edit'">编辑</div>
		</c:if>
		<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/checkPaul.do']}">
			<div onclick="checkPaul()" data-options="iconCls:'icon-standard-plugin-edit'">审核</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/runPaul.do']}">
			<div onclick="runPaul()" data-options="iconCls:'icon-standard-plugin-edit'">运行</div>
		</c:if> --%>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/delPaul.do']}">
			<div onclick="delPaul();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>
</body>
</html>