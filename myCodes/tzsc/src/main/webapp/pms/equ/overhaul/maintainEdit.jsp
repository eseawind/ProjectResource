<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 修改设备检修 -->
<script type="text/javascript">
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
function showPlace(equipmentId){
	$("#place").empty();
	$("#place").combobox({
		url:'${pageContext.request.contextPath}/pms/overhaul/combobox.do?equipmentId='+equipmentId,
		textField:'id',
		valueField:'id',
		multiple:true,//多选
		required:true,
	});  
}
showPlace("${bean.eqp_id}");
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
							<input id="eqp_name" name="eqp_name" class="text"  ReadOnly data-options="prompt: '请选择卷烟机或成型机',required:true" value="${bean.eqp_name}"/>  
							<input id="eqp_id" name="eqp_id" style="display:none;" value="${bean.eqp_id}"/>  
							<input id="id" name="id" style="display:none;" value="${bean.id}"/>  
						</td>
						<th>责任人</th>
						<td>
							<input id="blame_usr_name" name="blame_usr_name" class="easyui-searchbox"  data-options="prompt: '请选择卷烟机或成型机',required:true" value="${bean.blame_usr_name}"/>  
							<input id="blame_usr_id" name="blame_usr_id"  style="display:none;" value="${bean.blame_usr_id}"/>  
						</td>
					</tr>
					<tr>
						<th>检修日期</th>
						<td>
							<input name="date_plans" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:140px" data-options="required:true,prompt:'请选择结束时间'" value="${bean.date_plans}"/>
						</td>
						<th>部位</th>
						<td>
							<input type="text" name="place"  value="${bean.place}"  data-options="required:true,prompt: '请输入设备部位'" style="width:169px;"/>
						</td>
					</tr>
					<tr>
						<th>项目内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" name="contents">${bean.contents }</textarea>
						</td>
					</tr>
					<tr>	
						<th>解决措施</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" name="solution">${bean.solution }</textarea>
						</td>
					</tr>
					<tr>	
						<th>检修后质量注意事项</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" name="note">${bean.note }</textarea>
						</td>
					</tr>
					<tr>	
						<th>复查情况</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" name="review">${bean.review }</textarea>
						</td>
					</tr>
					<tr>	
						<th>后期跟进</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" name="followup">${bean.followup }</textarea>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea style="width:460px;height:30px;resize:none" name="remark">${bean.remark }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
