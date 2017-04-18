<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 一级数据新增 -->
<script type="text/javascript">
$(function(){
	var code1=$('#code1');
	var d = new Date()
	var str="ZB_"+d.getFullYear()+(d.getMonth() + 1)+ d.getDate()+d.getHours()+d.getMinutes()+d.getSeconds();
	code1.attr('value',str);
});


</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="formcategory" method="post">
			<fieldset>
				<legend>一级数据新增</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>一级编码</th>
						<td>
							<input id="code1" type="text" name="code" class="easyui-validatebox" data-options="required:true"  />
						</td>
						<th>一级名称</th>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th>数据描述</th>
						<td>
							<input type="text" name="des" class="easyui-validatebox" data-options="required:true" />
						</td>
						<th>是否启用</th>
						<td>
							<select name="enable" class="easyui-combobox fselect"
							data-options="panelHeight:'auto',width:176,required:true">
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