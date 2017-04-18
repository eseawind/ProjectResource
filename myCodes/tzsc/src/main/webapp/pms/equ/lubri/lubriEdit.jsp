<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 设备主数据新增 -->
<script type="text/javascript">
function queryUser(){
	var name=$("input[name='name']").val();
	var gender = $("select[name='gender']").val();
	var queryParams = {name:name,gender:gender};
	$("#sysUser_id").combogrid({
		url:"${pageContext.request.contextPath}/pms/sysUser/getAllUser.do",
		panelWidth:300,
		panelheight:400,
		queryParams :queryParams,
		toolbar:"#grid-toolbar",
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
					}}
		] ],
		

	});
}


$(function(){
	queryUser();	
});
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
						$.messager.show('提示', '请选择用户', 'info');
					} 
				}
			} ]
		});
    }  
});
</script>
<div id="grid-toolbar" style="display: none;">
	<table>
		<tr>
			<td>
				<form id="searchForm" style="margin:4px 0px 0px 0px">
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
								<input type="button" value="筛选" onclick="queryUser()">
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
				<legend>设备润滑基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>设备 </th>
						<td><input name="id"  type="hidden" value="${lubricant.id }"/> 
							<input id="equipmentName"  value="${lubricant.equipmentName }" class="easyui-searchbox"  data-options="prompt: '请选择卷烟机或成型机',required:true"/>  
							<input id="equipment" name="mdEquipment.id"  type="hidden" value="${lubricant.equipmentId }"/> 
						</td>
						<th>润滑部位</th>
						<td>
							<input type="text" name="lubricantPart" value="${lubricant.lubricantPart }"class="easyui-validatebox" data-options="required:true"/>
						</td>
					</tr>

					<tr>
						<th>润滑剂</th>
						<td>
							<input type="text" name="eqmLubricantMaintain.id"class="easyui-combobox easyui-validatebox" data-options="textField:'lubricantName',valueField:'id',
								url:'${pageContext.request.contextPath }/pms/equ/lubricant/queryAllLubricant.do' 
							"value="${lubricant.oilId }"/>
						</td>
						<th>检查人</th>
						<td>
						<input type="text" id="hoUser" value="${lubricant.examinePeopleName }"class="easyui-searchbox" data-options="prompt: '请选择检查用户'" />
								<input id="userid" type="hidden" name="examineUser.id" value="${lubricant.examinePeopleId }" />
							
						</td>
					</tr>
					<tr>
						<th>检查日期</th>
						<td>
							<input type="text" name="examineDate" value="${lubricant.examineDate }" class="easyui-datetimebox easyui-validatebox" />
						</td>
						<th></th>
						<td></td>
					</tr>
					<tr>	
						<th>润滑内容</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none"  name="lubricanteContent">${lubricant.lubricanteContent }</textarea>
						</td>
					</tr>
					
					<tr>
						<th>加油信息</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none"  name="refuelInfo">${lubricant.refuelInfo }</textarea>
						</td>
					</tr>
					<tr>
						<th>换油信息</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none"  name="oilChangeInfo">${lubricant.oilChangeInfo }</textarea>
						</td>
					</tr>
					
					<tr>
						<th>换油原因</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none"  name="oilChangeCause">${lubricant.oilChangeCause }</textarea>
						</td>
					</tr>
					<tr>
						<th>废油情况</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none"  name="wasteOilCase">${lubricant.wasteOilCase }</textarea>
						</td>
					</tr>
					<tr>
						<th>备注</th>
						<td colspan="3">
							<textarea style="width:460px;height:60px;resize:none"  name="remark">${lubricant.remark }</textarea>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>