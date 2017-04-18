<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 储烟量标准 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#unitId"),"UNIT",false);
		$.loadComboboxData($("#eqpTypeId"),"EQPTYPE",false);
	});
	
	function checkInput(v){
		v.value=v.value.replace(/[^\d]/g,'');
	}
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>备品备件维护</legend>
				<table class="table" style="width:97%;">
					<tr>
						<th>编码</th>
						<td>
							<input id="${id}d" name="${id}d" type="hidden" value="${bean.id}"/>
							<input type="text" name="attr1" id="attr1	" class="easyui-validatebox" data-options="required:true" value="${bean.code}"/>
						</td>
						<th>名称</th>
						<td>
							<input type="text" name="name" id="name" class="easyui-validatebox" data-options="required:true" value="${bean.name}"/>
						</td>
					</tr>
					<tr>
						<th>单位</th>
						<td>
							<select id="unitId" name="unitId" data-options="panelHeight:200,panelWidth:175,width:175,editable:false,required:true"><option value ="${bean.unitId}">${bean.unitId}</option></select>
						</td>
						<th>机型</th>
						<td>
							<select id="eqpTypeId" name="eqpTypeId" data-options="panelHeight:'auto',width:175,editable:false,required:true"><option value ="${bean.eqpTypeId}">${bean.eqpTypeId}</option></select>
						</td>
					</tr>
					<tr>
						<th>型号</th>
						<td>
							<input type="text" name="type" id="type" class="easyui-validatebox" data-options="required:true" value="${bean.type}"/>
						</td>
						<th>单价</th>
						<td>
							<input type="text" name="price" id="price" precision="4"  min="-99999.99" max="99999.99" class="easyui-numberbox" data-options="required:true,prompt:'请输入系数'" value="${bean.modulus}"/>
						</td>
					</tr>
					<tr>
						<th>数量</th>
						<td>
							<input type="text" name="attr2" id="attr2" min="-99999" max="99999" precision="0" class="easyui-numberbox" required="required" onkeyup='checkInput(this)' value="1"/>
						</td>
						<th>备注</th>
						<td colspan="3">
							<textarea style="width:200px;height:50px;resize:none" name="remark">${bean.remark}</textarea>
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