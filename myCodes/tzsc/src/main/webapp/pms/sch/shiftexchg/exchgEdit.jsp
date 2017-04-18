<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<style type="text/css">
	#form .table th{width: 60px;}
</style>
<script type="text/javascript">
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:10px">
		<form id="form" method="post">
				<c:if test="${type==1}">
					<table class="table" style="width: 100%;">
						<tr>
							<th colspan="2" style="font-size:14px;height:30px;text-align: center;">交班信息</th>
							
							<th colspan="2" style="font-size:14px;height:30px;text-align: center;">接班信息</th>
							<input type="hidden" name="id" value="${exchg.id}"/>
						</tr>
						<tr>
							<th>交班工单</th>
							<td>
								${exchg.hoOrderId}
							</td>
							<th>接班工单</th>
							<td>
								${exchg.toOrderId}
							</td>
						</tr>
						<tr>
							<th>机台</th>
							<td colspan="3">
								${exchg.equipment}
							</td>
						</tr>
						<tr>
							<th>交班牌号</th>
							<td>
								${exchg.hoMat}
							</td>
							<th>接班牌号</th>
							<td>
								${exchg.toMat}
							</td>
						</tr>
						<tr>
							<th>交班班次</th>
							<td>
								${exchg.hoShift}
							</td>
							<th>接班班次</th>
							<td>
								${exchg.toShift}
							</td>
						</tr>
						<tr>
							<th>交班班组</th>
							<td>
								${exchg.hoTeam}
							</td>
							<th>接班班组</th>
							<td>
								${exchg.toTeam}
							</td>
						</tr>
						<tr>
							<th>交班时间</th>
							<td>
								<input name="hoTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm" style="width:180px" value="${exchg.hoTime}"/>
							</td>
							<th>接班时间</th>
							<td>
								<input name="toTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm" style="width:180px" value="${exchg.toTime}"/>
							</td>
						</tr>
						<tr>
							<th>交班用户</th>
							<td>
								<input type="text" id="hoUser" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择交班用户',required:true" value="${exchg.hoUser}"/>
								<input type="hidden" name="hoUser"  />
							</td>
							<th>接班用户</th>
							<td>
								<input type="text" id="toUser" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择交班用户'"  value="${exchg.toUser}"/>
								<input type="hidden" name="toUser" />
							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${type==2}">
					<table class="table" style="width: 100%;">
						<tr>
							<th colspan="2" style="font-size:14px;height:30px;text-align: center;">前牌号信息</th>
							
							<th colspan="2" style="font-size:14px;height:30px;text-align: center;">后牌号信息</th>
							<input type="hidden" name="id" value="${exchg.id}"/>
						</tr>
						<tr>
							<th>交班工单</th>
							<td>
								${exchg.hoOrderId}
							</td>
							<th>接班工单</th>
							<td>
								${exchg.toOrderId}
							</td>
						</tr>
						<tr>
							<th>机台</th>
							<td colspan="3">
								${exchg.equipment}
							</td>
						</tr>
						<tr>
							<th>前牌号</th>
							<td>
								${exchg.hoMat}
							</td>
							<th>后牌号</th>
							<td>
								${exchg.toMat}
							</td>
						</tr>
						<tr>
							<th>班次</th>
							<td>
								${exchg.hoShift}
							</td>
							
						</tr>
						<tr>
							<th>班组</th>
							<td>
								${exchg.hoTeam}
							</td>
							
						</tr>
						<tr>
							<th>交班时间</th>
							<td>
								<input name="hoTime" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm" style="width:180px" value="${exchg.hoTime}"/>
							</td>
						</tr>
						<tr>
							<th>换牌用户</th>
							<td>
								<input type="text" id="hoUser" name="readonlySearchbox" class="easyui-searchbox" data-options="prompt: '请选择交班用户',required:true" value="${exchg.hoUser}"/>
								<input type="hidden" name="hoUser"  />
							</td>
						</tr>
					</table>
				</c:if>
		</form>
	</div>
</div>













