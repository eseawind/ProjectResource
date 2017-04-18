<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>消耗详情</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>工单号</th>
						<td>
							${workorder.code}
							<input type="hidden" name="id"  value="${input.id}"/>
							<input type="hidden" id="workorder"  value="${workorder.id}"/>
						</td>
						<th>日期</th>
						<td>
							${workorder.date}
						</td>
					</tr>
					<tr>
						<th>机台</th>
						<td>
							${workorder.equipment}
						</td>
						<th>物料名称</th>
						<td>
							${workorder.mat}
						</td>
					</tr>
					<tr>
						<th>班次</th>
						<td>
						${workorder.shift}
						</td>
						<th>班组</th>
						<td>
						${workorder.team}
						</td>
					</tr>
					<tr>
						<th>辅料</th>
						<td>
							<span style="font-size:12px">${input.mat}</span>
						</td>
						<th>消耗</th>
						<td style="width:180px">
							<input type="text" 
							name="qty" 
							class="easyui-numberbox" 
							data-options="required:true,min:0,precision:3" 
							style="width:120px"
							value="${input.qty}"
							/>
							<span id="unitName" style="font-size:12px">${input.unit}</span>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>