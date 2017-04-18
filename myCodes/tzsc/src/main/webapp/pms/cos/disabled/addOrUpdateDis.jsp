<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 储烟量标准 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#shift"),"SHIFT",false);
		$("#form input[name='matName']").attr("readonly","readonly");
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>储烟量标准维护</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>机型</th>
						<td colspan="3" >
							<input id="${id}d" name="${id}d" type="hidden" value="${disBean.id}"/>
							<input type="text" id="modelType" style="width:400px;" name="modelType" class="easyui-combobox easyui-validatebox" value="${disBean.modelType}" 
							data-options="textField:'name',valueField:'id',url:'${pageContext.request.contextPath }/pms/md/eqptype/queryMdEqpTypeByCategory.do'"/>
						</td>
					</tr>
					<tr>
						<th>班次</th>
						<td>
							<select id="shift" name="shift" data-options="panelHeight:'auto',width:100,editable:false"><option value ="${disBean.shift}">${disBean.shiftName}</option></select>
						</td>
						<th>储烟量</th>
						<td>
							<input type="text" name="storage_smoke" class="easyui-numberbox" data-options="required:true,prompt:'请输入数字'" value="${disBean.storage_smoke}"/>

						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="pickPidDialog" class="easyui-dialog" title="选择父级资源" style="width:250px;height:300px;"  
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">  
	<table id="pickPidTreeGrid"></table>
	</div>
</div>