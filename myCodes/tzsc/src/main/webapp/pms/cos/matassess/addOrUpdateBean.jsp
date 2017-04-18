<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 储烟量标准 -->
<script type="text/javascript">
	$(function() {
		
		$.loadComboboxData($("#mdMatId"),"MATPROD",false);
		//$("#mdMatId").combobox('select','${bean.mdMatName}');
		$("#form input[name='mdMatName']").attr("readonly","readonly");
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>储烟量标准维护</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>机型</th>
						<td>
							<input id="${id}d" name="${id}d" type="hidden" value="${bean.id}"/>
							<input type="text" id="eqpType" style="width:150px;" name="eqpTypeID" class="easyui-combobox easyui-validatebox" value="${bean.eqpTypeID}" 
							data-options="textField:'name',valueField:'id',url:'${pageContext.request.contextPath }/pms/md/eqptype/queryMdEqpType.do'"/>
							<!-- <input id="eqpTypeID" name="eqpTypeID" type="hidden" value="${bean.eqpTypeID}"/>  -->
						</td>
						<th>牌号</th>
						<td>
							<select id="mdMatId" name="mdMatId" data-options="panelHeight:200,width:200,editable:false"><option value ="${bean.mdMatId}">${bean.mdMatName}</option></select>
						</td>
					</tr>
					<tr>
						<th>版本号</th>
						<td colspan="3" >
							<input type="text" name="version" style="width:300px;" class="easyui-validatebox"  data-options="required:true,prompt:'请输入版本号'" value="${bean.version}"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>