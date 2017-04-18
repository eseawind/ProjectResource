<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 设备主数据新增 -->
<script type="text/javascript">
	$(function() {

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
				<legend>辅料奖罚金额设置</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>设备型号</th>
						<td>
							<input name="id" type="hidden" value="${cosMatBean.id}"/>
							<input type="text" id="categoryId" name="mdEqpTypeId" class="easyui-combobox easyui-validatebox"  value="${cosMatBean.mdEqpTypeId }"
							data-options="textField:'name',valueField:'id',url:'${pageContext.request.contextPath }/pms/maintaintc/queryMdEqpType.do'"/>
						</td>
						<th>辅料</th>
						<td>
							<input id="matName"  name="matName" class="easyui-searchbox" value="${cosMatBean.mdMatName }"  data-options="prompt: '请选择辅料',required:true"/>  
							<input id="mat" name="mdMatId" type="hidden" value="${cosMatBean.mdMatId }" />
						</td>
					</tr>
					<tr>
						<th>奖罚单价</th>
						<td colspan="3">
							<input type="text" name="award" value="${cosMatBean.award }" class="easyui-numberbox" data-options="required:true,prompt:'请输入数字'" />
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