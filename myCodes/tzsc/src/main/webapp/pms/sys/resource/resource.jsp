<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>资源管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
	var treeGrid;
	var dialog;
	$(function() {
		treeGrid = $('#treeGrid').treegrid({
			idField : 'id',
			treeField : 'text',
			dataPlain: true,
			fit : true,
			striped : true,
			fitColumns : false,
			rownumbers :true,
			border : false,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'text',
				title : '资源名称',
				width : 200, editor: "text"
			}, {
				field : 'url',
				title : '资源路径',
				width : 280, editor: "text"
			}, {
				field : 'typ',
				title : '资源类型',
				width : 60,
				align : 'center',
				formatter:function(value,row,index){
					return value=="1"?"<span style='color:blue;'>菜单<span>":"功能";
				}
			}
			, {
				field : 'remark',
				title : '描述',
				width : 250, editor: "text"
			}
			, {
				field : 'seq',
				title : '排序',
				width : 40, editor: "text"
			}, {
				field : 'securityLevel',
				title : '安全级别',
				width : 60,
				align:'center', editor: "text"
			}, {
				field : 'enable',
				title : '状态',
				width : 40,
				align:'center',
				formatter : function(value, row, index) {
					return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
				}
			}, {
				field : 'pname',
				title : '上级资源',
				width : 80
			} ] ],
			toolbar : '#toolbar',
			onContextMenu : function(e, row) {
				 e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				}); 
			},
			onLoadSuccess : function() {
				parent.$.messager.progress('close');

				$(this).treegrid('tooltip');
			},
            enableHeaderClickMenu: false,
            enableHeaderContextMenu: false,
            enableRowContextMenu: false,
            autoFocusField: "url",
            autoEditing: true,          //该属性启用双击行时自定开启该行的编辑状态
            extEditing: true,           //该属性启用行编辑状态的 ExtEditing 风格效果，该属性默认为 true。
            singleEditing: true,         //该属性启用datagrid的只允许单行编辑效果，该属性默认为 true。
            onAfterEdit: function(data) {
                $.fn.treegrid.extensions.onAfterEdit.apply(this, arguments);  //这句一定要加上
               $.post("${pageContext.request.contextPath}/pms/sysRes/editResource.do",
            		   {id  :data.id,
            	   		seq :data.seq,
            	   		text:data.text,
            	   		url:data.url,
            	   		remark:data.remark
            	   },
            		   function(json){
					if (json.success) {
						//$.messager.show('提示', json.msg, 'info');
						getAllResources();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				},"JSON");
            },
            onCancelEdit:function(){
            	//
            }
		});
	});
	function redo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('expandAll', node.id);
		} else {
			treeGrid.treegrid('expandAll');
		}
	}

	function undo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('collapseAll', node.id);
		} else {
			treeGrid.treegrid('collapseAll');
		}
	}
	function getAllResources() {
		treeGrid.treegrid({url : "${pageContext.request.contextPath}/pms/sysRes/getAllResources.do"});
	}
	function goToResourceAddJsp(b){
		var dialog = parent.$.modalDialog({
			title : '资源添加',
			width : 620,
			height : 390,
			href : '${pageContext.request.contextPath}/pms/sysRes/goToResourceAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/sysRes/addResource.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllResources();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ],
			onLoad:function(){
				if(b){
					var row = treeGrid.datagrid('getSelected');
					dialog.find("#pidComboTree").combotree({value:row.typ==2?row.pid:row.id});
			  	} 
			}
			
		});
	}
	function goToResourceEditJsp(){
		var dialog = parent.$.modalDialog({
			title : '资源编辑',
			width : 620,
			height : 390,
			href : '${pageContext.request.contextPath}/pms/sysRes/goToResourceEditJsp.do?id='+treeGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var f = dialog.find("#form");
					if(f.form("validate")){
						$.post("${pageContext.request.contextPath}/pms/sysRes/editResource.do",f.form("getData"),function(json){
							if (json.success) {
								$.messager.show('提示', json.msg, 'info');
								dialog.dialog('destroy');
								getAllResources();
							}else{
								$.messager.show('提示', json.msg, 'error');
							}
						},"JSON");
					}
				}
			} ]
		});
	}
	function deleteResource(){
		var row = treeGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前资源?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/sysRes/deleteResource.do', {
					id : row.id,
					name : row.name
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllResources();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
			<table id="treeGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		 <table style="margin:4px 0px 0px 0px">
		 	<tr>
		 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRes/getAllResources.do']}">
		 		<td><a onclick="getAllResources();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-zoom'">查询</a></td>
			</c:if>
		 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRes/addResource.do']}">
				<td><a onclick="goToResourceAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-plugin-add'">添加</a></td>
			</c:if>
				<td><a onclick="redo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-arrow-out'">展开</a></td>
				<td><a onclick="undo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-arrow-in'">折叠</a></td>
		 	</tr>
		 </table>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRes/addResource.do']}">
				<div onclick="goToResourceAddJsp(true);" data-options="iconCls:'icon-standard-plugin-add'">增加</div>
			</c:if>
		 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRes/editResource.do']}">
				<div onclick="goToResourceEditJsp();" data-options="iconCls:'icon-standard-plugin-edit'">编辑</div>
			</c:if>
		 	<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysRes/deleteResource.do']}">
				<div onclick="deleteResource();" data-options="iconCls:'icon-standard-plugin-delete'">删除</div>
			</c:if>
	</div>
</body>
</html>