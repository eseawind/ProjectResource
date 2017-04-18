<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
$(function() {
	$('#hoUser').searchbox({  
	    searcher:function(value,name){  
	    var dialog = parent.$.modalDialog({
				title : '选择用户',
				width : 430,
				height : 420,
				href : '${pageContext.request.contextPath}/pms/sys/user/userPicker.jsp',
				buttons : [ {
					text : '选择',
					iconCls:'icon-standard-disk',
					handler : function() {
						var row = dialog.find("#userPickGrid").datagrid('getSelected');
						if(row){
							$("#userid").attr("value",row.id);
							$("#hoUser").searchbox("setValue",row.name);
							dialog.dialog('destroy');
						}else{
							$.messager.show('提示', '请选择工单', 'info');
						} 
					}
				} ]
			});
	    }  
	});
	$('#hoUser1').searchbox({  
	    searcher:function(value,name){  
	    var dialog = parent.$.modalDialog({
				title : '选择用户',
				width : 430,
				height : 420,
				href : '${pageContext.request.contextPath}/pms/sys/user/userPicker.jsp',
				buttons : [ {
					text : '选择',
					iconCls:'icon-standard-disk',
					handler : function() {
						var row = dialog.find("#userPickGrid").datagrid('getSelected');
						if(row){
							$("#userid1").attr("value",row.id);
							$("#hoUser1").searchbox("setValue",row.name);
							dialog.dialog('destroy');
						}else{
							$.messager.show('提示', '请选择工单', 'info');
						} 
					}
				} ]
			});
	    }  
	});
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>机台通知信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>标题</th>
						<td colspan="3">
							<input type=hidden name=id value="${msgInfo.id }"/>
							<input type="text" class="e-input" name="title" value="${msgInfo.title }">
						</td>
					</tr>
					<tr>
						<th>通知内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="content">${msgInfo.content }</textarea>
						</td>
					</tr>
					<%-- <tr>
						<th>审核人</th>
						<td colspan="1">
						<input type="text" id="hoUser" class="easyui-searchbox" data-options="prompt: '请选择检查用户',required:true" value="${msgInfo.approvalName }"/>
								<input id="userid" type="hidden" name="sysUserByApproval.id" value="${msgInfo.approval }"/>
						</td>
						<th>签发人</th>
						<td colspan="2">
							<input type="text" id="hoUser1" class="easyui-searchbox" data-options="prompt: '请选择检查用户',required:true"value="${msgInfo.issuerName }" />
								<input id="userid1" type="hidden" name="sysUserByIssuer.id" value="${msgInfo.issuer }"/>
						</td>
					</tr> --%>
				</table>
			</fieldset>
		</form>
	</div>
</div>