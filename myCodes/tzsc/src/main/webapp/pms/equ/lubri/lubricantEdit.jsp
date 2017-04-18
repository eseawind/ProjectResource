<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 设备主数据新增 -->
<script type="text/javascript">
	$(function() {
		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>润滑剂基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>卡片编号</th>
						<td>
							<input type="hidden" name="id" value="${lubircant.id }"/>
							<input type="hidden" name="createDate" value="${lubircant.createDate }"/>
							<input type="text" name="lubricantCode" value="${lubircant.lubricantCode }" class="easyui-validatebox" data-options="required:true"/>
						</td>
						<th>油品名称</th>
						<td>
							<input type="text" name="lubricantName" value="${lubircant.lubricantName }"class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>

					<tr>
						<th>规格</th>
						<td colspan="3">
							<input type="text" name="standard" value="${lubircant.standard }"class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>		
				</table>
			</fieldset>
		</form>
	</div>
</div>