<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">

	$.loadComboboxData($("#matprod"),"MATPROD",false);

</script>
<!--新增编辑  -->
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>品名</th>
						<td>
							<input type="hidden" name="id" value="${bean.id}"/>
							<input id="matprod" name="procId" 
							data-options="panelHeight:'auto',width:176,editable:false,required:true" 
							value="${bean.procId}" maxlength="50" />
						</td>
						<th>工段</th>
						<td>
							<input value="${bean.procSection}" type="text" name="procSection"
							class="easyui-validatebox" data-options="required:true" maxlength="50"/>
						</td>				
					</tr>
					<tr>
						<th>适用机型</th>
						<td colspan="3">
							<input value="${bean.procType}" type="text"
							name="procType" class="easyui-validatebox"
							data-options="required:true,width:434" maxlength="50" />
						</td>
						<th style="display:none;">是否启用</th>
						<td style="display:none;">
							<select name="procStop" class="easyui-combobox fselect" 
							data-options="panelHeight:'auto',width:176,required:true">
								<option value="0" <c:if test="${bean.procStop=='0'}">selected="selected"</c:if>>启用</option>
								<option value="1" <c:if test="${bean.procStop=='1'}">selected="selected"</c:if> >禁用</option>
							</select>
						</td>		
					</tr>	
					<tr>
						<th>指导书内容</th>
						<td colspan="3">
							<textarea style="width:428px;height:60px;resize:none" 
							name="procContent"  maxlength="1000" >${bean.procContent}</textarea>
						</td>
					</tr>			
				</table>
			</fieldset>
		</form>
	</div>
</div>