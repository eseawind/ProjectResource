<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- 设备主数据新增 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
$('#equipmentName').searchbox({  
    searcher:function(value,name){  
    var dialog = parent.$.modalDialog({
			title : '选择设备',
			width : 600,
			height : 420,
			href : '${pageContext.request.contextPath}/pms/equ/main/eqpPicker.jsp',
			 buttons : [ {
				text : '选择',
				iconCls:'icon-standard-disk',
				handler : function() {
					var row = dialog.find("#eqpPickGrid").datagrid('getSelected');
					if(row){
						  $("#equipment").attr("value",row.id);
						$("#equipmentName").searchbox("setValue",row.equipmentName);
						 dialog.dialog('destroy'); 
					}else{
						$.messager.show('提示', '请选择一条设备信息', 'info');
					} 		
				}
			} ] 
		});
    }  
}); 
$('#hoUser').searchbox({  
    searcher:function(value,name){  
    var dialog = parent.$.modalDialog({
			title : '选择用户',
			width : 430,
			height : 420,
			href : '${pageContext.request.contextPath}/pms/sys/user/userPicker.jsp',
			buttons : [ {
				text : '选择',
				iconCls:'icon-standard-disk',
				handler : function() {
					var row = dialog.find("#userPickGrid").datagrid('getSelected');
					if(row){
						$("#userid").attr("value",row.id);
						$("#hoUser").searchbox("setValue",row.name);
						dialog.dialog('destroy');
					}else{
						$.messager.show('提示', '请选择工单', 'info');
					} 
				}
			} ]
		});
    }  
});




	$(function(){
		$.loadComboboxData($("#shift"),"shift");
		queryUser("#requsr_id",'#requsr-toolbar');
		queryUser("#resusr_id",'#resusr-toolbar');
	});
	
	function queryUser(cssId,toolbar){
		var name=$(toolbar).find("form").find("input[name='name']").val();
		var gender = $(toolbar).find("form").find("select[name='gender']").val();
		var queryParams = {name:name,gender:gender};
		$(cssId).combogrid({
			url:"${pageContext.request.contextPath}/pms/sysUser/getAllUser.do",
			panelWidth:300,
			panelheight:400,
			queryParams :queryParams,
			toolbar:toolbar,
		    textField:'name',
		    fit : true,
			fitColumns : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			singleSelect:true,
			nowrap : false,
			showPageList:false,
			columns : [ [
					{
						field : 'id',
						title : '编号',
						width : 150,
						checkbox : true
					}, {
						field : 'name',
						title : '姓名',
						width : 60,
						align:'center',
						sortable : true
					} , {
						field : 'eno',
						title : '工号',
						align:'center',
						width : 80,
						sortable : true
					},{
						field : 'gender',
						title : '性别',
						width : 30,
						align:'center',
						sortable : true,
						formatter : function(value, row, index) {
							return value==0?'女':'男';
						}
					}
			] ],
		});
	}
</script>
<div id="requsr-toolbar" style="display: none;">
	<table>
		<tr>
			<td>
				<form id="requsrForm" style="margin:4px 0px 0px 0px">
					<table>
						<tr>
							<th>姓名/工号</th>
							<td><input type="text" name="name" class="easyui-validatebox " style="width:80px;" /></td>
							<th>性别</th>
							<td>
							<select name="gender" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
							        <option value="">　</option>
									<option value="1">男</option>
									<option value="0">女</option>
							</select>
							</td>
							<td>
								<input type="button" value="筛选" onclick="queryUser('#requsr_id','#requsr-toolbar')">
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</div>
<div id="resusr-toolbar" style="display: none;">
	<table>
		<tr>
			<td>
				<form id="resusrForm" style="margin:4px 0px 0px 0px">
					<table>
						<tr>
							<th>姓名/工号</th>
							<td><input type="text" name="name" class="easyui-validatebox " style="width:80px;" /></td>
							<th>性别</th>
							<td>
							<select name="gender" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
							        <option value="">　</option>
									<option value="1">男</option>
									<option value="0">女</option>
							</select>
							</td>
							<td>
								<input type="button" value="筛选" onclick="queryUser('#resusr_id','#resusr-toolbar')">
							</td>
						</tr>
					</table>
				</form>
			</td>
		</tr>
	</table>
</div>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: auto;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>设备检修项目基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>设备</th>
						<td>
						<input type="hidden" value="${fixRecBean.id }" name="id"/>
							<input id="equipmentName" value="${fixRecBean.equipName }"  class="easyui-searchbox"  data-options="prompt: '请选择卷烟机或成型机',required:true"/>  
							<input id="equipment" value="${fixRecBean.equipId }" name="equipId"  type="hidden" /> 
						</td>
						<th>班次：</th>
						<td>
							<input  id="shift" name="shiftId" value="${fixRecBean.shiftId }"  data-options="panelHeight:'auto',width:100,editable:false,required:true"/>
						</td>
					</tr>

					<tr>
						<th>维修类型：</th>
							<td><select name="fixtype" style="width:120px;" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="">---选择类型---</option>
								
								<option value="0" <c:if test="${fixRecBean.fixtype == '0' }">Selected</c:if>>电器</option>
								<option value="1" <c:if test="${fixRecBean.fixtype == '1' }">Selected</c:if>>机械</option>				</select>
							</td>
						<th>维修人</th>
						<td>
							<input type="text" id="hoUser" value="${fixRecBean.appointUsrName }"  class="easyui-searchbox" data-options="prompt: '请选择检查用户',required:true"/>
							<input id="userid" type="hidden" name="appointUsrId"  value="${fixRecBean.appointUsrId }" />
						</td>			
					</tr>			
					
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none" name="remark" >${fixRecBean.remark }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>