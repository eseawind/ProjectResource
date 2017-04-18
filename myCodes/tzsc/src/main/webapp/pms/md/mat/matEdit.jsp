<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#matType2"),"MATTYPE",false);
		$.loadComboboxData($("#unit"),"UNIT",false);
		$("#form input[name='lastUpdateTime']").attr("readonly","readonly");
	});
</script>		
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>物料基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>物料全称</th>
						<td>
							<input name="id" type="hidden" value="${mat.id}"/>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${mat.name}"/>
						</td>
						<th>物料简称</th>
						<td>
							<input type="text" name="simpleName" class="easyui-validatebox" data-options="required:true" value="${mat.simpleName}"/>
						</td>
					</tr>
					<tr>
					<th>物料编码</th>
						<td>
							<input type="text" name="code" class="easyui-validatebox" data-options="required:true" value="${mat.code}"/>
						</td>
						<th>标准单耗</th>
						<td>
							<input type="text" name="standardVal" class="easyui-validatebox" data-options="required:true" value="${mat.standardVal}"/>
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1" <c:if test="${mat.enable=='1'}">selected="selected"</c:if>>启用</option>
								<option value="0" <c:if test="${mat.enable=='0'}">selected="selected"</c:if> >禁用</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>物料类型</th>
						<td>
							<input  id="matType2" name="matType" data-options="panelHeight:'auto',width:100,editable:false" value="${mat.matType}"/>
						</td>
						<th>计量单位</th>
						<td>
							<input  id="unit" name="unit" data-options="panelHeight:200,width:160,editable:false" value="${mat.unit}"/>
						</td>
					</tr>
					<tr>
					<th>描述</th>
						<td>
							<input type="text" name="des" class="easyui-validatebox" value="${mat.des}"/>
						</td>
						<th>最后修改时间</th>
						<td>
							${mat.lastUpdateTime}
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>