<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>物料组详情</legend>
				<table class="table" style="width: 100%;">
					<input name="id" type="hidden" value="${grp.id}"/> 
					<tr>
						<th>物料组编码</th>
						<td>
							<input type="text" name="code" class="easyui-validatebox" data-options="required:true" value="${grp.code }" />
						</td>
						<th>物料组名称</th>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${grp.name }"/>
						</td>
					</tr>
					<tr>
						<th>物料组描述</th>
						<td>
							<input type="text" name="des" class="easyui-validatebox" data-options="required:true" value="${grp.des }"/>
						</td>
						<th>是否启用</th>
						<td>
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1" <c:if test="${grp.enable=='1'}">selected="selected"</c:if>>启用</option>
								<option value="0" <c:if test="${grp.enable=='0'}">selected="selected"</c:if> >禁用</option>
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>