<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 给机构分配资源 -->
<script type="text/javascript">
	var resTreeGrid;
	$(function() {
		resTreeGrid = $('#resTreeGrid').treegrid({
			url:"${pageContext.request.contextPath}/pms/sysOrg/getResourcesByOrg.do?id=${checkedOrgId}",
			idField : 'id',
			treeField : 'text',
			parentField : 'pid',
			striped : true,
			fit : true,
			fitColumns : true,
			border : false,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				hidden : true
			},{
				field : 'text',
				title : '菜单名称',
				align : 'center'
			},{
				field : 'checked',
				title : '授权授权',
				align : 'center',
				formatter: function(value,row,index){
					return $.formatString("<input type='checkbox' style='position:relative;margin-top: -6px;' id='{0}' class='menu' {1} />",row.id,value==true?"checked='checked'":"");
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