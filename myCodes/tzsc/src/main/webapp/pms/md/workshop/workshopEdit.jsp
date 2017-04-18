<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>车间基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>名称</th>
						<td>
							<input name="del" type="hidden" value="0"/>
							<input name="id" type="hidden" value="${workshop.id}"/>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${workshop.name}"/>
						</td>
						<th>编码</th>
						<td>
							<input type="text" name="code" class="easyui-validatebox" value="${workshop.code}"/>
						</td>
					</tr>
					<tr>
					<th>排序</th>
						<td>
							<input name="seq" class="easyui-numberspinner"
        					required="required" 
        					data-options="min:1,max:999" value="${workshop.seq}"/>
						</td>
						<th>启用</th>
						<td>
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1" <c:if test="${workshop.enable=='1'}">selected="selected"</c:if>>启用</option>
								<option value="0" <c:if test="${workshop.enable=='0'}">selected="selected"</c:if> >禁用</option>
							</select>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>