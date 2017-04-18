<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 用户新增 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
$(function(){
	$.loadComboboxData($("#teamId"),"TEAM",false);
})
	
$('#blame_usr_name').searchbox({  
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
						$("#blame_usr_id").attr("value",row.id);
						$("#blame_usr_name").searchbox("setValue",row.name);
						dialog.dialog('destroy');
					}else{
						$.messager.show('提示', '请选择工单', 'info');
					} 
				}
			} ]
		});
    }  
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post" enctype = "multipart/form-data" >
			<fieldset>
				<legend>维修工基本信息</legend>
				<table class="table" style="width: 100%;">
				    <tr>
				        <th>照片</th>
				        <td>
				        <input type="file" name="file" style="width:175px;" />
				        </td>
				        <th>姓名</th>
						<td>
							<input id="blame_usr_name" name="userName" style="width:175px;" class="easyui-searchbox"  data-options="prompt: '请选择用户名',required:true"/>  
							<input id="blame_usr_id" name="userId"  style="display:none;"/>
							<input type="hidden" id="createUserName" name="createUserName" value="${sessionInfo.user.name}"/>
				        <input type="hidden" id="createUserId" name="createUserId" value="${sessionInfo.user.id}"/>  
						</td>
				        <%-- <th>姓名</th>
				        <td>
				        <input type="text" name="userName" class="easyui-validatebox " data-options="required:true" onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"/>
				        <input type="hidden" id="createUserName" name="createUserName" value="${sessionInfo.user.name}"/>
				        <input type="hidden" id="createUserId" name="createUserId" value="${sessionInfo.user.id}"/>
				        </td> --%>
				    </tr>
					<tr>
					<th>班组</th>
					    <td>
						<select id="teamId" name="teamId" data-options="panelHeight:'auto',width:183,editable:false,required:true,prompt:'请选择班次'"/></td>
						<th>维修工类别</th>
						<td>
							<select name="typeId" style="width:175px;" class="easyui-combobox fselect" data-options="panelHeight:'auto',required:true,prompt:'请选择维修工类别'">
							    <option value=""></option>
								<option value="1">机械维修工</option>
								<option value="2">电气维修工</option>
								<option value="3">维修主管</option>
							</select>
						</td>
					</tr>
					<tr>
					    <th>备注</th>
						<td>
							<textarea name="remark" style="width:175px;height:20px;"></textarea>
						</td>
						<th>维修设备</th>
						<td>
							<select name="eqpType" style="width:175px;" class="easyui-combobox fselect" data-options="panelHeight:'auto',prompt:'请选择设备类型'">
							    <option value=""></option>
								<option value="卷烟机">卷烟机</option>
								<option value="包装机">包装机</option>
							</select>
						</td>
					</tr>							
				</table>
			</fieldset>
		</form>
	</div>
</div>