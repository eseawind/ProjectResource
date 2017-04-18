<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<style type="text/css">
	#form .table th{width: 60px;}
</style>
<script type="text/javascript">
	$("#form #hoShift").attr("readonly","readonly");
	$("#form #hoTeam").attr("readonly","readonly");
	$("#form #hoMat").attr("readonly","readonly");
	
	$("#form #toShift").attr("readonly","readonly");
	$("#form #toTeam").attr("readonly","readonly");
	$("#form #toMat").attr("readonly","readonly");
	
	$("#form #equipment").attr("readonly","readonly");
	$('#hoOrder').searchbox({  
	    searcher:function(value,name){  
	    var dialog = parent.$.modalDialog({
				title : '选择接班工单',
				width : 750,
				height : 420,
				href : '${pageContext.request.contextPath}/pms/workorder/goToWorkOrderPickJsp.do?workshop=${workshop}',
				buttons : [ {
					text : '选择',
					iconCls:'icon-standard-disk',
					handler : function() {
						var row = dialog.find("#workorderPickGrid").datagrid('getSelected');
						if(row){
							$("#hoOrder").searchbox("setValue",row.code);
							$("#form input[name='hoOrderId']").attr("value",row.id);
							$("#form input[name='date']").attr("value",row.date);
							$("#form input[name='hoShift']").attr("value",row.shiftId);
							$("#form #hoShift").attr("value",row.shift);
							$("#form input[name='hoTeam']").attr("value",row.teamId);
							$("#form #hoTeam").attr("value",row.team);
							$("#form input[name='hoMat']").attr("value",row.matId);
							$("#form #hoMat").attr("value",row.mat);
							$("#form #equipment").attr("value",row.equipment);
							$("#form input[name='equipment']").attr("value",row.equipmentId);
							dialog.dialog('destroy');
						}else{
							$.messager.show('提示', '请选择工单', 'info');
						} 
					}
				} ]
			});
	    }  
	}); 
	$('#toOrder').searchbox({  
	    searcher:function(value,name){  
	    if(!$("#form input[name='equipment']").attr("value")){
	    	$.messager.show('提示', '请先填写交班信息', 'info');
	    	return;
	    }
	    var dialog = parent.$.modalDialog({
				title : '选择接班工单',
				width : 750,
				height : 420,
				href : '${pageContext.request.contextPath}/pms/workorder/goToWorkOrderPickJsp.do?workshop=${workshop}',
				buttons : [ {
					text : '选择',
					iconCls:'icon-standard-disk',
					handler : function() {
						var row = dialog.find("#workorderPickGrid").datagrid('getSelected');
						if(row){
							if($("#form input[name='equipment']").attr("value")!=row.equipmentId){
						    	$.messager.show('提示', '交班机台与接班机台不一致！', 'error');
						    	return;
						    }
							if($("#form input[name='hoOrder']").attr("value")==row.id){
						    	$.messager.show('提示', '接班工单不能与交班工单相同，请选择下一个班次工单！', 'error');
						    	return;
						    }
							$("#toOrder").searchbox("setValue",row.code);
							$("#form input[name='toOrderId']").attr("value",row.id);
							$("#form input[name='toShift']").attr("value",row.shiftId);
							$("#form #toShift").attr("value",row.shift);
							$("#form input[name='toTeam']").attr("value",row.teamId);
							$("#form #toTeam").attr("value",row.team);
							$("#form input[name='toMat']").attr("value",row.matId);
							$("#form #toMat").attr("value",row.mat);
							dialog.dialog('destroy');
						}else{
							$.messager.show('提示', '请选择工单', 'info');
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
							$("#form input[name='hoUser']").attr("value",row.id);
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
	$('#toUser').searchbox({  
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
							$("#form input[name='toUser']").attr("value",row.id);
							$("#toUser").searchbox("setValue",row.name);
							dialog.dialog('destroy');
						}else{
							$.messager.show('提示', '请选择工单', 'info');
						} 
					}
				} ]
			});
	    }  
	});
	$("#form input[name='readonlySearchbox']").attr("readonly","readonly");
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:10px">
		<form id="form" method="post">
				<c:if test="${type==1}">
					<table class="table" style="width: 100%;">
						<tr>
							<th colspan="2" style="font-size:14px;height:30px;text-align: center;">交班信息</th>
							
							<th colspan="2" style="font-size:14px;height:30px;text-align: center;">接班信息</th>
						</tr>
						<tr>
							<th>交班工单</th>
							<td>
								<input id="hoOrder" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择生产工单',required:true" style="width:158px"/>
								<input type="hidden" name="hoOrderId"/>
								<input type="hidden" name="date"/>
								<input type="hidden" name="type" value="${type}"/>
							</td>
							<th>接班工单</th>
							<td>
								<input id="toOrder" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择生产工单',required:true" style="width:158px"/>
								<input type="hidden"  name="toOrderId"/>
							</td>
						</tr>
						<tr>
							<th>机台</th>
							<td colspan="3">
								<input type="text" id="equipment" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="equipment"/>
							</td>
						</tr>
						<tr>
							<th>交班牌号</th>
							<td>
								<input type="text" id="hoMat" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="hoMat"  />
							</td>
							<th>接班牌号</th>
							<td>
								<input type="text" id="toMat" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="toMat"  />
							</td>
						</tr>
						<tr>
							<th>交班班次</th>
							<td>
								<input type="text" id="hoShift" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="hoShift"  />
							</td>
							<th>接班班次</th>
							<td>
								<input type="text" id="toShift" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="toShift"  />
							</td>
						</tr>
						<tr>
							<th>交班班组</th>
							<td>
								<input type="text" id="hoTeam" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="hoTeam"  />
							</td>
							<th>接班班组</th>
							<td>
								<input type="text" id="toTeam" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="toTeam"  />
							</td>
						</tr>
						<tr>
							<th>交班时间</th>
							<td>
								<input name="hoTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm" style="width:180px"/>
							</td>
							<th>接班时间</th>
							<td>
								<input name="toTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm" style="width:180px"/>
							</td>
						</tr>
						<tr>
							<th>交班用户</th>
							<td>
								<input type="text" id="hoUser" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择交班用户',required:true" />
								<input type="hidden" name="hoUser"  />
							</td>
							<th>接班用户</th>
							<td>
								<input type="text" id="toUser" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择交班用户'" />
								<input type="hidden" name="toUser"  />
							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${type==2}">
					<table class="table" style="width: 100%;">
						<tr>
							<th colspan="2" style="font-size:14px;height:30px;text-align: center;">交班信息</th>
							
							<th colspan="2" style="font-size:14px;height:30px;text-align: center;">接班信息</th>
						</tr>
						<tr>
							<th>交班工单</th>
							<td>
								<input id="hoOrder" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择生产工单',required:true" style="width:158px"/>
								<input type="hidden" name="hoOrderId"/>
								<input type="hidden" name="date"/>
								<input type="hidden" name="type" value="${type}"/>
							</td>
							<th>接班工单</th>
							<td>
								<input id="toOrder" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择生产工单',required:true" style="width:158px"/>
								<input type="hidden"  name="toOrderId"/>
							</td>
						</tr>
						<tr>
							<th>机台</th>
							<td colspan="3">
								<input type="text" id="equipment" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="equipment"/>
							</td>
						</tr>
						<tr>
							<th>前牌号</th>
							<td>
								<input type="text" id="hoMat" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="hoMat"  />
							</td>
							<th>后牌号</th>
							<td>
								<input type="text" id="toMat" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="toMat"  />
							</td>
						</tr>
						<tr>
							<th>班次</th>
							<td>
								<input type="text" id="hoShift" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="hoShift"  />
							</td>
							<!-- <th>接班班次</th>
							<td>
								<input type="text" id="toShift" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="toShift"  />
							</td> -->
						</tr>
						<tr>
							<th>班组</th>
							<td>
								<input type="text" id="hoTeam" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="hoTeam"  />
							</td>
							<!-- <th>接班班组</th>
							<td>
								<input type="text" id="toTeam" class="easyui-validatebox" data-options="prompt: '请选择生产工单'" />
								<input type="hidden" name="toTeam"  />
							</td> -->
						</tr>
						<tr>
							<th>换牌时间</th>
							<td>
								<input name="hoTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm" style="width:180px"/>
							</td>
							<!-- <th>接班时间</th>
							<td>
								<input name="toTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm" style="width:180px"/>
							</td> -->
						</tr>
						<tr>
							<th>换牌用户</th>
							<td>
								<input type="text" id="hoUser" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择交班用户',required:true" />
								<input type="hidden" name="hoUser"  />
							</td>
							<!-- <th>接班用户</th>
							<td>
								<input type="text" id="toUser" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择交班用户'" />
								<input type="hidden" name="toUser"  />
							</td> -->
						</tr>
					</table>
				</c:if>
		</form>
	</div>
</div>













