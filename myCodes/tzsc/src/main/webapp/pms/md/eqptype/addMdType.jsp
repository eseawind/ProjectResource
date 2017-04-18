<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 设备主数据新增 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#mdEquCategoryType"),"EQPCATEGORY",false);
	});
</script>
	<div style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
			<c:if test="${mdEqpTypeBean.id==null }">
			<legend>设备型号新增</legend>
			</c:if>
			<c:if test="${mdEqpTypeBean.id!=null }">
			<legend>设备型号修改</legend>
			</c:if>
				<table class="table" style="width: 100%;">

					<tr>
						<th>设备类型名称</th>
						<td>
						<c:if test="${mdEqpTypeBean.id==null }">
						<samp>${mdEqpTypeBean.categoryName }</samp>
						<input type="hidden" name="categoryId"  value="${mdEqpTypeBean.categoryId }"/>			
						</c:if>
						<c:if test="${mdEqpTypeBean.id!=null }">
						<input  id="mdEquCategoryType" name="categoryId" data-options="panelHeight:'auto',width:100,editable:false" value="${mdEqpTypeBean.categoryId }"/>			
						</c:if>
										
							
						</td>
						<th>设备型号名称</th>
						<td>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${mdEqpTypeBean.name }"/>
						</td>
					</tr>
					<tr>
						<th>设备型号编码</th>
						<td>					 <input name="id" type="hidden" value="${mdEqpTypeBean.id}"/> 
							<input type="text" name="code" class="easyui-validatebox" data-options="required:true" value="${mdEqpTypeBean.code }" />
						</td>
						<th>是否启用</th>
						<td>
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'" value="${mdEqpTypeBean.enable }">
								<option value="1">启用</option>
								<option value="0">禁用</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>设备型号描述</th>
						<td>
							<input type="text" name="des" class="easyui-validatebox" data-options="required:true" value="${mdEqpTypeBean.des }"/>
						</td>
						<th></th>
						<td>
						
						</td>
					</tr>
				
				</table>
			</fieldset>
		</form>
	</div>
