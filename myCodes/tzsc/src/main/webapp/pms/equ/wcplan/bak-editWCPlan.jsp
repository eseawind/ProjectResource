<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
function queryUser(){
	var name=$("input[name='name']").val();
	var gender = $("select[name='gender']").val();
	var queryParams = {name:name,gender:gender};
	$("#sysUser_id").combogrid({
		url:"${pageContext.request.contextPath}/pms/sysUser/getAllUser.do",
		panelWidth:300,
		panelheight:400,
		queryParams :queryParams,
		toolbar:"#grid-toolbar",
	    textField:'name',
	    fit : true,
		fitColumns : false,
		pagination : true,
		idField : 'id',
		pageSize : 10,
		pageList : [ 10, 20, 30, 40, 50 ],
		sortName : 'name',
		sortOrder : 'asc',
		singleSelect:true,
		nowrap : false,
		showPageList:false,
		columns : [ [
				{
					field : 'id',
					title : '编号',
					width : 150,
					checkbox : true
				}, {
					field : 'name',
					title : '姓名',
					width : 60,
					align:'center',
					sortable : true
				} , {
					field : 'eno',
					title : '工号',
					align:'center',
					width : 80,
					sortable : true
				},{
					field : 'gender',
					title : '性别',
					width : 30,
					align:'center',
					sortable : true,
					formatter : function(value, row, index) {
						return value==0?'女':'男';
					}}
		] ],
		

	});
}
$('#equipmentName').searchbox({  
    searcher:function(value,name){  
    var dialog = parent.$.modalDialog({
			title : '选择设备',
			width : 600,
			height : 420,
			href : '${pageContext.request.contextPath}/pms/equ/main/eqpPicker.jsp',
			 buttons : [ {
				text : '选择',
				iconCls:'icon-standard-disk',
				handler : function() {
					var row = dialog.find("#eqpPickGrid").datagrid('getSelected');
					if(row){
						 $("#equipment").attr("value",row.id);
						 $("#equipmentName").searchbox("setValue",row.equipmentName);
						 dialog.dialog('destroy'); 
					}else{
						$.messager.show('提示', '请选择一条设备信息', 'info');
					} 
				}
			} ] 
		});
    }  
}); 

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
$(function(){
	$.loadComboboxData($("#shiftId"),"SHIFT",false);
	queryUser();
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>设备轮保计划编辑</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>轮保计划编号</th>
						<td>	
							<input name="id" type="hidden" value="${wcpBean.id}"/>
							${wcpBean.planNo }
							<input name="planNo" type="hidden" class="easyui-validatebox" style="width:140px" value="${wcpBean.planNo }"/>
						</td>
						<th>轮保计划名称</th>
						<td>
							<input name="planName" class="easyui-validatebox" style="width:140px"  data-options="required:true" value="${wcpBean.planName }"/>
						</td>
					</tr>
					<tr>
						<th>计划开始日期</th>
						<td>
							<input name="scheduleDate" type="text" class="easyui-my97" style="width:140px" data-options="required:true" value="${wcpBean.scheduleDate }"/>
						</td>
						<th>计划结束日期</th>
						<td>
							<input name="scheduleEndDate" type="text" class="easyui-my97" style="width:140px" data-options="required:true" value="${wcpBean.scheduleEndDate }"/>
						</td>
					</tr>
					<tr>
						<th>设备名称</th>
						<td>
							<input id="equipmentName"  class="easyui-searchbox"  data-options="prompt:'请选择卷烟机或成型机',required:true,editable:true" value="${wcpBean.eqmName }"/>  
							<input id="equipment" name="eqmId" type="hidden"value="${wcpBean.eqmId }"/>  
						</td>
						<th>轮保类别</th>
						<td>
							<select name="maintenanceType" class="easyui-combobox fselect" readonly="readonly" data-options="panelHeight:'auto',required:true">
								<option value="轮保" <c:if test="${wcpBean.maintenanceType=='轮保' }">selected="selected"</c:if>>轮保</option>
								<option value="润滑" <c:if test="${wcpBean.maintenanceType=='润滑' }">selected="selected"</c:if>>润滑</option>
								<option value="停产检修" <c:if test="${wcpBean.maintenanceType=='停产检修' }">selected="selected"</c:if>>停产检修</option>
								<option value="其他" <c:if test="${wcpBean.maintenanceType=='其他' }">selected="selected"</c:if>>其他</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>轮保部件</th>
						<td>
							<input name="wheelParts" class="easyui-validatebox" data-options="required:true" style="width:140px" value="${wcpBean.wheelParts }"/> 
						</td>
						<th>责任人</th>
						<td>
							<input type="text" id="hoUser" value="${wcpBean.dutyPeopleName }" class="easyui-searchbox" data-options="prompt: '请选择检查用户',required:true" />
							<input id="userid" type="hidden" name="dutyPeopleId" value="${wcpBean.dutyPeopleId }"/>
						</td>
					</tr>
					<tr>
						<th>维护内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="maintenanceContent" data-options="required:true">${wcpBean.maintenanceContent }</textarea>
						</td>
					</tr>
					<tr>
						<th>轮保周期</th>
						<td>
							<input name="period" type="text" class="easyui-numberbox" data-options="required:true" style="width:140px" value="${wcpBean.period }"/>
						</td>
						<th>提醒周期</th>
						<td>
							<input name="remindCycle" type="text" class="easyui-numberbox" data-options="required:true" style="width:140px" value="${wcpBean.remindCycle }"/>
						</td>
					</tr>
						<tr>
						<th>班次</th>
						<td>
							<input id="shiftId" name="mdShiftId" type="text" class="easyui-numberbox" data-options="required:true" readonly="readonly" style="width:140px" value="${wcpBean.mdShiftId }"/>
						</td>
						<th></th>
						<td></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>