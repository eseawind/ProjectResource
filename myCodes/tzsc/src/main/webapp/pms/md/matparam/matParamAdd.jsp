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
		$("#calc").click(function(){
			var length = $("#length").numberbox("getValue");
			var width = $("#width").numberbox("getValue");
			var density = $("#density").numberbox("getValue");
			if(length&&width&&density){
				$("#val").numberbox("setValue",length*width*density);									
			}else{
				$("#form").form("validate");
			}
		});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>辅料系数基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>辅料</th>
						<td>
							<input id="matName"  name="matName" class="easyui-searchbox"  data-options="prompt: '请选择辅料',required:true"/>  
							<input id="mat" name="mat" type="hidden" value=""/> 
						</td>
						<th>系数</th>
						<td>
							<input name="val" id="val"
							class="easyui-numberbox"
        					data-options="required:true,prompt: '系数=长度*宽度*密度',precision:8  "
        					/>
        					<input type="button" value="计算" id="calc"/>
						</td>
					</tr>
					<tr>
					</tr>
					<tr>
						<th>长度</th>
						<td>
							<input name="length" id="length" 
							class="easyui-numberbox"
        					data-options="required:true,prompt: '[注]水松纸长度设置为:1',precision:8"
        					/>
        					(m)
						</td>
						<th>宽度</th>
						<td>
							<input name="width" id="width" 
							class="easyui-numberbox"
							data-options="required:true,precision:8"
        					/>
        					(m)
						</td>
					</tr>
					<tr>
						<th>密度</th>
						<td>
							<input name="density" id="density"
							class="easyui-numberbox"
							data-options="required:true,precision:8"
        					/>
        					(kg/㎡)
						</td>
						<th>说明</th>
						<td>
							<input name="des"
							class="easyui-validatebox"
        					/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>