<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
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
$("#form input[name='equipmentName']").attr("readonly","readonly");
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>滚轴系数基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>设备</th>
						<td>
        					<input name="id" type="hidden"  value="${eqppparam.id}" />
							<input id="equipmentName"  name="equipmentName" class="easyui-searchbox"  data-options="prompt: '请选择卷烟机或成型机',required:true"  value="${eqppparam.equipmentName}"/>  
							<input id="equipment" name="equipment" type="hidden" value="${eqppparam.equipment}"/> 
						</td>
						<th>卷烟纸/滤棒盘纸滚轴参数</th>
						<td>
							<input name="axlePz"
							value="${eqppparam.axlePz}"
							class="easyui-numberbox"
        					data-options="required:true,prompt:'盘纸与滚轴切面长度',precision:8"
							/> 
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<th>水松纸滚轴参数</th>
						<td>
							<input name="axleSsz" 
							value="${eqppparam.axleSsz}"
							class="easyui-numberbox"
        					data-options="prompt:'成型机可不配置本选项',precision:8"
        					/>
						</td>
						<th>说明</th>
						<td>
							<input name="des" 
							value="${eqppparam.des}"
							class="easyui-validatebox"
        					/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>