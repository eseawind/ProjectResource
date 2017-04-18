<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 用户编辑  -->
<div class="easyui-layout" data-options="fit:true,border:false">
<style type="text/css">
.aimgstl{
  text-align: center;
  cursor:pointer;
}
</style>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
$(function(){
	$.loadComboboxData($("#teamId3"),"TEAM",false);
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
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post" enctype = "multipart/form-data" >
			<fieldset>
				<legend>维修工基本信息</legend>
				<table class="table" style="width: 100%;">
				    <tr>
				        <th>照片</th>
				        <%-- <td><span class="spanimgs"><img src="${pageContext.request.contextPath}/pms/sys/repairResquest/img/shift_${fixUser.shiftId}/${fixUser.path}" style="width:80px;height:100px;"/></span></td> --%>
				        <td width="100">
				        	 <img src="${fixUser.path}" style="width:80px;height:100px;">
				        	 <input id="file1" type="file" name="file" style="width:100%" />
				        </td>
				        <th>备注</th>
						<td>
							<textarea value="${fixUser.remark}" name="remark" style="width:170px;height:100%;">${fixUser.remark}</textarea>
						</td>
				      	
					</tr>
					<tr>
					 <th>姓名</th>
				        <td><input type="hidden" name="id" value="${fixUser.id}" />
				        <input id="blame_usr_name" name="userName" value="${fixUser.userName}" style="width:175px;" class="easyui-searchbox"  data-options="prompt: '请选择用户名',required:true"/>  
						<input id="blame_usr_id" name="userId" type="hidden" value="${fixUser.userId}" style="display:none;"/>
						<input type="hidden" id="updateUserName" name="updateUserName" value="${sessionInfo.user.name}"/>
				        <input type="hidden" id="updateUserId" name="updateUserId" value="${sessionInfo.user.id}"/></td>
					<th>工号</th>
						<td><input value="${fixUser.eno}" type="text" name="eno" class="easyui-validatebox" data-options="required:true" readOnly="true"/></td>
					</tr>				
					<tr>
						<th>班组</th>
						<td>
							<input id="teamId3" name="teamId" type="text"
							data-options="required:true"
							style="width: 175px" value="${fixUser.teamId}"/>
						</td>
						<th>维修设备</th>
						<td>
							<select name="eqpType" style="width:175px;" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
							    <option value="" <c:if test="${fixUser.eqpType=='' }">selected="selected"</c:if>></option>
								<option value="卷烟机" <c:if test="${fixUser.eqpType=='卷烟机' }">selected="selected"</c:if>>卷烟机</option>
								<option value="包装机" <c:if test="${fixUser.eqpType=='包装机' }">selected="selected"</c:if>>包装机</option>
							</select>
						</td>
					</tr>
					<tr>
					<th>维修工类别</th>
						<td>
							<select name="typeId" style="width:175px;" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1" <c:if test="${fixUser.typeId=='1' }">selected="selected"</c:if>>机械维修工</option>
								<option value="2" <c:if test="${fixUser.typeId=='2' }">selected="selected"</c:if>>电气维修工</option>
								<option value="3" <c:if test="${fixUser.typeId=='3' }">selected="selected"</c:if>>维修主管</option>
							</select>
						</td>
						<th>状态</th>
						<td>
							<select name="status" style="width:175px;" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
							    <option value="0" <c:if test="${fixUser.status=='0' }">selected="selected"</c:if>>在线</option>
								<option value="1" <c:if test="${fixUser.status=='1' }">selected="selected"</c:if>>忙碌</option>
								<option value="2" <c:if test="${fixUser.status=='2' }">selected="selected"</c:if>>离线</option>
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>