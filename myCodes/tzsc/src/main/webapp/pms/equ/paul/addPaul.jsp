<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 批量增加 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#eqp_type_id"),"EQPCATEGORY",false);
		$.loadComboboxData($("#shiftId"),"SHIFT",false);
		$.loadComboboxData($("#teamId"),"TEAM",false);
		$("#paul_id").combobox({  
		    url:"${pageContext.request.contextPath}/pms/sys/eqpcategory/queryMdEqpCategory.do",
		    valueField:'id',  
		    textField:'name'  
		}); 
		$("#date").attr("setValue",new Date());
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>批量添加日保养</legend>
				<table class="table" style="width:97%;">
					<tr>
						<th>日保计划名称</th>
						<td colspan="3">
							<input name="name" type="text" class="easyui-validatebox"  data-options="width:120,editable:true,required:true,prompt:'请输入日保养名称'"/>
						</td>
					</tr>
					<tr>
						<th>设备类型</th>
						<td>
							<select id="eqp_type_id" name="eqp_type_id" data-options="panelHeight:'auto',width:175,editable:false,required:true,prompt:'请选择设备类型'"></select>
						</td>
						<th>日保养规则</th>
						<td>
							<select id="paul_id" name="paul_id" class="easyui-combobox" data-options="panelHeight:'auto',width:175,editable:false,required:true,prompt:'请选择日保养规则'"></select>
						</td>
					</tr>
					<tr>
						<th>班次</th>
						<td>
							<select id="shiftId" name="shiftId" class="easyui-combobox" data-options="width:140,editable:false,required:true,prompt:'请选择班次'"></select>
						</td>
						<th>班组</th>
						<td>
							<select id="teamId" name="teamId" class="easyui-combobox" data-options="width:140,editable:false,required:true,prompt:'请选择班组'"></select>
						</td>
					</tr>
					<tr>
						<th>计划开始时间</th>
						<td>
							<input name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px" data-options="required:true,prompt:'请选择结束时间'"/>
						</td>
						<th>计划结束时间</th>
						<td>
							<input name="etim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px" data-options="required:true,prompt:'请选择结束时间'"/>
						</td>
					</tr>
					
				</table>
			</fieldset>
		</form>
	</div>
</div>