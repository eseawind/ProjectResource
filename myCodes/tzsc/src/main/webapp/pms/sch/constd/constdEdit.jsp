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
				<legend>标准单耗基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>产品</th>
						<td>
							<input name="id" type="hidden" value="${constd.id}"/>
							<input id="matProd1" name="matProd" data-options="panelHeight:'auto',width:130,editable:false" value="${constd.matProd}"/>
						</td>
						<th>辅料</th>
						<td>
							<input id="matName"  name="matName" class="easyui-searchbox" value="${constd.matName}"  data-options="prompt: '请选择辅料'"/>  
							<input id="mat" name="mat" type="hidden" value="${constd.mat}"/>  
						</td>
					</tr>
					<tr>
						
					</tr>
					<tr>
						<th>标准值</th>
						<td>
							<input name="val" class="easyui-validatebox"
        					required="required" 
        					value="${constd.val}"
        					data-options="min:1,max:999"/>
						</td>
						<th>说明</th>
						<td>
							<input name="des" class="easyui-validatebox"
							value="${constd.des}"
        					required="required"/>
						</td>
					</tr>
					<tr>
						<th>标准上限</th>
						<td>
							<input name="uval" class="easyui-validatebox"
							value="${constd.uval}"
        					required="required"/>
						</td>
						<th>标准下限</th>
						<td>
							<input name="lval" class="easyui-validatebox"
							value="${constd.lval}"
        					required="required"/>
						</td>
					</tr>
					<tr>
						<th>报警上限</th>
						<td>
							<input name="euval" class="easyui-validatebox"
							value="${constd.euval}"
        					required="required"/>
						</td>
						<th>报警下限</th>
						<td>
							<input name="elval" class="easyui-validatebox"
							value="${constd.elval}"
        					required="required"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>