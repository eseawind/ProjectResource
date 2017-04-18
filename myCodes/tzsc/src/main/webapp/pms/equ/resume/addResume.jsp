<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="padding:5px"><!-- style="overflow: hidden;padding:5px" 滚动条隐藏-->
		<form id="form" method="post">
			<fieldset>
			<c:if test="${eqmResumeBean == null }">
			<legend>设备履历新增</legend>
			</c:if>
			<c:if test="${eqmResumeBean != null }">
			<legend>设备履历修改</legend>
			</c:if>
				<table class="table" style="width: 100%;">
					<tr>					
						<th nowrap="nowrap">设备名称</th>
						<td><input id="equipmentName"  value="${eqmResumeBean.mdEquName }" style="width:140px" class="easyui-searchbox"  data-options="required:true"/>  
							<input id="equipment" name="mdEquType" type="hidden" value="${eqmResumeBean.mdEquId }"/> 
							<input id="addUserId" name="addUserId" type="hidden" value="${eqmResumeBean.addUserId }"/> 
						</td>
						<th>设备出厂日期</th>
						<td><input name="id" type="hidden" value="${eqmResumeBean.id }"/> 
							<input name="manufactureDate" type="text" class="easyui-datetimebox" style="width:140px" value="${eqmResumeBean.manufactureDate }"/>
						</td>
						<th nowrap="nowrap">保养类型</th>
						<td>
							<select name="maintenanceType" style="width:140px" class="easyui-combobox fselect" data-options="panelHeight:'auto'" value="${eqmResumeBean.maintenanceType }">
								<option value=""></option>
								<option value="10" <c:if test="${eqmResumeBean.maintenanceType=='10' }">selected="selected"</c:if>>轮保</option>
								<option value="20" <c:if test="${eqmResumeBean.maintenanceType=='20' }">selected="selected"</c:if>>润滑</option>
								<option value="30" <c:if test="${eqmResumeBean.maintenanceType=='30' }">selected="selected"</c:if>>停产检修</option>
								<option value="40" <c:if test="${eqmResumeBean.maintenanceType=='40' }">selected="selected"</c:if>>其他</option>
							</select>
						</td>
						<th>维修类型</th>
						<td>
							<select name="maintainType" style="width:140px" class="easyui-combobox fselect" data-options="panelHeight:'auto'" value="${eqmResumeBean.maintainType }">
								<option value=""></option>
								<option value="10" <c:if test="${eqmResumeBean.maintainType=='10' }">selected="selected"</c:if>>电器维修</option>
								<option value="20" <c:if test="${eqmResumeBean.maintainType=='20' }">selected="selected"</c:if>>机械维修</option>
							</select>						
						</td>
					</tr>
					<tr>
						<th>维修人</th>
						<td>
							<input maxlength="50"  type="text" name="maintainPerson" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.maintainPerson }"/>
						</td>
						<th>维修日期</th>
						<td>
							<input name="maintainDate" type="text" class="easyui-datetimebox" style="width:140px" value="${eqmResumeBean.maintainDate }"/> 
						</td>
						<th>保养人</th>
						<td>
							<input maxlength="50"  type="text" name="maintenancePerson" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.maintenancePerson }"/>
						</td>
						<th>保养日期</th>
						<td>
							<input name="maintenanceDate" type="text" class="easyui-datetimebox" style="width:140px" value="${eqmResumeBean.maintenanceDate }"/> 
						</td>
						
					</tr>
					<tr>
						
					</tr>
					<tr>
						
						<th>保养内容</th>
						<td colspan="3">
							<textarea maxlength="3000"  style="width:460px;height:30px;resize:none" name="maintenanceContent">${eqmResumeBean.maintainContent }</textarea>
						</td>
						<th>维修内容</th>
						<td colspan="3">
							<textarea maxlength="3000"  style="width:460px;height:30px;resize:none" name="maintainContent">${eqmResumeBean.maintainContent }</textarea>
						</td>
					</tr>
					<tr>
						<th>制造厂名</th>
						<td>
							<input maxlength="100" type="text" name="factoryName" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.factoryName }"/>
						</td>
						<th>承建单位</th>
						<td>
							<input maxlength="100" type="text" name="company" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.company }"/>
						</td>
						<th>建造年份</th>
						<td>
							<input maxlength="50" name="buildDate" type="text" class="easyui-datetimebox" style="width:140px" value="${eqmResumeBean.buildDate }"/> 
						</td>
						<th>验收日期</th>
						<td>
							<input name="checkDate" type="text" class="easyui-datetimebox" style="width:140px" value="${eqmResumeBean.checkDate }"/>
						</td>
					</tr>
					<tr>
						<th>设备购置日期</th>
						<td>
							<input name="purchaseDate" type="text" class="easyui-datetimebox"style="width:140px"  value="${eqmResumeBean.purchaseDate }"/>
						</td>
						<th>开始使用日期</th>
						<td>
							<input name="usingDate" type="text" class="easyui-datetimebox" style="width:140px" value="${eqmResumeBean.usingDate }"/>
						</td>
						<th>交接凭证编号</th>
						<td>
							<input maxlength="50"  type="text" name="voucherCode" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.voucherCode }"/> 
						</td>
						<th>调入来源</th>
						<td>
							<input maxlength="50"  type="text" name="callSource" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.callSource }"/>
						</td>
					</tr>
					<tr>
						<th>资金来源</th>
						<td>
							<input maxlength="50"  type="text" name="moneySource" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.moneySource }"/>
						</td>
						<th>调入时已使用年限</th>
						<td>
							<input maxlength="50"  type="text" name="hasUsingYear" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.hasUsingYear }"/> 
						</td>
						<th>调入时已提折旧</th>
						<td>
							<input maxlength="50"  type="text" name="hasDepr" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.hasDepr }"/>
						</td>
						<th>牌号、型号、规格</th>
						<td>
							<input maxlength="50"  type="text" name="resumeModel" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.resumeModel }"/>
						</td>
					</tr>
					
					<tr>
						<th>类     别</th>
						<td>
							<input maxlength="50"  type="text" name="resumeType"  style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.resumeType }"/> 
						</td>
						<th>名     称</th>
						<td>
							<input maxlength="50"  type="text" name="resumeName" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.resumeName }"/>
						</td>
						<th>财产编号</th>
						<td>
							<input maxlength="50"  type="text" name="propertyCode" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.propertyCode }"/>
						</td>
						<th>技术资料编号</th>
						<td>
							<input maxlength="50"  type="text" name="skillDataCode" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.skillDataCode }"/> 
						</td>
					</tr>	
					
					<tr>
						<th>原价</th>
						<td>
							<input type="text" name="costPrice" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.costPrice }"/>
						</td>
						<th>其中：安装成本</th>
						<td>
							<input type="text" name="installPrice" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.installPrice }"/>
						</td>
						<th>预计使用年限</th>
						<td>
							<input type="text" name="predUsingYear" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.predUsingYear }"/> 
						</td>
						<th>预计残值</th>
						<td>
							<input type="text" name="predResidual" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.predResidual }"/>
						</td>
					</tr>
					<tr>
						<th>预计清理费用</th>
						<td>
							<input type="text" name="predClearMoney" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.predClearMoney }"/>
						</td>
						<th>年折旧率</th>
						<td>
							<input type="text" name="yearDeprRate"  style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.yearDeprRate }"/> 
						</td>
						<th>年大修理提存率</th>
						<td>
							<input type="text" name="yearFixRate"  style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.yearFixRate }"/>
						</td>
						<th>月折旧额</th>
						<td>
							<input type="text" name="monthDeprMoney" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.monthDeprMoney }"/>
						</td>
					</tr>	
					
					<tr>
						<th>月大修理提存额</th>
						<td>
							<input type="text" name="monthFixMoney" style="width:140px" class="easyui-validatebox" data-options="required:false" value="${eqmResumeBean.monthFixMoney }"/> 
						</td>
						<th></th>
						<td></td>
						<th></th>
						<td></td>
						<th></th>
						<td></td>
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