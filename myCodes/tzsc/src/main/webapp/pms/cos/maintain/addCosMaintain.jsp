<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 物料单价新增 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#shift"),"SHIFT",false);
		
		$('#matName').searchbox({
		    searcher:function(value,name){  
		    var dialog = parent.$.modalDialog({
					title : '选择辅料',
					width : 600,
					height : 420,
					href : '${pageContext.request.contextPath}/pms/md/mat/matPicker.jsp',
					//href : '${pageContext.request.contextPath}/readme.html',
					buttons : [ {
						text : '选择',
						iconCls:'icon-standard-disk',
						handler : function() {
							var row = dialog.find("#matPickGrid").datagrid('getSelected');
							if(row){
								 $("#mat").attr("value",row.id);
								 $("#matName").searchbox("setValue",row.name);
								 $('#categoryId').combobox('setValue', row.unitId);
								 dialog.dialog('destroy');
							}else{
								$.messager.show('提示', '请选择一条辅料信息', 'info');
							} 
						}
					} ]
				});
		    }  
		}); 
		$("#form input[name='matName']").attr("readonly","readonly");
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>辅料新增</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>辅料</th>
						<td colspan="3">
							<input id="matName"  name="matName" class="easyui-searchbox"  data-options="prompt: '请选择辅料',required:true"/>  
							<input id="mat" name="matId" type="hidden" value=""/> 
						</td>
					</tr>
					<tr>
						<th>辅料单价</th>
						<td>
							<input type="text" name="matPrice" class="easyui-numberbox" data-options="required:true,precision:8,prompt:'请输入数字'"/>
						</td>
						<th>单位</th>
						<td>
							<input type="text" id="categoryId" name="unitId" class="easyui-combobox easyui-validatebox" 
							data-options="textField:'name',valueField:'id',url:'${pageContext.request.contextPath }/pms/maintain/queryAllUnit.do'"/>
						</td>
					</tr>
					<tr>
						<th>生效时间</th>
						<td>
							 <input name="takeeffectDate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:140px" /> 
						</td>
						<th>班次</th>
						<td>
							<select  id="shift" name="shiftId" data-options="panelHeight:'auto',width:100,editable:false"></select>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="attr1"></textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
	<div id="pickPidDialog" class="easyui-dialog" title="选择父级资源" style="width:250px;height:300px;"  
        data-options="iconCls:'icon-save',resizable:true,modal:true,closed:true">  
	<table id="pickPidTreeGrid"></table>
	</div>
</div>