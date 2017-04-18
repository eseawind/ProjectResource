<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/css/formtab.css"
	rel="stylesheet" />
<script
	src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery/jquery-1.11.0.js"
	type="text/javascript"></script>
<!--<script src="jquery/jquery-1.11.0.min.js"></script>-->
<script
	src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-1.3.6/jquery.easyui.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extBrowser.js"
	charset="utf-8" type="text/javascript"></script>
<link
	href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-theme/default/easyui.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-theme/icon.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/jslib/jquery-extensions-master/icons/icon-all.css"
	rel="stylesheet" type="text/css" />


<script
	src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/changeEasyuiTheme.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery-easyui-1.3.6/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/jquery.jdirk.js"
	type="text/javascript"></script>

<script
	src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extEasyUI.js"></script>

<script
	src="${pageContext.request.contextPath}/jslib/jquery-extensions-master/extJquery.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
var datagrid;
	$(function() {
		datagrid=$("#dd").datagrid({
			title : 'Test表中的数据进行操作',
			width : 1000,
			height : 400,
			method : "get",	
			url : "${pageContext.request.contextPath}/pms/sys/test/List.do",
			remoteSort : false,
			columns : [ [ {
				field : 'id',
				title : '编号（id）',
				width : 230,
				sortable : true,
				editor : "text"
			}, {
				field : 'name',
				title : '名称(Name)',
				width : 230,
				sortable : true,
				editor : "text"
			}, {
				field : 'date',
				title : '日期(date)',
				width : 230,
				sortable : true,
				editor : "text"
			}
			] ],	 
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
	function goToRoleAddJsp(){
		 var dialog = parent.$.modalDialog({
				title : '用户管理',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/sys/test/Add.do',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							alert(f.form("getData"));
							$.post("${pageContext.request.contextPath}/pms/sys/test/Save.do",f.form("getData"),function(json){							
								alert("ok");
								
							},"JSON");
						}
					}
				} ]
			});
		
	}
	function batchDeleteRoles(){
		   var dialog = parent.$.modalDialog({
				title : '用户管理',
				width : 620,
				height : 250,
				href : '${pageContext.request.contextPath}/pms/sys/test/Add.jsp',
				buttons : [ {
					text : '保存',
					iconCls:'icon-standard-disk',
					handler : function() {
						var f = dialog.find("#form");
						if(f.form("validate")){
							$.post("${pageContext.request.contextPath}/pms/disabled/addOrUpdate.do",f.form("getData"),function(json){
								if (json.success) {
									$.messager.show('提示', json.msg, 'info');
									dialog.dialog('destroy');
									queryAll();
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
<body>
<div><a onclick="goToRoleAddJsp();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-user-add'">添加</a>
<a onclick="batchDeleteRoles();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-standard-group-delete'">删除</a></div>
	<div id="dd" onClick=""></div>
<div id="divdialog" style="display:none"> name<input type="test"/></div>
</body>
</html>