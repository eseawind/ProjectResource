<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
$(function() {
	$.loadComboboxData($("#matGrp"),"MATGRP",false);
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>物料类型详情</legend>
				<table class="table" style="width: 100%;">
					<input name="id" type="hidden" value="${type.id}"/> 
					<tr>
						<th>类型编码</th>
						<td>
							<input type="text" name="code" class="easyui-validatebox" data-options="required:true" value="${type.code }" />
						</td>
						<th>类型名称</th>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${type.name }"/>
						</td>
					</tr>
					<tr>
						<th>类型描述</th>
						<td>
							<input type="text" name="des" class="easyui-validatebox" data-options="required:true" value="${type.des }"/>
						</td>
						<th>物料组</th>
						<td>
							<input id="matGrp" name="matGrp"  data-options="panelHeight:'200',width:100,editable:false" value="${type.matGrp}"/>
						</td>
					</tr>
					<tr>
						<th>是否启用</th>
						<td colspan="3">
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1" <c:if test="${type.enable=='1'}">selected="selected"</c:if>>启用</option>
								<option value="0" <c:if test="${type.enable=='0'}">selected="selected"</c:if> >禁用</option>
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>