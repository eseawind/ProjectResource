<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/pms/pub/combobox/comboboxUtil.js" charset="utf-8"></script>
<!-- 增加 -->
<script type="text/javascript">
	$(function() {
		$.loadComboboxData($("#eqp_id"),"ALLEQPS",false);
		$.loadComboboxData($("#shift_id"),"SHIFT",false);
		$.loadComboboxData($("#team_id"),"TEAM",false);
		$("#paul_id").combobox({  
		    url:"${pageContext.request.contextPath}/pms/sys/eqpcategory/queryMdEqpCategory.do",
		    valueField:'id',  
		    textField:'name'  
		}); 
		$("#date").attr("setValue",new Date());

	});
	
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding:5px">
		<form id="form" method="post">
			<fieldset>
				<legend>新增设备停机剔除计划：</legend>
				<br/>
				<table class="table" style="width:97%;">
					<tr>
						<th>设备名称</th>
						<td>
							<select id="eqp_id" name="eqp_id" class="easyui-combobox" data-options="panelHeight:'auto',width:140,panelHeight:150,editable:false,required:true,prompt:'请选择日保养规则'"></select>
						</td>
						<th>剔除类型</th>
						<td>
							<select id="type_id" name="type_id" class="easyui-combobox"  style="width:140px;">   
							    <option value="">请选择</option>   
							    <option value="1">固定停机</option>   
							    <option value="2">不可控停机</option>  
							</select> 
						</td>
					</tr>
					<tr>
						<th>剔除类型</th>
						<td>
							<select id="type_name" name="type_name" class="easyui-combobox"  style="width:140px;">   
							    <option value="">请选择</option>   
							    <option value="就餐">就餐</option>   
							    <option value="试牌">试牌</option>   
							    <option value="保养">保养</option>   
							    <option value="换牌">换牌</option>   
							    <option value="停电">停电</option>   
							    <option value="停水">停水</option> 
							</select> 
						</td>
						<th>班次</th>
						<td>
							<select id="shift_id" name="shift_id" class="easyui-combobox" data-options="width:140,editable:false,required:true,prompt:'请选择班次'"></select>
						</td>
					</tr>
                    <tr>
						<th>班组：</th>
						<td colspan="3">
							<select name="team_id" id="team_id" type="text" class="easyui-validatebox"  data-options="width:140,editable:true,required:true,prompt:'请输入班组名称'"/>
						</td>
					</tr>
					<tr>
						<th>计划日期</th>
						<td>
							<input name="sdate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:140px" data-options="required:true,prompt:'请选择开始时间'"/>
						</td>
						<th>至</th>
						<td>
							<input name="edate" type="text" class="easyui-my97" datefmt="yyyy-MM-dd" style="width:140px" data-options="required:true,prompt:'请选择结束时间'"/>
						</td>
					</tr>
					<tr>
						<th>计划时间</th>
						<td>
							<input id="stime" name="stime" class="easyui-timespinner"  style="width:140px;height:26px;"  required="required" data-options="min:'06:30',showSeconds:true" />
						</td>
						<th>至</th>
						<td>
							<input id="etime" name="etime" class="easyui-timespinner"  style="width:140px;height:26px;"  required="required" data-options="min:'06:30',showSeconds:true" />
						</td>
					</tr>
					<tr>
						<th>备注：</th>
						<td colspan="3">
							<textarea name="remark" id="remark" type="text" class="easyui-validatebox"  data-options="width:640,editable:true,required:true,prompt:'请输入日保养名称'"/>
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>