<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 设备主数据新增 -->

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="formcategory" method="post">
			<fieldset>
				<legend>文件夹修改</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>文件夹名字：</th>
						<td><input type="hidden" name="id" value="${file.id }" />
							<input type="text" name="filename" class="easyui-validatebox" data-options="required:true" value="${file.filename }" />
						</td>
					
					</tr>
				
				</table>
			</fieldset>
		</form>
	</div>
</div>