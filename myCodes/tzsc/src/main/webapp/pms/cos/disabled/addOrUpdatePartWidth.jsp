<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 储烟量标准 -->
<script type="text/javascript">
	$.loadComboboxData($("#partNumber"),"MATPROD",false);
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>烟支重量标准维护</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>牌号：</th>
						<td>
							<input id="${id}d" name="${id}d" type="hidden" value="${bean.id}"/>
							<select id="partNumber" name="partNumber" data-options="panelHeight:200,width:130,required:true"><option value ="${bean.partNumber}">${bean.partName}</option></select>
						</td>
						<th>烟支重量：</th>
						<td>
							<input type="text" name="weight" class="easyui-numberbox" precision="4"  min="0.0001" max="99999.99" data-options="required:true,prompt:'请输入数字'" value="${bean.weight}"/>
							
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	
</div>