<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<script type="text/javascript">
		$.loadComboboxData($("#matProd1"),"MATPROD",false);
		$.loadComboboxData($("#shift1"),"SHIFT",false);
		$.loadComboboxData($("#team1"),"TEAM",false);
		$.loadComboboxData($("#unit"),"UNIT",false);
		$.loadComboboxData($("#eqps"),"ALLEQPS",false);
		
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>工单基本信息</legend>
				<table class="table" style="width: 100%;">
					<tr>
						<th>工单号</th>
						<td>
							<input name="code"  class="easyui-validatebox" style="width:123px"/>
						</td>
						<th>设备</th>
						<td>
							<input id="eqps" name="equipmentId" class="easyui-combobox easyui-validatebox" data-options="panelHeight:150,textField:'name',valueField:'id',editable:false,width:130,required:false"/>
							<!-- <input type="text" id="eqps" name="equipmentId" class="easyui-combobox easyui-validatebox" data-options="textField:'name',valueField:'id',width:130,editable:false"/> -->
						</td>
						<th>牌号</th>
						<td>
							<select id="matProd1" name="matId" data-options="panelHeight:150,width:130,editable:false,required:true"></select>
						</td>
					</tr>
					<tr>
						<th>日期</th>
						<td>
							<input name="date" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:130px"/>
						</td>
						<th>班次</th>
						<td>
							<select id="shift1" name="shiftId" data-options="panelHeight:'auto',width:130,editable:false,required:true"></select>
						</td>
						<th>班组</th>
						<td>
							<select id="team1" name="teamId" data-options="panelHeight:'auto',width:130,editable:false,required:true"></select>
						</td>
					</tr>
					<tr>
						<th>工单类型</th>
						<td>
							<select name="type"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:130">
							    <option value="1">卷烟机工单</option>
								<option value="2">包装机工单</option>						
								<option value="3">封箱机工单</option>						
								<option value="4">成型机工单</option>						
							</select>
						</td>
						<th>生产类型</th>
						<td>
							<select name="prodType"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:130">
							    <option value="1">正式生产</option>
								<option value="2">中试生产</option>						
							</select>
						</td>
						<th>是否为新</th>
						<td>
							<select name="isNew"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:130">
							    <option value="1">是</option>
								<option value="0">否</option>						
							</select>
						</td>
					</tr>
					<tr>
						<th>批次号</th>
						<td>
							<input name="bth"  class="easyui-validatebox" style="width:123px"/>
						</td>
						<th>Bom版本号</th>
						<td>
							<input name="bomVersion"  class="easyui-validatebox" style="width:123px"/>
						</td>
						<th>状态</th>
						<td>
							<select name="sts"
								class="easyui-combobox"
								data-options="panelHeight:'auto',width:130">
								<!-- 1,下发2,运行3,暂停4,完成,5,终止,6结束 -->
							    <option value="1">下发</option>
								<option value="2">运行</option>
								<option value="3">暂停</option>
								<option value="4">完成</option>							
								<option value="5">终止</option>							
								<option value="6">结束</option>							
							</select>
						</td>
					</tr>
					<tr>
						<th>计划产量</th>
						<td>
							<input name="qty"  class="easyui-validatebox" style="width:70px"/>
							<select id="unit" name="unitId" data-options="panelHeight:200,width:50,panelWidth:120,editable:false,required:true"></select>
						</td>
						<th>开始时间</th>
						<td>
							<input name="stim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:130px"/>
						</td>
						<th>结束时间</th>
						<td>
							<input name="etim" type="text" class="easyui-my97" datefmt="yyyy-MM-dd HH:mm:ss" style="width:130px"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>