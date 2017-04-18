<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 设备主数据新增 -->
<script type="text/javascript">
$(function(){
	$.loadComboboxData($("#shiftId"),"SHIFT",true);
});
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




</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>设备检修项目基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>设备</th>
						<td>
							<input id="equipmentName"   class="easyui-searchbox"  data-options="prompt: '请选择卷烟机或成型机',required:true"/>  
							<input id="equipment" name="equipId"  type="hidden" /> 
						</td>
						<th>班次：</th>
						<td>
							<input  id="shiftId" name="shiftId" data-options="panelHeight:'auto',width:100,editable:false,required:true"/>
						</td>
					</tr>

					<tr>
					<th>维修类型：</th>
						<td><select name="fixtype" style="width:120px;" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="">---选择类型---</option>
								<option value="0" >电器</option>
								<option value="1" >机械</option>				
								</select>
								</td>
						<th>维修人</th>
						<td>
							<input type="text" id="hoUser" class="easyui-searchbox" data-options="prompt: '请选择检查用户',required:true"/>
							<input id="userid" type="hidden" name="appointUsrId" />
						</td>
						
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="remark"></textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>