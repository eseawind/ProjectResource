<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>计量单位基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>名称</th>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th>编码</th>
						<td>
							<input type="text" name="code" class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<th>类型</th>
						<td>
							<input type="text" name="type" class="easyui-validatebox" value="${unit.type}"/>
						</td>
						<th>启用</th>
						<td>
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1">启用</option>
								<option value="0">禁用</option>
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>