<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 批量给角色分配权限 -->
<script type="text/javascript">
	var resTreeGrid;
	$(function() {
		resTreeGrid = $('#resTreeGrid').treegrid({
			idField : 'id',
			treeField : 'name',
			parentField : 'pid',
			striped : true,
			fitColumns : false,
			border : false,
			columns : [ [ {
				title : '编号',
				field : 'id',
				width : 150,
				hidden : true
			},{
				field : 'text',
				title : '资源名称',
				width : 200
			} ] ],
			onLoadSuccess : function() {
				$(this).treegrid('tooltip');
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<table id="resTreeGrid"></table>
	</div>
</div>