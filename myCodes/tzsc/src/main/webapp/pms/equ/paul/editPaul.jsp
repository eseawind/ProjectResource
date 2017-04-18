<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 批量增加 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#eqp_type_id"),"EQPCATEGORY",false);
		$.loadComboboxData($("#shiftId"),"SHIFT",false);
		$.loadComboboxData($("#teamId"),"TEAM",false);
		$("#paul_id").combobox({  
		    url:"${pageContext.request.contextPath}/pms/sys/eqpcategory/queryMdEqpCategory.do",
		    valueField:'id',  
		    textField:'name'  
		}); 
		$("#date").attr("setValue",new Date());
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>修改日保养计划</legend>
				<table class="table" style="width:97%;">
					<tr>
						<th>名称</th>
						<td>
							<input id="id" name="id" type="text" value="${bean.id}" style="display:none;"/>
							<input name="name" type="text"  style="width:135px" data-options="required:true,prompt:'请选择结束时间'" value="${bean.name}"/>
						</td>
						<th>执行日期</th>
						<td>
							<input name="date_p" type="text" class="easyui-datebox" datefmt="yyyy-MM-dd" style="width:140px" data-options="required:true,prompt:'请选择结束时间'" value="${bean.date_p}"/>
						</td>
					</tr>
					<tr>
						<th>计划开始时间</th>
						<td>
							<input name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px" data-options="required:true,prompt:'请选择结束时间'" value="${bean.stim}"/>
						</td>
						<th>计划结束时间</th>
						<td>
							<input name="etim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px" data-options="required:true,prompt:'请选择结束时间'" value="${bean.etim}"/>
						</td>
					</tr>
					
				</table>
			</fieldset>
		</form>
	</div>
</div>