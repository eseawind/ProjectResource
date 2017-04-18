<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>物料单耗告警信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>工单</th>
						<td >
							<input type=hidden name=id value="${msgConWarn.id }"/>
							<input type="text" class="e-input" name="schWorkorder.id" value="${msgConWarn.schWorkorder.id }">
						</td>
						<th>科目</th>
						<td >
							<input type="text" class="e-input" name="item" value="${msgConWarn.item }">
						</td>
					</tr>
					<tr>
						<th>单耗标准值</th>
						<td >
							<input type="text" class="e-input" name="std" value="${msgConWarn.std }">
						</td>
						<th>单耗实际值</th>
						<td >
							<input type="text" class="e-input" name="val" value="${msgConWarn.val }">
						</td>
					</tr>
					<tr>
						<th>告警内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="content">${msgConWarn.content }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>