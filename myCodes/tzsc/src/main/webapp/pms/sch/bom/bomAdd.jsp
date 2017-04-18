<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		$.loadComboboxData($("#matProd1"),"MATPROD",false);
		$('#matName').searchbox({  
		    searcher:function(value,name){  
		    var dialog = parent.$.modalDialog({
					title : '选择辅料',
					width : 600,
					height : 420,
					href : '${pageContext.request.contextPath}/pms/md/mat/matPicker.jsp',
					buttons : [ {
						text : '选择',
						iconCls:'icon-standard-disk',
						handler : function() {
							var row = dialog.find("#matPickGrid").datagrid('getSelected');
							if(row){
								 $("#mat").attr("value",row.id);
								 $("#matName").searchbox("setValue",row.name);
								 $("#unit").attr("value",row.unitId);
								 $("#unitName").attr("value",row.unit);
								 dialog.dialog('destroy');
							}else{
								$.messager.show('提示', '请选择一条物料信息', 'info');
							} 
						}
					} ]
				});
		    }  
		}); 
		$("#form input[name='matName']").attr("readonly","readonly");
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>投料信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>辅料</th>
						<td>
							
							<input id="matName"  name="matName" class="easyui-searchbox"  data-options="prompt: '请选择辅料',required:true"/>  
							<input id="mat" name="mat" type="hidden" value=""/> 
							<input  name="orderId" type="hidden" value="${orderId}"/> 
						</td>
					</tr>
					<tr>
						<th>计划投料</th>
						<td>
							<input id="qty"  name="qty" style="width:70px"/>  
							<input id="unitName" readonly="readonly"  style="width:30px"/> 
							<input id="unit" name="unit" type="hidden"/> 
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>