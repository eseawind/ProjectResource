<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<style type="text/css">
	#form .table th{width: 60px;}
</style>
<script type="text/javascript">
	$("#form input[name='date']").attr("readonly","readonly");
	$("#form input[name='shift']").attr("readonly","readonly");
	$("#form input[name='team']").attr("readonly","readonly");
	$("#form input[name='matProd']").attr("readonly","readonly");
	$("#form input[name='unitName']").attr("readonly","readonly");
	$("#form input[name='equipment']").attr("readonly","readonly");
	$("#form input[name='feedbackUser']").attr("readonly","readonly");
	$('#workorderCode').searchbox({  
	    searcher:function(value,name){  
	    var dialog = parent.$.modalDialog({
				title : '选择工单',
				width : 750,
				height : 420,
				href : '${pageContext.request.contextPath}/pms/workorder/goToWorkOrderPickJspCX.do?workshop=${workshop}',
				buttons : [ {
					text : '选择',
					iconCls:'icon-standard-disk',
					handler : function() {
						var row = dialog.find("#workorderPickGrid").datagrid('getSelected');
						if(row){
							$("#workorderCode").searchbox("setValue",row.code);
							$("#form input[name='workorder']").attr("value",row.id);
							$("#form input[name='date']").attr("value",row.date);
							$("#form input[name='shift']").attr("value",row.shift);
							$("#form input[name='team']").attr("value",row.team);
							$("#form input[name='unitName']").attr("value",row.unit);
							$("#form input[name='unit']").attr("value",row.unitId);
							$("#form input[name='matProd']").attr("value",row.mat);
							$("#form input[name='equipment']").attr("value",row.equipment);
							$("#stim").datetimebox("setValue",row.stim);
							$("#etim").datetimebox("setValue",row.etim);
							dialog.dialog('destroy');
						}else{
							$.messager.show('提示', '请选择工单', 'info');
						} 
					}
				} ]
			});
	    }  
	}); 
	$("#form input[name='workorderCode']").attr("readonly","readonly");
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>产出详情</legend>
				<table class="table" style="width: 100%; margin-top: -5px;">
					<tr>
						<th>工单</th>
						<td>
							<input id="workorderCode" name="workorderCode"class="easyui-searchbox" data-options="prompt: '请选择生产工单',required:true" style="width:158px"/>
							<input type="hidden" id="workorder"  name="workorder"/>
						</td>
						<th>日期</th>
						<td>
							<input type="text" name="date" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
						</td>
					</tr>
					<tr>
						<th>牌号</th>
						<td>
							<input type="text" name="matProd" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
						</td>
						<th>机台</th>
						<td>
							<input type="text" name="equipment" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
						</td>
					</tr>
					<tr>
						<th>班次</th>
						<td>
							<input type="text" name="shift" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
						</td>
						<th>班组</th>
						<td>
							<input type="text" name="team" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
						</td>
					</tr>
					<tr>
						<th>产量</th>
						<td>
							<input type="text" name="qty" class="easyui-numberbox" data-options="required:true,min:0,precision:3" />
						</td>
						<th>剔除</th>
						<td>
							<input type="text" name="badQty" class="easyui-numberbox" data-options="required:true,min:0,precision:3" style="width:112px"/>
							<input type="text" name="unitName" class="easyui-validatebox" data-options="prompt: '单位'" style="width:30px"/>
							<input type="hidden" name="unit"  />
						</td>
					</tr>
					<tr>
						<th>开始时间</th>
						<td>
							<input name="stim" id="stim" type="text" class="easyui-datetimebox" readonly="readonly" data-options="required:true" style="width:158px"/>
						</td>
						<th>结束时间</th>
						<td>
							<input name="etim" id="etim" type="text" class="easyui-datetimebox" readonly="readonly" data-options="required:true" style="width:158px"/>
						</td>
					</tr>
					<tr>
						
						<th>运行时间</th>
						<td>
							<input type="text" name="runTime" class="easyui-numberbox" data-options="required:true" />
						</td>
						<th>停机时间</th>
						<td>
							<input type="text" name="stopTime" class="easyui-numberbox" data-options="required:true" style="width:135px"/>
							<input type="text" readonly="readonly" value="分钟" style="width:30px">
						</td>
					</tr>
					<tr>
						
						<th>停机次数</th>
						<td>
							<input type="text" name="stopTimes" class="easyui-numberbox" data-options="required:true" style="width:135px"/>
							<input type="text" readonly="readonly" value="次" style="width:30px">
						</td>
						<th>操作用户</th>
						<td>
							<input type="text" name="feedbackUser" class="easyui-validatebox" data-options="required:true"  value="${sessionInfo.user.name}"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>













