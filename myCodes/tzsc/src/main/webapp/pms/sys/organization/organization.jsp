<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>treeGrid原始机构管理</title>
<jsp:include page="../../../initlib/initAll.jsp"></jsp:include>
<script type="text/javascript">
	var treeGrid;
	$(function(){
		treeGrid = $('#treeGrid').treegrid({			
			idField : 'id',
			treeField : 'name',
			parentField : 'pid',
			rownumbers : true,
			pagination : false,
			sortName : 'seq',
			sortOrder : 'asc',
			frozenColumns : [ [ {
				width : '200',
				title : '机构名称',
				field : 'name'
			} ] ],
			columns : [ [ {
				width : '150',
				title : '图标名称',
				field : 'iconCls'
			}, {
				width : '250',
				title : '组织机构描述',
				field : 'remark'
			}, {
				width : '30',
				title : '排序',
				field : 'seq'
			}, {
				field : 'enable',
				title : '状态',
				width : 40,
				align:'center',
				formatter : function(value, row, index) {
					return value==1?'<span style="color:green">启用<span>':'<span style="color:red">禁用<span>';
				}
			} ] ],
			//toolbar : '#toolbar',
			onContextMenu : function(e, row) {
				e.preventDefault();
				$(this).treegrid('unselectAll');
				$(this).treegrid('select', row.id);
				$('#menu').menu('show', {
					left : e.pageX-5,
					top : e.pageY-5
				});
			},
			onLoadSuccess : function() {
				parent.$.messager.progress('close');

				$(this).treegrid('tooltip');
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
	};
	function undo() {
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			treeGrid.treegrid('collapseAll', node.id);
		} else {
			treeGrid.treegrid('collapseAll');
		}
	}
	function getAllOrgs(){
		treeGrid.treegrid({url : "${pageContext.request.contextPath}/pms/sysOrg/getAllOrgs.do"});
	}
	function goToOrgAddJsp(b){
		var dialog = parent.$.modalDialog({
			title : '组织机构添加',
			width : 620,
			height : 290,
			href : '${pageContext.request.contextPath}/pms/sysOrg/goToOrgAddJsp.do',
			buttons : [ {
				text : '保存',
				iconCls:'',
				handler : function() {
					var f = dialog.find("#form");
					$.post("${pageContext.request.contextPath}/pms/sysOrg/addOrg.do",f.form("getData"),function(json){
						if (json.success) {
							parent.$.messager.show('提示', json.msg, 'info');
							dialog.dialog('destroy');
							getAllOrgs();
						}else{
							dialog.dialog('destroy');
							parent.$.messager.show('提示', json.msg, 'error');
						}
					},"JSON");
				}
			} ],
			onLoad:function(){
			if(b){
				dialog.find("#pidComboTree").combotree({value:treeGrid.datagrid('getSelected').id});
		  	} 
			}
		});
	}
	function goToOrgEditJsp(){
		var dialog = parent.$.modalDialog({
			title : '组织机构编辑',
			width : 620,
			height : 290,
			href : '${pageContext.request.contextPath}/pms/sysOrg/goToOrgEditJsp.do?id='+treeGrid.datagrid('getSelected').id,
			buttons : [ {
				text : '保存',
				iconCls:'',
				handler : function() {
					var f = dialog.find("#form");
					$.post("${pageContext.request.contextPath}/pms/sysOrg/editOrg.do",f.form("getData"),function(json){
						if (json.success) {
							$.messager.show('提示', json.msg, 'info');
							dialog.dialog('destroy');
							getAllOrgs();
						}else{
							$.messager.show('提示', json.msg, 'error');
						}
					},"JSON");
				}
			} ]
		});
	}
	function deleteOrg(){
		var row = treeGrid.datagrid('getSelected');
		parent.$.messager.confirm('操作提示', '您是否要删除当前组织机构?', function(b) {
			if (b) {					
				$.post('${pageContext.request.contextPath}/pms/sysOrg/deleteOrg.do', {
					id : row.id,
					name : row.name
				}, function(json) {
					if (json.success) {
						$.messager.show('提示', json.msg, 'info');
						getAllOrgs();
					}else{
						$.messager.show('提示', json.msg, 'error');
					}
				}, 'JSON');
			}
		});
	}
	function toView(){
		$("#orgView").dialog("open");
	}
	/**
	* 跳转到给机构分配权限页面
	*/
	function goToAssignResourceJsp(){
		var id=treeGrid.datagrid('getSelected').id;//机构id
		dialog = parent.$.modalDialog({
			title : '权限分配',
			width : 700,
			height : 400,
			iconCls:'',
			href : '${pageContext.request.contextPath}/pms/sysOrg/goToAssignResourceJsp.do?id='+id,//跳转到授权页面,并加载该机构原有权限
			buttons : [ {
				text : '保存',
				iconCls:'',
				handler : function() {
					var ids=[];//资源ids(菜单,功能)
					var menus=dialog.find('input[class="menu"]:checked');
					var funcs=dialog.find('input[class="func"]:checked');
					menus.each(function(i){
						ids.push($(this).attr("id"));
					});
					funcs.each(function(i){
						ids.push($(this).attr("id"));
					});
					$.post("${pageContext.request.contextPath}/pms/sysOrg/assignResourceToOrg.do"
							,{id:id,ids:ids.join(",")},function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
								}else{
									$.messager.show('提示', json.msg, 'error');
								}
					},"JSON");
					
				}
			} ]
		});
	}
	/**
	 * 跳转到给机构用户分配界面
	 */
	function goToAssignUserJsp(){
		var id=treeGrid.datagrid('getSelected').id;//机构id
		dialog = parent.$.modalDialog({
			title : '分配用户',
			width : 700,
			height : 400,
			iconCls:'',
			href : '${pageContext.request.contextPath}/pms/sysOrg/goToAssignUserJsp.do?id='+id,//并加载该机构原有用户
			buttons : [ {
				text : '保存',
				iconCls:'icon-standard-disk',
				handler : function() {
					var rows=dialog.find("#assignedGrid").datagrid("getRows");
					var ids = [];
					for(var i=0;i<rows.length;i++){
						ids.push(rows[i].id);
					}
					//alert(id+","+ids.join(','));
					$.post("${pageContext.request.contextPath}/pms/sysOrg/assignUsersToOrg.do",{id:id,ids:ids.join(',')},function(json){
						if (json.success) {
							parent.$.messager.show('提示', json.msg, 'info');
						}else{
							parent.$.messager.show('提示', json.msg, 'error');
						}
						dialog.dialog('destroy');
					},"JSON");
				}
			} ]
		});
	}
</script>
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false,height:35">
		<div id="toolbar" class="easyui-toolbar">
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/getAllOrgs.do']}">
				<a onclick="getAllOrgs();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-zoom',plain:true">查询</a>
			</c:if>
			<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/addOrg.do']}">
				<a onclick="goToOrgAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-standard-chart-organisation-add',plain:true">添加</a>
			</c:if>
				<a onclick="redo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-arrow-out'">展开</a>
				<a onclick="undo();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-arrow-in'">折叠</a>			
		</div>
	</div>
	<div data-options="region:'center',fit:true,border:false">
	<table id="treeGrid" data-options="fit:true,border:false"></table>	 
	</div>  		
		<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/addOrg.do']}">
			<div onclick="goToOrgAddJsp(true);" data-options="iconCls:'icon-standard-chart-organisation-add'">增加</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/deleteOrg.do']}">
			<div onclick="deleteOrg();" data-options="iconCls:'icon-standard-chart-organisation-delete'">删除</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/editOrg.do']}">
			<div onclick="goToOrgEditJsp();" data-options="iconCls:'icon-standard-chart-organisation'">编辑</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/assignResourceToOrg.do']}">
			<div onclick="goToAssignResourceJsp();" data-options="iconCls:'icon-standard-cog-edit'">分配权限</div>
		</c:if>
		<c:if test="${not empty sessionInfo.resourcesMap['/pms/sysOrg/assignResourceToOrg.do']}">
			<div onclick="goToAssignUserJsp();" data-options="iconCls:'icon-standard-group'">分配用户</div>
		</c:if>
	</div>
	
</body>
</html>