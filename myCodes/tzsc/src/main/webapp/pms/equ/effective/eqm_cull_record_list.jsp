<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>设备剔除时间管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="../../pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var dataGrid=null;
		$(function() {
			//下拉赋值
			$.loadComboboxData($("#shift_id"),"SHIFT",true);
			$.loadComboboxData($("#eqp_id"),"ALLEQPS",true);
			//初始化时间
		    var d=new Date();
			var sts=d.getFullYear()+"-" + (d.getMonth()+1) + "-1";
			var end = d.getFullYear()+"-" + (d.getMonth()+1) + "-" + d.getDate();
		    $("#runtime").my97("setValue",sts);//
		    $("#endtime").my97("setValue",end);	//
			dataGrid = $('#dataGrid').datagrid({
				fit : true,
				fitColumns : false,
				border : false,
				pagination : true,
				idField : 'id',
				striped : true,
				rownumbers :true,
				pageSize : 10,
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
					field : 'eqp_name',
					title : '设备名称',
					width : 100,
					align:'center'
				},{
					field : 'shift_name',
					title : '班次',
					align:'center',
					width : 100
				}, {
					field : 'st_date',
					title : '开始时间',
					align:'center',
					width : 140
				},{
					field : 'ed_date',
					title : '结束时间',
					align:'center',
					width : 140
				},{
					field : 'remark',
					title : '备注',
					align:'center',
					width : 180
				},{
					field : 'stop_time',
					title : '停机总时间/分钟',
					align:'center',
					width : 100
				},{
					field : 'type_id',
					title : '停机类型',
					align:'center',
					width : 100
				},{
					field : 'type_name',
					title : '停机原因',
					align:'center',
					width : 100
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
			var tempUrl ="${pageContext.request.contextPath}/pms/effect";
			dataGrid.datagrid({
				url : tempUrl+"/queryCullRecord.do",
				queryParams :$("#searchForm").form("getData")
			});
		}
		function clearForm(){
			$("#searchForm input").val(null);
		}
		
	
		//增加
		function addPaul(){
			var dialog=parent.$.modalDialog({
				title : '新增/添加',
				width : 850,
				height : 420,
				href : '${pageContext.request.contextPath}/pms/equ/effective/eqm_cull_record_add.jsp',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							 $.post("${pageContext.request.contextPath}/pms/effect/addCullRecord.do",f.form("getData"),function(json){
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
		
		/**
		*单个删除
		*/
		function delPaul(zid) {
			var rows = dataGrid.datagrid('getSelected');
			parent.$.messager.confirm('确认', '您是否确认删除该计划?', function(r) {
				if(r){
				    var id="";
					if(zid=='1'){ //批量删除
					   return false;
					}else if(zid=='2'){ //右击单条删除
						id=rows.id;
					}
					/* parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					}); */
					$.post('${pageContext.request.contextPath}/pms/effect/deleteCullRecordById.do', 
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
		
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div id="toolbar" style="display: none;">
		<form id="searchForm" style="margin:4px 0px 0px 0px">
			<div class="topTool">
				<fieldset >
					<div >
						<span class="label">设备名称：</span>
						<select id="eqp_id" name="eqp_id" class="easyui-combobox" data-options="panelHeight:'auto',width:100,panelHeight:150,editable:false"></select>
					</div>
					<div >
						<span class="label">班次：</span>
						<select id="shift_id" name="shift_id" class="easyui-combobox" data-options="panelHeight:'auto',width:100,editable:false"></select>
					</div>
					<div >
						<span class="label" >停机类型：</span>
						<select id="type_id" name="type_id" class="easyui-combobox" data-options="panelHeight:'auto'"  style="width:100px;">   
						    <option value="">请选择</option>   
						    <option value="1">固定停机</option>   
						    <option value="2">不可控停机</option> 
						    <!-- <option value="3">轮保剔除</option> 
						    <option value="4">日保剔除</option> -->
						</select> 
					</div>
					<div>
						<span class="label" style="width:120px">计划日期范围：</span>
						<input id="runtime" name="runtime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:90px"/>
					</div>	
					
					<div >
						<span class="label" style="width:10px">到</span>
						<input id="endtime" name="endtime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:100px"/>					 
					</div>	
					
				</fieldset>
				</div>
		</form>
		<div class="easyui-toolbar">
				<a onclick="queryBean()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/addPaul.do']}">	
					<a onclick="addPaul();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">增加</a>
				</c:if>
				<%-- <c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/editPaul.do']}">	
					<a onclick="delPaul('1')" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-delete'">删除</a>
				</c:if> --%>
		</div>
	</div>
	<div data-options="region:'center',border:false">
		<table id="dataGrid"></table>
	</div>
	<div id="menu" class="easyui-menu" style="width: 80px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/addPaul.do']}">
			<div onclick="addPaul()" data-options="iconCls:'icon-standard-plugin-add'">添加</div>
		</c:if>
		
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/paul/delPaul.do']}">
			<div onclick="delPaul('2');" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
		</c:if>
	</div>
</body>
</html>