<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<title>机构编辑</title>
<script type="text/javascript">
$(function() {
	$("#pickIcon").click(function () {
	    $.easyui.icons.showSelector({
	        onEnter: function (val) { 
	        	$("#iconCls").removeClass().addClass(val);	        	
	        	$("#iconClsHid").attr("value",val);
	        }
	    });
	});
	$("#pidComboTree").combotree({
		width:150,
		dataPlain : true,
		value : '${checkedOrg.pid}',
		url : "${pageContext.request.contextPath}/pms/sysOrg/getAllOrgsSelfNotIn.do?id=${checkedOrg.id}"
	});
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>组织机构基本信息</legend>
				
				
				<table class="table" style="width: 100%;">
					<tr>
						<th>机构名称</th>
						<td>
							<input name="id" type="hidden" value="${checkedOrg.id}"/>
							<input name="del" type="hidden" value="${checkedOrg.del}"/>
							<input type="text" name="name" class="easyui-validatebox" data-options="required:true" value="${checkedOrg.name}"/>
						</td>
						<th>启用</th>
						<td>
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1" <c:if test="${checkedOrg.enable=='1'}">selected="selected"</c:if>>启用</option>
								<option value="0" <c:if test="${checkedOrg.enable=='0'}">selected="selected"</c:if> >禁用</option>
							</select>
						</td>
					</tr>

					<tr>
					</tr>
					<tr>
						<th>资源排序</th>
						<td>
							<input name="seq" class="easyui-numberspinner fselect"
        					required="required" 
        					style="width:158px"
        					value="${checkedOrg.seq}";
        					data-options="min:1,max:999"/>
						</td>
						<th>上级机构</th>
						<td>
							<select id="pidComboTree" name="pid"  class="fselect"></select>							
						</td>
					</tr>
					<tr>
						<th>机构描述</th>
						<td colspan="3">
							<input type="text" name="remark" class="easyui-validatebox" value="${checkedOrg.remark}"/>
						</td>
					</tr>								
				</table>
				
			</fieldset>
		</form>
	</div>
</div>
