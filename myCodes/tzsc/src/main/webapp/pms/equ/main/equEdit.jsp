<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 设备主数据新增 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#workShopName2"),"WORKSHOP",true);
		$.loadComboboxData($("#categoryId2"),"EQPTYPE",true);
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>设备主数据基本信息</legend>
				<table class="table" style="width: 100%;">
					
					<tr>
						<th>设备编号</th>
						<td><input name="id" type="hidden" value="${equBean.id}"/>
							<input type="text" name="equipmentCode" class="easyui-validatebox" data-options="required:true" value="${equBean.equipmentCode }" />
						</td>
						<th>设备名称</th>
						<td>
							<input type="text" name="equipmentName" class="easyui-validatebox" data-options="required:true" value="${equBean.equipmentName }"/>
						</td>
					</tr>
					<tr>
						<th>设备描述</th>
						<td>
							<input type="text" name="equipmentDesc" class="easyui-validatebox" data-options="required:true" value="${equBean.equipmentDesc }"/>
						</td>
						<th>额定生产能力</th>
						<td>
							<input type="text" name="vouchProduction" class="easyui-validatebox" precision="2" data-options="required:true" value="${equBean.vouchProduction }"/>
						</td>
					</tr>
					<tr>
						<th>机型</th>
						<td>
							<input  id="categoryId2" name="mdEqpType.id" data-options="panelHeight:'auto',width:100,editable:false" value="${equBean.eqpTypeId }"/>	
						</td>
						<th>车间</th>
						<td>
							<input  id="workShopName2" name="mdWorkshop.id" data-options="panelHeight:'auto',width:100,editable:false" value="${equBean.workShopId }"/>
						</td>
					</tr>
					
					<tr>
						<th>工序段</th>
						<td>
							<input type="text" name="workCenter" class="easyui-validatebox" data-options="required:false" value="${equBean.workCenter }"/>
						</td>
						<th>设备位置</th>
						<td>
							<input type="text" name="equipmentPosition" class="easyui-validatebox" data-options="required:false" value="${equBean.equipmentPosition }"/>
						</td>
					</tr>
					
					<tr>
						<th>额定车速</th>
						<td>
							<input type="text" name="ratedSpeed" class="easyui-numberbox" precision="2" data-options="required:true,prompt:'请输入数字'" value="${equBean.ratedSpeed }"/>
						</td>
						<th>额定车速单位</th>
						<td>
							<input type="text" name="rateSpeedUnit" class="easyui-validatebox" data-options="required:true" value="${equBean.rateSpeedUnit }"/>
						</td>
					</tr>
					<tr>
						<th>台时产量</th>
						<td>
							<input type="text" name="yieId" class="easyui-numberbox" precision="2" data-options="required:true,prompt:'请输入数字'" value="${equBean.yieId }"/>
						</td>
						<th>台时产量单位</th>
						<td>
							<input type="text" name="yieldUnit" class="easyui-validatebox" data-options="required:true" value="${equBean.yieldUnit }"/>
						</td>
					</tr>
					<tr>
						<th>是否启用</th>
						<td>
							<select name="enabled" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="1">启用</option>
								<option value="0">禁用</option>
							</select>
						</td>
						<th>固定资产编号</th>
						<td>
							<input type="text" name="fixedAssetNum" class="easyui-validatebox" data-options="required:false" value="${equBean.fixedAssetNum }"/>
						</td>
					</tr>
					<tr>
						<th>出厂编号</th>
						<td>
							<input type="text" name="manufacturingNum" class="easyui-validatebox" data-options="required:false" value="${equBean.manufacturingNum }"/>
						</td>
						<th>批准文号</th>
						<td>
							<input type="text" name="approvalNum" class="easyui-validatebox" data-options="required:false" value="${equBean.approvalNum }"/>
						</td>
					</tr>
					<tr>
						<th>准运证编号</th>
						<td>
							<input type="text" name="navicertNum" class="easyui-validatebox" data-options="required:false" value="${equBean.navicertNum }"/>
						</td>
						<th>是否已入固</th>
						<td>
							<select name="fixedAssetFlag" class="easyui-combobox fselect" data-options="panelHeight:'auto'">
								<option value="0">已入固</option>
								<option value="1">未入固</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>使用部门</th>
						<td>
							<input type="text" name="usingDepartment" class="easyui-validatebox" data-options="required:false" value="${equBean.usingDepartment }"/>
						</td>
						<th>MESCODE</th>
						<td>
							<input type="text" name="mesEqpCode" class="easyui-validatebox" data-options="required:true" value="${equBean.mesEqpCode }"/>
						</td>
					</tr>				
				</table>
			</fieldset>
		</form>
	</div>
</div>