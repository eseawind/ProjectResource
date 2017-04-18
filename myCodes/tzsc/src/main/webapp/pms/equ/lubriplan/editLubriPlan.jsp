<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 增加润滑计划编辑 -->
<script type="text/javascript">
	$(function() {
		
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<table class="table" style="width:97%;">
					<tr>
						<th>润滑计划编号</th>
						<td colspan="3">
							<input id="id" name="id" type="text"  value="${bean.id}" style="display:none;"/>
							<input id="name" name="name" type="text" readonly class="easyui-validatebox"  data-options="width:120,editable:true,required:true,prompt:'请输入日保养名称'" value="${bean.code}"/>
						</td>
					</tr>
					<tr>
						<th>设备名称</th>
						<td>
							<input id="eqp_name" name="eqp_name" readonly  data-options="panelHeight:'auto',width:175,editable:false,required:true,prompt:'请选择设备类型'" value="${bean.eqp_name}"/>
							<input id="eqp_id" name="eqp_id" type="hidden"  value="${bean.eqp_id}" readonly/>
						</td>
						<th>计划执行日期</th>
						<td>
							<input id="date_p" name="date_p" class="easyui-datebox"  value="${bean.date_plan}"/>
						</td>
					</tr>
					
				</table>
			</fieldset>
		</form>
	</div>
</div>