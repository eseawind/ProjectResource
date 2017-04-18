<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 机构新增 -->
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
			url : "${pageContext.request.contextPath}/pms/sysOrg/getAllOrgsSelfNotIn.do?id=-1"
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
							<input name="del" type="hidden" value="0"/>
							<input type="text" name="name" class="easyui-validatebox span2" data-options="required:true"/>
						</td>
						<th>启用</th>
						<td>
							<select name="enable" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1">启用</option>
								<option value="0">禁用</option>
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
							<input type="text" name="remark" class="easyui-validatebox"/>
						</td>
					</tr>								
				</table>
			</fieldset>
		</form>
	</div>
</div>
