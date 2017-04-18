<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<title>辅料奖罚金额管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<link href="${pageContext.request.contextPath}/css/toptoolbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
		var typeGrid = null;
		var paramGrid=null;
		//load from 
		$(function() {
			$.loadComboboxData($("#eqpTypeID"),"EQPTYPE",true);
			$.loadComboboxData($("#mdMatId"),"MATPROD",true);
			typeGrid = $('#typeGrid').datagrid({
				fitColumns:true,
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
				columns : [ [ {
					field : 'id',
					title : '设备型号id',
					width : 120,
					hidden : true
				}, {
					field : 'eqpTypeName',
					title : '设备型号',
					width : 100,
					align:'center',
					sortable : true
				}, {
					field : 'mdMatName',
					title : '辅料名称',
					align:'center',
					width : 150,
					sortable : true
				}, {
					field : 'version',
					title : '版本号',
					align:'center',
					width : 100,
					sortable : true
				}, {
					field : 'status',
					title : '状态',
					align:'center',
					width : 70,
					sortable : true
				}, {
					field : 'statusLasting',
					title : '状态变化时间',
					align:'center',
					width : 90,
					sortable : true
				}  ] ],
				toolbar : '#typeToolbar',
				onLoadSuccess : function() {
					$(this).datagrid('tooltip');
				},
				onRowContextMenu : function(e, rowIndex, rowData) {
					e.preventDefault();
					$(this).datagrid('unselectAll').datagrid('uncheckAll');
					$(this).datagrid('selectRow', rowIndex);
					$('#typeMenu').menu('show', {
						left : e.pageX-10,
						top : e.pageY-5
					}); 
				}, onClickRow:function(rowIndex,rowData){
					queryParamBean(rowData.id);
				}
			});
			
			$(function() {
				paramGrid = $('#paramGrid').datagrid({
					fitColumns:true,
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
					columns : [ [ {
						field : 'id',
						title : 'Row Id',
						width : 120,
						hidden : true
					}, {
						field : 'matName',
						title : '辅料名称',
						width : 180,
						align:'center',
						sortable : true
					}, {
						field : 'uval',
						title : '上限',
						align:'center',
						width : 50,
						sortable : true
					}, {
						field : 'val',
						title : '标准',
						align:'center',
						width : 50,
						sortable : true
					}, {
						field : 'lval',
						title : '下限',
						align:'center',
						width : 50,
						sortable : true
					},{
						field : 'unitprice',
						title : '奖罚单价',
						align:'center',
						width : 90,
						sortable : true
					}, {
						field : 'remark',
						title : '备注',
						align:'left',
						width : 90,
						sortable : true
					}  ] ],
					toolbar : '#paramToolbar',
					onLoadSuccess : function() {
						$(this).datagrid('tooltip');
					},
					onRowContextMenu : function(e, rowIndex, rowData) {
						e.preventDefault();
						$(this).datagrid('unselectAll').datagrid('uncheckAll');
						$(this).datagrid('selectRow', rowIndex);
						$('#typeMenu').menu('show', {
							left : e.pageX-10,
							top : e.pageY-5
						}); 
					}, onClickRow:function(rowIndex,rowData){
						//alert(rowIndex);
						
					}
				});
			});
			queryBean();
		});
	   function queryParamBean(id){
		   paramGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/matassess/getChildByParentId.do?id=" + id
		   });
	   }
	   
	  
	  
	   //查询
	   function queryBean(){
		   typeGrid.datagrid({
				url : "${pageContext.request.contextPath}/pms/matassess/queryBeanByExtend.do",
				queryParams :$("#typeSearchForm").form("getData")
			});
	   }
	   
	   //生效
	   function editStatus(){
			var id=typeGrid.datagrid("getSelected").id;
			parent.$.messager.confirm('确认生效', function(b) {
				if (b) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${pageContext.request.contextPath}/pms/matassess/editStatus.do', {
						id : id
					},function(json) {
					    parent.$.messager.progress('close');
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryBean();
						}else{
							$.messager.show('提示', json.msg, 'info');
						}
					}, 'JSON');
				}
			});
		}
	   //跳转到添加页面
	   function gotoAddBean(){
		   var dialog = parent.$.modalDialog({
				title : '新增辅料考核版本信息',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/matassess/gotoBean.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/matassess/addOrUpdateBean.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryBean();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   //跳转到编辑页面
	   function gotoEditBean(){
		   var dialog = parent.$.modalDialog({
				title : '编辑辅料考核版本信息',
				width : 620,
				height : 200,
				href : '${pageContext.request.contextPath}/pms/matassess/gotoEditBean.do?id='+typeGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/matassess/addOrUpdateBean.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryBean();
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   //跳转到添加页面
	   function gotoAddParamBean(){
		   row=typeGrid.datagrid('getSelected');
		   if(row==null||row==""){
			   $.messager.show('提示', "请选中一个版本行", 'info');
		   }
		   var dialog = parent.$.modalDialog({
				title : '新增辅料考核详细标准',
				width : 620,
				height : 610,
				href : '${pageContext.request.contextPath}/pms/matassessParam/gotoBean.do?parentId='+typeGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/matassessParam/addOrUpdateBean.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryParamBean(typeGrid.datagrid('getSelected').id);
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   //跳转到编辑详细页面
	   function gotoEditParamBean(){
		   var dialog = parent.$.modalDialog({
				title : '编辑辅料考核版本信息',
				width : 620,
				height : 310,
				href : '${pageContext.request.contextPath}/pms/matassessParam/gotoEditBean.do?id='+paramGrid.datagrid('getSelected').id,
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/matassessParam/addOrUpdateBean.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryParamBean(typeGrid.datagrid('getSelected').id);
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
							},"JSON");
						}
					}
				} ]
			});
	   }
	   //删除辅料奖罚金额
	   function deleteCosMat(){
			var row = categoryGrid.datagrid('getSelected');
			parent.$.messager.confirm('操作提示', '是否删除？', function(b) {
				if (b) {
					$.post('${pageContext.request.contextPath}/pms/param/deleteCosMat.do', {
						id : row.id
					}, function(json) {
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							queryCosMat();
						} else {
							$.messager.show('提示', json.msg, 'error');
						}
					}, 'JSON');
				}
			});
		}
		function clearForm(){
			$("#typeSearchForm input").val(null);
		}
</script>
</head>
<body class="easyui-layout" data-options="fit : true,border : false">
	<div data-options="region:'west',border:true,split:false,title:'辅料考核参数版本管理'" style="width:560px;">
		<div id="typeToolbar" style="display: none;width:100%;">
			<form id="typeSearchForm" style="margin:4px 0px 0px 0px">
				<div class="topTool">
					<fieldset >
						<div style="width:160px;">
							<span>设备型号</span>
							<span><input type="text" id="eqpTypeID" name="eqpTypeID" class="easyui-combobox easyui-validatebox" data-options="textField:'name',valueField:'id',width:85,editable:false"/></span>
						</div>
						<div style="width:220px;">
							<span>牌号</span>
							<select id="mdMatId" name="mdMatId" class="easyui-combobox easyui-validatebox"  data-options="panelHeight:200,width:150,editable:false"></select>
						</div>
						<div style="width:150px;"> 
							<span>状态</span>
							<select id="status" name="status" class="easyui-combobox easyui-validatebox"  data-options="panelHeight:'auto',width:80,editable:false">
							<option value="">全部</option> 
							<option value="新建">新建</option>
							<option value="生效">生效</option>
							<option value="归档">归档</option>
							</select>
						</div>
					</fieldset>
				</div>
			</form>
			<div class="easyui-toolbar" >
				<a onclick="queryBean()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a>
				<a onclick="clearForm();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-table-refresh'">重置</a>
				<a onclick="gotoAddBean();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
				<a onclick="gotoEditBean()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑</a>
				<a onclick="editStatus()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">生效</a>
			</div>
		</div>
		<table id="typeGrid"></table>
	</div>
	<div data-options="region:'center',border:true,title:'辅料考核参数版本详情'" style="width:560px;">
		<div id="paramToolbar"  style="display: none;">
			<form id="paramForm" style="margin:4px 0px 0px 0px">
			</form>
			<div class="easyui-toolbar">				
				<a onclick="gotoAddParamBean();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
				<a onclick="gotoEditParamBean();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑</a>
			</div>
		</div>
		<table id="paramGrid"></table>
	</div>
	
	
	<!-- <div id="typeMenu" class="easyui-menu" style="width: 40px; display: none;">
		<a onclick="gotoAddBean();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
		<a onclick="gotoEditBean()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑</a>
		<a onclick="editStatus()" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">生效</a>
	</div>
	<div id="paramGrid" class="easyui-menu" style="width: 80px; display: none;">
		<a onclick="gotoAddParamBean();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a>
		<a onclick="gotoEditParamBean();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-edit'">编辑</a>
	</div> -->
</body>
</html>