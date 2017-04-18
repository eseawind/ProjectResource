<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 批量给用户分配角色 -->
<script type="text/javascript">
	var roleDataGrid;
	$(function() {
		roleDataGrid = $('#roleDataGrid').datagrid({
			url:"${pageContext.request.contextPath}/pms/sysRole/getAllRoles.do",
			rownumbers :true,
			idField : 'id',
			fit : true,			
			fitColumns : false,
			border : false,
			striped : true,
			nowrap : true,
			checkOnSelect : false,
			selectOnCheck : false,
			columns : [ [ {
				title : '编号',
				field : 'id',
				width : 20,
				checkbox : true
			}, {
				field : 'name',
				title : '角色名称',
				width : 100
			}, {
				field : 'remark',
				title : '备注',
				width : 150
			} ] ],
			toolbar : '#toolbar3',
			onLoadSuccess : function(row) {
				$(this).datagrid('tooltip');
                var rowData = row.rows;
                $.each(rowData,function(idx,val){//遍历JSON
                      if(val.checked){
                    	  roleDataGrid.datagrid("checkRow", idx);//如果数据行为已选中则选中改行
                      }
                });           
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="padding:10px" class="dialog-toolbar">
		<code>批量分配角色，将会覆盖用户原有角色</code>
	</div>
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<table id="roleDataGrid"></table>
	</div>
</div>
