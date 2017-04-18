<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 设备主数据新增 -->
<script type="text/javascript">
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
				<legend>设备润滑基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>设备</th>
						<td>
							<input id="equipmentName"   class="easyui-searchbox"  data-options="prompt: '请选择卷烟机或成型机',required:true"/>  
							<input id="equipment" name="mdEquipment.id"  type="hidden" /> 
						</td>
						<th>润滑部位</th>
						<td>
							<input type="text" name="lubricantPart" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>

					<tr>
						<th>润滑剂</th>
						<td>
							<input type="text" name="eqmLubricantMaintain.id" class="easyui-combobox easyui-validatebox" data-options="required:true,textField:'lubricantName',valueField:'id',
								url:'${pageContext.request.contextPath }/pms/equ/lubricant/queryAllLubricant.do'
							"/>
						</td>
						<th>检查人</th>
						<td>
							<input type="text" id="hoUser" class="easyui-searchbox" data-options="prompt: '请选择检查用户',required:true" />
								<input id="userid" type="hidden" name="examineUser.id" />
						</td>
					</tr>
					<tr>
						<th>检查日期</th>
						<td>
							<input type="text" name="examineDate" class="easyui-datetimebox easyui-validatebox" data-options="required:true"/>
						</td>
						<th></th>
						<td></td>
					</tr>
					<tr>	
						<th>润滑内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="lubricanteContent"></textarea>
						</td>
					</tr>
					
					<tr>
						<th>加油信息</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="refuelInfo"></textarea>
						</td>
					</tr>
					<tr>
						<th>换油信息</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="oilChangeInfo"></textarea>
						</td>
					</tr>
					
					<tr>
						<th>换油原因</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="oilChangeCause"></textarea>
						</td>
					</tr>
					<tr>
						<th>废油情况</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="wasteOilCase"></textarea>
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