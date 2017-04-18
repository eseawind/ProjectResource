<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	//${pageContext.request.contextPath}/pms/sysOrg/getOrgUsers.do?id=${checkedRoleId}
	var bomGrid;
	var carftGrid;
	var workOrderId="${workOrderId}";
	var id;
	$(function() {
		bomGrid = $('#bomGrid').datagrid({
			url:"${pageContext.request.contextPath}/pms/bom/getBomByWorkOrder.do?workOrderId="+workOrderId,
			fit : true,
			fitColumns : true,
			border : false,
			pagination : false,
			idField : 'id',
			striped : true,
			remoteSort: false,
			rownumbers:true,
			singleSelect:true,
			checkOnSelect : true,
			selectOnCheck : false,
			nowrap : false,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'mat',
				title : '物料名称',
				align:'center',
				width : 120,
				sortable : true
			}, {
				field:'typeName',
				title:'简称',
				align:'center',
				width:100,
				sortable:true	
			},{
				field : 'qty',
				title : '计划投料',
				width : 60,
				align : 'right',
				sortable : true
			},{
				field : 'unit',
				title : '单位',
				width : 50,
				align:'center',
				sortable : true
			} ] ],
			toolbar : '#bomGrid-toolbar',
			onLoadSuccess : function() {								
				$(this).datagrid('tooltip');
			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll').datagrid('uncheckAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu2').menu('show', {
					left : e.pageX-10,
					top : e.pageY-5
				});
				id=rowData.id;
			}
		});
		 carftGrid = $('#carftGrid').datagrid({
			url:"${pageContext.request.contextPath}/pms/craft/getCraftByWorkOrder.do?workOrderId="+workOrderId,
			fit : true,
			fitColumns : false,
			border : false,
			pagination : false,
			idField : 'id',
			remoteSort: false,
			rownumbers:true,
			striped : true,
			singleSelect:true,
			checkOnSelect : true,
			selectOnCheck : false,
			nowrap : false,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
			}, {
				field : 'paramId',
				title : '参数ID',
				align:'center',
				width : 80,
				sortable : true
			}, {
				field : 'pcp',
				title : '过程控制参数',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'qc',
				title : '检验项目',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'pval',
				title : 'P值',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'zval',
				title : 'Z值',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'cpk',
				title : 'CPK',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'cp',
				title : 'CP',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'pp',
				title : 'PP',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'ppk',
				title : 'PPK',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'ppm',
				title : 'PPM',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'std',
				title : '标准值',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'uval',
				title : '标准值允差上限',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'lval',
				title : '标准值允差下限',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'cuval',
				title : '控制线上限',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'clval',
				title : '控制线下限',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'wuval',
				title : '警戒线上限',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'wlval',
				title : '警戒线下限',
				width : 60,
				align:'center',
				sortable : true
			}/* , {
				field : 'unit',
				title : '',
				width : 60,
				align:'center',
				
			} */, {
				field : 'dtm',
				title : '是否需要判定',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'dtmuval',
				title : '不合格上限',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'dtmlval',
				title : '不合格下限',
				width : 60,
				align:'center',
				sortable : true
			}, {
				field : 'paramType',
				title : '计数类型',
				width : 60,
				align:'center',
				sortable : true
			} ] ],
			toolbar : '#carftGrid-toolbar',
			onLoadSuccess : function() {								
				$(this).datagrid('tooltip');
			}
		});  
	});
	var dialog;
	function goToBomAddJsp(){
		dialog = parent.$.modalDialog({
			title : '投料信息添加',
			width : 350,
			height : 220,
			href : '${pageContext.request.contextPath}/pms/bom/goToBomAddJsp.do?orderId='+workOrderId,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/bom/addBom.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								bomGrid.datagrid("reload");
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
				}
			} ]
		});
	}
	function goToBomEditJsp(){
		dialog = parent.$.modalDialog({
			title : '投料信息编辑',
			width : 350,
			height : 220,
			href : '${pageContext.request.contextPath}/pms/bom/goToBomEditJsp.do?id='+id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find('#form');
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/bom/editBom.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								bomGrid.datagrid("reload");
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
					
				}
			} ]
		});
	}
	function deleteBom(){
		parent.$.messager.confirm('操作提示', '您是否要删除当前投料?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/bom/deleteBom.do', {
					id : id
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						bomGrid.datagrid("reload");
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div id="bomGrid-toolbar" style="display: none;">				
			<table style="height:36px">
				<tr>
					<td>
						<code>BOM物料清单</code>
					</td>
					<td>
						<a onclick="goToBomAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">添加辅料</a>
					</td>
				</tr>
			</table>
	</div>
	<div data-options="region:'west',border:false,width:340" title="" style="overflow: hidden;background-color: red">
		<table id="bomGrid"></table>
	</div>
	<div id="menu2" class="easyui-menu" style="width: 80px; display: none;">
		<div onclick="goToBomEditJsp();" data-options="iconCls:'icon-standard-user-edit'">查看详情</div>
		<div onclick="deleteBom();" data-options="iconCls:'icon-standard-user-edit'">编辑状态</div>	
	</div>
	<div id="carftGrid-toolbar" style="display: none;">				
			<table style="height:36px">
				<tr>
					<td>
						<code>工艺参数</code>
					</td>
				</tr>
			</table>
	</div>
	<div data-options="region:'center',border:false,width:315" title="" style="overflow: hidden;">
		<table id="carftGrid"></table>
	</div>
</div>