<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
$(function(){
	
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>设备轮保计划新增</legend>
				<table class="table" style="width: 100%;">
				<input name="id" type="hidden" value="${wcpBean.id}"/>
					<tr>
						<th>轮保计划编号</th>
						<td><span>${wcpBean.planNo }</span></td>
						<th>轮保计划名称</th>
						<td><span>${wcpBean.planName }</span></td>
					</tr>
					<tr>
						<th>计划开始日期</th>
						<td>
							<span>${wcpBean.scheduleDate }</span>
						</td>
						<th>计划结束日期</th>
						<td>
							<span>${wcpBean.scheduleEndDate }</span>
						</td>
					</tr>
					<tr>
						<th>设备名称</th>
						<td>
							<span>${wcpBean.eqmName }</span>
						</td>
						<th>轮保类别</th>
						<td>
							<c:if test="${wcpBean.maintenanceType=='10' }"><span>轮保</span></c:if>
							<c:if test="${wcpBean.maintenanceType=='20' }"><span>润滑</span></c:if>
							<c:if test="${wcpBean.maintenanceType=='30' }"><span>停产检修</span></c:if>
							<c:if test="${wcpBean.maintenanceType=='40' }"><span>其他</span></c:if>
						</td>
					</tr>
					<tr>
						<th>轮保部件</th>
						<td><span>${wcpBean.wheelParts }</span></td>
						<th>责任人</th>
						<td><span>${wcpBean.dutyPeopleName }</span></td>
					</tr>
					<tr>
						<th>维护内容</th>
						<td colspan="3"><span>${wcpBean.maintenanceContent }</span></td>
					</tr>
					<tr>
						<th>轮保周期</th>
						<td>
							<span>${wcpBean.period }</span>
						</td>
						<th>提醒周期</th>
						<td>
							<span>${wcpBean.remindCycle }</span>
						</td>
					</tr>
					<tr>
						<th>通过意见</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="attr1">${wcpBean.attr1 }</textarea>
						</td>
					</tr>
					<tr>
						<th>驳回理由</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="attr2">${wcpBean.attr2 }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>