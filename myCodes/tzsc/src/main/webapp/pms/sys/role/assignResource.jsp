<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 给角色分配权限 -->
<script type="text/javascript">
	var resTreeGrid;
	$(function() {
		resTreeGrid = $('#resTreeGrid').treegrid({
			url:"${pageContext.request.contextPath}/pms/sysRole/getResourcesByRole.do?id=${checkedRoleId}",
			idField : 'id',
			treeField : 'text',
			parentField : 'pid',
			striped : true,
			fit : true,
			fitColumns : true,
			border : false,
			dataPlain : true,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				hidden : true
			},{
				field : 'text',
				title : '菜单名称',
				align : 'left'
			},{
				field : 'checked',
				title : '菜单授权',
				width : 60,
				align : 'center',
				formatter: function(value,row,index){
					return $.formatString("<input type='checkbox' style='position:relative;margin-top: -6px;margin-left: -6px;' id='{0}' class='menu' {1} />",row.id,value==true?"checked='checked'":"");
				}
			} ] ],
			columns:[[
			{
				field : 'function',
				title : '功能授权',
				formatter: function(value,row,index){
					var html="";
					var functions=row.functions;
					if(functions.length>0){
						for(var i=0;i<functions.length;i++){
							html+=$.formatString("&nbsp;<input type='checkbox' style='margin-top: 1px;' id='{0}' class='func' {1} />&nbsp;",functions[i].id,functions[i].checked==true?"checked='checked'":"")
							
							if(functions[i].checked){
								html += $.formatString('<span style="color:green;display:inline-block;vertical-align:middle;">{0}</span>',functions[i].text);
							}else{
								html += $.formatString('<span style="color:red;display:inline-block;vertical-align:middle;">{0}</span>',functions[i].text);
							}
							
						}
						return html;
					}else{
						return '<div style="color:gray">&nbsp;该菜单暂无操作</div>';
					}
					return html;
				}
			}	
			]],
			onLoadSuccess : function() {
				$(this).treegrid('tooltip');
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<table id="resTreeGrid"></table>
	</div>
</div>