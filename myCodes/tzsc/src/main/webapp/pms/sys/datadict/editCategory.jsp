<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 数据新增 -->

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>一级数据修改</legend>
				<table class="table" style="width: 100%;">
					 
					<tr>
						<th>一级编码</th>
						<td><input name="id" type="hidden" value="${mdCategory.id}"/>
							<input type="text" name="code" class="easyui-validatebox" data-options="required:true" value="${mdCategory.code }" />
						</td>
						<th>一级名称</th>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${mdCategory.name }"/>
						</td>
					</tr>
					<tr>
						<th>数据描述</th>
						<td>
							<input type="text" name="des" class="easyui-validatebox" data-options="required:true" value="${mdCategory.des }"/>
						</td>
						<th>是否启用</th>
						<td>
							<c:if test="${mdCategory.enable==1}">
								<select name="enable" class="easyui-combobox fselect"
									data-options="panelHeight:'auto',width:176,required:true"
									value="${mdCategory.enable}">
										<option value="1" selected="selected">启用</option>
										<option value="0" >禁用</option>
								</select>
							</c:if>
							<c:if test="${mdCategory.enable==0}">
								<select name="enable" class="easyui-combobox fselect"
									data-options="panelHeight:'auto',width:176,required:true"
									value="${mdCategory.enable}">
										<option value="1" >启用</option>
										<option value="0" selected="selected">禁用</option>
								</select>
							</c:if>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>